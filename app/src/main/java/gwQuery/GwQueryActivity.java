package gwQuery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.example.fgwoa.fgwmobile.R;
import com.example.fgwoa.fgwmobile.RestApi;
import com.example.fgwoa.fgwmobile.RetrofitFactory;

import java.io.Serializable;
import java.util.List;

import activity.GwqpActivity;
import config.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wangxiao on 16/6/4.
 */
public class GwQueryActivity extends AppCompatActivity {

    private RadioGroup gw_category;
    private RadioButton gw_receive;
    private RadioButton gw_send;
    private RadioButton gw_sign;
    private RadioButton gw_all;
    private RadioGroup gw_status;
    private RadioButton gw_pend;
    private RadioButton gw_approve;
    private EditText gw_pageNum;
    private EditText gw_pageSize;
    private EditText gw_keyword;
    private Button gw_check;

    private String category = null;
    private String status = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gwquery_main);

        Intent intent = getIntent();
        final String ret = intent.getStringExtra("ret");

        gw_category = (RadioGroup) findViewById(R.id.gw_category);
        gw_receive = (RadioButton) findViewById(R.id.gw_receive);
        gw_send = (RadioButton) findViewById(R.id.gw_send);
        gw_sign = (RadioButton) findViewById(R.id.gw_sign);
        gw_all = (RadioButton) findViewById(R.id.gw_all);
        gw_status = (RadioGroup) findViewById(R.id.gw_status);
        gw_pend = (RadioButton) findViewById(R.id.gw_pend);
        gw_approve = (RadioButton) findViewById(R.id.gw_approve);
        gw_pageNum = (EditText) findViewById(R.id.gw_pageNum);
        gw_pageSize = (EditText) findViewById(R.id.gw_pageSize);
        gw_keyword = (EditText) findViewById(R.id.gw_keyword);
        gw_check = (Button) findViewById(R.id.gw_check);

        gw_category.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == gw_receive.getId()){
                    category = "sw";
                }
                else if (checkedId == gw_send.getId()){
                    category = "fw";
                }
                else if (checkedId == gw_sign.getId()){
                    category = "qb";
                }
                else if (checkedId == gw_all.getId()){
                    category = "all";
                }
            }
        });

        gw_status.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == gw_pend.getId()){
                    status = "1";
                }
                else if (checkedId == gw_approve.getId()){
                    status = "2";
                }
            }
        });

        gw_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = gw_pageNum.getText().toString().trim();
                String size = gw_pageSize.getText().toString().trim();
                String keyword = gw_keyword.getText().toString().trim();

//                Params params = new Params();
//                params.token = ret;
//                params.category = category;
//                params.status = status;
//                params.pageNum = num;
//                params.pageSize = size;
//                params.keyword = keyword;
                String token = ret;
                String pageNum = num;
                String pageSize = size;
                String kwd = keyword;

                Call<Result> call = RetrofitFactory.getRetorfit().create(RestApi.class).gwQuery("http://210.74.194.125:8082/gw-web-manager/gws/gwQuery", token, category, status, pageNum, pageSize, kwd);
                call.enqueue(new Callback<Result>(){

                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if (response.isSuccessful()){
                            List gwList = response.body().gwList;

                            Intent intent1 = new Intent();
                            intent1.setClass(GwQueryActivity.this, GwqpActivity.class);
                            intent1.putExtra("gwList", (Serializable) gwList);
                            intent1.putExtra("ret", ret);
                            startActivity(intent1);
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        t.printStackTrace();

                    }
                });
            }
        });
    }

}
