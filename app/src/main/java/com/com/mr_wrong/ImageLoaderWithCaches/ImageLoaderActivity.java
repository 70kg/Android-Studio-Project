package com.com.mr_wrong.ImageLoaderWithCaches;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.mr_wrong.androidstudioproject.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Mr_Wrong on 2015/5/2.
 */
public class ImageLoaderActivity extends Activity {
    private ListView mListview;
    private List<String> mData;
    private MyAdapterUseCaches mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageloader_layout);
        mListview = (ListView) findViewById(R.id.listview);
        mData = Arrays.asList(Images.IMAGE_URLS);
        mAdapter = new MyAdapterUseCaches(this,mData,mListview);
        mListview.setAdapter(mAdapter);
    }
}
