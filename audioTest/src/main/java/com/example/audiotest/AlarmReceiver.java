package com.example.audiotest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Logger.d("onReceive");
		NotificationManager manager = (NotificationManager) context
				.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		Intent playIntent = new Intent(context, MainActivity.class);
		playIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		PendingIntent pendingIntent = PendingIntent.getActivity(context, 1,
				playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				context);
		builder.setContentTitle("title").setContentText("提醒内容")
				.setSmallIcon(R.drawable.ic_launcher)
				.setDefaults(Notification.DEFAULT_ALL)
				.setContentIntent(pendingIntent).setAutoCancel(true)
				.setSubText("二级text");
		manager.notify(1, builder.build());
	}
}
