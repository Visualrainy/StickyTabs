package com.example.audiotest;

import android.app.Activity;
import android.os.Bundle;

public class ShortCutTargetActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shortc_cut_layout);
		Logger.d("ShortCutTargetActivity task id: " + getTaskId());
	}
}
