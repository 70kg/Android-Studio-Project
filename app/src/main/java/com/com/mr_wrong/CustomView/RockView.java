package com.com.mr_wrong.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 15/6/27.
 */
public class RockView extends View {
    int color ;
    int delaytime = 0;
    private Paint mPaint;
    private float mRadius = 0;
    boolean isBiger = true;
    int mSpeed;
    public RockView(Context context) {
        this(context, null);
    }
    public RockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public RockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RockView, defStyleAttr, 0);
        color = array.getColor(R.styleable.RockView_rockcolor, Color.RED);
        delaytime = array.getInt(R.styleable.RockView_delaytime, 0);
        mSpeed = array.getInt(R.styleable.RockView_rockspeed,20);
        array.recycle();

        mPaint = new Paint();
        mPaint.setColor(color);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(delaytime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (true) {
                    if (isBiger){//变大
                        mRadius++;
                        if (mRadius == getWidth()/2) {
                            isBiger = false;
                        }
                    }else {//变小
                        mRadius--;
                        if(mRadius ==0){
                            isBiger = true;
                        }
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
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centre,centre,mRadius,mPaint);
    }
}
