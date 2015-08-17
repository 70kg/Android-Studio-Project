package com.com.mr_wrong.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Utils.LogUtils;
import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 15/8/10.
 */
public class CustomLayout extends RelativeLayout {
    private ImageView mImageLeft, mImageRight;
    private EditText mEditText;
    private TextView mTextLeft, mTextRight;

    private int rightimage, leftimage;

    private int lefttextwidth;

    private String lefttext, edittext, hint, righttext;

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

        rightimage = array.getResourceId(R.styleable.CustomLayout_rightimage, 0);
        leftimage = array.getResourceId(R.styleable.CustomLayout_leftimage, 0);

        lefttext = array.getString(R.styleable.CustomLayout_lefttext);
        lefttextwidth = array.getDimensionPixelSize(R.styleable.CustomLayout_lefttextwidth, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_PX, 60, getResources().getDisplayMetrics()));

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


        mTextLeft.setText(lefttext);

        LogUtils.e(lefttextwidth);

        if(lefttextwidth!=(int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_PX, 60, getResources().getDisplayMetrics())){
            ViewGroup.LayoutParams lp = mTextLeft.getLayoutParams();
            lp.width = lefttextwidth;
            mTextLeft.setLayoutParams(lp);
        }


        if (rightimage != 0) {
            mImageRight.setVisibility(VISIBLE);
            mImageRight.setImageResource(rightimage);
        }


        mEditText.setText(edittext);
        mEditText.setEnabled(false);
        if (hint != null) {
            mEditText.setHint(hint);
            mEditText.setEnabled(true);
        }

        if (righttext != null) {
            mTextRight.setText(righttext);
        }
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
