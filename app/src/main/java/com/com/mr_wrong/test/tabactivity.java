package com.com.mr_wrong.test;

import android.app.Activity;
import android.os.Bundle;

import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 2015/5/28.
 */
public class tabactivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        TabView tabview = (TabView) findViewById(R.id.tabview);
       // tabview.setIcon(R.drawable.actionbar_add_icon);
        tabview.setText("hjahah",1);
    }
}
