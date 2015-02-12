package com.example.lizhijun.stickytabs.Fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lizhijun.stickytabs.util.Logger;

/**
 * baseFragment
 * 
 * @author Visual
 * 
 */
public abstract class BaseFragment extends Fragment implements IBaseFragment {
	protected View mRootView = null;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		Logger.d(this+":onAttach" );
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Logger.d(this+":onCreate" );
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Logger.d(this + ":onCreateView");
//		if (mRootView == null) {
			mRootView = getFragmentView(inflater, container, savedInstanceState);
			init(mRootView);
//		}
//		ViewGroup parent = (ViewGroup) mRootView.getParent();
//		if (parent != null) {
//			parent.removeView(mRootView);
//		}
		return mRootView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Logger.d(this+":onActivityCreated" );
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Logger.d(this+":onResume" );
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Logger.d(this+":onPause" );
	}
	
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Logger.d(this+":onStop" );
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Logger.d(this+":onDestroyView" );
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Logger.d(this+":onDestroy" );
	}
	
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		Logger.d(this+":onDetach" );
	}
	
	/**
	 * 最外层fragment是否拦截事件(是否可以侧滑menu)
	 * @return
	 */
	public boolean getIntercept(){
		return true;
	};
}
