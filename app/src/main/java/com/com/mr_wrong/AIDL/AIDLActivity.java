package com.com.mr_wrong.AIDL;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.Utils.LogUtils;
import com.example.mr_wrong.androidstudioproject.ICalcAIDL;
import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 15/9/27.
 */
public class AIDLActivity extends Activity {
    private ICalcAIDL mCalcAIDL;


    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if(mCalcAIDL==null){
                return;
            }
            try {
                mCalcAIDL.asBinder().linkToDeath(mDeathRecipient,0);
                mCalcAIDL = null;
                LogUtils.e("服务死亡了，重新绑定");
                bindService();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

    private ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mCalcAIDL = ICalcAIDL.Stub.asInterface(service);
            try {
                service.linkToDeath(mDeathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.e("client", "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mCalcAIDL = null;
            Log.e("client", "onServiceDisConnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aidl_layout);
    }

    public void bindService(View view) {
        bindService();
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setAction("info.70kg.aidl.calc");
        bindService(intent, mServiceConn, Context.BIND_AUTO_CREATE);
    }

    public void unbindService(View view) {
        unbindService(mServiceConn);
    }

    public void addInvoked(View view) throws Exception {

        if (mCalcAIDL != null) {
            int addRes = mCalcAIDL.add(12, 12);
            Toast.makeText(this, addRes + "", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "服务器被异常杀死，请重新绑定服务端", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    public void minInvoked(View view) throws Exception {

        if (mCalcAIDL != null) {
            int addRes = mCalcAIDL.min(58, 12);
            Toast.makeText(this, addRes + "", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "服务端未绑定或被异常杀死，请重新绑定服务端", Toast.LENGTH_SHORT)
                    .show();

        }

    }
}
