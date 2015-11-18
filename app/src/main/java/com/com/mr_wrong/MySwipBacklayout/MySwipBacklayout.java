package com.com.mr_wrong.MySwipBacklayout;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by Mr_Wrong on 15/11/18.
 */
public class MySwipBacklayout extends FrameLayout {
    private int mWidth;//整个宽度
    private int mHeight;//整个宽度
    private int mSwipLeft;//左滑的距离
    private int mSwipWidth;//左滑的最大距离
    private ViewGroup mContentLyout;//内容
    private ViewGroup mBehindLyout;//后面的返回
    private ViewDragHelper mViewHelper;
    private GestureDetectorCompat mGestureDetecyor;
    private SwipeBackListener mSwipBackListener;
    private SwipBackStatusEnum mStatusEnum = SwipBackStatusEnum.close;


    public enum SwipBackStatusEnum {
        swip,
        open,
        close
    }

    public interface SwipeBackListener {
        void onOpen();

        void onClose();

        void onSwipe(float percent);
    }

    public void setOnSwipeBackListener(SwipeBackListener swipBackListener) {
        this.mSwipBackListener = swipBackListener;
    }

    public MySwipBacklayout(Context context) {
        super(context);
        init();
    }

    public MySwipBacklayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MySwipBacklayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mViewHelper = ViewDragHelper.create(this, 1.0f, new DragHelper());
        mGestureDetecyor = new GestureDetectorCompat(getContext(), new SwipDeteor());
    }

    class SwipDeteor extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return Math.abs(distanceX) >= Math.abs(distanceY);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContentLyout = (ViewGroup) getChildAt(1);
        mBehindLyout = (ViewGroup) getChildAt(0);
        mBehindLyout.setClickable(true);
        mContentLyout.setClickable(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = mBehindLyout.getMeasuredWidth();
        mHeight = mBehindLyout.getMeasuredHeight();
        mSwipWidth = (int) (mWidth * 0.15);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mContentLyout.layout(mSwipLeft, 0, mWidth + mSwipLeft, mHeight);
        mBehindLyout.layout(0, 0, mWidth, mHeight);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewHelper.shouldInterceptTouchEvent(ev) && mGestureDetecyor.onTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewHelper.processTouchEvent(event);
        return false;
    }

    class DragHelper extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View view, int i) {
            return true;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return mWidth;
        }

        //限制水平滑动的范围
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (mSwipLeft + dx < 0) {
                return 0;
            } else if (mSwipLeft + dx > mSwipWidth) {
                return mSwipWidth;
            } else {
                return left;
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (xvel > 0) {
                openEvent();
            } else if (xvel < 0) {
                closeEvent();
            }
            if (releasedChild == mContentLyout && mSwipLeft > mSwipWidth * 0.5) {
                openEvent();
            } else {
                closeEvent();
            }
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (changedView == mContentLyout) {
                mSwipLeft = left;
            } else {
                mSwipLeft = mSwipLeft + left;
            }
            dispatchEvent(mSwipLeft);
            invalidate();
        }
    }



    private void dispatchEvent(int swipLeft) {
        if (mSwipBackListener == null) {
            return;
        }
        setStatus(mSwipLeft);
        float percent = swipLeft /mSwipWidth;
        mSwipBackListener.onSwipe(percent);
        if (mStatusEnum == SwipBackStatusEnum.open) {
            //open回调在openEvent里面 这样在松手的时候再触发效果更好
            mBehindLyout.setClickable(true);
        } else if (mStatusEnum == SwipBackStatusEnum.close) {
            mSwipBackListener.onClose();
            mBehindLyout.setClickable(false);
        }
    }
    private void setStatus(int swipLeft) {
        if (swipLeft == 0) {
            mStatusEnum = SwipBackStatusEnum.close;
        } else if (swipLeft == mSwipWidth) {
            mStatusEnum = SwipBackStatusEnum.open;
        } else {
            mStatusEnum = SwipBackStatusEnum.swip;
        }
    }
    @Override
    public void computeScroll() {
        if (mViewHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private void closeEvent() {
        if (mViewHelper.smoothSlideViewTo(mContentLyout, 0, 0)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private void openEvent() {
        if (mViewHelper.smoothSlideViewTo(mContentLyout, mSwipWidth, 0)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        MySwipBacklayout.this.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (null != mSwipBackListener) mSwipBackListener.onOpen();
            }
        }, 200);
    }
}
