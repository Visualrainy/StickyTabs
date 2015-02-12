package com.example.lizhijun.stickytabs.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.lizhijun.stickytabs.MainApplication;
import com.example.lizhijun.stickytabs.R;



public class SystemUtils {
	public static String MIUI_V6 = "V6";
	public static String MIUI_V5 = "V5";

	/**
	 * 将dip转为px
	 * 
	 * @param dip
	 * @return
	 */
	public static int dipToPx(int dip) {
		float scale = MainApplication.getInstance().getResources()
				.getDisplayMetrics().density;
		return (int) (dip * scale + 0.5);
	}

	/**
	 * 将px转为dip
	 * 
	 * @param px
	 * @return
	 */
	public static int pxToDip(int px) {
		float scale = MainApplication.getInstance().getResources()
				.getDisplayMetrics().density;
		return (int) (px / scale + 0.5);
	}

	/**
	 * 获取屏幕的宽度 px
	 * 
	 * @return
	 */
	public static int getScreenWidth() {
		return MainApplication.getInstance().getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 获取屏幕高度 px
	 * 
	 * @return
	 */
	public static int getScreenHeight() {
		return MainApplication.getInstance().getResources().getDisplayMetrics().heightPixels;
	}

	/**
	 * 获取状态栏的高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getStatusHeight(Context context) {
		int statusHeight = 0;
		Rect localRect = new Rect();
		((Activity) context).getWindow().getDecorView()
				.getWindowVisibleDisplayFrame(localRect);
		statusHeight = localRect.top;
		if (0 == statusHeight) {
			Class<?> localClass;
			try {
				localClass = Class.forName("com.android.internal.R$dimen");
				Object localObject = localClass.newInstance();
				int i5 = Integer.parseInt(localClass
						.getField("status_bar_height").get(localObject)
						.toString());
				statusHeight = context.getResources().getDimensionPixelSize(i5);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return statusHeight;
	}

	/**
	 * 获取actionbar的size
	 * 
	 * @param context
	 * @return
	 */
	public static int getActionbarSize(Context context) {
		TypedArray actionbarSizeTypedArray = context
				.obtainStyledAttributes(new int[] { R.attr.actionBarSize });
		float h = actionbarSizeTypedArray.getDimension(0, 0);
		actionbarSizeTypedArray.recycle();
		return (int) h;
	}

	/**
	 * 设定listview的高度
	 * 
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	/**
	 * 获取listview的高度
	 * 
	 * @param listView
	 * @return
	 */
	public static int getListViewHeight(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return 0;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		return totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
	}

	/**
	 * 显示软键盘
	 * 
	 * @param view
	 */
	public static void showInputMethod(EditText view) {
		InputMethodManager inputManager = (InputMethodManager) view
				.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
	}

	/**
	 * 隐藏输入法（避免自动弹出输入法）
	 * 
	 * @param context
	 */
	public static void hideInputMethod(Activity context) {
		context.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);// 隐藏输入法
	}

	/**
	 * 隐藏软键盘
	 * 
	 * @param view
	 */
	public static void hideInputMethod(EditText view) {
		// 如果软键盘之前已经弹出，则关闭
		InputMethodManager imm = (InputMethodManager) MainApplication
				.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	/**
	 * 禁止弹出软键盘
	 * 
	 * @param view
	 */
	public static void banInputMethod(EditText view) {
		// 禁止弹出软键盘
		view.setInputType(InputType.TYPE_NULL);
		// 如果软键盘之前已经弹出，则关闭
		hideInputMethod(view);
	}

	public static boolean clickInView(MotionEvent ev, View view) {
		Rect rect = new Rect();
		if (view == null) {
			return false;
		}
		view.getGlobalVisibleRect(rect);
		if (rect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
			return true;
		}
		return false;
	}

}
