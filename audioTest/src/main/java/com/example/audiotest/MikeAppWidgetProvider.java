package com.example.audiotest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.SparseArray;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViews.RemoteView;

public class MikeAppWidgetProvider extends AppWidgetProvider {

	private static List<Integer> mAppWidgetIds = new ArrayList<Integer>();
	private static List<Boolean> mAppWidgetFlag = new ArrayList<Boolean>();

	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
		Logger.d("onUpdate");
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		Logger.d("onUpdate");
		for (int i : appWidgetIds) {
			Logger.d("appwidgetIds: " + i);
			mAppWidgetIds.add(i);
			mAppWidgetFlag.add(false);
		}
		updateWidget(context, appWidgetIds, appWidgetManager);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
		Logger.d("action:" + intent.getAction());
		Logger.d("onReceive");
		if (intent.getAction().equals("com.example.audiotest.ON_MORE_CLICK")) {
			int appWidgetId = intent.getIntExtra("app_widget_id", 0);
			int index = mAppWidgetIds.indexOf((Integer) appWidgetId);
			if (appWidgetId != 0 && index != -1) {
				RemoteViews remoteViews = new RemoteViews(
						context.getPackageName(), R.layout.widget_layout);
				if (!mAppWidgetFlag.get(index)) {
					remoteViews.setViewVisibility(R.id.hide_layout, View.VISIBLE);
					mAppWidgetFlag.set(index, true);
				} else {
					mAppWidgetFlag.set(index, false);
					remoteViews.setViewVisibility(R.id.hide_layout,
							View.GONE);
				}
				AppWidgetManager.getInstance(context).updateAppWidget(appWidgetId, remoteViews);
			}
		}
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
		Logger.d("onDeleted");
		for (int i : appWidgetIds) {
			int index = mAppWidgetIds.indexOf((Integer) i);
			if(index != -1) {
				mAppWidgetIds.remove(index);
				mAppWidgetFlag.remove(index);
			}
		}
	}

	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		super.onDisabled(context);
		Logger.d("onDisabled");
	}

	public void updateWidget(Context context, int[] appWidgetIds,
			AppWidgetManager appWidgetManager) {
		for (int i : appWidgetIds) {
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
					R.layout.widget_layout);
			Intent intent = new Intent("com.example.audiotest.ON_MORE_CLICK");
			intent.putExtra("app_widget_id", i);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
					i, intent, 0);
//			Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:123456789"));
//			PendingIntent pendingIntent = PendingIntent.getActivity(context, i, intent, 0);
			remoteViews.setOnClickPendingIntent(R.id.more_operation,
					pendingIntent);
			appWidgetManager.updateAppWidget(i, remoteViews);
		}
	}
}
