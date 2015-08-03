package com.com.mr_wrong.test

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.example.mr_wrong.androidstudioproject.R


/**
 * Created by Mr_Wrong on 15/8/1.
 */
public class SecondKotlinActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_kotlin_layout)

        var intent = getIntent();
        var name = intent.getStringExtra("name")
        var age = intent.getStringExtra("age")

        var tv_name = findViewById(R.id.tv_name) as TextView
        var tv_age = findViewById(R.id.tv_age) as TextView

        tv_name.setText(name)
        tv_age.setText(age)

    }
}