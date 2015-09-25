package com.com.mr_wrong.CustomView;

import android.content.Context;
import android.graphics.*;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Mr_Wrong on 15/9/10.
 */
public class TextDraw extends View{
    private Paint mPaint;
    private Path mPath;

    public TextDraw(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
       mPath = new Path();

        mPath.moveTo(360,0);

        mPath.cubicTo(232,128,488,128,360,0);

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawPath(mPath,mPaint);
    }
}
