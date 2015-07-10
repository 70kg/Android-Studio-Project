package com.com.mr_wrong.CustomJobView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.Utils.LogUtils;
import com.example.mr_wrong.androidstudioproject.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Mr_Wrong on 15/7/7.
 */
public class ArcView extends View {
    private int mArcColor;
    private int mArcWidth;
    private float mDegree;
    private int mInColor;
    private int mOutColor;
    private Paint mPaint;

    private Path mPath;
    private Path mPath1;
    private int mWidth;
    private int mHeight;
    private float ctrx, ctry;//控制点
    boolean isinc;

    int centre;
    int cricle_radius_width;
    int arc_radius_width;
    int degree;
    float arcwidth;
    float archeight;

    public ArcView(Context context) {
        this(context, null);
    }

    public ArcView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ArcView, defStyleAttr, 0);
        mArcColor = array.getColor(R.styleable.ArcView_arccolor, Color.RED);
        mInColor = array.getColor(R.styleable.ArcView_incriclecolor, Color.BLACK);
        mOutColor = array.getColor(R.styleable.ArcView_outcriclecolor, Color.GRAY);

        mDegree = array.getFloat(R.styleable.ArcView_degree, 0);
        mArcWidth = array.getDimensionPixelSize(R.styleable.ArcView_arcwidth, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));

        mPointsList = new ArrayList<Point>();
        timer = new Timer();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);


        mPath = new Path();
        mPath1 = new Path();
    }

    private float mWaveHeight = 80;
    /**
     * 波长
     */
    private float mWaveWidth = 200;
    /**
     * 被隐藏的最左边的波形
     */
    private float mLeftSide;

    private float mMoveLen;
    /**
     * 水波平移速度
     */
    public static final float SPEED = 1.7f;

    private List<Point> mPointsList;
    private Timer timer;
    private MyTimerTask mTask;

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        // 开始波动
        start();
    }

    private void start() {
        if (mTask != null) {
            mTask.cancel();
            mTask = null;
        }
        mTask = new MyTimerTask(updateHandler);
        timer.schedule(mTask, 0, 10);
    }

    Handler updateHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // 记录平移总位移
            mMoveLen += SPEED;

            mLeftSide += SPEED;
            // 波形平移
            for (int i = 0; i < mPointsList.size(); i++) {
                mPointsList.get(i).setX(mPointsList.get(i).getX() + SPEED);
                switch (i % 4) {
                    case 0:
                    case 2:
                        mPointsList.get(i).setY(archeight);
                        break;
                    case 1:
                        mPointsList.get(i).setY(archeight + mWaveHeight);
                        break;
                    case 3:
                        mPointsList.get(i).setY(archeight - mWaveHeight);
                        break;
                }
            }
            if (mMoveLen >= mWaveWidth) {
                // 波形平移超过一个完整波形后复位
                mMoveLen = 0;
                resetPoints();
            }
            invalidate();
        }

    };

    private void resetPoints() {
        mLeftSide = -mWaveWidth;
        for (int i = 0; i < mPointsList.size(); i++) {
            mPointsList.get(i).setX(i * mWaveWidth / 4 - mWaveWidth);
        }
    }

    class MyTimerTask extends TimerTask {
        Handler handler;

        public MyTimerTask(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            handler.sendMessage(handler.obtainMessage());
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        centre = getWidth() / 2;
        cricle_radius_width = centre - mArcWidth;//内圆的半径
        arc_radius_width = centre - mArcWidth / 2;//圆环半径

        degree = (int) (0.5 * mDegree * 360);
        mWidth = getWidth();
        mHeight = getHeight();
        arcwidth = (float) (2 * cricle_radius_width * Math.sin(degree * Math.PI / 180));
        archeight = (float) (mHeight - (cricle_radius_width - cricle_radius_width * Math.cos(degree * Math.PI / 180)));


        // 根据View宽度计算波形峰值
        mWaveHeight = arcwidth / 5f;
        // 波长等于四倍View宽度也就是View中只能看到四分之一个波形，这样可以使起伏更明显
        mWaveWidth = (float) (arcwidth * 1.5);
        // 左边隐藏的距离预留一个波形
        mLeftSide = -mWaveWidth;
        // 这里计算在可见的View宽度中能容纳几个波形，注意n上取整
        int n = (int) Math.round(arcwidth / mWaveWidth + 0.5);
        // n个波形需要4n+1个点，但是我们要预留一个波形在左边隐藏区域，所以需要4n+5个点
        for (int i = 0; i < (4 * n + 5); i++) {
            // 从P0开始初始化到P4n+4，总共4n+5个点
            float x = i * mWaveWidth / 4 - mWaveWidth;
            float y = 0;
            switch (i % 4) {
                case 0:
                case 2:
                    // 零点位于水位线上
                    y = archeight;
                    break;
                case 1:
                    // 往下波动的控制点
                    y = archeight + mWaveHeight;
                    break;
                case 3:
                    // 往上波动的控制点
                    y = archeight - mWaveHeight;
                    break;
            }
            mPointsList.add(new Point(x, y));
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {

        //内圆
        mPaint.setColor(mInColor);
        canvas.drawCircle(centre, centre, cricle_radius_width, mPaint);

        //外圆
        mPaint.setStrokeWidth(mArcWidth);
        mPaint.setColor(mOutColor);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(centre, centre, arc_radius_width, mPaint);
        //圆弧
        RectF rect = new RectF(centre - arc_radius_width, centre - arc_radius_width
                , centre + arc_radius_width, centre + arc_radius_width);
        float startangle = (float) (0.5 * (1 - mDegree) * 360);
        float sweepangle = mDegree * 360;
        mPaint.setColor(mArcColor);
        canvas.drawArc(rect, startangle - 90, sweepangle, false, mPaint);

        mPath.reset();
        int i = 0;
        mPath.moveTo(mPointsList.get(0).getX(), mPointsList.get(0).getY());
        for (; i < mPointsList.size() - 2; i = i + 2) {
            mPath.quadTo(mPointsList.get(i + 1).getX(),
                    mPointsList.get(i + 1).getY(), mPointsList.get(i + 2)
                            .getX(), mPointsList.get(i + 2).getY());
        }
        mPath.arcTo(rect, startangle - 90, sweepangle);
        mPath1.arcTo(rect, startangle - 90, 360, true);
        LogUtils.e(startangle - 90);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.clipPath(mPath1);
        canvas.drawPath(mPath, mPaint);


        //获取path上的点

        PathMeasure pathMeasure = new PathMeasure(mPath1, false);
        float length = pathMeasure.getLength();
        float speed = length / 100;
        float dis = 0;
        int count = 0;
        float[] aCoordinates = new float[2];
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        FlaotPoint[] pointArray = new FlaotPoint[200];
        while (dis < length && count < 100) {
            pathMeasure.getPosTan(dis, aCoordinates, null);
            dis += speed;
            count++;
            pointArray[count] = new FlaotPoint(aCoordinates[0],
                    aCoordinates[1]);

            canvas.drawCircle(pointArray[count].getX(), pointArray[count].getY(), 20, mPaint);
            mPaint.setColor(Color.BLACK);
            if(pointArray[count-1]!=null){
                canvas.drawCircle(pointArray[count-1].getX(), pointArray[count-1].getY(), 20, mPaint);

            }


            // LogUtils.e("x--->"+aCoordinates[0]+"  y--->"+aCoordinates[1]);
            if (pointArray[50] != null) {
                //LogUtils.e(pointArray[50].getY());
                LogUtils.e(pointArray[50].getX());
//                canvas.drawCircle(pointArray[50].getX(), pointArray[50].getY(), 20, mPaint);
            }

        }


    }

    class FlaotPoint {
        float x, y;

        public FlaotPoint(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }
    }

    class Point {
        private float x;
        private float y;

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

    }
}
