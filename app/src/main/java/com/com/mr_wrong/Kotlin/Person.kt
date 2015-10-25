package com.com.mr_wrong.Kotlin

import android.app.Activity
import android.app.Notification
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import com.Utils.LogUtils
import java.util.ArrayList

/**
 * Created by Mr_Wrong on 15/10/21.
 */
public open class Person(val name: String) : Activity() {

    val context = getApplicationContext();

    init {
        LogUtils.e("初始化");
    }

    constructor(parent: Person) : this("haha") {

    }

    open fun v() {
        LogUtils.d("parent fun")
    }

    open fun f() {
        print("B")
    }
}

interface B {
    fun f() {
        print("B")
    } // interface members are 'open' by default

    fun b() {
        print("b")
    }
}

class Man : Person, B {
    constructor(name: String) : super(name)

    override fun f() {
        //
        super<B>.f();//回调给B的
        super<Person>.f();
    }

    override fun v() {
        LogUtils.d("child fun")
    }
}

class MyView : View {
    constructor(ctx: Context) : super(ctx) {
    }


    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs) {
        val man = Man("i am a man");
        man.name;
    }

    val list = Preset.getTips()


    fun printlist(args: ArrayList<String>) {

        if (list == null) {
            //空指针安全  带？是可能为空 要先进行检查
            return
        }
        for (k in list) {
        }
    }

    fun MutableList<Int>.swap(index1: Int, index2: Int) {
        val tmp = this[index1] // 'this' corresponds to the list
        this[index1] = this[index2]
        this[index2] = tmp
    }
    val view = MyView(getContext())




}

fun Activity.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT){ 
    Toast.makeText(this, message, duration) 
}

public object Preset {
    public fun getTips(): ArrayList<String>? {
        var list = ArrayList<String>()
        list.add("111")
        list.add("222")
        list.add("333")
        list.add("444")
        return list
    }

}