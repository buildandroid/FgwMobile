package activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fgwoa.fgwmobile.R;
import com.example.fgwoa.fgwmobile.RestApi;
import com.example.fgwoa.fgwmobile.RetrofitFactory;

import Utils.MD5EncodeUtils;
import config.Params;
import config.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wangxiao on 16/6/8.
 */
public class LoginActivity extends AppCompatActivity {
    private long exitTime = 0;
    private EditText userid = null;
    private EditText password = null;
    private Button user_login_button = null;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        userid = (EditText) findViewById(R.id.userid);
        password = (EditText) findViewById(R.id.password);
        user_login_button = (Button) findViewById(R.id.user_login_button);

        user_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = userid.getText().toString().trim();
                String pswd = password.getText().toString().trim();
                // TODO 做uid pswd 合法输入校验
                Params params = new Params();
                String str = uid + "|" + MD5EncodeUtils.MD5Encode(pswd.getBytes());
                String authToken = Base64.encodeToString(str.getBytes(), Base64.NO_WRAP);

                Call<Result> call = RetrofitFactory.getRetorfit().create(RestApi.class).sysLogin("http://210.74.194.125:8082/gw-web-manager/gws/sysLogin", authToken);
                call.enqueue(new Callback<Result>() {

                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if (response.isSuccessful()) {
                            //  TODO 请求成功
                            Intent intent = new Intent();
                            intent.setClass(LoginActivity.this, MainActivity.class);
                            intent.putExtra("ret", response.body().ret);
                            Toast.makeText(LoginActivity.this, response.body().ret, Toast.LENGTH_LONG).show();
                            startActivity(intent);

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
