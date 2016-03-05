package com.easyroads.app.utils;

import android.util.Log;


public class Logger {

	public static String TAG = "EasyRoads";
	public static Logger instance;

	private boolean debugEnabled;

	public static synchronized Logger init() {
		if (null == instance) {
			instance = new Logger();
		}
		return instance;
	}

	public Logger() {
		super();
		debugEnabled = false;
	}

	public void debug(String msg) {
		if (debugEnabled && msg != null) {
			Log.d(TAG, msg);
		}
	}

	public void debug(String msg, Throwable t) {
		if (debugEnabled && msg != null) {
			Log.d(TAG, msg, t);
		}
	}

	public void debug(Throwable t) {
		if (debugEnabled) {
			Log.d(TAG, "Exception: ", t);
		}
	}

	public void debug(String tag, String msg) {
		if (debugEnabled && msg != null) {
			Log.d(tag, msg);
		}
	}

	public void warn(String msg) {
		if (debugEnabled && msg != null) {
			Log.w(TAG, msg);
		}
	}

	public void info(String msg) {
		if (debugEnabled && msg != null) {
			Log.i(TAG, msg);
		}
	}

	public void error(String msg) {
		if (debugEnabled && msg != null) {
			Log.e(TAG, msg);
		}
	}
}
