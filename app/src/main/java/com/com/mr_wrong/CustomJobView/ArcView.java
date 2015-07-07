package com.com.mr_wrong.CustomJobView;

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
 * Created by Mr_Wrong on 15/7/7.
 */
public class ArcView extends View {
    private int mArcColor;
    private int mArcWidth;
    private float mDegree;
    private int mInColor;
    private int mOutColor;
    private Paint mPaint;

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

        mPaint = new Paint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        int centre = getWidth() / 2;
        int cricle_radius_width = centre - mArcWidth;//内圆的半径
        int arc_radius_width = centre - mArcWidth / 2;//圆环半径
        //内圆
        mPaint.setColor(mInColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        canvas.drawCircle(centre, centre, cricle_radius_width, mPaint);

        //外圆
        mPaint.setStrokeWidth(mArcWidth);
        mPaint.setColor(mOutColor);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(centre,centre,arc_radius_width,mPaint);
        //圆弧
        RectF rect = new RectF(centre - arc_radius_width, centre - arc_radius_width
                , centre + arc_radius_width, centre + arc_radius_width);
        float startangle = (float) (0.5 * (1 - mDegree) * 360);
        float sweepangle = mDegree * 360;
        mPaint.setStrokeWidth(mArcWidth);
        mPaint.setColor(mArcColor);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(rect, startangle - 90, sweepangle, false, mPaint);
    }
}
