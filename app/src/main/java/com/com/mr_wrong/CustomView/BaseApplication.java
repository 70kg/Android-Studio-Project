package com.com.mr_wrong.CustomView;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

public class BaseApplication extends Application {

	private static Context mContext;

	private static Handler mHandler = new Handler() {
	};

	@Override
	public void onCreate() {
		mContext = this;
	}

	public static Context getContext() {
		return mContext;
	}

	public static Handler getMainThreadHandler() {
		return mHandler;
	}

	/** dip转换px */
	public static int dip2px(int dip) {
		final float scale = getContext().getResources().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f);
	}

}
