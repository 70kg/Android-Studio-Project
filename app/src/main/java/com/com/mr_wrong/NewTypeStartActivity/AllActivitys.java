package com.com.mr_wrong.NewTypeStartActivity;

import android.app.Activity;

import com.com.mr_wrong.Bezier.BezierActivity;
import com.com.mr_wrong.AsyncTask.AsyncTaskActivity;
/**
 * Created by Mr_Wrong on 15/7/5.
 */
public enum AllActivitys {
    BezierActivity(BezierActivity.class),
    AsyncTaskActivity(AsyncTaskActivity.class);


    private Class activityClazz;

    private AllActivitys(Class clazz) {
        activityClazz = clazz;
    }
    public Activity getActivity() {
        try {
            return (Activity) activityClazz.newInstance();
        } catch (Exception e) {
            throw new Error("Can not init Activity instance");
        }
    }
}
