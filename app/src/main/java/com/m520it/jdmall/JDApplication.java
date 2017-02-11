package com.m520it.jdmall;

import android.app.Application;

import com.m520it.jdmall.bean.RLogin;

public class JDApplication extends Application {
	
	public RLogin mUserInfo; 
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
}
