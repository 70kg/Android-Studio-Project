package com.com.mr_wrong.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 15/5/31.
 */
public class CustomColorCricle extends View {
    private int mFristColor;
    private int mSecondColor;
    private int mCricleWidth;
    private int mSpeed;
    private Paint mPaint;
    private int mProgress = 0;
    private boolean isNext;

    public CustomColorCricle(Context context) {
        this(context, null);
    }

    public CustomColorCricle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomColorCricle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomColorCricle, defStyleAttr, 0);
        mFristColor = array.getColor(R.styleable.CustomColorCricle_fristcolor, Color.RED);
        mSecondColor = array.getColor(R.styleable.CustomColorCricle_secondcolor, Color.BLUE);
        mCricleWidth = array.getDimensionPixelSize(R.styleable.CustomColorCricle_criclewidth, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
        mSpeed = array.getInt(R.styleable.CustomColorCricle_speed, 20);
        array.recycle();

        mPaint = new Paint();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    mProgress++;
                    if (mProgress == 360) {
                        mProgress = 0;
                        if (!isNext) {
                            isNext = true;
                        } else
                            isNext = false;
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(mSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int centre = getWidth() / 2;
        int radius = centre - mCricleWidth / 2;
//
        mPaint.setStrokeWidth(mCricleWidth);
        mPaint.setStyle(Paint.Style.STROKE);//设置空心
        mPaint.setAntiAlias(true);
        RectF rect = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);
        if (!isNext) {
            mPaint.setColor(mFristColor);
            canvas.drawCircle(centre, centre, radius, mPaint);
            mPaint.setColor(mSecondColor);
            canvas.drawArc(rect, -90, mProgress, false, mPaint);
        } else {
            mPaint.setColor(mSecondColor);
            canvas.drawCircle(centre, centre, radius, mPaint);
            mPaint.setColor(mFristColor);
            canvas.drawArc(rect, -90, mProgress, false, mPaint);
        }

    }
}
