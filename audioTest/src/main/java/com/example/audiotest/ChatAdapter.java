package com.example.audiotest;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatAdapter extends BaseAdapter {
	public static final int MSG_IN = 0;
	public static final int MSG_OUT = MSG_IN + 1;

	private Context mContext = null;
	private List<Map<String, Object>> mList = null;

	public ChatAdapter(Context context, List<Map<String, Object>> list) {
		this.mContext = context;
		this.mList = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList == null ? 0 : mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		if (mList.get(position).get("type").equals("1")) {
			return MSG_IN;
		}
		return MSG_OUT;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return MSG_OUT + 1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		int type = getItemViewType(position);
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			if (type == MSG_IN) {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.chat_msg_in_item, null);
				holder.mChatContentTv = (TextView) convertView.findViewById(R.id.chat_in_content);
			} else if(type == MSG_OUT) {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.chat_msg_out_item, null);
				holder.mChatContentTv = (TextView) convertView.findViewById(R.id.chat_out_content);
				holder.mChatAudioIv = (ImageView) convertView.findViewById(R.id.chat_out_audio);
			}
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.mChatContentTv.setText((CharSequence) mList.get(position).get("content"));
		if(type == MSG_OUT && mList.get(position).containsKey("audio")) {
			holder.mChatContentTv.setOnClickListener(new AudioClickListener(position, (String) mList.get(position).get("audio")));
		}
		return convertView;
	}

	class AudioClickListener implements OnClickListener {
		int mPosition;
		String mPath; 
		public AudioClickListener(int position, String path) {
			mPosition = position;
			mPath = path;
		}
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			AudioPlayer player = new AudioPlayer();
			player.playAudio(mPath);
		}
	}
	
	class ViewHolder {
		TextView mChatContentTv = null;
		ImageView mChatAudioIv = null;
	}
}
