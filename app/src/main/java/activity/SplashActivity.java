package activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.fgwoa.fgwmobile.R;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangxiao on 16/6/8.
 */
public class SplashActivity  extends AppCompatActivity {
    private final long SPLASH_LENGTH = 2000;
    Handler handler = new Handler();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        fullScreenDisplay();
        switchToLoginOrMainActivity();
//        handler.postDelayed(new Runnable() {  //使用handler的postDelayed实现延时跳转
//
//            public void run() {
//                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, SPLASH_LENGTH);//2秒后跳转至应用主界面MainActivity

    }

    private void fullScreenDisplay() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void switchToLoginOrMainActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, TimeUnit.SECONDS.toMillis(2));
    }


}
