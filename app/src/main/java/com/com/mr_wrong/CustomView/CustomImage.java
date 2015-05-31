package com.com.mr_wrong.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 15/5/30.
 */
public class CustomImage extends View {
    private int mTitleSize;
    private String mTitle;
    private Bitmap mImage;
    private int mTitleColor;
    private Paint mPaint;
    private Rect rect;
    private Rect mTextBound;
    private int mWidth;
    private int mHeight;
    private int mImageSacle;

    public CustomImage(Context context) {
        this(context, null);
    }

    public CustomImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomImageView, defStyleAttr, 0);
        mTitleSize = array.getDimensionPixelSize(R.styleable.CustomImageView_titlesize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                16, getResources().getDisplayMetrics()));
        mTitle = array.getString(R.styleable.CustomImageView_titletext);
        mImage = BitmapFactory.decodeResource(getResources(), array.getResourceId(R.styleable.CustomImageView_image, 0));
        mTitleColor = array.getColor(R.styleable.CustomImageView_titlecolor, Color.BLACK);
        mImageSacle = array.getInt(R.styleable.CustomImageView_imageScaleType, 0);
        array.recycle();

        mPaint = new Paint();
        mPaint.setTextSize(mTitleSize);
        rect = new Rect();
        mTextBound = new Rect();
        mPaint.getTextBounds(mTitle, 0, mTitle.length(), mTextBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        /**
         * 设置宽度
         */
        if (mode == MeasureSpec.EXACTLY) {//match_parent
            mWidth = size;
        } else {
            int widthbyimage = getPaddingLeft() + getPaddingRight() + mImage.getWidth();
            int widthbytext = getPaddingLeft() + getPaddingRight() + mTextBound.width();
            if (mode == MeasureSpec.AT_MOST) {//warp_content
                int deswidth = Math.max(widthbyimage, widthbytext);
                mWidth = Math.min(deswidth, size);
            }
        }
        /**
         * 设置高度
         */
        mode = MeasureSpec.getMode(heightMeasureSpec);
        size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            mHeight = size;
        } else {
            int desheight = getPaddingBottom() + getPaddingTop() + mTextBound.height() + mImage.getHeight();
            if (mode == MeasureSpec.AT_MOST) {
                mHeight = Math.min(desheight, size);
            }
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        rect.left = getPaddingLeft();
        rect.right = mWidth - getPaddingRight();
        rect.top = getPaddingTop();
        rect.bottom = getHeight() - getPaddingBottom();

        mPaint.setColor(mTitleColor);
        mPaint.setStyle(Paint.Style.FILL);
        if (mTextBound.width() > mWidth) {
            //文字的宽度大于宽度  改变文字为。。
            TextPaint paint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(mTitle, paint, (float) mWidth - getPaddingLeft() - getPaddingRight(),
                    TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg, getPaddingLeft(), mHeight - getPaddingBottom(), mPaint);

        } else {
            canvas.drawText(mTitle, (mWidth - mTextBound.width()) / 2, mHeight - getPaddingBottom(), mPaint);
        }
        rect.bottom -= mTextBound.height();

        if (mImageSacle == 0) {//fill_xy
            canvas.drawBitmap(mImage, null, rect, mPaint);
        } else {
            rect.left = mWidth / 2 - mImage.getWidth() / 2;
            rect.right = mWidth / 2 + mImage.getWidth() / 2;
            rect.bottom = (mHeight - mTextBound.height() + mImage.getHeight()) / 2;
            rect.top = (mHeight - mTextBound.height() - mImage.getHeight()) / 2;
            canvas.drawBitmap(mImage, null, rect, mPaint);
        }
    }
}
