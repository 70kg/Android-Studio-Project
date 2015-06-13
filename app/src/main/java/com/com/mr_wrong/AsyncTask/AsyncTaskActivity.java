package com.com.mr_wrong.AsyncTask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 2015/5/1.
 */
public class AsyncTaskActivity extends Activity {
    private ImageView imageView;
    private ProgressBar mProgressBar;
    private String URL = "http://image181-c.poco.cn/mypoco/myphoto/20110519/17/56213396201105191718042329008763474_000_640.jpg";
    MyAsyncTask myAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asynctask_layout);
        imageView = (ImageView) findViewById(R.id.image);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);

        myAsyncTask = new MyAsyncTask(imageView, mProgressBar);
        myAsyncTask.execute(URL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (myAsyncTask != null && myAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            myAsyncTask.cancel(true);
        }
    }

}
