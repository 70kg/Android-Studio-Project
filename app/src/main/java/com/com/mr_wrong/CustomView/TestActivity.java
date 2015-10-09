package com.com.mr_wrong.CustomView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 15/5/30.
 */
public class TestActivity extends Activity {

    private MyPullScrollView mScrollView;
    private ImageView mHeadImg;

    private TableLayout mMainLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customjob_layout);

        initView();

        showTable();

        WindowManager mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams layoutParams =
                new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                        0, 0, PixelFormat.TRANSPARENT);
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.x = 100;
        layoutParams.y = 100;
        mWindowManager.addView(mHeadImg, layoutParams);



    }

    protected void initView() {
        mScrollView = (MyPullScrollView) findViewById(R.id.scroll_view);
        mHeadImg = (ImageView) findViewById(R.id.background_img);

        mMainLayout = (TableLayout) findViewById(R.id.table_layout);

        mScrollView.setHeader(mHeadImg);
        //mScrollView.setOnTurnListener(this);
    }

    public void showTable() {
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.leftMargin = 30;
        layoutParams.bottomMargin = 10;
        layoutParams.topMargin = 10;

        for (int i = 0; i < 30; i++) {
            TableRow tableRow = new TableRow(this);
            TextView textView = new TextView(this);
            textView.setText("Test pull down scroll view " + i);
            textView.setTextSize(20);
            textView.setPadding(15, 15, 15, 15);

            tableRow.addView(textView, layoutParams);
            if (i % 2 != 0) {
                tableRow.setBackgroundColor(Color.LTGRAY);
            } else {
                tableRow.setBackgroundColor(Color.WHITE);
            }

            final int n = i;
            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(TestActivity.this, "Click item " + n, Toast.LENGTH_SHORT).show();
                }
            });

            mMainLayout.addView(tableRow);
        }
    }

}