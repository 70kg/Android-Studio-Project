package com.com.mr_wrong.Socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.Utils.LogUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Created by Mr_Wrong on 15/9/30.
 */
public class TCPServerService extends Service {

    private boolean mIsServiceDestoryed = false;
    private String[] mDefinedMessages = new String[]{"你好啊", "buhao", "哈哈哈", "呵呵", "笔笔什么呢"};

    @Override
    public void onCreate() {
        new Thread(new TcpService()).start();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsServiceDestoryed = true;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class TcpService implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket = null;

            try {
                serverSocket = new ServerSocket(8688);
            } catch (IOException e) {
                LogUtils.d("zhao'bu'd找不到8688端口玩个毛");
                e.printStackTrace();
            }
            while (!mIsServiceDestoryed) {
                try {
                    final Socket client = serverSocket.accept();
                    LogUtils.e("接受到了");
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                responseClient(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void responseClient(Socket client) throws IOException {
        //读取客户端消息
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //发送消息给客户端
        PrintWriter out = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(client.getOutputStream())), true);
        out.println("欢迎啊  小伙子");

        while (!mIsServiceDestoryed) {
            String str = in.readLine();
            LogUtils.e("来自客户端的消息" + str);
            if (str == null) {
                break;//断开连接
            }
            int i = new Random().nextInt(mDefinedMessages.length);
            String msg = mDefinedMessages[i];
            out.println(msg);
            LogUtils.e("服务器发送消息了：" + msg);
        }
        LogUtils.e("客户端退出了");
        if (in != null)
            in.close();
        if (out != null)
            out.close();
        client.close();
    }
}
