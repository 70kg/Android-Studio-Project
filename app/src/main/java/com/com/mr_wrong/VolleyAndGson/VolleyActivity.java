package com.com.mr_wrong.VolleyAndGson;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 2015/5/16.
 */
public class VolleyActivity extends Activity {
    private EditText mEditText;
    private Button mButton;
    private TextView mTextView;
    private String URL = "http://m.weather.com.cn/atad/101010100.html";
    CityUtil cityutil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volley_layout);
        initView();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityutil = new CityUtil();
                String new_url = URL.replace("101010100", cityutil.GetCityId(mEditText.getText().toString()));
                Log.e("new_url", new_url);
                initData(new_url);

            }
        });

    }

    private void initData(String url) {
        RequestQueue mQueue = Volley.newRequestQueue(this);
        GsonRequest<Weather> gsonRequest = new GsonRequest<Weather>(url
                , Weather.class,
                new Response.Listener<Weather>() {
                    @Override
                    public void onResponse(Weather weather) {
                        WeatherInfo weatherInfo = weather.getWeatherinfo();
                        mTextView.setText("城市:" + weatherInfo.getCity() + "   温度:" + weatherInfo.getTemp1() +
                                "  发布时间:" + weatherInfo.getDate_y() + " 天气状况：" + weatherInfo.getIndex() +
                                "  穿衣意见：" + weatherInfo.getIndex_d());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mQueue.add(gsonRequest);
    }

    private void initView() {
        mEditText = (EditText) findViewById(R.id.id_editText);
        mButton = (Button) findViewById(R.id.id_button);
        mTextView = (TextView) findViewById(R.id.id_textview);
    }
}
