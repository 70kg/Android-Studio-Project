package com.com.mr_wrong.CustomView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 15/7/7.
 */
public class CustomViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_view_layout);

        CustomLayout customLayout = (CustomLayout) findViewById(R.id.custom_1);

        TextView left = customLayout.getTextLeft();

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"左边的",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
