package com.example.lizhijun.stickytabs.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.lizhijun.stickytabs.R;


/**
 * 关注页
 * @author Visual
 *
 */
public class ContactDetailFragment extends ContactDetailBaseFragment implements OnItemClickListener
		 {

	private ListView mList = null;
	private ContactDetailAdapter mAdapter = null;
	
	public final static ContactDetailFragment newInstance(boolean autoLoad) {
		ContactDetailFragment fragment = new ContactDetailFragment();
		Bundle bundle = new Bundle();
		bundle.putBoolean("auto_load", autoLoad);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		List<Map<String, String>> data = null;
		if(mAdapter == null) {
			data = new ArrayList<Map<String,String>>();
			Map<String, String> map = new HashMap<String, String>();
			map.put("detail_item", "手机");
			map.put("detail_brief", "四川移动  默认");
			map.put("detail_content", "成都市双顶街5号");
			data.add(map);
			for(int i = 0; i < 20; i++) {
				map = new HashMap<String, String>();
				map.put("detail_item", "手机"+i);
				map.put("detail_brief", "四川移动  默认");
				map.put("detail_content", "+861881224684" + i);
				data.add(map);
			}
			mAdapter = new ContactDetailAdapter(getActivity(), R.layout.contact_detail_list_item, data);
		}
	}
	
	@Override
	public View getFragmentView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.contact_detail_fragment, container, false);
	}

	@Override
	public void init(View rootView) {
		// TODO Auto-generated method stub
		mList = (ListView) rootView.findViewById(R.id.contact_detail_list);
		mList.setAdapter(mAdapter);
		mList.setOnItemClickListener(this);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
//		mList.setAdapter(mAdapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
	}

	@Override
	public ListView getCurrentList() {
		// TODO Auto-generated method stub
		return mList;
	}
}
