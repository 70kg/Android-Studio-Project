package com.com.mr_wrong.Scroller;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by Mr_Wrong on 15/9/30.
 */
public class ScrollerLayout extends LinearLayout {
    private Button button1, button2;
    Scroller mScroller;

    public ScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);


    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void smoothScrollBy(int dx, int dy,int du) {
        //设置mScroller的滚动偏移量
        mScroller.startScroll(0, 0, dx, dy, du);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        button1 = (Button) getChildAt(0);
        button2 = (Button) getChildAt(1);

    }


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
           scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }


}
