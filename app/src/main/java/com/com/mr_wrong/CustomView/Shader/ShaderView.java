package com.com.mr_wrong.CustomView.Shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.Utils.ScreenUtils;
import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 15/8/25.
 * 这是shader的第一个
 */
public class ShaderView extends View {
    private static final int RECT_SIZE = 400;
    private Paint mPaint;
    private int left, top, right, bottom;
    private Bitmap mBitmap;

    public ShaderView(Context context) {
        super(context);
    }

    public ShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        int height = ScreenUtils.getScreenHeight(context) / 2;
        int width = ScreenUtils.getScreenWidth(context) / 2;


        left = width - RECT_SIZE;
        top = height - RECT_SIZE;
        right = width + RECT_SIZE;
        bottom = height + RECT_SIZE;

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_header_logo);


        //mPaint.setShader(new BitmapShader(mBitmap, Shader.TileMode.MIRROR, Shader.TileMode.CLAMP));
        mPaint.setShader(new LinearGradient(left, top, right-RECT_SIZE, bottom, Color.RED, Color.YELLOW, Shader.TileMode.REPEAT));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(left, top, right, bottom, mPaint);
    }
}
