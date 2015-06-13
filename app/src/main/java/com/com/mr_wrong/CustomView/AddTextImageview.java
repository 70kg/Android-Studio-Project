package com.com.mr_wrong.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.Utils.LogUtils;
import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 15/6/10.
 */
public class AddTextImageview extends ImageView {
    private String mText = "";
    private Paint mPaint;
    private Rect mTextBound;
    private int mTextSize;
    private int mTextColor;
    private int mTextLeft;
    private int mTextTop;
    private int mTextGravity;
    private int mTextWidth;
    private int mTextHeight;
    private int mWidth;
    private int mHeight;
    private int x;
    private int y;

    public AddTextImageview(Context context) {
        this(context, null);
    }

    public AddTextImageview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddTextImageview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AddTextImageview, defStyleAttr, 0);
        mTextSize = array.getDimensionPixelSize(R.styleable.AddTextImageview_textsize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics()));
        mTextLeft = array.getDimensionPixelSize(R.styleable.AddTextImageview_textleft, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics()));
        mTextTop = array.getDimensionPixelSize(R.styleable.AddTextImageview_texttop, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics()));
        mTextColor = array.getColor(R.styleable.AddTextImageview_textcolor, Color.BLACK);
        mText = array.getString(R.styleable.AddTextImageview_text);
        mTextGravity = array.getInt(R.styleable.AddTextImageview_textgravity, 0);
        array.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);

        mTextBound = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
        mTextWidth = mTextBound.width();
        mTextHeight = mTextBound.height();

    }

    public void setTextGravity(int TextGravity) {
        this.mTextGravity = TextGravity;
        judegtextgravity(TextGravity);
        invalidate();
    }

    private void judegtextgravity(int TextGravity) {
        switch (TextGravity) {
            case 0:
                x = getPaddingLeft();
                y = (int) -mPaint.ascent();
                break;
            case 1:
                x = mWidth / 2 - mTextWidth / 2;
                y = (int) -mPaint.ascent();
                break;
            case 2:
                x = mWidth - mTextWidth - getPaddingRight();
                y = (int) -mPaint.ascent();
                break;
            case 3:
                x = getPaddingLeft();
                y = (int) (mHeight / 2 - (mPaint.descent() + mPaint.ascent()) / 2);
                break;
            case 4:
                x = mWidth / 2 - mTextWidth / 2;
                y = (int) (mHeight / 2 - (mPaint.descent() + mPaint.ascent()) / 2);
                break;
            case 5:
                x = mWidth - mTextWidth - getPaddingRight();
                y = (int) (mHeight / 2 - (mPaint.descent() + mPaint.ascent()) / 2);
                break;
            case 6:
                x = getPaddingLeft();
                y =mHeight-mTextHeight-getPaddingBottom();
                break;
            case 7:
                x = mWidth / 2 - mTextWidth / 2;
                y =mHeight-mTextHeight-getPaddingBottom();
                break;
            case 8:
                x = mWidth - mTextWidth - getPaddingRight();
                y =mHeight-mTextHeight-getPaddingBottom();
                break;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        judegtextgravity(mTextGravity);
        x += mTextLeft;
        y += mTextTop;
        LogUtils.e(y);
        canvas.drawText(mText, x, y, mPaint);

    }


}
