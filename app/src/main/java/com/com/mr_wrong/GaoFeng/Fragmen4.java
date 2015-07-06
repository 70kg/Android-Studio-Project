package com.com.mr_wrong.GaoFeng;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.mr_wrong.androidstudioproject.BaseFragment;

/**
 * Created by Mr_Wrong on 15/7/6.
 */
public class Fragmen4 extends BaseFragment {
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBaseTextView.setText("Fragment4");
    }
}
