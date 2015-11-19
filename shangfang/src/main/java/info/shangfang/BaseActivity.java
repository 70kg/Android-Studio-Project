package info.shangfang;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Mr_Wrong on 15/11/19.
 */
public class BaseActivity extends AppCompatActivity {
    protected <T> T find(int id) {
        return (T) findViewById(id);
    }

    protected <T> T find(View parent, int id) {
        return (T) parent.findViewById(id);
    }

    protected void Toast(Object msg) {
        if (msg == null) {
            return;
        }
        Toast.makeText(getBaseContext(), msg.toString(), Toast.LENGTH_SHORT).show();
    }
}
