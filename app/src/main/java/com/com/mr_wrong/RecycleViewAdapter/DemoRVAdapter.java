package com.com.mr_wrong.RecycleViewAdapter;

import android.content.Context;

import java.util.List;

/**
 * Created by Mr_Wrong on 15/8/19.
 */
public class DemoRVAdapter extends  AutoRVAdapter {
    public DemoRVAdapter(Context context, List<?> list) {
        super(context, list);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return 0;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
          holder.get(1);
    }
}
