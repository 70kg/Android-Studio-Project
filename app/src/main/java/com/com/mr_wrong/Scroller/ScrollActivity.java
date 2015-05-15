package com.com.mr_wrong.Scroller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 2015/5/14.
 */
public class ScrollActivity extends Activity implements View.OnClickListener {
    private Button mScrollTo, mScrollBy, mSpingBack;
    private JellyTextView mJellyTextView;
    private static int distance = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroller_layout);

        mScrollTo = (Button) findViewById(R.id.scrollTo);
        mScrollBy = (Button) findViewById(R.id.scrollBy);
        mSpingBack = (Button) findViewById(R.id.spingback);
        mJellyTextView = (JellyTextView) findViewById(R.id.tv);

        mScrollTo.setOnClickListener(this);
        mScrollBy.setOnClickListener(this);
        mSpingBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scrollTo:
                mJellyTextView.scrollTo(distance, 0);
                distance += 10;
                break;
            case R.id.scrollBy:
                mJellyTextView.scrollBy(distance, 0);
                break;
            case R.id.spingback:
                mJellyTextView.spingBack();
                break;
        }
    }
}
