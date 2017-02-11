package com.m520it.jdmall.listener;

public interface IModelChangeListener {
	
	//加载完数据的时候回调该方法
	public void onModelChanged(int action, Object... values);
	
}
