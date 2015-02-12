package com.example.lizhijun.stickytabs.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface IBaseFragment {
	public View getFragmentView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState);

	public void init(View rootView);
}
