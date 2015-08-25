package com.com.mr_wrong.CustomView.Shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.Utils.ScreenUtils;
import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 15/8/25.
 * 梦幻相机效果 径向渐变
 */
public class DreamEffectView extends View {
    private Paint mPaint, mShaderpaint;
    private int x, y;
    private Bitmap mBitmap;
    private PorterDuffXfermode xfermode;
    private int width, height;

    public DreamEffectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.SCREEN);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.testimage);
        width = ScreenUtils.getScreenWidth(context);
        height = ScreenUtils.getScreenHeight(context);

        x = width / 2 - mBitmap.getWidth() / 2;
        y = height / 2 - mBitmap.getHeight() / 2;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColorFilter(new ColorMatrixColorFilter(new float[] { 0.8587F, 0.2940F, -0.0927F, 0, 6.79F, 0.0821F, 0.9145F, 0.0634F, 0, 6.79F, 0.2019F, 0.1097F, 0.7483F, 0, 6.79F, 0, 0, 0, 1, 0 }));

        mShaderpaint = new Paint();
        mShaderpaint.setShader(new RadialGradient(width/2,height/2,mBitmap.getHeight()*7/8, Color.TRANSPARENT, Color.BLACK, Shader.TileMode.CLAMP));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);

        int sc = canvas.saveLayer(x,y,x+mBitmap.getWidth(),y+mBitmap.getHeight(),null,Canvas.ALL_SAVE_FLAG);

        canvas.drawColor(0xcc1c093e);

        mPaint.setXfermode(xfermode);

        canvas.drawBitmap(mBitmap,x,y,mPaint);

        mPaint.setXfermode(null);

        canvas.restoreToCount(sc);

        canvas.drawRect(x,y,x+mBitmap.getWidth(),y+mBitmap.getHeight(),mShaderpaint);
    }
}
