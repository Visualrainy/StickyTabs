package com.example.audiotest;

import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;

public class AudioPlayer implements OnCompletionListener, OnErrorListener {
	private MediaPlayer player = null;

	public AudioPlayer() {
		initPlayer();
	}
	
	public void initPlayer() {
		player = new MediaPlayer();
		player.setOnCompletionListener(this);
		player.setOnErrorListener(this);
	}
	
	public void playAudio(String srcPath) {
		if(srcPath == null) {
			return ;
		}
		try {
			player.setDataSource(srcPath);
			player.prepare();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		player.start();
	}
	
	public void stopRecorder() {
		if(player != null) {
			player.stop();
			player.reset();
			player.release();
			player = null;
		}
	}

	public boolean isPlaying() {
		if(player == null || !player.isPlaying()) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		Logger.d("player error");
		return false;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		Logger.d("player completion");
	}
}
