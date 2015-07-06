package com.example.mr_wrong.androidstudioproject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by Mr_Wrong on 15/7/6.
 */
public class BaseActivity extends Activity {
    Tool tool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tool = new Tool();
    }

    class Tool {
        public void ShowToast(String message) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
}
