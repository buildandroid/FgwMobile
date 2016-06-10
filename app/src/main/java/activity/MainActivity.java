package activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.fgwoa.fgwmobile.R;
import com.example.fgwoa.fgwmobile.RestApi;
import com.example.fgwoa.fgwmobile.RetrofitFactory;

import config.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wangxiao on 16/6/8.
 */
public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String token = intent.getStringExtra("ret");

        Intent intent1 = new Intent(this, GwppActivity.class);
        intent1.putExtra("barcode", "116009070");
        intent1.putExtra("category", "fw");
        startActivity(intent1);

//        String serverUrl = getString(R.string.SERVER_URL);
//        Call<Result> call = RetrofitFactory.getRetorfit().create(RestApi.class).gwQuery(serverUrl + "gwQuery",
//                token, "all", "2", "1", "20", "");
//        call.enqueue(new Callback<Result>() {
//            @Override
//            public void onResponse(Call<Result> call, Response<Result> response) {
//                Log.d("MainActivity", response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Call<Result> call, Throwable t) {
//
//            }
//        });
    }
}
