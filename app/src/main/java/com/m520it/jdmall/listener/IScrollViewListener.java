package com.m520it.jdmall.listener;

import com.m520it.jdmall.view.ObservableScrollView;

public interface IScrollViewListener {
	public void onScrollChanged(ObservableScrollView scrollView, int x, int y,
			int oldx, int oldy);
}
