package com.com.mr_wrong.GaoFeng;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mr_wrong.androidstudioproject.BaseFragment;
import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 15/7/6.
 */
public class Fragmen3 extends BaseFragment {
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment3, container, false);
        return rootView;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
