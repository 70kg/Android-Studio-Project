package com.example.mr_wrong.androidstudioproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.com.mr_wrong.AsyncTask.AsyncTaskActivity;
import com.com.mr_wrong.Bezier.BezierActivity;
import com.com.mr_wrong.ImageLoaderWithCaches.ImageLoaderActivity;
import com.com.mr_wrong.NumberProgressBar.NumberProgressBarActivity;
import com.com.mr_wrong.PopButton.PopButtonActivity;
import com.com.mr_wrong.RecyclerView.RecyclerViewActivity;
import com.com.mr_wrong.RecyclerViewItemAnimator.ItemAnimatorActivity;
import com.com.mr_wrong.Scroller.ScrollActivity;
import com.com.mr_wrong.VolleyAndGson.VolleyActivity;


public class MainActivity extends Activity implements View.OnClickListener {

    private Button bt_BezierActivity;
    private Button bt_AsyncTaskActivity;
    private Button bt_ImageLoaderActivity;
    private Button bt_RecyclerViewActivity;
    private Button bt_ScrollActivity;
    private Button bt_ItemAnimatorActivity;
    private Button bt_VolleyActivity;
    private Button bt_NumberProgressBarActivity;
    private Button bt_DragViewHelperActivity;
    private Button bt_PopButtonActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_BezierActivity = (Button) findViewById(R.id.BezierActivity);
        bt_AsyncTaskActivity = (Button) findViewById(R.id.AsyncTaskActivity);
        bt_ImageLoaderActivity = (Button) findViewById(R.id.ImageLoaderActivity);
        bt_RecyclerViewActivity = (Button) findViewById(R.id.RecyclerViewActivity);
        bt_ScrollActivity = (Button) findViewById(R.id.ScrollActivity);
        bt_ItemAnimatorActivity = (Button) findViewById(R.id.ItemAnimatorActivity);
        bt_VolleyActivity = (Button) findViewById(R.id.VolleyActivity);
        bt_NumberProgressBarActivity = (Button) findViewById(R.id.NumberProgressBarActivity);
        bt_DragViewHelperActivity = (Button) findViewById(R.id.DragViewHelperActivity);
        bt_PopButtonActivity = (Button) findViewById(R.id.PopButtonActivity);

        bt_AsyncTaskActivity.setOnClickListener(this);
        bt_BezierActivity.setOnClickListener(this);
        bt_ImageLoaderActivity.setOnClickListener(this);
        bt_RecyclerViewActivity.setOnClickListener(this);
        bt_ScrollActivity.setOnClickListener(this);
        bt_ItemAnimatorActivity.setOnClickListener(this);
        bt_VolleyActivity.setOnClickListener(this);
        bt_NumberProgressBarActivity.setOnClickListener(this);
        bt_DragViewHelperActivity.setOnClickListener(this);
        bt_PopButtonActivity.setOnClickListener(this);
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
            case R.id.RecyclerViewActivity:
                intent = new Intent(this, RecyclerViewActivity.class);
                startActivity(intent);
                break;
            case R.id.ScrollActivity:
                intent = new Intent(this, ScrollActivity.class);
                startActivity(intent);
                break;
            case R.id.ItemAnimatorActivity:
                intent = new Intent(this, ItemAnimatorActivity.class);
                startActivity(intent);
                break;
            case R.id.VolleyActivity:
                intent = new Intent(this, VolleyActivity.class);
                startActivity(intent);
                break;
            case R.id.NumberProgressBarActivity:
                intent = new Intent(this, NumberProgressBarActivity.class);
                startActivity(intent);
                break;
            case R.id.DragViewHelperActivity:
                intent = new Intent(this, com.com.mr_wrong.ViewDraghelper.MainActivity.class);
                startActivity(intent);
                break;
            case R.id.PopButtonActivity:
                intent = new Intent(this, PopButtonActivity.class);
                startActivity(intent);
                break;

        }
    }
}
