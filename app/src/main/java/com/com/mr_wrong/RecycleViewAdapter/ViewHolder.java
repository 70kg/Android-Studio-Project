package com.com.mr_wrong.RecycleViewAdapter;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Mr_Wrong on 15/8/19.
 */
public class ViewHolder {
    private SparseArray<View> viewHolder;
    private View view;

    public static  ViewHolder getViewHolder(View view){
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        return viewHolder;
    }
    private ViewHolder(View view) {
        this.view = view;
        viewHolder = new SparseArray<View>();
        view.setTag(viewHolder);
    }
    public <T extends View> T get(int id) {
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

    public View getConvertView() {
        return view;
    }
    public void setTextView(int  id,CharSequence charSequence){
        ((TextView)get(id)).setText(charSequence);
    }

}