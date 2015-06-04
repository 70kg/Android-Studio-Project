package com.com.mr_wrong.Scroller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.BounceInterpolator;
import android.widget.OverScroller;
import android.widget.TextView;


/**
 * Created by Mr_Wrong on 2015/5/14.
 */
public class JellyTextView extends TextView {
    private OverScroller mScroller;
    private float startX, startY;
    private float lastX, lastY;

    public JellyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new OverScroller(getContext(), new BounceInterpolator());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        startX = getX();
        startY = getY();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getRawX();
                lastY = event.getRawY();
               break;

            case MotionEvent.ACTION_MOVE:
                float disx = event.getRawX() - lastX;
                float disy = event.getRawY() - lastY;
                disx = Math.abs(disx)>getWidth()/2?getWidth()/2:disx;
                offsetLeftAndRight((int) disx);
               // offsetTopAndBottom((int) disy);
                lastX = event.getRawX();
                lastY = event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
               // mScroller.startScroll((int) getX(), (int) getY(), -(int) (getX() - startX), -(int) (getY() - startY));
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            setX(mScroller.getCurrX());
            setY(mScroller.getCurrY());
            invalidate();
        }
        super.computeScroll();
    }

    public void spingBack() {
        if (mScroller.springBack((int) getX(), (int) getY(), 50, 100, 0, (int) getY() - 100)) {
            invalidate();
        }
    }
}
