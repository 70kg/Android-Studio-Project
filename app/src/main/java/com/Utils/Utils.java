package com.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.widget.Toast;

/**
 * Created by Mr_Wrong on 2015/5/26.
 */
public class Utils {
    public static void Log(String Tag, Object obj) {
        android.util.Log.e(Tag, obj + "");
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
//    public static int sp2dp(float spVal){
//            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal,
//                    MyAppliction.getInstance().getApplicationContext().getResources().getDisplayMetrics()));
//         }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int setsp(Context context, int sp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_PX, sp, context.getResources().getDisplayMetrics());
    }
    public static void ShowToast(Context context,String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static Bitmap drawableToBitamp(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }
}
