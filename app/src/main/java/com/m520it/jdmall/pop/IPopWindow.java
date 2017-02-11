package com.m520it.jdmall.pop;

import android.content.Context;
import android.view.View;

public interface IPopWindow {
	
	public void onShow(View anchor);
	
	public void dismiss();
	
	public void initView(Context c);
	
}
