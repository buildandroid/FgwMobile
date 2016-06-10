package activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.fgwoa.fgwmobile.R;

/**
 * Created by wangxiao on 16/6/8.
 */
public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLogo();

        Intent intent = getIntent();
        final String ret = intent.getStringExtra("ret");
    }

    private void initLogo() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.beijing_fagai_logo);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }
}
