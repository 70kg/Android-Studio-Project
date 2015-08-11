package com.com.mr_wrong.test

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.Utils.LogUtils
import com.example.mr_wrong.androidstudioproject.R

//import kotlinx.android.synthetic.kotlin_test_layout.btn_push
//import kotlinx.android.synthetic.kotlin_test_layout.et_age
//import kotlinx.android.synthetic.kotlin_test_layout.et_name

/**
 * Created by Mr_Wrong on 15/8/1.
 */
public class KotlinTest : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kotlin_test_layout);

        var person = Person();
        var et_name = findViewById(R.id.et_name) as EditText
        var et_age = findViewById(R.id.et_age) as EditText
        var btn_push = findViewById(R.id.btn_push) as Button

        person.name = et_name.getText().toString()
        person.age = et_age.getText().toString()

        var intent = Intent(this, SecondKotlinActivity().javaClass)

<<<<<<< HEAD
        btn_push.setOnClickListener({
            var namestring = person.name;
=======


        btn_push.setOnClickListener({
            var namestring = et_name.getText().toString()
>>>>>>> origin/master
            var agestring = et_age.getText().toString()

            LogUtils.e(namestring)

            intent.putExtra("name", namestring)
            intent.putExtra("age",agestring)

<<<<<<< HEAD


            startActivity(intent)
            Toast.makeText(this,"这是使用Kotlin的跳转",Toast.LENGTH_SHORT).show();
=======
            startActivity(intent)
            Toast.makeText(this,"zhe",Toast.LENGTH_SHORT).show();
>>>>>>> origin/master
        })
    }

    class Person {
        var name: String = "";
        var age: String = "20";
        var address: String? = "";//可以为空
<<<<<<< HEAD



=======
>>>>>>> origin/master
    }

}


