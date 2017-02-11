package com.m520it.jdmall.pop;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;

import com.m520it.jdmall.R;
import com.m520it.jdmall.bean.SProductList;
import com.m520it.jdmall.listener.IProductsSortListener;

public class ProductsSortPopWindow implements IPopWindow, OnClickListener{
	
	private PopupWindow mPw;
	private View mContentView;
	private IProductsSortListener mListener;
	
	public void setListener(IProductsSortListener mListener) {
		this.mListener = mListener;
	}

	public ProductsSortPopWindow(Context c) {
		initView(c);
	}
	
	@Override
	public void onShow(View anchor) {
		if (!mPw.isShowing()) {
			mPw.showAsDropDown(anchor);
		}
	}

	@Override
	public void dismiss() {
		if (mPw.isShowing()) {
			mPw.dismiss();
		}
	}

	@Override
	public void initView(Context c) {
		mContentView=LayoutInflater.from(c).inflate(R.layout.product_sort_pop_view, null);
		mContentView.findViewById(R.id.all_sort).setOnClickListener(this);
		mContentView.findViewById(R.id.new_sort).setOnClickListener(this);
		mContentView.findViewById(R.id.comment_sort).setOnClickListener(this);
		mContentView.findViewById(R.id.left_v).setOnClickListener(this);
		
		mPw=new PopupWindow(mContentView,-1,-2);
		mPw.setFocusable(true);
		mPw.setOutsideTouchable(true);
		mPw.setBackgroundDrawable(new BitmapDrawable());
		mPw.update();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.all_sort:
				mListener.onSortChange(SProductList.SEARCH_ALL);
				break;
			case R.id.new_sort:
				mListener.onSortChange(SProductList.SEARCH_NEW);
				break;
			case R.id.comment_sort:
				mListener.onSortChange(SProductList.SEARCH_COMMENT_UP2DOWN);
				break;
		}
		dismiss();
	}

}
