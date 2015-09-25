package info.meizi.net;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by Mr_Wrong on 15/9/14.
 */
public class MeiziService extends IntentService{
    private static final String TAG = "MeiziService";
    public MeiziService() {
        super(TAG);
    }
    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
