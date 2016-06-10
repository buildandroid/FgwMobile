package gwQuery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fgwoa.fgwmobile.R;
import com.example.fgwoa.fgwmobile.RestApi;
import com.example.fgwoa.fgwmobile.RetrofitFactory;

import java.io.Serializable;
import java.util.List;

import Utils.HttpDownloader;
import bean.Gw;
import bean.GwAttachs;
import config.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wangxiao on 16/6/4.
 */
public class QueryListActivity extends Activity{
//    private JSONArray mGWList;
    private Button lookform;
    private Button download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aaa);



        lookform = (Button) findViewById(R.id.lookform);
        download = (Button) findViewById(R.id.download);

        Intent intent = getIntent();
        List<Gw> gwList1 = (List<Gw>) intent.getSerializableExtra("gwList");
        final String ret = intent.getStringExtra("ret");

//        for (int i = 0; i < gwList1.size(); i++){
            Gw gw = gwList1.get(5);
            final String barcode = gw.getBARCODE();

            lookform.setOnClickListener(new View.OnClickListener() {
                String token = ret;
                @Override
                public void onClick(View v) {
                    Call<Result> call = RetrofitFactory.getRetorfit().create(RestApi.class)
                            .gwView("http://210.74.194.125:8082/gw-web-manager/gws/gwView", token, barcode);
                    call.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            if (response.isSuccessful()){
                                List gwAttachs = response.body().gwAttachs;
//                                List gwForm = response.body().gwForm;
                                List gwSignInfo = response.body().gwSignInfo;

                                Intent intent1 = new Intent();
                                intent1.setClass(QueryListActivity.this, aaaActivity.class);
                                intent1.putExtra("gwAttachs", (Serializable) gwAttachs);
//                                intent1.putExtra("gwForm", (Serializable) gwForm);
                                intent1.putExtra("gwSignInfo", (Serializable) gwSignInfo);
                                intent1.putExtra("ret", ret);
                                startActivity(intent1);
                            }
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {

                        }
                    });
                }
            });

            download.setOnClickListener(new View.OnClickListener() {
                String token = ret;
                @Override
                public void onClick(View v) {
                    Call<Result> call = RetrofitFactory.getRetorfit().create(RestApi.class)
                            .gwView("http://210.74.194.125:8082/gw-web-manager/gws/gwView", token, barcode);
                    call.enqueue(new Callback<Result>() {

                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            if (response.isSuccessful()){
                                List<GwAttachs> gwAttachs = response.body().gwAttachs;
//                                List gwForm = response.body().gwForm;
//                                List gwSignInfo = response.body().gwSignInfo;

                                for (int j = 0; j < gwAttachs.size(); j++){
                                    String fileId = gwAttachs.get(j).getROWGUID();
                                    Call<Result> call1 = RetrofitFactory.getRetorfit().create(RestApi.class)
                                            .gwAttachDown("http://210.74.194.125:8082/gw-web-manager/gws/gwView", token, fileId);
                                        call1.enqueue(new Callback<Result>() {

                                            @Override
                                            public void onResponse(Call<Result> call, Response<Result> response) {
                                                if (response.isSuccessful()){
                                                    HttpDownloader httpDownloader = new HttpDownloader();
                                                    //所要下载文件的url路径
                                                    String lrc = httpDownloader.download(response.body().url.toString());
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Result> call, Throwable t) {

                                            }
                                        });

                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {

                        }
                    });
                }
            });
    }
}
