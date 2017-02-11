package com.m520it.jdmall.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.loopj.android.image.SmartImageView;
import com.m520it.jdmall.bean.RBanner;
import com.m520it.jdmall.cons.NetworkConst;

public class HomeAdAdapter extends PagerAdapter {
	
	private ArrayList<SmartImageView> mAdIvs=new ArrayList<SmartImageView>();
	private Context mContext;
	
	public HomeAdAdapter(Context c) {
		mContext=c;
	}
	

	public void setBeans(ArrayList<RBanner> banners) {
		for (RBanner rBanner : banners) {
			SmartImageView adIv=new SmartImageView(mContext);
			adIv.setLayoutParams(new LayoutParams(-1,-1));
			adIv.setImageUrl(NetworkConst.DOMAIN+rBanner.getAdUrl());
			mAdIvs.add(adIv);
		}
	}


	@Override
	public int getCount() {
		return mAdIvs.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView imageView = mAdIvs.get(position);
		container.addView(imageView);
		return imageView;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		ImageView imageView = mAdIvs.get(position);
		container.removeView(imageView);
	}
}
