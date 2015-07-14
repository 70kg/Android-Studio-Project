package com.com.mr_wrong.CustomView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 15/7/13.
 */
public class Canvas extends View {
    private Paint mPaint;
    private Bitmap mBitmap;
    private RectF mRect1;
    private Rect mRect2;
    private float[] pos;

    private Region mRegiona, mRegionb;

    public Canvas(Context context) {
        this(context, null);
    }

    public Canvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(2);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sky);
        mRect1 = new RectF(0, 0, 500, 500);
       // mRect1.union(250, 250, 750, 750);

        mRegiona = new Region(100, 100, 300, 300);
        mRegionb = new Region(200, 200, 400, 400);

        mRect2 = new Rect();
        pos = new float[200];
        for (int i = 0; i < 10; i += 10) {
            for (int j = 0; j < 10; j += 5) {
                pos[i * 2 + 0] = i + j;
                pos[i * 2 + 1] = 100;
            }

        }

    }

    @Override
    protected void onDraw(android.graphics.Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        //canvas.drawBitmap(mBitmap, mRect2, mRect1, mPaint);
        //mPaint.setTextSize(30);
        //canvas.drawPosText("hahhahaha",pos,mPaint);
        canvas.drawColor(Color.BLUE);
        canvas.save();

        canvas.clipRegion(mRegiona);
        canvas.clipRegion(mRegionb, Region.Op.INTERSECT);
        canvas.drawColor(Color.RED);
        canvas.restore();
//        canvas.clipRect(mRect1);
//        canvas.drawColor(Color.RED);

        canvas.drawRect(100, 100, 300, 300,mPaint);
        canvas.drawRect(200, 200, 400, 400,mPaint);

    }
}
