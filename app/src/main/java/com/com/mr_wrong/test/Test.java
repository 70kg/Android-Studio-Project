package com.com.mr_wrong.test;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.Utils.KLog;

/**
 * Created by Mr_Wrong on 15/11/13.
 */
public class Test extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        KLog.e();
    }
}
