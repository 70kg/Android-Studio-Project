package com.com.mr_wrong.NewTypeStartActivity;

import android.app.Activity;

import com.com.mr_wrong.Actionbar.ActionbarActivity;
import com.com.mr_wrong.AsyncTask.AsyncTaskActivity;
import com.com.mr_wrong.Bezier.BezierActivity;
import com.com.mr_wrong.CustomView.CustomViewActivity;
import com.com.mr_wrong.GaoFeng.GFMainActivity;
import com.com.mr_wrong.ImageLoaderWithCaches.ImageLoaderActivity;
import com.com.mr_wrong.NumberProgressBar.NumberProgressBarActivity;
import com.com.mr_wrong.PopButton.PopButtonActivity;
import com.com.mr_wrong.Property_Animation.AnimActivity;
import com.com.mr_wrong.RecyclerView.RecyclerViewActivity;
import com.com.mr_wrong.RecyclerViewItemAnimator.ItemAnimatorActivity;
import com.com.mr_wrong.Scroller.ScrollActivity;
import com.com.mr_wrong.SlideView.SlideActivity;
import com.com.mr_wrong.VolleyAndGson.VolleyActivity;

/**
 * Created by Mr_Wrong on 15/7/5.
 */
public enum AllActivitys {
    BezierActivity(BezierActivity.class),
    AsyncTaskActivity(AsyncTaskActivity.class),
    ImageLoaderActivity(ImageLoaderActivity.class),
    RecyclerViewActivity(RecyclerViewActivity.class),
    ScrollActivity(ScrollActivity.class),
    ItemAnimatorActivity(ItemAnimatorActivity.class),
    VolleyActivity(VolleyActivity.class),
    NumberProgressBarActivity(NumberProgressBarActivity.class),
    ViewDraghelper(com.com.mr_wrong.ViewDraghelper.MainActivity.class),
    PopButtonActivity(PopButtonActivity.class),
    SlideActivity(SlideActivity.class),
    ActionbarActivity(ActionbarActivity.class),
    GFMainActivity(GFMainActivity.class),
    AnimActivity(AnimActivity.class),
    CustomViewActivity(CustomViewActivity.class);
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
