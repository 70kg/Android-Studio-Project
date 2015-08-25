package com.com.mr_wrong.CustomView.Shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.Utils.ScreenUtils;
import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 15/8/25.
 * 介绍matrix的变换什么的
 */
public class MatrixView extends View {
    private static final int RECT_SIZE = 400;// 矩形尺寸的一半

    private Paint mPaint;// 画笔

    private int left, top, right, bottom;// 矩形坐上右下坐标
    private int screenX, screenY;

    public MatrixView(Context context, AttributeSet attrs) {
        super(context, attrs);


        // 获取屏幕中点坐标
        screenX = ScreenUtils.getScreenWidth(context) / 2;
        screenY = ScreenUtils.getScreenHeight(context) / 2;

        // 计算矩形左上右下坐标值
        left = screenX - RECT_SIZE;
        top = screenY - RECT_SIZE;
        right = screenX + RECT_SIZE;
        bottom = screenY + RECT_SIZE;

        // 实例化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // 获取位图
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

        // 实例化一个Shader
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        // 实例一个矩阵对象
        Matrix matrix = new Matrix();

        // 设置矩阵变换
        matrix.setTranslate(500, 500);
        matrix.postRotate(5);

        // 设置Shader的变换矩阵
        bitmapShader.setLocalMatrix(matrix);

        // 设置着色器
        mPaint.setShader(bitmapShader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制矩形
        // canvas.drawRect(left, top, right, bottom, mPaint);
        canvas.drawRect(0, 0, screenX * 2, screenY * 2, mPaint);
    }
}