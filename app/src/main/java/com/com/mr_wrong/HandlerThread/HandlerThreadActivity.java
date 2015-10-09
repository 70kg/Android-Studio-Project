package com.com.mr_wrong.HandlerThread;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mr_wrong.androidstudioproject.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mr_Wrong on 15/10/7.
 */
public class HandlerThreadActivity extends AppCompatActivity {

    @InjectView(R.id.handlerThreadBtn)
    Button handlerThreadBtn;
    private TextView mTvServiceInfo;

    private HandlerThread mCheckMsgThread;
    private Handler mCheckMsgHandler;
    private boolean isUpdateInfo;

    private static final int MSG_UPDATE_INFO = 0x110;

    //与UI线程管理的handler
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String result = "实时更新中，当前大盘指数：<font color='red'>%d</font>";
            result = String.format(result, (int) (Math.random() * 3000 + 1000));
            mTvServiceInfo.setText(Html.fromHtml(result));
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handlerthread);
        ButterKnife.inject(this);
        //创建后台线程
        initBackThread();

        mTvServiceInfo = (TextView) findViewById(R.id.text_handlethread);
        handlerThreadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckMsgHandler.removeMessages(MSG_UPDATE_INFO);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        //开始查询
        isUpdateInfo = true;
        mCheckMsgHandler.sendEmptyMessage(MSG_UPDATE_INFO);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //停止查询
        isUpdateInfo = false;
        mCheckMsgHandler.removeMessages(MSG_UPDATE_INFO);

    }

    private void initBackThread() {


        mCheckMsgThread = new HandlerThread("check-message-coming");
        mCheckMsgThread.start();
        mCheckMsgHandler = new Handler(mCheckMsgThread.getLooper()) {
           /* 这个是子线程的handler 通过mCheckMsgThread.getLooper()和子线程的messagequeue进行绑定
            这个可以当做是子线程的run使用  其实就是省略了在子线程中的 Looper.prepare();和 Looper.loop();这个句话

            对于handler 发送一个message 进入handler当前的queue 同时设置target是自己
            loop进行无限的循环 回调msg.target.dispathmessage进行message的分发 对于handler的dispathmessage方法，如果有callback就
            去处理。没有的话就是我们自己处理handlemessage了*/

            @Override
            public void handleMessage(Message msg) {
                //模拟耗时
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(1);
                if (isUpdateInfo) {
                    mCheckMsgHandler.sendEmptyMessageDelayed(MSG_UPDATE_INFO, 1000);
                }
            }
        };


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        mCheckMsgThread.quit();
    }


}