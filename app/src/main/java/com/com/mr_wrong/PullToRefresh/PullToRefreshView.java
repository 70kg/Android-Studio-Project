package com.com.mr_wrong.PullToRefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.Utils.LogUtils;
import com.Utils.Utils;

/**
 * Created by Mr_Wrong on 15/7/11.
 */
public class PullToRefreshView extends ViewGroup {
    private final static int DRAG_MAX_DISTANCE = 120;
    private View mTarget;
    private ImageView mRefreshview;
    private int mTotalDragDistance;
    private boolean mRefreshing;
    private int mTouchSlop;
    private int mCurrentOffSetTop;
    private boolean mIsBeingDraged;
    private float mInitDownY;
    private BaseRefreshView mBaseRefreshView;

    private float mCurrentDragPercent;

    public PullToRefreshView(Context context) {
        this(context, null);
    }

    public PullToRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mRefreshview = new ImageView(context);
        //mRefreshview.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        mBaseRefreshView = new SunRefreshView(getContext(),this);
        mRefreshview.setImageDrawable(mBaseRefreshView);
        addView(mRefreshview);
        mTotalDragDistance = Utils.dip2px(context, DRAG_MAX_DISTANCE);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ensureTarget();

        widthMeasureSpec = MeasureSpec.makeMeasureSpec(getWidth() - getPaddingLeft() - getPaddingRight(), MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(getHeight() - getPaddingTop() - getPaddingBottom(), MeasureSpec.EXACTLY);

        mTarget.measure(widthMeasureSpec, heightMeasureSpec);
        mRefreshview.measure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 获取child
     */
//    private void ensuretarget() {
//        for (int i = 0; i < getChildCount(); i++) {
//            mTarget = getChildAt(0);
//        }
//    }
    private void ensureTarget() {
        if (mTarget != null)
            return;
        if (getChildCount() > 0) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child != mRefreshview)
                    mTarget = child;
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isEnabled() || mRefreshing) {
            return false;
        }
        final int action = ev.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mRefreshing = false;
                setTargetOffsetTop(0);
                mIsBeingDraged = false;
                mInitDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float yd = ev.getY() - mInitDownY;
                if (yd > mTouchSlop && !mIsBeingDraged) {
                    mIsBeingDraged = true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsBeingDraged = false;
                break;
        }
        return mIsBeingDraged;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mIsBeingDraged) {
            return super.onTouchEvent(event);
        }
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_MOVE:

                final float y = event.getY();
                final float yDiff = y - mInitDownY;//手指移动距离
                final float scrollTop = yDiff * 0.5f;//*0.5
                mCurrentDragPercent = scrollTop / mTotalDragDistance;
                //Log.e("mTotalDragDistance----", mTotalDragDistance+"");//330
                if (mCurrentDragPercent < 0) {
                    return false;
                }
                float boundedDragPercent = Math.min(1f, Math.abs(mCurrentDragPercent));
                float extraOS = Math.abs(scrollTop) - mTotalDragDistance;
                float slingshotDist = mTotalDragDistance;//330弹弓
                float tensionSlingshotPercent = Math.max(0,        //张力弹弓
                        Math.min(extraOS, slingshotDist * 2) / slingshotDist);
                //Log.e("tensionSlingshotPercent----", tensionSlingshotPercent+"");//0-0.23....
                float tensionPercent = (float) ((tensionSlingshotPercent / 4) - Math.pow(
                        (tensionSlingshotPercent / 4), 2)) * 2f;
                //Log.e("tensionPercent----", tensionPercent+"");//0-0.24...
                float extraMove = (slingshotDist) * tensionPercent / 2;
                int targetY = (int) ((slingshotDist * boundedDragPercent) + extraMove);
                //Log.e("mCurrentDragPercent----", mCurrentDragPercent+"");//0-0.24...
                 mBaseRefreshView.setPercent(mCurrentDragPercent, true);//在这里将百分比穿进去的啊
                //Log.e("targetY - mCurrentOffsetTop----", targetY - mCurrentOffsetTop+"");//很小整数
                setTargetOffsetTop(targetY - mCurrentOffSetTop);//如果注释，向下滑动list没反应，targetY - mCurrentOffsetTop一直变大
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        ensureTarget();

        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getPaddingRight();
        int bottom = getPaddingBottom();

        mTarget.layout(left, top + mCurrentOffSetTop, left + width - right, top + mCurrentOffSetTop + height - bottom);
        mRefreshview.layout(left, top, left + width - right, top + height - bottom);
    }

    /**
     * 设置offset
     *
     * @param OffsetTop
     */
    public void setTargetOffsetTop(int OffsetTop) {
        LogUtils.e(OffsetTop);//向下就是正，向上就是负
        mTarget.offsetTopAndBottom(OffsetTop);//相对的移动距离
        mBaseRefreshView.offsetTopAndBottom(OffsetTop);
        mCurrentOffSetTop = mTarget.getTop();
        invalidate();
    }
    public int getTotalDragDistance() {
        return mTotalDragDistance;
    }
}
