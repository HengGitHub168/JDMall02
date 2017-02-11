package com.m520it.jdmall.contrller;

import android.app.Activity;
import android.content.Context;

import com.m520it.jdmall.JDApplication;
import com.m520it.jdmall.listener.IModelChangeListener;

/**
 *	创建控制器基类
 */
public abstract class BaseController {
	
	protected IModelChangeListener mModelChangeListener;
	protected Context mContext;
	
	protected String getUserId() {
		JDApplication application=(JDApplication) ((Activity)mContext).getApplication();
		if (application.mUserInfo!=null) {
			return application.mUserInfo.getId();
		}
		return "";
	}
	
	public BaseController(Context c) {
		mContext=c;
	}
	
	public void setIModelChangeListener(IModelChangeListener listener) {
		this.mModelChangeListener = listener;
	}
	
	/**
	 * 处理获取数据的操作 由子类来实现
	 * */
	protected abstract void handleMessage(int action, Object[] values);

	/**
	 * 	发送异步数据时 子类handleMessage(action,values);来处理
	 */
	public void sendAsyncMessage(final int action,final Object... values){
		new Thread(){
			public void run() {
				handleMessage(action,values);
			}
		}.start();
	}
	

	/**
	 * 	发送同步数据
	 */
	public void sendMessage(final int action,final Object... values){
		handleMessage(action,values);
	}
	
}
