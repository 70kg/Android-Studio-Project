package com.com.mr_wrong.Kotlin

import android.app.Activity
import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle

/**
 * Created by Mr_Wrong on 15/10/25.
 */
public class KotlinActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ///toast("哈哈");//这个是拓展的方法  拓展是对类或者接口进行拓展  然后在使用这个类时或者类的内部进行使用

        var activity = KotlinActivity()
       // activity.toast("yes")

    }

    fun send(id: Int, builder: Notification.Builder) {
        var notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager;
        notificationManager.notify(id, builder.build())
    }
}