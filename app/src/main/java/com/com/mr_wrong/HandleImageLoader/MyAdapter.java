package com.com.mr_wrong.HandleImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.mr_wrong.androidstudioproject.R;

import java.util.List;

/**
 * Created by Mr_Wrong on 15/8/13.
 */
public class MyAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mData;
    private String mDirPath;
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;

    public MyAdapter(Context context, List<String> mData, String dirPath)
    {
        this.mContext = context;
        this.mData = mData;//图片路径
        this.mDirPath = dirPath;//文件夹路径
        mInflater = LayoutInflater.from(mContext);

        mImageLoader = ImageLoader.getInstance(3 , ImageLoader.Type.LIFO);
    }

    @Override
    public int getCount()
    {
        return mData.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent)
    {
        ViewHolder holder = null;
        if (convertView == null)
        {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.grid_item, parent,
                    false);
            holder.mImageView = (ImageView) convertView
                    .findViewById(R.id.id_item_image);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mImageView
                .setImageResource(R.drawable.testimage);
        //使用Imageloader去加载图片
        mImageLoader.loadImage(mDirPath + "/" + mData.get(position),
                holder.mImageView);
        return convertView;
    }

    private final class ViewHolder
    {
        ImageView mImageView;
    }

}
