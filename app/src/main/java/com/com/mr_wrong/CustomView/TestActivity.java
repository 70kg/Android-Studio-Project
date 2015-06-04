package com.com.mr_wrong.CustomView;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.Utils.ScreenUtils;
import com.com.mr_wrong.Scroller.JellyTextView;
import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 15/5/30.
 */
public class TestActivity extends Activity {
    Button button;
    JellyTextView menu1;

    JellyTextView menu2;

    public static void setLayout(View view, int x, int y) {
        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(x, y, x + margin.width, y + margin.height);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customjob_layout);
        button = (Button) findViewById(R.id.button1);
        menu1 = (JellyTextView) findViewById(R.id.menu1);
        menu2 = (JellyTextView) findViewById(R.id.menu2);
        final int width = ScreenUtils.getScreenWidth(this);
        int widthpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                width, getResources().getDisplayMetrics());

        menu1.setLayoutParams(new RelativeLayout.LayoutParams(4*width/5, ViewGroup.LayoutParams.MATCH_PARENT));
        menu2.setLayoutParams(new RelativeLayout.LayoutParams(width/2, ViewGroup.LayoutParams.MATCH_PARENT));
        ObjectAnimator.ofFloat(menu2, "translationX", 0.0F, width).start();
        ObjectAnimator.ofFloat(menu1, "translationX", 0.0F, width).start();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator.ofFloat(menu1, "translationX", width, 1 * width / 3)
                        .setDuration(1000)
                        .start();
            }
        });
        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator.ofFloat(menu2, "translationX", width, width / 2).setDuration(1000).start();
            }
        });
    }
}
