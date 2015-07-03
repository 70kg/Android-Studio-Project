package com.com.mr_wrong.Property_Animation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * Created by Mr_Wrong on 15/7/3.
 */
public abstract class BaseActivity extends Activity {
    Tool tool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        ButterKnife.inject(this);
        tool = new Tool();
    }

    public void init() {
        setContentView();

    }

    public abstract void setContentView();

    class Tool {
        public void ShowToast( String message) {
            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
        }
    }
}

