package com.example.audiotest;

import java.io.IOException;

import android.media.MediaRecorder;

public class AudioRecorder {
	private MediaRecorder recorder = null;

	public AudioRecorder() {
		initRecorder();
	}
	
	public void initRecorder() {
		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
	}
	
	public void recorderAudio(String outPath) {
		if(outPath == null) {
			return ;
		}
		recorder.setOutputFile(outPath);
		try {
			recorder.prepare();
			recorder.getMaxAmplitude();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		recorder.start();
//		Logger.d("yyyyyyyyyy:" + recorder.getMaxAmplitude());
	}
	
	public void stopRecorder() {
		if(recorder != null) {
			recorder.stop();
			recorder.reset();
			recorder.release();
			recorder = null;
		}
	}
	
	public int getAmplitude() {
		return recorder.getMaxAmplitude();
	}
}
