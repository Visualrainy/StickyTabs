package com.example.audiotest;

import android.util.Log;

/**
 * 日志类
 * @author Visual
 *
 */
public class Logger {
	public static final String TAG = "MikeAdmin";

	/**
	 * 打印Verbose信息
	 * @param msg
	 */
	public static void v(String msg) {
		Log.v(TAG, msg);
	}

	/**
	 * 打印Debug信息
	 * @param msg
	 */
	public static void d(String msg) {
		Log.d(TAG, msg);
	}

	/**
	 * 打印Info打印
	 * @param msg
	 */
	public static void i(String msg) {
		Log.i(TAG, msg);
	}

	/**
	 * 打印Warn信息
	 * @param msg
	 */
	public static void w(String msg) {
		Log.w(TAG, msg);
	}

	/**
	 * 打印Error信息
	 * @param msg
	 */
	public static void e(String msg) {
		Log.e(TAG, msg);
	}
}
