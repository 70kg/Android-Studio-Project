package com.com.mr_wrong.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by Mr_Wrong on 15/6/14.
 */
public class TestButton extends Button {
    public TestButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i(null, "dispatchTouchEvent-- action=" + event.getAction());
       // Utils.Log("super.dispatchTouchEvent", super.dispatchTouchEvent(event));
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(null, "onTouchEvent-- action=" + event.getAction());
        return true;
    }
}