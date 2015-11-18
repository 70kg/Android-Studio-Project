package com.com.mr_wrong;

import android.app.Application;

import com.Utils.KLog;
import com.example.mr_wrong.androidstudioproject.BuildConfig;

/**
 * Created by Mr_Wrong on 15/11/15.
 */
public class KLogApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        KLog.init(BuildConfig.LOG_DEBUG);
    }
}