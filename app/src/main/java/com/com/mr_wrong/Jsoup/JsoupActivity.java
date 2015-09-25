package com.com.mr_wrong.Jsoup;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mr_wrong.androidstudioproject.BaseActivity;
import com.example.mr_wrong.androidstudioproject.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mr_Wrong on 15/9/14.
 */
public class JsoupActivity extends BaseActivity {

    @InjectView(R.id.tv_jsoup)
    TextView tvJsoup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jsoup);
        ButterKnife.inject(this);

        RequestQueue mQueue = Volley.newRequestQueue(this);

        StringRequest request1 = new StringRequest("http://www.mzitu.com/48428", new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                Content content = ContentParser.Parser(s);
                //tvJsoup.setText(content.getUrl());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        mQueue.add(request1);

    }
}
