package com.com.mr_wrong.ContentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.Utils.LogUtils;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Mr_Wrong on 15/9/29.
 */
public class BookProvider extends ContentProvider {
    private static final String TAG = "BookProvider";

    private static final String AUTHORITY = "com.example.mr_wrong.androidstudioproject.provider";

    private static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/book");
    private static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user");

    private static final int BOOK_URI_CODE = 0;
    private static final int USER_URI_CODE = 1;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, "book", BOOK_URI_CODE);
        sUriMatcher.addURI(AUTHORITY, "user", USER_URI_CODE);
    }

    private Context mContext;
    private SQLiteDatabase mDb;

    @Override
    public boolean onCreate() {
        LogUtils.e("oncreate(),create thred:" + Thread.currentThread().getName());
        mContext = getContext();
        //初始化数据库  不建议在主线程中进行耗时操作  这是运行在主线程中的方法
        initProviderData();
        return true;
    }

    private void initProviderData() {
        mDb = new DbOpenHelper(mContext).getWritableDatabase();
        mDb.execSQL("delete from " + DbOpenHelper.BOOK_TABLE_NAME);
        mDb.execSQL("delete from " + DbOpenHelper.USER_TABLE_NAME);

        mDb.execSQL("insert into book values(3,'android');");
        mDb.execSQL("insert into book values(4,'ios');");
        mDb.execSQL("insert into book values(5,'python');");
        mDb.execSQL("insert into user values(1,'70kg',1);");
        mDb.execSQL("insert into user values(2,'80kg',0);");
    }

    @NotNull
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        LogUtils.e("query(),query thred:" + Thread.currentThread().getName());
        String table = gettab(uri);
        return mDb.query(table, projection, selection, selectionArgs, null, null, sortOrder, null);
    }

    @NotNull
    @Override
    public String getType(Uri uri) {
        return null;
    }

    private String gettab(Uri uri) {
        String table = getTabName(uri);
        if (table == null) {
            throw new IllegalArgumentException("unsupport uri" + uri);
        }
        return table;
    }

    private void notifychange(Uri uri) {
        mContext.getContentResolver().notifyChange(uri, null);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        LogUtils.e("insert");
        String table = gettab(uri);
        mDb.insert(table, null, values);
        notifychange(uri);
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        LogUtils.e("delete");
        String table = gettab(uri);
        int count = mDb.delete(table, selection, selectionArgs);
        if(count >0){
            notifychange(uri);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        LogUtils.e("update");
        String table = gettab(uri);
        int count = mDb.update(table, values, selection, selectionArgs);
        if(count >0){
            notifychange(uri);
        }
        return count;
    }

    private String getTabName(Uri uri) {
        String tableName = null;

        switch (sUriMatcher.match(uri)) {
            case BOOK_URI_CODE:
                tableName = DbOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DbOpenHelper.USER_TABLE_NAME;
                break;
        }

        return tableName;
    }
}
