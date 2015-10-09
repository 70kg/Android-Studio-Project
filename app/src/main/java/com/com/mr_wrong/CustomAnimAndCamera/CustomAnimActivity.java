package com.com.mr_wrong.CustomAnimAndCamera;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.Utils.LogUtils;
import com.example.mr_wrong.androidstudioproject.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mr_Wrong on 15/9/22.
 */
public class CustomAnimActivity extends Activity {

    @InjectView(R.id.btn_test)
    Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_anim);
        ButterKnife.inject(this);

        //btnTest.setTranslationX(100);
        btnTest.setLeft(100);
        LogUtils.e("left:" + btnTest.getLeft());
        LogUtils.e("x:" + btnTest.getX());
        LogUtils.e("TranslationX:" + btnTest.getTranslationX());


    }

    public void btnAnim(View view) {
        CustomAnim customAnim = new CustomAnim();
        customAnim.setRotateY(180);
        view.startAnimation(customAnim);



    }
}
