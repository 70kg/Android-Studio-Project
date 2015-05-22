package com.com.mr_wrong.ViewDraghelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 2015/5/22.
 */
public class MainActivity extends Activity implements View.OnClickListener {
    private Button mbt_DragH, mbt_DragV, mbt_DragEdge, mbt_DragCapture, mbt_Youtube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draghelper_layout);
        initView();
    }

    private void initView() {
        mbt_DragH = (Button) findViewById(R.id.buttonDragH);
        mbt_DragV = (Button) findViewById(R.id.buttonDragV);
        mbt_DragEdge = (Button) findViewById(R.id.buttonDragEdge);
        mbt_DragCapture = (Button) findViewById(R.id.buttonDragCapture);
        mbt_Youtube = (Button) findViewById(R.id.buttonYoutube);

        mbt_DragH.setOnClickListener(this);
        mbt_DragV.setOnClickListener(this);
        mbt_DragEdge.setOnClickListener(this);
        mbt_DragCapture.setOnClickListener(this);
        mbt_Youtube.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, DragActivity.class);
        switch (v.getId()) {
            case R.id.buttonDragH:
                intent.putExtra("horizontal", true);
                startActivity(intent);
                break;
            case R.id.buttonDragV:
                intent.putExtra("vertical", true);
                startActivity(intent);
                break;
            case R.id.buttonDragEdge:
                intent.putExtra("edge", true);
                startActivity(intent);
                break;
            case R.id.buttonDragCapture:
                intent.putExtra("capture", true);
                startActivity(intent);
                break;
            case R.id.buttonYoutube:
                Intent intent1 = new Intent(MainActivity.this, YoutuboActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
