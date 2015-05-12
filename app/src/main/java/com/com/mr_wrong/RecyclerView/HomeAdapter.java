package com.com.mr_wrong.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mr_wrong.androidstudioproject.R;

import java.util.List;

/**
 * Created by Mr_Wrong on 2015/5/11.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private List<String> mDatas;
    private LayoutInflater mInflater;

    /**
     * 点击事件接口
     */
    public interface OnitemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int pisition);
    }

    private OnitemClickListener mOnitemClickListener;

    public void setOnitemClickListener(OnitemClickListener onitemClickListener) {
        this.mOnitemClickListener = onitemClickListener;
    }

    public HomeAdapter(Context context, List<String> mDatas) {
        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(R.layout.item_home, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int i) {
        holder.tv.setText(mDatas.get(i));
        //如果设置回调，设置点击事件
        if (mOnitemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnitemClickListener.onItemClick(holder.itemView, pos);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnitemClickListener.onItemLongClick(holder.itemView, pos);
                    removeData(pos);
                    return false;
                }
            });
        }
    }

    //删除数据
    public void removeData(int pos) {
        mDatas.remove(pos);
        notifyItemRemoved(pos);
    }

    //添加数据
    public void addData(int position) {
        mDatas.add(position, "Insert One");
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends ViewHolder {

        TextView tv;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.id_num);
        }
    }
}
