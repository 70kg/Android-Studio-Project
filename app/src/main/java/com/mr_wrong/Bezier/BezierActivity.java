package com.mr_wrong.Bezier;

import android.app.Activity;
import android.os.Bundle;

import com.example.mr_wrong.androidstudioproject.R;

/**
  * Created by Mr_Wrong on 2015/5/1.
  */
 public class BezierActivity extends Activity{
    private BezierView bezierview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bezier_layout);
        bezierview = (BezierView) findViewById(R.id.bezierview);
        bezierview.setStartX(200);
        bezierview.setStartY(200);

    }
}
