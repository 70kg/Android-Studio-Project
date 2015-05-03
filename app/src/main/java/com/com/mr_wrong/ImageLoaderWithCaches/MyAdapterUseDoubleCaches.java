package com.com.mr_wrong.ImageLoaderWithCaches;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.mr_wrong.androidstudioproject.R;

import java.util.List;

/**
 * Created by Mr_Wrong on 2015/5/3.
 */
public class MyAdapterUseDoubleCaches extends BaseAdapter implements AbsListView.OnScrollListener {
    private LayoutInflater mInflater;
    private List<String> mData;
    private ImageLoaderWithDoubleCaches mImageLoader;
    private int mStart = 0, mEnd = 0;
    private boolean mFirstFlag = true;

    public MyAdapterUseDoubleCaches(Context context, List<String> data, ListView listView) {
        this.mData = data;
        mInflater = LayoutInflater.from(context);
        mImageLoader = new ImageLoaderWithDoubleCaches(context, listView);
        mImageLoader.loadImages(mStart, mEnd);
        listView.setOnScrollListener(this);
    }

    public void flushCache() {
        mImageLoader.flushCache();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String url = mData.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.listview_item, null);
            viewHolder.imageView =
                    (ImageView) convertView.findViewById(R.id.iv_lv_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setTag(url);
        viewHolder.imageView.setImageResource(R.mipmap.ic_launcher);
        mImageLoader.showImage(url, viewHolder.imageView);
        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            mImageLoader.loadImages(mStart, mEnd);
        } else {
            mImageLoader.cancelAllTasks();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mStart = firstVisibleItem;
        mEnd = firstVisibleItem + visibleItemCount;
        if (mFirstFlag && visibleItemCount > 0) {
            mImageLoader.loadImages(mStart, mEnd);
            mFirstFlag = false;
        }
    }

    public class ViewHolder {
        public ImageView imageView;
    }
}