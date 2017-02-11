package com.m520it.jdmall.frag;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.m520it.jdmall.R;
import com.m520it.jdmall.pop.LoadingDailog;

public class BaseFragment extends Fragment {
	
	protected LoadingDailog mLoadingDailog;
	
	protected void showBuidingTip(){
		Toast.makeText(getActivity(), "模块暂未开放...", 800).show();
	};
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mLoadingDailog=new LoadingDailog(getActivity(), R.style.CustomDialog);
	}
	
}
