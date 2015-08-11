package com.com.mr_wrong.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 15/8/10.
 */
public class CustomLayout extends RelativeLayout {
    private ImageView mImageLeft, mImageRight;
    private EditText mEditText;
    private TextView mTextLeft, mTextRight;

    private int rightimage, leftimage, editstle;


    private boolean isshowlefttext, isIsshowleftimage,
            isIsshowrighttext, isIsshowrightimage, isedit;

    private String lefttext, edittext, hint, righttext;


    private onLayoutClick mOnLayoutClick;
    private onAllClick mAllClick;
    private onRightTextClick mOnRightTextClick;

    public CustomLayout(Context context) {
        this(context, null);
    }

    public CustomLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public CustomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.custom_layout_item, this, true);

        mImageLeft = (ImageView) findViewById(R.id.iv_left);
        mImageRight = (ImageView) findViewById(R.id.iv_right);
        mEditText = (EditText) findViewById(R.id.etv);

        mTextLeft = (TextView) findViewById(R.id.tv_left);

        mTextRight = (TextView) findViewById(R.id.tv_right);

        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.CustomLayout);

        rightimage = array.getResourceId(R.styleable.CustomLayout_rightimage, R.drawable.right1);
        leftimage = array.getResourceId(R.styleable.CustomLayout_leftimage, 0);
        editstle = array.getResourceId(R.styleable.CustomLayout_editstyle, 0);

        isshowlefttext = array.getBoolean(R.styleable.CustomLayout_isshowlefttext, true);
        isIsshowrightimage = array.getBoolean(R.styleable.CustomLayout_isshowrightimage, true);
        isedit = array.getBoolean(R.styleable.CustomLayout_iseditable, false);
        lefttext = array.getString(R.styleable.CustomLayout_lefttext);

        righttext = array.getString(R.styleable.CustomLayout_righttext);
        hint = array.getString(R.styleable.CustomLayout_hint);
        edittext = array.getString(R.styleable.CustomLayout_edittext);
        array.recycle();

        if (leftimage != 0) {//左边有图片
            mImageLeft.setVisibility(VISIBLE);
            mImageLeft.setImageResource(leftimage);
            //设置左边文字的外边距
            RelativeLayout.LayoutParams lp = (LayoutParams) mTextLeft.getLayoutParams();
            lp.setMargins(0, 0, 0, 0);
            mTextLeft.setLayoutParams(lp);
        }


        if (isshowlefttext) {
            mTextLeft.setVisibility(VISIBLE);
            mTextLeft.setText(lefttext);
        }
        if (righttext != null) {
            mTextRight.setVisibility(VISIBLE);
            mTextRight.setText(righttext);
            mTextRight.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnRightTextClick != null) {
                        mOnRightTextClick.onRightTextClick();
                    }
                    if (mAllClick != null) {
                        mAllClick.onRightTextClick();
                    }
                }
            });
        }
        if (rightimage != 0) {
            mImageRight.setVisibility(VISIBLE);
            mImageRight.setImageResource(rightimage);
        }

        mEditText.setBackgroundColor(editstle);
        mEditText.setText(edittext);
        if (isedit) {
            mEditText.setHint(hint);
        } else {
            mEditText.setEnabled(false);
        }

        this.setOnClickListener(new OnClickListener() {//整个layout的点击事件
            @Override
            public void onClick(View view) {
                if (mOnLayoutClick != null) {
                    mOnLayoutClick.onLayoutClick();
                }
                if (mAllClick != null) {
                    mAllClick.onLayoutClick();
                }
            }
        });


    }

    public void setonLayoutClick(onLayoutClick onLayoutClick) {
        this.mOnLayoutClick = onLayoutClick;
    }

    public void setonAllClick(onAllClick allClick) {
        this.mAllClick = allClick;
    }

    public void setonRightTextClick(onRightTextClick rightTextClick) {
        this.mOnRightTextClick = rightTextClick;
    }

    public ImageView getImageLeft() {
        return mImageLeft;
    }

    public void setImageLeft(ImageView mImageLeft) {
        this.mImageLeft = mImageLeft;
    }

    public ImageView getImageRight() {
        return mImageRight;
    }

    public void setImageRight(ImageView mImageRight) {
        this.mImageRight = mImageRight;
    }

    public EditText getEditText() {
        return mEditText;
    }

    public void setEditText(EditText mEditText) {
        this.mEditText = mEditText;
    }

    public TextView getTextLeft() {
        return mTextLeft;
    }

    public void setTextLeft(TextView mTextLeft) {
        this.mTextLeft = mTextLeft;
    }

    public TextView getTextRight() {
        return mTextRight;
    }

    public void setTextRight(TextView mTextRight) {
        this.mTextRight = mTextRight;
    }
}

interface onLayoutClick {
    void onLayoutClick();
}

interface onRightTextClick {
    void onRightTextClick();
}

interface onAllClick extends onLayoutClick {
    void onRightTextClick();
}