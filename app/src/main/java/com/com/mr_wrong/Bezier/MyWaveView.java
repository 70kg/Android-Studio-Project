package com.com.mr_wrong.Bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Mr_Wrong on 15/7/9.
 */
public class MyWaveView extends View {
    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    private float radius;
    private float centre;
    private Path mPath, mPath1;
    private List<PointF> mPoints;
    private float mWaveHeight;
    private float mWaveWidth;
    private RectF mRect;
    private float mLeftSide;
    private float mMoveLen;
    private float mLevelLine = 30;
    private static final float SPEED = 1.7F;

    private Boolean isMeasured = false;
    private Timer timer;
    private MyTimerTask mTask;
    Handler updateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mMoveLen += SPEED;
            mLeftSide += SPEED;
            // 波形平移
            for (int i = 0; i < mPoints.size(); i++)
            {
                mPoints.get(i).x = (mPoints.get(i).x + SPEED);
                switch (i % 4)
                {
                    case 0:
                    case 2:
                        mPoints.get(i).y = (mLevelLine);
                        break;
                    case 1:
                        mPoints.get(i).y = (mLevelLine + mWaveHeight);
                        break;
                    case 3:
                        mPoints.get(i).y = (mLevelLine - mWaveHeight);
                        break;
                }
            }
            if (mMoveLen >= mWaveWidth)
            {
                // 波形平移超过一个完整波形后复位
                mMoveLen = 0;
                resetPoints();
            }
            invalidate();
        }
    };

    public MyWaveView(Context context) {
        super(context);
        init(context);
    }

    public MyWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;

        radius = mWidth / 2;
        centre = mWidth / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!isMeasured) {
            isMeasured = true;

            mWaveHeight = mWidth / 5;
            mWaveWidth = (float) (mWidth * 1.5);

            mLeftSide = -mWaveWidth;

            int n = (int) Math.round(mWidth / mWaveWidth + 0.5);

            for (int i = 0; i < (4 * n + 5); i++) {
                float x = i * mWaveWidth / 4 - mWaveWidth;
                float y = 0;
                switch (i % 4) {
                    case 0:
                    case 2:
                        //水平线上的点
                        y = mLevelLine;
                        break;
                    case 1://向下的
                        y = mLevelLine + mWaveHeight;
                        break;
                    case 3://向上的
                        y = mLevelLine - mWaveHeight;
                        break;
                }
                mPoints.add(new PointF(x, y));
            }
        }

    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        timer = new Timer();
        mPoints = new ArrayList<PointF>();
        mPath = new Path();

        mPath1 = new Path();
        mRect = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(centre, centre, radius, mPaint);

        //mPath.reset();
        mPath.moveTo(mPoints.get(0).x, mPoints.get(0).y);
        int i = 0;
        for (; i < mPoints.size() - 2; i += 2) {
            mPath.quadTo(mPoints.get(i + 1).x, mPoints.get(i + 1).y,
                    mPoints.get(i + 2).x, mPoints.get(i + 2).y);
        }
        mPath.lineTo(mPoints.get(i).x, mHeight);
        mPath.lineTo(mLeftSide, mHeight);
        mPath.close();

        mPath1.arcTo(mRect, 0, 360, true);
        canvas.clipPath(mPath1);

        mPaint.setColor(Color.BLUE);
        canvas.drawPath(mPath,mPaint);

    }
    private void resetPoints()
    {
        mLeftSide = -mWaveWidth;
        for (int i = 0; i < mPoints.size(); i++)
        {
            mPoints.get(i).x = (i * mWaveWidth / 4 - mWaveWidth);
        }
    }
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus)
    {
        super.onWindowFocusChanged(hasWindowFocus);
        // 开始波动
        start();
    }
    private void start()
    {
        if (mTask != null)
        {
            mTask.cancel();
            mTask = null;
        }
        mTask = new MyTimerTask(updateHandler);
        timer.schedule(mTask, 0, 10);
    }
    class MyTimerTask extends TimerTask
    {
        Handler handler;

        public MyTimerTask(Handler handler)
        {
            this.handler = handler;
        }

        @Override
        public void run()
        {
            handler.sendMessage(handler.obtainMessage());
        }

    }
}
