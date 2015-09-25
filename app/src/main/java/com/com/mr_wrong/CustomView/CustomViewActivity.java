package com.com.mr_wrong.CustomView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 15/7/7.
 */
public class CustomViewActivity extends Activity {
    Button btn_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_view_layout);


//        btn_test = (Button) findViewById(R.id.btn_test);
//        btn_test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TranslateAnimation animation = new TranslateAnimation(0, 300, 0, 300);
//                animation.setDuration(1000);
//                btn_test.startAnimation(animation);
//            }
//        });


//        AnimListView animListView = (AnimListView) findViewById(R.id.main_alv);
//        animListView.setAdapter(new BaseAdapter() {
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.anim_list_item, null);
//                return convertView;
//            }
//
//            @Override
//            public long getItemId(int position) {
//                return 0;
//            }
//
//            @Override
//            public Object getItem(int position) {
//                return null;
//            }
//
//            @Override
//            public int getCount() {
//                return 100;
//            }
//        });
    }
//        CustomLayout customLayout = (CustomLayout) findViewById(R.id.custom_1);
//
//        TextView left = customLayout.getTextLeft();
//
//
//        left.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"左边的",Toast.LENGTH_SHORT).show();
//            }
//        });
}

