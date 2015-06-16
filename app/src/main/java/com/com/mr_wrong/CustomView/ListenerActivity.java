package com.com.mr_wrong.CustomView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 15/6/14.
 */
public class ListenerActivity extends Activity implements View.OnTouchListener, View.OnClickListener {
    private LinearLayout mLayout;
    private TestButton mButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        mLayout = (LinearLayout) this.findViewById(R.id.mylayout);
        mButton = (TestButton) this.findViewById(R.id.my_btn);

        mLayout.setOnTouchListener(this);
        mButton.setOnTouchListener(this);

        mLayout.setOnClickListener(this);
        mButton.setOnClickListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i(null, "OnTouchListener--onTouch-- action=" + event.getAction() + " --" + v);
        return false;
    }

    @Override
    public void onClick(View v) {
        Log.i(null, "OnClickListener--onClick--"+v);
    }
}
