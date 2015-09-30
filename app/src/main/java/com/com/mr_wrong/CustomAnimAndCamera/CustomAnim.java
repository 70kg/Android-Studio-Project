package com.com.mr_wrong.CustomAnimAndCamera;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Transformation;

/**
 * Created by Mr_Wrong on 15/9/22.
 */
public class CustomAnim extends Animation {

    private int mCenterWidth;
    private int mCenterHeight;
    private Camera mCamera = new Camera();
    private float mRotateY = 0.0f;

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        setDuration(2000);
        setFillAfter(true);
        setInterpolator(new BounceInterpolator());

        mCenterWidth = width / 2;
        mCenterHeight = height / 2;
    }
    public void setRotateY(float rotateY) {
        mRotateY = rotateY;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        Matrix matrix = t.getMatrix();
        mCamera.save();

        mCamera.rotateY(-mRotateY * interpolatedTime);
        //mCamera.translate(0,0,-mRotateY * interpolatedTime);

        mCamera.getMatrix(matrix);

        mCamera.restore();

        // 通过pre方法设置矩阵作用前的偏移量来改变旋转中心
        matrix.preTranslate(-mCenterWidth, -mCenterHeight);
        matrix.postTranslate(mCenterWidth, mCenterHeight);
    }
}
