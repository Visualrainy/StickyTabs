package com.example.audiotest;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements OnTouchListener,
		OnClickListener {

	private ListView mChatListView = null;
	private ChatAdapter mAdapter = null;
	private Button mRecordBtn = null;
	private AudioRecorder mAudioRecorder = null;
	private List<Map<String, Object>> mListData = null;
	private String mAudioPath = null;
	private float mAudioTime = 0;
	private Handler mHandler = new Handler();
	private Dialog mDialog = null;
	private ImageView mVolumeIv = null;
	private TextView mDialogTips = null;

	private ImageButton mChatKeyboardBtn = null;
	private ImageButton mChatSelectBtn = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getSupportActionBar().setTitle("sf");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(false);

		initViews();
		initDatas();
		Logger.d("MainActivity task id: " + getTaskId());
	}

	public void initViews() {
		mChatListView = (ListView) findViewById(R.id.chat_listview);
		mRecordBtn = (Button) findViewById(R.id.audio_record_btn);
		mRecordBtn.setOnTouchListener(this);

		mChatKeyboardBtn = (ImageButton) findViewById(R.id.chat_keyboard_btn);
		mChatKeyboardBtn.setOnClickListener(this);
		mChatSelectBtn = (ImageButton) findViewById(R.id.chat_select_btn);
		mChatSelectBtn.setOnClickListener(this);
	}

	public void initDatas() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			mAudioPath = getExternalCacheDir().getPath() + File.separator
					+ "test.amr";
		} else {
			mAudioPath = null;
		}
		mListData = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 20; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", i % 2 + "");
			map.put("content", "你好啊" + i);
			mListData.add(map);
		}
		mAdapter = new ChatAdapter(this, mListData);
		mChatListView.setAdapter(mAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			handleActionDown(event);
			break;
		case MotionEvent.ACTION_MOVE:
			handleActionMove(event);
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			handleActionUp(event);
			break;
		default:
			break;
		}
		return false;
	}

	private float mDownY = 0;
	private float mMoveY = 0;

	public void handleActionDown(MotionEvent event) {
		if (mAudioRecorder == null) {
			mAudioRecorder = new AudioRecorder();
		}
		mDownY = event.getY();
		mRecordState = RECORD_ON;
		mAudioRecorder.recorderAudio(mAudioPath);
		// recordTimethread();
		mHandler.postDelayed(mUpdateRunnable, 200);
		showAudioDialog(1);
		removeTempAmr();
	}

	private final int RECORD_OFF = 0;
	private final int RECORD_ON = 1;
	private final int RECORD_CANCEL = 2;
	private int mRecordState = RECORD_OFF;

	public void handleActionMove(MotionEvent event) {
		mMoveY = event.getY();
		if ((mMoveY > 0 && mMoveY - mDownY > 50)
				|| (mMoveY < 0 && mMoveY < -50)) {
			Logger.d("xxxxxx");
			showAudioDialog(0);
			mRecordState = RECORD_CANCEL;
		} else {
			mRecordState = RECORD_ON;
		}
	}

	public void handleActionUp(MotionEvent event) {
		Logger.d("event action: " + event.getAction());
		mHandler.removeCallbacks(mUpdateRunnable);
		if (mRecordState == RECORD_CANCEL) {
			mAudioRecorder.stopRecorder();
			mAudioRecorder = null;
		}
		if (mRecordState == RECORD_ON) {
			mAudioRecorder.stopRecorder();
			mAudioRecorder = null;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", "0");
			map.put("content", Math.round(mAudioTime) + "");
			map.put("audio", mAudioPath);
			mListData.add(map);
			mAdapter.notifyDataSetChanged();
		}
		mDialog.dismiss();
		// mRecordThread.interrupt();
		mAudioTime = 0f;
		mRecordState = RECORD_OFF;
	}

	public void showAudioDialog(int type) {
		if (mDialog == null) {
			mDialog = new Dialog(this, R.style.Theme_BaseDialog);
			mDialog.setCanceledOnTouchOutside(false);
			mDialog.setContentView(R.layout.record_dialog_view);
			mVolumeIv = (ImageView) mDialog
					.findViewById(R.id.record_dialog_img);
			mDialogTips = (TextView) mDialog
					.findViewById(R.id.record_dialog_txt);
		}
		if (type == 0) {
			mVolumeIv.setImageResource(R.drawable.record_cancel);
			mDialogTips.setText("松开手指可取消录音");
		} else {
			mVolumeIv.setImageResource(R.drawable.record_animate_01);
			mDialogTips.setText("向下滑动可取消录音");
		}
		mDialog.show();
	}

	private Thread mRecordThread;

	void recordTimethread() {
		mRecordThread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (mRecordState == RECORD_ON) {
					try {
						Thread.sleep(1500);
						int voiceValue = mAudioRecorder.getAmplitude();
						Logger.d("mAudioRecorder: " + mAudioRecorder);
						Logger.d(":" + voiceValue);
						// updateVolume(voiceValue);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		mRecordThread.start();
	}

	private Runnable mUpdateRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			mAudioTime += 0.2;
			Logger.d("mAudioRecorder: " + mAudioRecorder);
			int voiceValue = mAudioRecorder.getAmplitude();
			Logger.d("voiceValue:" + voiceValue);
			updateVolume(voiceValue);
			mHandler.postDelayed(mUpdateRunnable, 200);
		}
	};

	void updateVolume(int voiceValue) {
		if (voiceValue < 600.0) {
			mVolumeIv.setImageResource(R.drawable.record_animate_01);
		} else if (voiceValue > 600.0 && voiceValue < 1000.0) {
			mVolumeIv.setImageResource(R.drawable.record_animate_02);
		} else if (voiceValue > 1000.0 && voiceValue < 1200.0) {
			mVolumeIv.setImageResource(R.drawable.record_animate_03);
		} else if (voiceValue > 1200.0 && voiceValue < 1400.0) {
			mVolumeIv.setImageResource(R.drawable.record_animate_04);
		} else if (voiceValue > 1400.0 && voiceValue < 1600.0) {
			mVolumeIv.setImageResource(R.drawable.record_animate_05);
		} else if (voiceValue > 1600.0 && voiceValue < 1800.0) {
			mVolumeIv.setImageResource(R.drawable.record_animate_06);
		} else if (voiceValue > 1800.0 && voiceValue < 2000.0) {
			mVolumeIv.setImageResource(R.drawable.record_animate_07);
		} else if (voiceValue > 2000.0 && voiceValue < 3000.0) {
			mVolumeIv.setImageResource(R.drawable.record_animate_08);
		} else if (voiceValue > 3000.0 && voiceValue < 4000.0) {
			mVolumeIv.setImageResource(R.drawable.record_animate_09);
		} else if (voiceValue > 4000.0 && voiceValue < 6000.0) {
			mVolumeIv.setImageResource(R.drawable.record_animate_10);
		} else if (voiceValue > 6000.0 && voiceValue < 8000.0) {
			mVolumeIv.setImageResource(R.drawable.record_animate_11);
		} else if (voiceValue > 8000.0 && voiceValue < 10000.0) {
			mVolumeIv.setImageResource(R.drawable.record_animate_12);
		} else if (voiceValue > 10000.0 && voiceValue < 12000.0) {
			mVolumeIv.setImageResource(R.drawable.record_animate_13);
		} else if (voiceValue > 12000.0) {
			mVolumeIv.setImageResource(R.drawable.record_animate_14);
		}
	}

	public void removeTempAmr() {

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (v.getId()) {
		case R.id.chat_keyboard_btn:
			intent = new Intent(this, ClockAlarmActivity.class);
			startActivity(intent);
			break;
		case R.id.chat_select_btn:
			intent = new Intent(this, ShortCutActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
