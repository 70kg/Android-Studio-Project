package com.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Mr_Wrong on 2015/5/26.
 */
public class TestAdapter extends BaseAdapter {
    private Context mContext;
    LayoutInflater inflate;

    public TestAdapter(Context context) {
        this.mContext = context;
        inflate = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return 50;
    }

    @Override
    public String getItem(int i) {
        return "object" + i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View rView, ViewGroup viewGroup) {
        View view = rView;
        if (view == null) {
            view = inflate.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
        }
        ((TextView) view.findViewById(android.R.id.text1)).setText(getItem(i));
        return view;
    }
}
