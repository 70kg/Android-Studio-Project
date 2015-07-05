package com.com.mr_wrong.NewTypeStartActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 15/7/5.
 */
public class NewMainAdapter extends BaseAdapter {
    private Context mContext;

    public NewMainAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return AllActivitys.values().length;
    }

    @Override
    public Object getItem(int i) {
        return AllActivitys.values()[i].getActivity();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v  = LayoutInflater.from(mContext).inflate(R.layout.new_main_item,null,false);
        TextView t = (TextView)v.findViewById(R.id.tv_new_main);
        Object o = getItem(position);
        int start = o.getClass().getName().lastIndexOf(".") + 1;
        String name = o.getClass().getName().substring(start);
        t.setText(name);
        v.setTag(AllActivitys.values()[position]);
        return v;
    }
}
