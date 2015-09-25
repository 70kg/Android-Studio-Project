package com.com.mr_wrong.CustomJobView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Mr_Wrong on 15/8/29.
 */
public class CoustomIcon extends ImageView {
    private Paint mPaint;
    private Rect mRect;
    private Bitmap mBitmap;
    private PorterDuffXfermode xfermode;
    private int width, height;

    public CoustomIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);

        BitmapDrawable bd = (BitmapDrawable)this.getDrawable();
        if(bd != null){
            mBitmap = bd.getBitmap();
        }
        height = mBitmap.getHeight();
        mRect = new Rect();
        mRect.left = 0;
        mRect.right = mBitmap.getWidth();
        mRect.bottom = mBitmap.getHeight();

        drawrect();
    }


    private void drawrect() {
        ValueAnimator animator = ValueAnimator.ofInt(height, 0);
        animator.setDuration(1500);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(-1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRect.top = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();

    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);

        int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        mPaint.setColor(Color.RED);
        canvas.drawRect(mRect, mPaint);


        mPaint.setXfermode(xfermode);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        mPaint.setXfermode(null);

        canvas.restoreToCount(sc);

    }
}
