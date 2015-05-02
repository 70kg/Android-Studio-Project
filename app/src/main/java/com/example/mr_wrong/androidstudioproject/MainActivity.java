package com.example.mr_wrong.androidstudioproject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.com.mr_wrong.AsyncTask.AsyncTaskActivity;
import com.com.mr_wrong.Bezier.BezierActivity;
import com.com.mr_wrong.ImageLoaderWithCaches.ImageLoaderActivity;


public class MainActivity extends Activity implements View.OnClickListener {

    private Button bt_BezierActivity;
    private Button bt_AsyncTaskActivity;
private Button bt_ImageLoaderActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_BezierActivity = (Button) findViewById(R.id.BezierActivity);
        bt_AsyncTaskActivity = (Button) findViewById(R.id.AsyncTaskActivity);
        bt_ImageLoaderActivity = (Button) findViewById(R.id.ImageLoaderActivity);

        bt_AsyncTaskActivity.setOnClickListener(this);
        bt_BezierActivity.setOnClickListener(this);
        bt_ImageLoaderActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.BezierActivity:
                intent = new Intent(this, BezierActivity.class);
                startActivity(intent);
                break;
            case R.id.AsyncTaskActivity:
                intent = new Intent(this, AsyncTaskActivity.class);
                startActivity(intent);
                break;
            case R.id.ImageLoaderActivity:
                intent = new Intent(this, ImageLoaderActivity.class);
                startActivity(intent);
                break;
        }
    }
}
