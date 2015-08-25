package com.com.mr_wrong.CustomView.Shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 15/8/25.
 * 跟着手指移动的圆
 */
public class BrickView extends View {
    private Paint mPaint, mPaint1;
    private Bitmap mBitmap;
    private float posx, posy;

    public BrickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);//圆环
        mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xFF000000);
        mPaint.setStrokeWidth(5);


        mPaint1.setStyle(Paint.Style.FILL);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.right);
        BitmapShader shader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        mPaint1.setShader(shader);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction()==MotionEvent.ACTION_MOVE){
            posx = event.getX();
            posy = event.getY();

            invalidate();
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(posx,posy,300,mPaint);
        canvas.drawCircle(posx,posy,300,mPaint1);
        super.onDraw(canvas);
    }
}
