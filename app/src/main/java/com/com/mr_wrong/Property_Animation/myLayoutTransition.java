package com.com.mr_wrong.Property_Animation;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.example.mr_wrong.androidstudioproject.R;

import butterknife.InjectView;

/**
 * Created by Mr_Wrong on 15/7/3.
 */
public class myLayoutTransition extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    @InjectView(R.id.id_appear)
    CheckBox idAppear;
    @InjectView(R.id.id_change_appear)
    CheckBox idChangeAppear;
    @InjectView(R.id.id_disappear)
    CheckBox idDisappear;
    @InjectView(R.id.id_change_disappear)
    CheckBox idChangeDisappear;
    @InjectView(R.id.id_container)
    LinearLayout viewGroup;
    private GridLayout mGridlayout;
    private int mVal;
    private LayoutTransition mLayoutTransition;

    @Override
    public void setContentView() {
        setContentView(R.layout.mylayouttransition);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idAppear.setOnCheckedChangeListener(this);
        idChangeAppear.setOnCheckedChangeListener(this);
        idDisappear.setOnCheckedChangeListener(this);
        idChangeDisappear.setOnCheckedChangeListener(this);
        mGridlayout = new GridLayout(this);
        mGridlayout.setColumnCount(5);
        viewGroup.addView(mGridlayout);

        mLayoutTransition = new LayoutTransition();
        mGridlayout.setLayoutTransition(mLayoutTransition);

    }

    public void addBtn(View view) {
        final Button button = new Button(this);
        button.setText("" + (++mVal));
        mGridlayout.addView(button, Math.min(1, mGridlayout.getChildCount()));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGridlayout.removeView(button);
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mLayoutTransition = new LayoutTransition();
       // mLayoutTransition.setAnimator(LayoutTransition.APPEARING, (idAppear.isChecked() ? mLayoutTransition.getAnimator(LayoutTransition.APPEARING) : null));
        mLayoutTransition.setAnimator(LayoutTransition.APPEARING, (idAppear.isChecked() ? ObjectAnimator.ofFloat(this, "scaleX", 0f, 1.0f) : null));

        mLayoutTransition.setAnimator(LayoutTransition.CHANGE_APPEARING, (idChangeAppear.isChecked() ? mLayoutTransition.getAnimator(LayoutTransition.CHANGE_APPEARING) : null));
        mLayoutTransition.setAnimator(LayoutTransition.DISAPPEARING, (idDisappear.isChecked() ? mLayoutTransition.getAnimator(LayoutTransition.DISAPPEARING) : null));
        mLayoutTransition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, (idChangeDisappear.isChecked() ? mLayoutTransition.getAnimator(LayoutTransition.CHANGE_DISAPPEARING) : null));

        mGridlayout.setLayoutTransition(mLayoutTransition);
    }
}
