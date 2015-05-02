package com.mr_wrong.Bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 2015/5/1.
 */
public class BezierView extends FrameLayout {
    private static final float DEFAULT_RADIUS = 50;
    private float startX = 100;//起始点的坐标
    private float startY = 100;
    private float anchorX;//锚点的坐标
    private float anchorY;
    private float x;//手势的坐标
    private float y;
    private Path path;
    private Paint paint;
    private float radius;
    private float speed = 15;//变瘦的速度，越大变化越慢
    private float explored_radius = 9;
    private ImageView exploredImageView;
    private ImageView tipImageView;
    private boolean istouch;
    // 判断动画是否开始
    private boolean isAnimStart;

    public BezierView(Context context) {
        this(context, null);
    }

    public BezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {

        path = new Path();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.RED);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        exploredImageView = new ImageView(getContext());
        exploredImageView.setLayoutParams(params);

        tipImageView = new ImageView(getContext());
        tipImageView.setLayoutParams(params);
        tipImageView.setImageResource(R.drawable.skin_tips_new);

        addView(exploredImageView);
        addView(tipImageView);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        exploredImageView.setX(startX - exploredImageView.getWidth() / 2);
        exploredImageView.setY(startX - exploredImageView.getHeight() / 2);

        tipImageView.setX(startX - tipImageView.getWidth() / 2);
        tipImageView.setY(startY - tipImageView.getHeight() / 2);
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * 计算位置
     */
    private void calculate() {
        float distance = (float) Math.sqrt(Math.pow(y - startY, 2) + Math.pow(x - startX, 2));
        radius = DEFAULT_RADIUS - distance / speed;
        if (radius < explored_radius) {
            isAnimStart = true;
            //explore
            exploredImageView.setVisibility(View.VISIBLE);
            exploredImageView.setImageResource(R.drawable.tip_anim);
            ((AnimationDrawable) exploredImageView.getDrawable()).stop();
            ((AnimationDrawable) exploredImageView.getDrawable()).start();

            tipImageView.setVisibility(View.GONE);
        }

        //计算四个点的位置
        float offsetX = (float) (radius * Math.sin(Math.atan((y - startY) / (x - startX))));
        float offsetY = (float) (radius * Math.cos(Math.atan((y - startY) / (x - startX))));

        float x1 = startX - offsetX;
        float y1 = startY + offsetY;
        float x2 = x - offsetX;
        float y2 = y + offsetY;
        float x3 = x + offsetX;
        float y3 = y - offsetY;
        float x4 = startX + offsetX;
        float y4 = startY - offsetY;

        path.reset();
        path.moveTo(x1, y1);
        path.quadTo(anchorX, anchorY, x2, y2);
        path.lineTo(x3, y3);
        path.quadTo(anchorX, anchorY, x4, y4);
        path.lineTo(x1, y1);

        tipImageView.setX(x - tipImageView.getWidth() / 2);
        tipImageView.setY(y - tipImageView.getHeight() / 2);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isAnimStart || !istouch) {
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.OVERLAY);
        } else {
            calculate();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.OVERLAY);
            canvas.drawPath(path, paint);
            canvas.drawCircle(startX, startY, radius, paint);
            canvas.drawCircle(x, y, radius, paint);
        }

        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //判断是否触摸tipimageview
            Rect rect = new Rect();
            int[] location = new int[2];
            tipImageView.getDrawingRect(rect);
            tipImageView.getLocationOnScreen(location);
            rect.left = location[0];
            rect.top = location[1];
            rect.right = location[0] + rect.right;
            rect.bottom = rect.bottom + location[1];
            if (rect.contains((int) event.getRawX(), (int) event.getRawY())) {
                istouch = true;
            }
        } else if (event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP) {
            istouch = false;
            tipImageView.setX(startX - tipImageView.getWidth() / 2);
            tipImageView.setY(startX - tipImageView.getHeight() / 2);
        }
        invalidate();

        anchorX = (event.getX() + startX) / 2;
        anchorY = (event.getY() + startY) / 2;
        x = event.getX();
        y = event.getY();
        return true;
    }

    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public float getStartY() {
        return startY;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getExplored_radius() {
        return explored_radius;
    }

    public void setExplored_radius(float explored_radius) {
        this.explored_radius = explored_radius;
    }
}
