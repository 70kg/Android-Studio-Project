package com.com.mr_wrong.RecycleViewAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Mr_Wrong on 15/8/19.
 */
public class RVHolder  extends RecyclerView.ViewHolder {


    private ViewHolder viewHolder;

    public RVHolder(View itemView) {
        super(itemView);
        viewHolder=ViewHolder.getViewHolder(itemView);
    }


    public ViewHolder getViewHolder() {
        return viewHolder;
    }

}

