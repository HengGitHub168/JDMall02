package com.m520it.jdmall.pop;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.m520it.jdmall.R;

public class LoadingDailog extends AlertDialog {

	private ImageView mLoadingIv;

	public LoadingDailog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading_dialog_view);
		mLoadingIv=(ImageView)findViewById(R.id.loading_iv);
		AnimationDrawable drawable = (AnimationDrawable) mLoadingIv.getDrawable();
		drawable.start();
	}
	
	
}
