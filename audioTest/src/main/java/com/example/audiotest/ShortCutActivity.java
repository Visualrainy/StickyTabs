package com.example.audiotest;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ShortCutActivity extends ActionBarActivity implements OnClickListener {
	private final String CREATE_SHORTCUT_ACTION = "com.android.launcher.action.INSTALL_SHORTCUT";
	private final String DROP_SHORTCUT_ACTION = "com.android.launcher.action.UNINSTALL_SHORTCUT";
	
	private Button mMoreOperationBtn = null;
	private TextView mMikeName = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shortc_cut_layout);
		
		mMikeName = (TextView) findViewById(R.id.mike_name);
		mMoreOperationBtn = (Button) findViewById(R.id.more_operation);
		mMoreOperationBtn.setOnClickListener(this);
		Rect rect = getIntent().getSourceBounds();
		if(rect != null) {
			Logger.d("left: " + rect.left);
			Logger.d("top: " + rect.top);
			Logger.d("right: " + rect.right);
			Logger.d("bottom: " + rect.bottom);}
		else {
			
		}
		Logger.d("ShortCutActivity task id: " + getTaskId());
	}
	
	public void setUpShortCut() {
		Intent intent = new Intent(CREATE_SHORTCUT_ACTION);
		intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(this, R.drawable.ic_launcher));
		intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, mMikeName.getText().toString());
		intent.putExtra("duplicate", false);
		
		Intent targetIntent = new Intent();
		targetIntent.setAction(Intent.ACTION_MAIN);
//		targetIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		targetIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		targetIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		Logger.d("setUpShortCut: " + this.getClass().getName());
		ComponentName componentName = new ComponentName(getPackageName(), ShortCutTargetActivity.class.getName());
		targetIntent.setComponent(componentName);
		
		intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, targetIntent);
		
		sendBroadcast(intent);
	}
	
	public void clearShortCut() {
		Intent intent = new Intent(DROP_SHORTCUT_ACTION);
		intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, mMikeName.getText().toString());
		Logger.d("clearShortCut: " + getPackageName() + "."+this.getLocalClassName());
		ComponentName componentName = new ComponentName(getPackageName(), getPackageName() + "."+this.getLocalClassName());
		intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent().setAction(Intent.ACTION_MAIN).setComponent(componentName));
		sendBroadcast(intent);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.more_operation:
//			startActivity(new Intent(this, MainActivity.class));
			setUpShortCut();
			break;

		default:
			break;
		}
	}
	
//	public boolean hasShortCut(String shortCutName) {
//		PackageManager pm = this.getPackageManager();
//		String appName = null;
//		try {
//			appName = pm.getApplicationLabel(pm.getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA)).toString();
//			Logger.d("app name is: " + appName);
//		} catch (NameNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String uri = null;
//		if(android.os.Build.VERSION.SDK_INT < 8) {
//			uri = "content://com.android.launcher.settings/favorites?notify=true";
//		} else {
//			uri = "content://com.android.launcher2.settings/favorites?notify=true";
//		}
//		
//		Cursor cursor = getContentResolver().query(Uri.parse(uri), null, "title=?", new String[]{appName}, null);
//		if(cursor != null && cursor.getCount() > 0) {
//			return true;
//		}
//		return false;
//	}
}
