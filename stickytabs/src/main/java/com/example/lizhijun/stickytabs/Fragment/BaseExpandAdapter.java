package com.example.lizhijun.stickytabs.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 扩展的BaseAdapterView
 * @author Visual
 *
 * @param <T>
 */
public abstract class BaseExpandAdapter<T> extends BaseAdapter implements ExpandListAdapter {

	protected Context context = null;
	protected LayoutInflater inflater = null;
	protected int resourceId;
	protected List<T> list = null;
	
	public BaseExpandAdapter(Context context, int resourceId) {
		init(context, resourceId, new ArrayList<T>());
	}
	
	public BaseExpandAdapter(Context context, int resourceId, List<T> data){
		init(context, resourceId, data);
	}
	
	/**
	 * 初始化数据
	 * @param context
	 * @param resourceId
	 * @param data
	 */
	protected void init(Context context, int resourceId, List<T> data) {
		this.context = context;
		this.resourceId = resourceId;
		this.inflater = LayoutInflater.from(context);
		this.list = data;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return null == list ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Object viewHolder = null;
		if(null == convertView) {
			viewHolder = getViewHolder();
			convertView = inflater.inflate(resourceId, parent, false);
			initView(viewHolder, convertView);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = convertView.getTag();
		}
		setData(viewHolder,position, convertView);
		return convertView;
	}
}
