package com.com.mr_wrong.MySwipBacklayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 15/11/18.
 */
public class SimpleBookBackDemo extends AppCompatActivity {
    private MySwipBacklayout mSwipeBackLayout;
    private TextView tv;
    private RelativeLayout rl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_book);
        rl = (RelativeLayout) findViewById(R.id.rl);
        tv = (TextView) findViewById(R.id.tv);
        tv.animate().rotation(-90);
        mSwipeBackLayout = (MySwipBacklayout) findViewById(R.id.sb);
        mSwipeBackLayout.setOnSwipeBackListener(new MySwipBacklayout.SwipeBackListener() {
            @Override
            public void onOpen() {
                finish();
                overridePendingTransition(0, R.anim.right_anim);
            }

            @Override
            public void onClose() {

            }

            @Override
            public void onSwipe(float percent) {
                rl.setTranslationX(percent * 30 - 30);
                tv.setAlpha(percent<0.5?0.1f:percent);
            }
        });
    }

}
