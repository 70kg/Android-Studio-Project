package info.meizi;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class CalcService extends Service {
    private static final String TAG = "server";

    public void onCreate() {
        Log.e(TAG, "onCreate");
    }

    public IBinder onBind(Intent t) {
        Log.e(TAG, "onBind");
        return mBinder;
    }

    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }

    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    public void onRebind(Intent intent) {
        Log.e(TAG, "onRebind");
        super.onRebind(intent);
    }

    private final ICalcAIDL.Stub mBinder = new ICalcAIDL.Stub() {

        @Override
        public int add(int x, int y) throws RemoteException {
            return x + y;
        }

        @Override
        public int min(int x, int y) throws RemoteException {
            return x - y;
        }

    };

}