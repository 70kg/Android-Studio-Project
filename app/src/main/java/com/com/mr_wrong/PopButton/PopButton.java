package com.com.mr_wrong.PopButton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 2015/5/26.
 */
public class PopButton extends Button implements PopupWindow.OnDismissListener {
    private int normalBg;
    private int pressBg;
    private int normalIcon;
    private int pressIcon;
    private PopupWindow mPopupWindow;
    private Context mContext;
    private int screenWidth;
    private int screenHeight;
    private int paddingTop;
    private int paddingLeft;
    private int paddingButtom;
    private int paddingRight;

    public PopButton(Context context) {
        this(context, null);
    }

    public PopButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        TypedArray a = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.popupbtn, defStyleAttr, 0);
        normalBg = a.getResourceId(R.styleable.popupbtn_normalBg, 0);
        pressBg = a.getResourceId(R.styleable.popupbtn_pressBg, 0);
        normalIcon = a.getResourceId(R.styleable.popupbtn_normalIcon, 0);
        pressIcon = a.getResourceId(R.styleable.popupbtn_pressIcon, 0);
        a.recycle();
        initView(context);
    }

    private void initView(Context context) {
        paddingButtom = this.getPaddingBottom();
        paddingLeft = this.getPaddingLeft();
        paddingRight = this.getPaddingRight();
        paddingTop = this.getPaddingTop();
        setNomal();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenHeight = wm.getDefaultDisplay().getHeight();
        screenWidth = wm.getDefaultDisplay().getWidth();
    }

    /**
     * 正常状态下的
     */
    private void setNomal() {
        if (normalBg != 0) {
            this.setBackgroundResource(normalBg);
            this.setPadding(paddingLeft, paddingTop, paddingRight, paddingButtom);

        }
        if (normalIcon != 0) {
            Drawable drawable = getResources().getDrawable(normalIcon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            this.setCompoundDrawables(null, null, drawable, null);
        }
    }

    /**
     * 选中状态下的
     */

    private void setPress() {
        if (pressBg != 0) {
            this.setBackgroundResource(pressBg);
            this.setPadding(paddingLeft, paddingTop, paddingRight, paddingButtom);

        }
        if (pressIcon != 0) {
            Drawable drawable = getResources().getDrawable(pressIcon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            this.setCompoundDrawables(null, null, drawable, null);
        }
    }

    public void setPopupView(final View view) {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupWindow == null) {
                    LinearLayout layout = new LinearLayout(mContext);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (screenHeight * 0.6));
                    view.setLayoutParams(params);
                    layout.addView(view);
                    layout.setBackgroundColor(Color.argb(60, 0, 0, 0));

                    mPopupWindow = new PopupWindow(layout, screenWidth, screenHeight);
                    mPopupWindow.setFocusable(true);
                    mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
                    mPopupWindow.setOutsideTouchable(true);
                    mPopupWindow.setOnDismissListener(PopButton.this);
                    layout.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPopupWindow.dismiss();
                        }
                    });
                }
                setPress();
                mPopupWindow.showAsDropDown(PopButton.this);
            }
        });
    }

    /**
     * 隐藏
     */
    public void hidePopup() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    @Override
    public void onDismiss() {
        setNomal();
    }
}
