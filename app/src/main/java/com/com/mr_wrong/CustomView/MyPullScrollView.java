package com.com.mr_wrong.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 15/6/16.
 */
public class MyPullScrollView extends ScrollView {
    private static final float SCROLL_RATIO = 0.5F;
    private View mHeaderView;
    private int mHeaderHeight;
    private View mContent;
    private Rect mContentRect = new Rect();
    private int mHeaderVisibleHeight;
    private boolean isMoving;
    private int mInitTop, mInitBottom;
    private int mCurrentTop, mCurrentBottom;
    private float mTouchDownY;
    private boolean istop;

    private enum State {
        UP, DOWN, NORMAL
    }

    private State mState = State.NORMAL;

    public MyPullScrollView(Context context) {
        this(context, null);
    }

    public MyPullScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyPullScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOverScrollMode(OVER_SCROLL_NEVER);

        if (null != attrs) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PullScrollView);

            if (ta != null) {
                mHeaderHeight = (int) ta.getDimension(R.styleable.PullScrollView_headerHeight, -1);
                mHeaderVisibleHeight = (int) ta.getDimension(R.styleable
                        .PullScrollView_headerVisibleHeight, -1);
                ta.recycle();
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        mContent = getChildAt(0);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (getScrollY() == 0) {
            istop = true;
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            mTouchDownY = ev.getY();
            mCurrentTop = mInitTop = mHeaderView.getTop();
            mCurrentBottom = mInitBottom = mHeaderView.getBottom();
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mContent != null) {
            dotouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    private void dotouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            doActionMove(ev);
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            //回滚动画
            mHeaderView.layout(mHeaderView.getLeft(), mInitTop, mHeaderView.getRight(), mInitBottom);
            mContent.layout(mContentRect.left, mContentRect.top, mContentRect.right, mContentRect.bottom);
        }
    }

    private void doActionMove(MotionEvent ev) {
        if (getScrollY() == 0) {
            mState = State.NORMAL;
            if (istop) {
                istop = false;
                mTouchDownY = ev.getY();
            }
        }
        float deltaY = ev.getY() - mTouchDownY;
        //判断滑动方向
        if (deltaY < 0 && mState == State.NORMAL) {
            mState = State.UP;
        } else if (deltaY > 0 && mState == State.NORMAL) {
            mState = State.DOWN;
        }

        if (mState == State.UP) {
            isMoving = false;
            deltaY = deltaY < 0 ? deltaY : 0;
        } else if (mState == State.DOWN) {
            if (getScrollY() <= deltaY) {
                isMoving = true;
            }
            deltaY = deltaY > 0 ? deltaY : 0;
        }

        if (isMoving) {//向下拖拽
            if (mContentRect.isEmpty()) {
                mContentRect.set(mContent.getLeft(), mContent.getTop(), mContent.getRight(),
                        mContent.getBottom());
            }

            //计算header移动的距离
            float headerMoveHeight = deltaY * SCROLL_RATIO * 0.5f;
            mCurrentBottom = (int) (mInitBottom + headerMoveHeight);
            mCurrentTop = (int) (mInitTop + headerMoveHeight);

            //计算content的移动距离
            float contentMoveHeight = deltaY * SCROLL_RATIO;
            int top = (int) (mContentRect.top + contentMoveHeight);
            int bottom = (int) (mContentRect.bottom + contentMoveHeight);

            if (mCurrentBottom >= top) {
                mHeaderView.layout(mHeaderView.getLeft(), mCurrentTop, mHeaderView.getRight(),
                        mCurrentBottom);
                mContent.layout(mContentRect.left, top, mContentRect.right, bottom);
            }
        }
    }

    public void setHeader(View headview) {
        this.mHeaderView = headview;
    }
}
