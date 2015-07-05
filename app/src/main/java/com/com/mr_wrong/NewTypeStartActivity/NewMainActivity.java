package com.com.mr_wrong.NewTypeStartActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 15/7/5.
 */
public class NewMainActivity extends Activity {
    ListView mListview;
    NewMainAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_main_layout);

        mListview = (ListView) findViewById(R.id.lv_new_main);
        mAdapter = new NewMainAdapter(this);
        mListview.setAdapter(mAdapter);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AllActivitys allactivitys = (AllActivitys) view.getTag();
                Activity activity = allactivitys.getActivity();
                Intent intent = new Intent(NewMainActivity.this, activity.getClass());
                startActivity(intent);
            }
        });
    }
}
