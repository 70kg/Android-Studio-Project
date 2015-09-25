package com.com.mr_wrong.ViewDraghelper;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.Utils.LogUtils;

/**
 * Created by Mr_Wrong on 2015/5/24.
 */
public class YoutubeLayout extends ViewGroup {
    private final ViewDragHelper mDragHelper;
    private View mHeaderView;
    private View mDescView;

    private float mInitialMotionX;
    private float mInitialMotionY;

    private int mDragRange;//拖拽的范围
    private int mTop;
    private float mDragOffset;

    public YoutubeLayout(Context context) {
        this(context, null);
    }

    public YoutubeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YoutubeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDragHelper = ViewDragHelper.create(this, 1f, new DragHelperCallBack());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mHeaderView = getChildAt(0);
        mDescView = getChildAt(1);
    }

    public void maximize() {
        smoothSlideTo(0f);
    }

    public void minimize() {
        smoothSlideTo(1f);
    }

    private class DragHelperCallBack extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mHeaderView;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            mTop = top;
            mDragOffset = (float) top / mDragRange;//0-1

            mHeaderView.setPivotX(mHeaderView.getWidth());
            mHeaderView.setPivotY(mHeaderView.getHeight());
            mHeaderView.setScaleX(1 - mDragOffset / 2);//1-0.5
            mHeaderView.setScaleY(1 - mDragOffset / 3);

            mDescView.setAlpha(1 - mDragOffset);

            requestLayout();

        }

        /**
         * 当松手后加速度滑动
         *
         * @param releasedChild
         * @param xvel          x加速度
         * @param yvel
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            LogUtils.e(yvel);
            int top = getPaddingTop();
            if (yvel > 0 || (yvel == 0 && mDragOffset > 0.5f)) {//向下
                top += mDragRange;
            }
            mDragHelper.settleCapturedViewAt(releasedChild.getLeft(), top);
            invalidate();
        }

        /**
         * 垂直方向的滑动范围
         *
         * @param child
         * @return
         */
        @Override
        public int getViewVerticalDragRange(View child) {
            return mDragRange;
        }

        /**
         * 控制垂直滑动的范围，不低于屏幕底端
         *
         * @param child
         * @param top
         * @param dy
         * @return
         */
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            final int topBound = getPaddingTop();
            final int bottomBound = getHeight() - mHeaderView.getHeight();
            final int newTop = Math.min(Math.max(top, topBound), bottomBound);

            return newTop;
        }
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        final float x = ev.getX();
        final float y = ev.getY();
        boolean interceptTap = false;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mInitialMotionX = x;
                mInitialMotionY = y;
                interceptTap = mDragHelper.isViewUnder(mHeaderView, (int) x, (int) y);
                break;
            case MotionEvent.ACTION_MOVE:
                final float adx = Math.abs(x - mInitialMotionX);
                final float ady = Math.abs(y - mInitialMotionY);
                final int slop = mDragHelper.getTouchSlop();//滑动的最小距离
                if (ady > slop && adx > ady) {
                    mDragHelper.cancel();
                    return false;
                }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mDragHelper.cancel();
                return false;
        }
        return mDragHelper.shouldInterceptTouchEvent(ev) || interceptTap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, 0), resolveSizeAndState(maxHeight, heightMeasureSpec, 0));

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mDragRange = getHeight() - mHeaderView.getHeight();
        mHeaderView.layout(0, mTop, r, mTop + mHeaderView.getMeasuredHeight());
        mDescView.layout(0, mTop + mHeaderView.getMeasuredHeight(), r, mTop + b);
    }

    private void smoothSlideTo(float slideOffset) {
        final int topBound = getPaddingTop();
        int y = (int) (topBound + slideOffset * mDragRange);
        if (mDragHelper.smoothSlideViewTo(mHeaderView, mHeaderView.getLeft(), y)) {
            ViewCompat.postInvalidateOnAnimation(this);

        }
    }
}
