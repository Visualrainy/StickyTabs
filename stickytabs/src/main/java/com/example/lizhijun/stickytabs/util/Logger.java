package com.example.lizhijun.stickytabs.util;

import android.util.Log;

/**
 * 日之类
 * @author Visual
 *
 */
public class Logger {
	private static final String TAG = "MikeCRM";
	
	/**
	 * verbose类信息
	 * @param msg
	 */
	public static void v(String msg) {
		Log.v(TAG, msg);
	}
	
	/**
	 * debug类信息
	 * @param msg
	 */
	public static void d(String msg) {
		Log.d(TAG, msg);
	}
	
	/**
	 * info类信息
	 * @param msg
	 */
	public static void i(String msg) {
		Log.i(TAG, msg);
	}
	
	/**
	 * warn类信息
	 * @param msg
	 */
	public static void w(String msg) {
		Log.w(TAG, msg);
	}
	
	/**
	 * error类信息
	 * @param msg
	 */
	public static void e(String msg) {
		Log.e(TAG, msg);
	}
}
