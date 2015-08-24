package com.com.mr_wrong.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import java.util.HashMap;
import java.util.Set;

/**
 * @author : 苦咖啡
 * @version : 1.0
 * @date ：2015年4月19日
 * @blog : http://blog.csdn.net/cyp331203
 * @desc :
 */
public class InterceptorFrameLayout extends FrameLayout {


    /**
     * 代表滑动方向向上
     */
    public static final int ORIENTATION_UP = 0x1;// 0000 0001
    /**
     * 代表滑动方向向下
     */
    public static final int ORIENTATION_DOWN = 0x2;// 0000 0010
    /**
     * 代表滑动方向向左
     */
    public static final int ORIENTATION_LEFT = 0x4;// 0000 0100
    /**
     * 代表滑动方向向右
     */
    public static final int ORIENTATION_RIGHT = 0x8;// 0000 1000
    /**
     * 代表滑动方向的所有方向
     */
    public static final int ORIENTATION_ALL = 0x10;// 0001 0000

    /**
     * 存放view的左上角的x和y坐标
     */
    static int[] touchLocation = new int[2];

    /**
     * 用来代表触发移动事件的最短距离，如果小于这个距离就不触发移动控件，如viewpager就是用这个距离来判断用户是否翻页
     */
    private int mTouchSlop;

    /**
     * 用来记录Down事件发生时的x坐标
     */
    private float downX;
    /**
     * 用来记录Down事件发生时的y坐标
     */
    private float downY;
    /**
     * 用来存放需要自主控制事件分发的子view，以及其对应的滑动方向
     */
    private HashMap<View, Integer> mViewAndOrientation = new HashMap<View, Integer>();
    /**
     * 表示某次事件发生时，找到的mViewAndOrientation中符合条件的子view
     */
    private View mFirstTarget = null;
    private ViewConfiguration configuration;

    public InterceptorFrameLayout(Context context, AttributeSet attrs,
                                  int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public InterceptorFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InterceptorFrameLayout(Context context) {
        super(context);
        init();
    }

    private void init() {
        configuration = ViewConfiguration.get(getContext());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        // 意思应该是触发移动事件的最短距离，如果小于这个距离就不触发移动控件，
        // 如viewpager就是用这个距离来判断用户是否翻页
        mTouchSlop = configuration.getScaledTouchSlop();

        if (mFirstTarget != null) {
            // mFirstTarget不为空，表示最近的一次DOWN事件已经被mViewAndOrientation集合中的某个子view响应
            // 于是将后续的事件继续分发给这个子view
            boolean flag = mFirstTarget.dispatchTouchEvent(ev);

            // 如果flag=true，表示事件被完全消耗，结束了，如果事件是ACTION_CANCEL或者ACTION_UP，
            // 也代表事件的结束，于是将mFirstTarget置空，便于下一次DOWN事件的响应
            if (flag
                    && (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP)) {
                mFirstTarget = null;
            }
            // 返回flag
            return flag;
        }

        // 拿到本次事件的坐标，由于只需要计算差值，所以getX也可以
        final float currentX = ev.getX();
        final float currentY = ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mFirstTarget = findTargetView(ev, ORIENTATION_ALL);
                downX = currentX;
                downY = currentY;
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(currentX - downX) / Math.abs(currentY - downY) > 0.5f
                        && Math.abs(currentX - downX) > mTouchSlop) {
                    System.out.print("左右滑动");
                    // 左右滑动
                    if (currentX - downX > 0) {
                        // 右滑
                        mFirstTarget = findTargetView(ev, ORIENTATION_RIGHT);
                        System.out.println("mFirstTarget=" + mFirstTarget);
                    } else {
                        // 左滑
                        mFirstTarget = findTargetView(ev, ORIENTATION_LEFT);
                        System.out.println("mFirstTarget=" + mFirstTarget);
                    }
                } else if (Math.abs(currentY - downY) / Math.abs(currentX - downX) > 0.5f
                        && Math.abs(currentY - downY) > mTouchSlop) {
                    System.out.print("上下滑动");
                    // 上下滑动
                    if (currentY - downY > 0) {
                        // 向下
                        mFirstTarget = findTargetView(ev, ORIENTATION_DOWN);
                        System.out.println("mFirstTarget=" + mFirstTarget);
                    } else {
                        // 向上
                        mFirstTarget = findTargetView(ev, ORIENTATION_UP);
                        System.out.println("mFirstTarget=" + mFirstTarget);
                    }
                    mFirstTarget = null;
                }
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mFirstTarget = null;
                break;
        }

        // 走到这里，只要mFirstTarget不为空，则在集合中找到了对应的子view，
        // 则返回true，表示本次事件被消耗，不继续分发
        if (mFirstTarget != null) {
            return true;
        } else {
            return super.dispatchTouchEvent(ev);
        }
    }

    /**
     * 在集合中查找对应event和方向参数的view，找到了则返回，没找到返回null
     */
    private View findTargetView(MotionEvent ev, int orientation) {
        // mViewAndOrientation为存放要监测触摸事件的子view和对应方向参数的集合
        Set<View> keySet = mViewAndOrientation.keySet();
        for (View view : keySet) {
            Integer ori = mViewAndOrientation.get(view);

            // 由于所有的方向参数都是二进制相互与运算为0的
            // 所以这里使用与运算来判断方向是否符合
            // 这里所有的判断条件是：
            // ①该子view在mViewAndOrientation集合内
            // ②方向一致
            // ③触摸事件落在该子view的范围内
            // ④该子view可以消费掉本次事件
            // 同时满足上面四个条件，则代表该子view是我们要找的子view，于是返回
            if ((ori & orientation) == orientation && isTouchInView(ev, view)
                    && view.dispatchTouchEvent(ev)) {
                return view;
            }
        }
        return null;
    }

    public static boolean isTouchInView(MotionEvent ev, View view) {
        view.getLocationOnScreen(touchLocation);
        float motionX = ev.getRawX();
        float motionY = ev.getRawY();

        // 返回是否在范围内
        return motionX >= touchLocation[0]
                && motionX <= (touchLocation[0] + view.getWidth())
                && motionY >= touchLocation[1]
                && motionY <= (touchLocation[1] + view.getHeight());
    }

    /**
     * 添加拦截
     */
    public void addInterceptorView(final View view, final int orientation) {
        // 到主线程执行
        BaseApplication.getMainThreadHandler().post(new Runnable() {

            @Override
            public void run() {
                if (!mViewAndOrientation.containsKey(view)) {
                    mViewAndOrientation.put(view, orientation);
                }
            }
        });
    }

    /**
     * 去除拦截效果
     */
    public void removeInterceptorView(final View v) {
        // 到主线程执行
        BaseApplication.getMainThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                if (!mViewAndOrientation.containsKey(v)) {
                    mViewAndOrientation.remove(v);
                }
            }
        });
    }
}
