package com.com.mr_wrong.Bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Mr_Wrong on 15/7/9.
 */
public class TestBezier extends View {
    private Paint mPaint;
    private Path mPath;
    private float mCtrX = 200, mCtrY = 200;

    public TestBezier(Context context) {
        super(context);
        init();
    }

    public TestBezier(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestBezier(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    float dis;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float downx, downy = 0;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downx = event.getX();
                downy = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                dis = event.getY() - downy;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                mCtrY = 200;
                invalidate();
                break;

        }
        return true;
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mPaint.setColor(Color.RED);
        mPath.moveTo(100, 100);
        mPath.quadTo(mCtrX, mCtrY+dis, 300, 100);
        canvas.drawPath(mPath, mPaint);
    }
}
