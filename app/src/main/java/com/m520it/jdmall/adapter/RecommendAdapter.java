package com.m520it.jdmall.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.m520it.jdmall.R;
import com.m520it.jdmall.bean.RGetYourFav;
import com.m520it.jdmall.cons.NetworkConst;

public class RecommendAdapter extends BaseAdapter {

	private ArrayList<RGetYourFav> beans=new ArrayList<RGetYourFav>();
	private Context mContext;
	
	public RecommendAdapter(Context c) {
		mContext=c;
	}
	
	public void setBeans(ArrayList<RGetYourFav> beans) {
		this.beans = beans;
	}

	@Override
	public int getCount() {
		return beans.size();
	}

	@Override
	public Object getItem(int position) {
		return beans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	class ViewHolder{
		SmartImageView mImageIv;
		TextView mNameTv;
		TextView mPriceTv;
	} 

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if (convertView==null) {
			convertView=LayoutInflater.from(mContext).inflate(R.layout.recommend_gv_item, parent,false);
			holder=new ViewHolder();
			holder.mImageIv=(SmartImageView) convertView.findViewById(R.id.image_iv);
			holder.mNameTv=(TextView) convertView.findViewById(R.id.name_tv);
			holder.mPriceTv=(TextView) convertView.findViewById(R.id.price_tv);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		RGetYourFav rGetYourFav = beans.get(position);
		holder.mImageIv.setImageUrl(NetworkConst.DOMAIN+rGetYourFav.getIconUrl());
		holder.mNameTv.setText(rGetYourFav.getName());
		holder.mPriceTv.setText("￥"+rGetYourFav.getPrice());
		return convertView;
	}

}
