package com.example.lizhijun.stickytabs.Fragment;

import android.view.View;

/**
 * 扩展的listAdapter接口
 * @author Visual
 *
 */
public interface ExpandListAdapter {
	
	/**
	 * 获取
	 * @return viewholder(object)实例
	 */
	public Object getViewHolder();
	
	/**
	 * 初始化viewholder(object)中的控件
	 * @param object
	 * @param view
	 */
	public void initView(Object viewHolder, View convertView);
	
	/**
	 * 设置viewholder(object)中的数据
	 * @param object
	 */
	public void setData(Object viewHolder, int position, View convertView);
}
