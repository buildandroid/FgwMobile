package activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.fgwoa.fgwmobile.R;

/**
 * Created by jikai on 6/10/16.
 */
public class GwppActivity extends AppCompatActivity {
    private ViewGroup container;
    private ViewGroup listContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setContentView(R.layout.activity_gwpp);
        init();
    }

    private void init() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
        listContainer = (ViewGroup)findViewById(R.id.list_container);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)listContainer.getLayoutParams();
        params.setMarginStart(width / 2);
        listContainer.setLayoutParams(params);
        container = (ViewGroup)findViewById(R.id.container);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.tpl_fawengaozhi, null);
        container.addView(v);
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
                if (listContainer.getVisibility() == View.GONE) {
                    listContainer.setVisibility(View.VISIBLE);
                } else {
                    listContainer.setVisibility(View.GONE);
                }
                break;
            case R.id.menu_fujian:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
