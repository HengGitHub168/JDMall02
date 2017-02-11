package com.m520it.jdmall.listener;

import com.m520it.jdmall.bean.RBuildedOrder;

public interface IBuildOrderListener {
	
	public void onSure(RBuildedOrder order);
	
	public void onCancel();
	
}
