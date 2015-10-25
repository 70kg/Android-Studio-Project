package com.com.mr_wrong.Notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.Utils.LogUtils;
import com.example.mr_wrong.androidstudioproject.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mr_Wrong on 15/9/24.
 */
public class NotificationActivity extends Activity {

    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://70kg.info"));
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        ButterKnife.inject(this);
        LogUtils.e("oncreate");
    }

    @OnClick(R.id.btn_notification_1)
    void setBtnNotification1() {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setContentText("这是基本的Notification")
                .setSubText("这是二级文字")
                .setContentTitle("这是Title");
        send(1, builder);
    }

    @OnClick(R.id.btn_notification_2)
    void setBtnNotification2() {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        RemoteViews contnetview = new RemoteViews(getPackageName(), R.layout.notification);
        contnetview.setTextViewText(R.id.notification_textView, "这是折叠的");

        Notification notification = builder.build();
        notification.contentView = contnetview;

        RemoteViews expandedview = new RemoteViews(getPackageName(), R.layout.notification_expanded);
        notification.bigContentView = expandedview;

        send(2, notification);
    }

    @OnClick(R.id.btn_notification_3)
    void setBtnNotification3() {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher)
                .setSmallIcon(R.drawable.ic_launcher)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setContentText("这是悬挂式的")
                .setContentTitle("这是title");
        Intent push = new Intent();
        push.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        pendingIntent = PendingIntent.getActivity(this, 0, push, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentText("这是5.0").setFullScreenIntent(pendingIntent, true);

        send(3, builder);

        Intent intent = new Intent("info.70kg");
        startActivity(intent);
    }



    private void send(int id, Notification.Builder builder) {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(
                        NOTIFICATION_SERVICE);
        notificationManager.notify(id,
                builder.build());
    }

    private void send(int id, Notification notification) {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(
                        NOTIFICATION_SERVICE);
        notificationManager.notify(id,
                notification);


    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        LogUtils.e("onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 这是个在singletop（栈顶复用）和singletask(栈内复用)模式下去获取启动acitvity的信息
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}

