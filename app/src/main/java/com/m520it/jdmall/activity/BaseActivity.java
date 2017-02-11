package com.m520it.jdmall.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.m520it.jdmall.R;
import com.m520it.jdmall.pop.LoadingDailog;

/**
 *	所有Activity的基类
 */
public class BaseActivity extends Activity {
	
	protected LoadingDailog mLodingDailog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mLodingDailog=new LoadingDailog(this, R.style.CustomDialog);
	}
	
	public void goBack(View view){
		finish();
	}
	
}
