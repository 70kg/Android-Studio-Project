package com.com.mr_wrong.SlideView;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 2015/5/27.
 */
public class SlideActivity extends Activity implements
        SlideView.OnSlideListener, View.OnClickListener {
    SlideView mslideView;
    private LinearLayout slide_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_view_layout);
        mslideView = (SlideView) findViewById(R.id.sv_view);
        View slideContentView = View.inflate(this, R.layout.slide_list_item, null);
        mslideView.setOnSlideListener(this);
        mslideView.setButtonText("删除");
        mslideView.setContentView(slideContentView);

        slide_delete = (LinearLayout) findViewById(R.id.holder);
        slide_delete.setOnClickListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mslideView.onRequireTouchEvent(event);
        return true;
    }

    @Override
    public void onSlide(View view, int status) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.holder) {
            Toast.makeText(this, "删除", Toast.LENGTH_SHORT).show();
        }
    }
}
