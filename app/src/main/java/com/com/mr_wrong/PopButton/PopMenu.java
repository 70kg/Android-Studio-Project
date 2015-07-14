package com.com.mr_wrong.PopButton;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Mr_Wrong on 15/7/13.
 */
public class PopMenu extends LinearLayout {
    private final static int STRIP_HEIGHT = 6;//默认的指示条高度
    private int mChildCounts = 3;
    private Paint mPaint;
    private int mStripHeight;
    private int mStripColor;

    private View mChild;

    public PopMenu(Context context) {
        this(context, null);
    }

    public PopMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mStripHeight = (int) (STRIP_HEIGHT * getResources().getDisplayMetrics().density + .5f);
        this.setOrientation(LinearLayout.HORIZONTAL);

        getchilds();
        setWillNotDraw(false);
    }

    private void getchilds() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        for (int i = 0; i < mChildCounts; i++) {
            TextView textView = new TextView(getContext());
            textView.setLayoutParams(layoutParams);
            textView.setGravity(Gravity.CENTER);
            textView.setText("textview" + i);
            this.addView(textView);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mChild = getChildAt(1);
        int left = mChild.getLeft();
        int right = mChild.getRight();
        int height = getHeight();
        mPaint.setColor(Color.RED);
        canvas.drawRect(left, height - mStripHeight, right, height, mPaint);

    }
}
