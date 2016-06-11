package activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.fgwoa.fgwmobile.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxiao on 16/6/7.
 */
public class GwqpActivity extends AppCompatActivity {
    private ListView mGwqpListView;


//    Intent intent = getIntent();
//    final List<Gw> gwList1 = (List<Gw>) intent.getSerializableExtra("gwList");
//    final String ret = intent.getStringExtra("ret");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gwqp);
        mGwqpListView = (ListView) findViewById(R.id.gwqp_list);
        initGwqpListView();
//
//        initTitle();

    }

    private void initGwqpListView(){
        mGwqpListView.setAdapter(new GwListAdapter());
    }

    private class GwListAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int position) {
            return "";
        }

        @Override
        public long getItemId(int position) {
            return 1;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_gwqp_list, parent, false);
            } else {
                view = convertView;
            }
            return view;
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

    public List<Map<String, Object>> getData(){
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < 3; i++){
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("image", R.drawable.logo);
            map.put("TITLE", "市人力社保局、市发展改革委关于印发北京市“十三五”时期 人力资源和社会保障发展规划的通知" + i);
            map.put("content1", "市发改委");
            map.put("content2", "张某某");
            map.put("content3", "2016年4月22日");
            map.put("content4", "收文");
            list.add(map);
        }
        return list;
    }



}
