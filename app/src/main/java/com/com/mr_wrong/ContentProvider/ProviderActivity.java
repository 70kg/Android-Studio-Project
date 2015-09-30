package com.com.mr_wrong.ContentProvider;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.Utils.LogUtils;

/**
 * Created by Mr_Wrong on 15/9/29.
 */
public class ProviderActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri bookUri = Uri.parse("content://com.example.mr_wrong.androidstudioproject.provider/book");
        //getContentResolver().query(bookUri, new String[]{"_id", "name"}, null, null, null);

        ContentValues values = new ContentValues();
        values.put("_id", 6);
        values.put("name", "开发的艺术");
        getContentResolver().insert(bookUri, values);

        Cursor bookCursor = getContentResolver().query(bookUri, new String[]{"_id", "name"}, null, null, null);
        while (bookCursor.moveToNext()) {
            LogUtils.e("id:" + bookCursor.getInt(0) + " name:" + bookCursor.getString(1));
        }
        bookCursor.close();


        Uri userUri = Uri.parse("content://com.example.mr_wrong.androidstudioproject.provider/user");
        Cursor userCursor = getContentResolver().query(userUri, new String[]{"_id", "name", "sex"}, null, null, null);
        while (userCursor.moveToNext()) {
            LogUtils.e("id:"+userCursor.getInt(0)+" name:"+userCursor.getString(1)+" sex:"+userCursor.getInt(2));
            //LogUtils.e("正在查询");
        }
        userCursor.close();


    }
}
