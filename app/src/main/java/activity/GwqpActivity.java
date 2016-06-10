package activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.fgwoa.fgwmobile.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Utils.MyAdapter;

/**
 * Created by wangxiao on 16/6/7.
 */
public class GwqpActivity extends AppCompatActivity {
    private ListView listView;


//    Intent intent = getIntent();
//    final List<Gw> gwList1 = (List<Gw>) intent.getSerializableExtra("gwList");
//    final String ret = intent.getStringExtra("ret");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gwqianpi_main);
        listView = (ListView) findViewById(R.id.gw_list);
        List<Map<String, Object>> list = getData();
        listView.setAdapter(new MyAdapter(this, list));

        initTitle();

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
