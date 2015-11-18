package com.com.mr_wrong.KLog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.Utils.KLog;
import com.com.mr_wrong.ViewDraghelper.MainActivity;
import com.example.mr_wrong.androidstudioproject.R;

import java.util.Date;

/**
 * Created by Mr_Wrong on 15/11/15.
 */
public class KLogActivity extends AppCompatActivity {
    private static final String LOG_MSG = "KLog is a so cool Log Tool!";
    private static final String JSON = "{\"menu\":[\"泰式柠檬肉片\",\"鸡柳汉堡\",\"蒸桂鱼卷 \"],\"tag\":\"其他\"}";
    private static final String TAG = "KLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.klog);
    }

    public void log(View view) {
        KLog.v();
        KLog.d("ww");
        KLog.i();
        KLog.w();
        KLog.e();
        KLog.a();
    }

    public void logWithMsg(View view) {
        KLog.v(1);
        KLog.d(2);
        KLog.i(2.2);
        KLog.w(new MainActivity());
        KLog.e(new Date());
        KLog.a(LOG_MSG);
    }

    public void logWithTag(View view) {
        KLog.v(TAG, LOG_MSG);
        KLog.d(TAG, LOG_MSG);
        KLog.i(TAG, LOG_MSG);
        KLog.w(TAG, LOG_MSG);
        KLog.e(TAG, LOG_MSG);
        KLog.a(TAG, LOG_MSG);
    }

    public void logWithJson(View view) {
        KLog.json(JSON);
    }

    public void logWithJsonTag(View view) {
        KLog.json(TAG, JSON);
    }


}