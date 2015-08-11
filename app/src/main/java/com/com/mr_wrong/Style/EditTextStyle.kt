package com.com.mr_wrong.Style

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import com.example.mr_wrong.androidstudioproject.R

/**
 * Created by Mr_Wrong on 15/8/5.
 */
public class EditTextStyle : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var edittextview = EditText(this);

        setContentView(R.layout.second_kotlin_layout)


    }
}
