package com.com.mr_wrong.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 15/5/31.
 */
public class CustomVolumContralBar extends View {
    private int mFirsstColor;
    private int mSecondColor;
    private int mCircleWidth;
    private int mDotCount;
    private int mSplitSize;
    private Bitmap mBg;
    private Paint mPaint;
    private Rect rect;
    private int mCurrentCount = 3;

    public CustomVolumContralBar(Context context) {
        this(context, null);
    }

    public CustomVolumContralBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomVolumContralBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomVolumContralBar, defStyleAttr, 0);
        mFirsstColor = array.getColor(R.styleable.CustomVolumContralBar_bar_fristcolor, Color.BLUE);
        mSecondColor = array.getColor(R.styleable.CustomVolumContralBar_bar_secondcolor, Color.RED);
        mCircleWidth = array.getDimensionPixelSize(R.styleable.CustomVolumContralBar_circlewidth, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
        mDotCount = array.getInt(R.styleable.CustomVolumContralBar_dotcount, 20);
        mSplitSize = array.getInt(R.styleable.CustomVolumContralBar_splitsize, 20);
        mBg = BitmapFactory.decodeResource(getResources(), array.getResourceId(R.styleable.CustomVolumContralBar_bg, 0));

        array.recycle();
        mPaint = new Paint();
        rect = new Rect();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        int centre = getWidth() / 2;
        int radius = centre - mCircleWidth / 2;
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);

        drawOval(canvas, centre, radius);//绘制块块
        double sqrt22 = Math.sqrt(2) / 2;
        int relRadius = radius - mCircleWidth / 2;
        rect.left = (int) (relRadius * (1 - sqrt22) + mCircleWidth);
        rect.top = rect.left;
        rect.right = (int) (rect.left + 2 * sqrt22 * relRadius);
        rect.bottom = rect.right;
        if (mBg.getWidth() < 2 * sqrt22 * relRadius) {//图片小 居中显示
            rect.left = getWidth() / 2 - mBg.getWidth() / 2;
            rect.top = getHeight() / 2 - mBg.getHeight() / 2;
            rect.right = rect.left + mBg.getWidth();
            rect.bottom = rect.top + mBg.getHeight();
        }
        canvas.drawBitmap(mBg, null, rect, mPaint);
    }

    /**
     * 绘制块块
     */
    private void drawOval(Canvas canvas, int centre, int radius) {
        int itemsize = (360 - mDotCount * mSplitSize) / mDotCount;//每个块大小
        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);
        mPaint.setColor(mFirsstColor);
        for (int i = 0; i < mDotCount; i++) {
            canvas.drawArc(oval, i * (itemsize + mSplitSize), itemsize, false, mPaint);
        }
        mPaint.setColor(mSecondColor);
        for (int i = 0; i < mCurrentCount; i++) {
            canvas.drawArc(oval, i * (itemsize + mSplitSize), itemsize, false, mPaint);
        }
    }

    private void up() {
        mCurrentCount++;
        postInvalidate();
    }

    private void down() {
        mCurrentCount--;
        postInvalidate();
    }

    private int xDown, xUp;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                xUp = (int) event.getY();
                if (xUp > xDown) {//下
                    down();
                } else {
                    up();
                }
                break;
        }
        return true;
    }

}
