package com.example.audiotest;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ClockAlarmActivity extends ActionBarActivity implements
		OnClickListener {
	private Button mStartAlarmBtn = null;
	private EditText mAlarmEt = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);

		initViews();
	}

	protected void initViews() {
		mAlarmEt = (EditText) findViewById(R.id.alarm_time);
		mStartAlarmBtn = (Button) findViewById(R.id.start_alarm);
		mStartAlarmBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.start_alarm:
			startAlarm();
			break;

		default:
			break;
		}
	}

	public void startAlarm() {
		Logger.d("start Alarm");
		long systemTime = System.currentTimeMillis();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(systemTime);
		calendar.add(Calendar.SECOND, Integer.parseInt(mAlarmEt.getText().toString()));
		
		Intent intent = new Intent(this, AlarmReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		
		alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
	}
}
