package com.com.mr_wrong.SlideView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.Utils.Utils;
import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 2015/5/27.
 */
public class SlideView extends LinearLayout {
    private Context mContext;
    private LinearLayout mViewContent;
    private LinearLayout mHolder;
    private TextView tv_detele;
    private Scroller mScroll;
    private OnSlideListener mOnSlideListener;

    private int mHolderWidth = 100;
    private int mLastX = 0;
    private int mLastY = 0;
    private static final int TAN = 2;

    public SlideView(Context context) {
        this(context, null);
    }

    public SlideView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mContext = getContext();
        mScroll = new Scroller(mContext);
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        View.inflate(mContext, R.layout.slide_view, this);
        mViewContent = (LinearLayout) findViewById(R.id.view_content);
        mHolder = (LinearLayout) findViewById(R.id.holder);
        tv_detele = (TextView) findViewById(R.id.delete);
    }

    public void setButtonText(String text) {
        tv_detele.setText(text);
    }

    public void setContentView(View view) {
        mViewContent.addView(view);
    }

    public void onRequireTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int scrollX = getScrollX();
        Utils.Log("scrollX---", scrollX);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroll.isFinished()) {
                    mScroll.abortAnimation();
                }
                if (mOnSlideListener != null) {
                    mOnSlideListener.onSlide(this, OnSlideListener.SLIDE_STATUS_START_SCROLL);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                if (Math.abs(deltaX) < Math.abs(deltaY) * 2) {
                    break;//不满足滑动条件，不能横向滑动
                }
                int newScrollX = scrollX - deltaX;
                if (deltaX != 0) {
                    if (newScrollX < 0) {
                        newScrollX = 0;
                    } else if (newScrollX > mHolderWidth) {
                        newScrollX = mHolderWidth;
                    }
                    this.scrollTo(newScrollX, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                int newscrollx = 0;
                if (scrollX - mHolderWidth * 0.75 > 0) {
                    newscrollx = mHolderWidth;
                }
                this.smoothScrollTo(newscrollx, 0);
                if (mOnSlideListener != null) {
                    mOnSlideListener.onSlide(this, newscrollx == 0 ? OnSlideListener.SLIDE_STATUS_OFF :
                            OnSlideListener.SLIDE_STATUS_ON);
                }
                break;
        }
        mLastY = y;
        mLastX = x;
    }

    /**
     * 滚动到目标位置
     *
     * @param x 目标x
     * @param y 目标y
     */
    private void smoothScrollTo(int x, int y) {
        int scrollX = getScrollX();
        int dx = x - scrollX;

        int scrollY = getScrollY();
        int dy = y - scrollY;

        mScroll.startScroll(scrollX, scrollY, dx, dy, Math.abs(dx * 3));

        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroll.computeScrollOffset()) {
            scrollTo(mScroll.getCurrX(), mScroll.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }

    public void setOnSlideListener(OnSlideListener listener) {
        this.mOnSlideListener = listener;
    }

    public interface OnSlideListener {
        public static final int SLIDE_STATUS_OFF = 0;
        public static final int SLIDE_STATUS_START_SCROLL = 1;
        public static final int SLIDE_STATUS_ON = 2;

        public void onSlide(View view, int status);
    }
}
