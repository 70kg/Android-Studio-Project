package com.com.mr_wrong.CustomView.PageTurnView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.mr_wrong.androidstudioproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr_Wrong on 15/10/17.
 */
public class PageTurnActivity extends Activity {
    private PageTurnView mPageCurlView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pageturn);


        mPageCurlView = (PageTurnView) findViewById(R.id.main_pcv);

        Bitmap bitmap = null;
        List<Bitmap> bitmaps = new ArrayList<Bitmap>();

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.page_img_a);
        bitmaps.add(bitmap);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.page_img_b);
        bitmaps.add(bitmap);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.page_img_c);
        bitmaps.add(bitmap);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.page_img_d);
        bitmaps.add(bitmap);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.page_img_e);
        bitmaps.add(bitmap);

        mPageCurlView.setBitmaps(bitmaps);
    }
}
