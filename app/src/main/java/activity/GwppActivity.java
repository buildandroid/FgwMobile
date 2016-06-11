package activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fgwoa.fgwmobile.R;
import com.example.fgwoa.fgwmobile.RestApi;
import com.example.fgwoa.fgwmobile.RetrofitFactory;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Utils.MapUtils;
import bean.GwAttachs;
import bean.GwForm;
import config.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jikai on 6/10/16.
 */
public class GwppActivity extends AppCompatActivity {
    private ViewGroup container;
    private ViewGroup listContainer;
    private SharedPreferences sharedPreferences;
    private View tplView;
    private String category;
    private String barcode;
    private TextView listTitle;
    private ListView listView;
    private LayoutInflater layoutInflater;
    private final static int REQUEST_SIGN = 1000;
    private Result mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setContentView(R.layout.activity_gwpp);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Intent intent = getIntent();
        barcode = intent.getStringExtra("barcode");
        category = intent.getStringExtra("category");

        init(category);
        fetchData(barcode);

    }

    private void init(String category) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
        listContainer = (ViewGroup) findViewById(R.id.list_container);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) listContainer.getLayoutParams();
        params.setMarginStart(width / 2);
        listContainer.setLayoutParams(params);
        container = (ViewGroup) findViewById(R.id.container);
        LayoutInflater inflater = getLayoutInflater();

        listTitle = (TextView)findViewById(R.id.title);
        listView = (ListView)findViewById(R.id.list);
        layoutInflater = getLayoutInflater();

        switch (category) {
            case "fw":
                tplView = inflater.inflate(R.layout.tpl_fw, null);
                break;
            case "sw":
                tplView = inflater.inflate(R.layout.tpl_sw, null);
                break;
            case "qb":
                tplView = inflater.inflate(R.layout.tpl_qb, null);
                break;
            case "lhfw":
                tplView = inflater.inflate(R.layout.tpl_lhfw, null);
                break;
        }
        if (tplView != null) {
            container.addView(tplView);
        }

        findViewById(R.id.btn_pishi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SignActivity.class);
                intent.putExtra("barcode", barcode);
                GwppActivity.this.startActivityForResult(intent, REQUEST_SIGN);
            }
        });
        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listContainer.getVisibility() == View.GONE) {
                    listContainer.setVisibility(View.VISIBLE);
                } else {
                    listContainer.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void fetchData(String barcode) {
        String token = sharedPreferences.getString("token", "");
        if (TextUtils.isEmpty(token)) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        String serverUrl = getString(R.string.SERVER_URL);
        Call<Result> call = RetrofitFactory.getRetorfit().create(RestApi.class).gwView(serverUrl + "gwView",
                token, barcode);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    mResult = response.body();
                    listContainer.setVisibility(View.VISIBLE);
                    fillPinshiList(mResult);
                    try {
                        updateText(mResult.gwForm);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }

    private class SignInfoItem {
        String content;
        String date;
        String imageUrl;
        String photo;
        String people;
        boolean onlyContent;
    }

    private class ViewHolder {
        ImageView photo;
        TextView content;
        TextView time;
        View button;
        View timeContainer;
    }

    private void fillPinshiList(Result result) {
        if (result == null) return;

        final List<SignInfoItem> list = new ArrayList<>();
        SignInfoItem item = new SignInfoItem();
        item.onlyContent = true;
        switch (category) {
            case "fw":
            case "lhfw":
                item.content = result.gwForm.LEADERDIRECTION;
                break;
            case "sw":
                item.content = result.gwForm.PISHI;
                break;
            case "qb":
                item.content = result.gwForm.LEADER;
                break;
        }
        list.add(item);
        String url = getString(R.string.SERVER_URL);
        String token = sharedPreferences.getString("token", "");
        for (int i = 0; i < result.gwSignInfo.size(); i++) {
            item = new SignInfoItem();
            item.content = result.gwSignInfo.get(i).getSQLEADER();
            item.date = result.gwSignInfo.get(i).getSQTIME();
            item.people = result.gwSignInfo.get(i).getSQPEOPLE();
            item.imageUrl = url + "gwSignDown?token=" + token + "&fileId=" + result.gwSignInfo.get(i).getURL();
            item.onlyContent = false;
            item.photo = null;
            list.add(item);
        }
        listView.setOnItemClickListener(null);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public SignInfoItem getItem(int position) {
                return list.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final SignInfoItem item = getItem(position);
                if (convertView == null) {
                    convertView = layoutInflater.inflate(R.layout.item_pishiqingkuang, null);
                    ViewHolder holder = new ViewHolder();
                    holder.content = (TextView)convertView.findViewById(R.id.content);
                    holder.photo = (ImageView)convertView.findViewById(R.id.img_lingdao);
                    holder.time = (TextView)convertView.findViewById(R.id.txt_pishishijian);
                    holder.timeContainer = convertView.findViewById(R.id.pishicontainer);
                    holder.button = convertView.findViewById(R.id.btn_yuanbiji);
                    convertView.setTag(holder);
                }
                ViewHolder holder = (ViewHolder)convertView.getTag();
                if (item.onlyContent) {
                    holder.photo.setVisibility(View.INVISIBLE);
                    holder.timeContainer.setVisibility(View.GONE);
                } else {
                    holder.photo.setVisibility(View.VISIBLE);
                    holder.timeContainer.setVisibility(View.VISIBLE);
                }
                holder.content.setText(item.content);
                holder.time.setText(item.date);
                Glide.with(GwppActivity.this).load(Uri.parse("file:///android_asset/lingdao_photo/" +
                        MapUtils.getLingDaoPhoto(item.people))).into(holder.photo);
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog = new Dialog(GwppActivity.this, R.style.roundDialog);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setContentView(R.layout.dialog_showbiji);
                        TextView lingdao = (TextView)dialog.findViewById(R.id.txtLingdao);
                        ImageView image = (ImageView)dialog.findViewById(R.id.imgSign);
                        Log.d("url", item.imageUrl);
                        Glide.with(GwppActivity.this).load(Uri.parse(item.imageUrl)).into(image);
                        dialog.findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        lingdao.setText(item.people);
                        dialog.show();
                    }
                });
                return convertView;
            }
        });
        if (category.equals("sw")) {
            listTitle.setText(result.gwForm.WENJIANBIAOTI);
        }else if(category.equals("fw") || category.equals("lhfw")) {
            Resources res = getResources();
            String fwwh = String.format(res.getString(R.string.fw_wenhao), result.gwForm.getNUMBERCOUNT());
            listTitle.setText(fwwh);
        }else if(category.equals("qb")){
            Resources res = getResources();
            String fwwh = String.format(res.getString(R.string.fw_wenhao), result.gwForm.BANWENHAO);
            listTitle.setText(fwwh);
        } else {
            listTitle.setText(result.gwForm.DOCTITLE);
        }
        try {
            updateText(result.gwForm);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private class FujianItemInfo {
        String attchUrl;
        String attchName;
    }

    private void fillFujianList(Result result) {
        if (result == null) return;
        final List<FujianItemInfo> list = new ArrayList<>();
        String url = getString(R.string.SERVER_URL);
        String token = sharedPreferences.getString("token", "");

        listTitle.setText("附件列表");
        if (result.gwAttachs != null) {
            for (int i = 0; i < result.gwAttachs.size(); i++) {
                FujianItemInfo one = new FujianItemInfo();
                one.attchName = result.gwAttachs.get(i).getTITLENAME();
                one.attchUrl = url + "gwAttachDown?token=" + token + "&fileId=" + result.gwAttachs.get(i).getROWGUID();
                list.add(one);
            }
        }
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public FujianItemInfo getItem(int position) {
                return list.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final FujianItemInfo item = getItem(position);
                if (convertView == null) {
                    convertView = layoutInflater.inflate(R.layout.item_fujian, null);
                    ViewHolder holder = new ViewHolder();
                    holder.content = (TextView)convertView.findViewById(R.id.content);
                    convertView.setTag(holder);
                }
                ViewHolder holder = (ViewHolder)convertView.getTag();
                holder.content.setText(item.attchName);
                return convertView;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FujianItemInfo item = list.get(position);
                Intent intent = new Intent(GwppActivity.this, PdfViewActivity.class);
                intent.putExtra("url", item.attchUrl);
                GwppActivity.this.startActivity(intent);
            }
        });

    }

    private void updateText(GwForm form) throws IllegalAccessException {
        Resources resource = getResources();
        String date = form.UPDATEDATE;
        if (date != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date d = simpleDateFormat.parse(date);
                form.year = d.getYear() + 1900 + "";
                form.month = d.getMonth() + 1 + "";
                form.day = d.getDate() + "";
            } catch (ParseException e) {
            }
        }
        Field[] fileds = form.getClass().getDeclaredFields();
        for (int i = 0; i < fileds.length; i++) {
            fileds[i].setAccessible(true);
            Object value = fileds[i].get(form);
            if (value == null) {
                continue;
            }
            int id = resource.getIdentifier(fileds[i].getName(), "id", getPackageName());
            TextView textView = (TextView)findViewById(id);
            if (textView != null) {
                textView.setText((String)value);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_gwpp, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_pishiqingkuang:
                if (mResult == null)
                    break;
                if (listContainer.getVisibility() == View.GONE) {
                    listContainer.setVisibility(View.VISIBLE);
                } else {
                    listContainer.setVisibility(View.GONE);
                }
                fillPinshiList(mResult);
                break;
            case R.id.menu_fujian:
                if (mResult == null)
                    break;
                if (listContainer.getVisibility() == View.GONE) {
                    listContainer.setVisibility(View.VISIBLE);
                } else {
                    listContainer.setVisibility(View.GONE);
                }
                fillFujianList(mResult);
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
