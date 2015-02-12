package com.example.lizhijun.stickytabs.fragment;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.lizhijun.stickytabs.R;


public class ContactDetailAdapter extends BaseExpandAdapter<Map<String, String>> {

	public ContactDetailAdapter(Context context, int resourceId, List<Map<String, String>> data) {
		super(context, resourceId, data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object getViewHolder() {
		// TODO Auto-generated method stub
		return new ViewHolder();
	}

	@Override
	public void initView(Object viewHolder, View convertView) {
		// TODO Auto-generated method stub
		((ViewHolder)viewHolder).mDetailItemNameTv = (TextView) convertView
				.findViewById(R.id.detail_item_name);
		((ViewHolder)viewHolder).mDetailItemBriefTv = (TextView) convertView
				.findViewById(R.id.detail_item_brief);
		((ViewHolder)viewHolder).mDetailContentTv = (TextView) convertView
				.findViewById(R.id.detail_item_content);
	}

	@Override
	public void setData(Object viewHolder, int position, View convertView) {
		// TODO Auto-generated method stub
		((ViewHolder)viewHolder).mDetailItemNameTv.setText(list.get(position).get("detail_item"));
		((ViewHolder)viewHolder).mDetailItemBriefTv.setText(list.get(position).get("detail_brief"));
		((ViewHolder)viewHolder).mDetailContentTv.setText(list.get(position).get("detail_content"));
	}
	
	class ViewHolder {
		TextView mDetailItemNameTv = null;
		TextView mDetailItemBriefTv = null;
		TextView mDetailContentTv  = null;
	}

}
