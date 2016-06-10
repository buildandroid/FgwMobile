package activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.fgwoa.fgwmobile.R;

/**
 * Created by wangxiao on 16/6/8.
 */
public class MainActivity extends AppCompatActivity{
    private static final float ICON_TITLE_SPACE = 50f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLogo();
        initAnimation();


        Intent intent = getIntent();
        final String ret = intent.getStringExtra("ret");
    }

    private void initLogo() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.beijing_fagai_logo);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    private void initAnimation(){
        startCircleAnimator(R.id.button1, R.animator.c1, R.id.image1);
        startCircleAnimator(R.id.button2, R.animator.c2, R.id.image2);
        startCircleAnimator(R.id.button3, R.animator.c3, R.id.image3);
        startCircleAnimator(R.id.button4, R.animator.c4, R.id.image4);
        startCircleAnimator(R.id.button5, R.animator.c5, R.id.image5);
    }

    private void startCircleAnimator(int targetViewId, int animatorResourceId){
        View target = findViewById(targetViewId);
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(target.getContext(), animatorResourceId);
        set.setTarget(target);
        ((ValueAnimator)set.getChildAnimations().get(0)).addUpdateListener(
                new CircleAnimatorUpdateListener(target));
        set.start();
    }

    private void startCircleAnimator(final int targetViewId, int animatorResourceId, final int titleViewId){
        View target = findViewById(targetViewId);
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(target.getContext(), animatorResourceId);
        set.setTarget(target);
        ((ValueAnimator)set.getChildAnimations().get(0)).addUpdateListener(
                new CircleAnimatorUpdateListener(target));
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                View target = findViewById(targetViewId);
//                Log.d("target", "X" + target.getX());
//                Log.d("target", "width" + target.());
//                Log.d("target", "X" + target.getX());
                View title = findViewById(titleViewId);
                title.setVisibility(View.VISIBLE);
                title.setX(target.getX() + target.getWidth()/2 -title.getWidth()/2);
                title.setY(target.getY() - ICON_TITLE_SPACE);
            }
        });
    }

    private static class CircleAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener{
        private View view;
        private static int CENTER_X = 444;
        private static int CENTER_Y = 556;
        private static int RADIUS = 400;
        private static float A_TO_R = (float)Math.PI / 180;

        CircleAnimatorUpdateListener(View aView){
            view = aView;
        }


        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float value = (Integer) animation.getAnimatedValue();
            float v = value * A_TO_R;
            float x = (float) (CENTER_X + Math.cos(v) * RADIUS);
            float y = (float) (CENTER_Y + Math.sin(v) * RADIUS);
            view.setX(x);
            view.setY(y);
        }
    }
}
