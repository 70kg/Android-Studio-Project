package com.com.mr_wrong.PullToRefresh;

import android.content.Context;
import android.view.animation.Interpolator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;

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
    private int mFrom;
    private float mFromDragPercent;
    private float mCurrentDragPercent;
    private Interpolator mInterpolator;

    public PullToRefreshView(Context context) {
        this(context, null);
    }

    public PullToRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mRefreshview = new ImageView(context);
        mInterpolator = new BounceInterpolator();
        mBaseRefreshView = new SunRefreshView(getContext(), this);
        mRefreshview.setImageDrawable(mBaseRefreshView);
        addView(mRefreshview);
        mTotalDragDistance = Utils.dip2px(context, DRAG_MAX_DISTANCE);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        setWillNotDraw(false);

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
                // LogUtils.e(boundedDragPercent);//0-1

                float extraOS = Math.abs(scrollTop) - mTotalDragDistance;
                //LogUtils.e(extraOS);//-330-+300+

                float slingshotDist = mTotalDragDistance;//330弹弓
                float tensionSlingshotPercent = Math.max(0, extraOS / 330);//张力弹弓
                //Log.e("tensionSlingshotPercent----", tensionSlingshotPercent+"");//0-0.23....

                float tensionPercent = (float) ((tensionSlingshotPercent / 4) -
                        Math.pow((tensionSlingshotPercent / 4), 2)) * 2f;
                //Log.e("tensionPercent----", tensionPercent+"");//0-0.24...

                float extraMove = (slingshotDist) * tensionPercent / 2;
                int targetY = (int) ((slingshotDist * boundedDragPercent) + extraMove);
                //LogUtils.e(targetY);//0-330+

                //Log.e("mCurrentDragPercent----", mCurrentDragPercent+"");//0-0.24...
                mBaseRefreshView.setPercent(mCurrentDragPercent, true);//在这里将百分比穿进去的啊
                //Log.e("targetY - mCurrentOffsetTop----", targetY - mCurrentOffsetTop+"");//很小整数
                setTargetOffsetTop(targetY - mCurrentOffSetTop);//如果注释，向下滑动list没反应，targetY - mCurrentOffsetTop一直变大
                break;
            case MotionEvent.ACTION_UP:
                animateOffsetToStartPosition();

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
        //LogUtils.e(OffsetTop);//向下就是正，向上就是负
        mTarget.offsetTopAndBottom(OffsetTop);//相对的移动距离
        mBaseRefreshView.offsetTopAndBottom(OffsetTop);
        mCurrentOffSetTop = mTarget.getTop();
        invalidate();
    }

    public int getTotalDragDistance() {
        return mTotalDragDistance;
    }

    /**
     * 滚动到起始位置
     */
    private void animateOffsetToStartPosition() {
        mFrom = mCurrentOffSetTop;
        //Log.e("mFrom-====", mFrom+"");330
        mFromDragPercent = mCurrentDragPercent;
        long animationDuration = Math.abs((long) (700 * mFromDragPercent));

        mAnimateToStartPosition.reset();
        mAnimateToStartPosition.setDuration(animationDuration);
        mAnimateToStartPosition.setInterpolator(mInterpolator);
        mAnimateToStartPosition.setAnimationListener(mToStartListener);

        mRefreshview.clearAnimation();
        mRefreshview.startAnimation(mAnimateToStartPosition);
    }

    private final Animation mAnimateToStartPosition = new Animation() {
        @Override
        public void applyTransformation(float interpolatedTime, Transformation t) {
            //Log.e("mAnimateToStartPosition----interpolatedTime", interpolatedTime+"");
            moveToStart(interpolatedTime);
        }
    };

    private void moveToStart(float interpolatedTime) {
        int targetTop = mFrom - (int) (mFrom * interpolatedTime);
        float targetPercent = mFromDragPercent * (1.0f - interpolatedTime);
        int offset = targetTop - mTarget.getTop();

        mCurrentDragPercent = targetPercent;
        mBaseRefreshView.setPercent(mCurrentDragPercent, true);
        setTargetOffsetTop(offset);
    }

    private Animation.AnimationListener mToStartListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mBaseRefreshView.stop();
            mCurrentOffSetTop = mTarget.getTop();
        }
    };
}
