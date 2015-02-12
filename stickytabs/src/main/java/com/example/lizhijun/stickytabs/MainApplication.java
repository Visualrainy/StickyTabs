
package com.example.lizhijun.stickytabs;

import android.app.Application;

/**
 * application继承类(应用程序唯一实例类）
 * @author Visual
 *
 */
public class MainApplication extends Application {

	private static MainApplication mInstance;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mInstance = this;
	}
	
	/**
	 * 单利模式获取全局唯一实例
	 * @return
	 */
	public static MainApplication getInstance() {
		if (mInstance == null) {
			synchronized(MainApplication.class) {
				if(mInstance == null) { 
					mInstance = new MainApplication();
				}
			}
		}
		return mInstance;
	}
}
