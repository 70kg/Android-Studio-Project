package com.com.mr_wrong.NumberProgressBar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mr_wrong.androidstudioproject.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Mr_Wrong on 2015/5/18.
 */
public class NumberProgressBarActivity extends Activity implements OnProgressBarListener {
    private NumberProgressBar mProgressBar;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.numberprogressbar_layout);
        mProgressBar = (NumberProgressBar) findViewById(R.id.progressbar);
        mProgressBar.setOnProgressBarListener(this);
        //mProgressBar.setProgress(96);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.incrementProgressBy(1);
                    }
                });
            }
        }, 1000, 100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    public void onProgressChange(int current, int max) {
        if(current == max) {
            Toast.makeText(getApplicationContext(), "结束", Toast.LENGTH_SHORT).show();
            mProgressBar.setProgress(0);
        }
    }
}
