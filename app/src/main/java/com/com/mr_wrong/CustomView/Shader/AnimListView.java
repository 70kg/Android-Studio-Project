package com.com.mr_wrong.CustomView.Shader;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Mr_Wrong on 15/8/25.
 * 关于camera的
 */
public class AnimListView extends ListView {
    private Camera mCamera;
    private Matrix mMatrix;

    public AnimListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCamera = new Camera();
        mMatrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCamera.save();
        mCamera.rotate(30, 0, 0);
        mCamera.getMatrix(mMatrix);
        mMatrix.preTranslate(-getWidth() / 2, -getHeight() / 2);
        mMatrix.postTranslate(getWidth() / 2, getHeight() / 2);
        canvas.concat(mMatrix);
        super.onDraw(canvas);
        mCamera.restore();
    }
}