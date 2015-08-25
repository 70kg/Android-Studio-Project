package com.com.mr_wrong.CustomView.Shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.Utils.ScreenUtils;
import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 15/8/25.
 * 倒影图像
 */
public class ReflectView extends View {
    private Paint mPaint;
    private Bitmap mSrcBitmap, mRefBitmap;
    private PorterDuffXfermode xfermode;
    private int x, y;

    private Paint paint;

    public ReflectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

        mSrcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_header_logo);

        Matrix matrix = new Matrix();
        matrix.setScale(1f, -1f);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);

        mRefBitmap = Bitmap.createBitmap(mSrcBitmap, 0, 0, mSrcBitmap.getWidth(), mSrcBitmap.getHeight(), matrix, true);

        x = ScreenUtils.getScreenWidth(context) / 2 - mSrcBitmap.getWidth() / 2;
        y = ScreenUtils.getScreenHeight(context) / 2 - mSrcBitmap.getHeight() / 2;

        mPaint.setShader(new LinearGradient(x, y + mSrcBitmap.getHeight(),
                x, y + mSrcBitmap.getHeight() + mSrcBitmap.getHeight() / 4,
                0xAA000000, Color.TRANSPARENT, Shader.TileMode.CLAMP));
        //这里是把y轴给拉伸了  所以看不到下面的3/4了
        //如果是渐变加上REPEAT  会有一种百叶窗的效果  还是挺好的

        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(mSrcBitmap, x, y, null);

        int sc = canvas.saveLayer(x, y + mSrcBitmap.getHeight(),
                x + mRefBitmap.getWidth(), y + mSrcBitmap.getHeight() * 2,
                null, Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(mRefBitmap, x, y + mSrcBitmap.getHeight(), null);

        mPaint.setXfermode(xfermode);

        canvas.drawRect(x, y + mSrcBitmap.getHeight(), x + mRefBitmap.getWidth(), y + mSrcBitmap.getHeight() * 2, mPaint);

        canvas.drawRect(x, y + mSrcBitmap.getHeight(), x + mRefBitmap.getWidth(), y + mSrcBitmap.getHeight() * 2, paint);


        mPaint.setXfermode(null);

        canvas.restoreToCount(sc);
    }
}
