package com.com.mr_wrong.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Mr_Wrong on 15/6/11.
 */
public class TestTextView extends View {
    private Paint paint;
    private Rect bounds;
    public TestTextView(Context context) {
        this(context, null);
    }

    public TestTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(32);
        bounds = new Rect();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        String text = "this is my text";
        paint.getTextBounds(text, 0, text.length(), bounds);
        Log.d("-----", "onDraw " + bounds);

        int x = (getWidth() - bounds.width()) / 2;
        int y = (getHeight()-bounds.height())/2;

        paint.setColor(0xff008800);
        bounds.offset(x, y);
        canvas.drawRect(bounds, paint);

        paint.setColor(0xffeeeeee);
        canvas.drawText(text, x, y, paint);
    }
}
