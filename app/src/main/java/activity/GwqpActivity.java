package activity;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fgwoa.fgwmobile.R;
import com.example.fgwoa.fgwmobile.RestApi;
import com.example.fgwoa.fgwmobile.RetrofitFactory;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Utils.SharedObject;
import bean.Gw;
import config.Result;
import fragment.DaiPiYiPiSwitcherFragment;
import fragment.GwqpTabFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wangxiao on 16/6/7.
 */
public class GwqpActivity extends AppCompatActivity implements GwqpTabFragment.OnTabSelectedListener,
        DaiPiYiPiSwitcherFragment.OnSwitchChangeListener{
    private ListView mGwqpListView;
    private String mCategory;
    private static final String[] CATEGORY = {"all", "fw", "sw", "qb"};
    private String mStatus;
    private static final String[] STATUS = {"1", "2"};
    private int mCurrentPageNumber;
    private int mMaxPageNumber;
    private static final int PAGE_SIZE = 10;
    private List<Gw> mGwList;

    private static final ArrayMap<String, String> GW_TYPE = new ArrayMap<>();
    static{
        GW_TYPE.put("fw", "发文");
        GW_TYPE.put("lhfw", "联合发文");
        GW_TYPE.put("sw", "收文");
        GW_TYPE.put("qb", "签报");
    }



//    Intent intent = getIntent();
//    final List<Gw> gwList1 = (List<Gw>) intent.getSerializableExtra("gwList");
//    final String ret = intent.getStringExtra("ret");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParameter();
        setContentView(R.layout.activity_gwqp);
        mGwqpListView = (ListView) findViewById(R.id.gwqp_list);
        initGwqpListView();
        remoteFetchGongWenAsync(false);
//
//        initTitle();

    }

    private void initParameter(){
        mCategory = CATEGORY[0];
        mStatus = STATUS[0];
        mCurrentPageNumber = 1;
    }

    private void initGwqpListView(){
        mGwqpListView.setAdapter(new GwListAdapter());
        mGwqpListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int mScrollState;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                mScrollState = scrollState;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastVisibleItem = firstVisibleItem + visibleItemCount - 1; //可视的最后一个列表项的索引
                if (mScrollState != AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && lastVisibleItem == totalItemCount - 1) {
                    remoteFetchGongWenAsync(true);
                }
            }
        });
    }

    private class GwListAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return mGwList != null ? mGwList.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return mGwList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder;
            if (convertView == null) {
                view = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_gwqp_list, parent, false);
                holder = new ViewHolder();
                holder.gongWenBiaoti = (TextView)view.findViewById(R.id.gongwen_biaoti);
                holder.laiWenDanwei = (TextView)view.findViewById(R.id.laiwen_danwei);
                holder.daiPi = (TextView)view.findViewById(R.id.daipi);
                holder.laiWenRiqi = (TextView)view.findViewById(R.id.laiwen_riqi);
                holder.leiXing = (TextView)view.findViewById(R.id.leixi);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder)view.getTag();
            }
            Gw gw = (Gw)getItem(position);
            holder.gongWenBiaoti.setText(gw.getTITLE());
            Resources res = getResources();
            holder.laiWenDanwei.setText(String.format(res.getString(R.string.list_text_1), gw.getUNIT1()));
            holder.daiPi.setText(String.format(res.getString(R.string.list_text_2), gw.getSQLEADER()));
            holder.laiWenRiqi.setText(String.format(res.getString(R.string.list_text_3), gw.getDATE1()));
            holder.leiXing.setText(String.format(res.getString(R.string.list_text_4), GW_TYPE.get(gw.getTYPE())));

            return view;
        }

        class ViewHolder{
            TextView gongWenBiaoti;
            TextView laiWenDanwei;
            TextView daiPi;
            TextView laiWenRiqi;
            TextView leiXing;
        }
    }

    private void initTitle(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.gongwenqianpi);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onAttachFragment(Fragment fragment) {
        if(fragment.getTag().equals("tag")){
            Bundle bundle = new Bundle();
            bundle.putInt(GwqpTabFragment.CATEGORY, 0);
            fragment.setArguments(bundle);
        }else if(fragment.getTag().equals("daipiyipi")){
            Bundle bundle = new Bundle();
            bundle.putInt(DaiPiYiPiSwitcherFragment.SWITCH, 0);
            fragment.setArguments(bundle);
        }
    }

    @Override
    public void onTabSelected(int index) {
        mCurrentPageNumber = 1;
        mCategory = CATEGORY[index];
        remoteFetchGongWenAsync(false);
    }

    @Override
    public void onSwitch(int index) {
        Log.d("switch", "index " + index);
        mCurrentPageNumber = 1;
        mStatus = STATUS[index];
        remoteFetchGongWenAsync(false);
    }

    private void remoteFetchGongWenAsync(final boolean appendMode) {
        String token = SharedObject.getToken(this);
        String category = mCategory;
        String status = mStatus;
        String pageNum = String.valueOf(mCurrentPageNumber);
        String pageSize = String.valueOf(PAGE_SIZE);
        String kwd = "";
        Call<Result> call = RetrofitFactory.getRetorfit().create(RestApi.class).gwQuery(getString(R.string.SERVER_URL) + "gwQuery", token, category, status, pageNum, pageSize, kwd);
        call.enqueue(new Callback<Result>() {

            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    if(appendMode){
                        mGwList.addAll(response.body().gwList);
                    }else {
                        mGwList = response.body().gwList;
                    }
                    Log.d("gwList", "size: " + mGwList.size());
                    ((BaseAdapter)mGwqpListView.getAdapter()).notifyDataSetChanged();
                    mCurrentPageNumber++;
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }
}
