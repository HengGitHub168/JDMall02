package com.m520it.jdmall.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.m520it.jdmall.R;
import com.m520it.jdmall.bean.RSecKill;
import com.m520it.jdmall.cons.NetworkConst;

public class HomeSecKillAdapter extends BaseAdapter {
	
	private ArrayList<RSecKill> beans=new ArrayList<RSecKill>();
	private Context mContext;
	
	public HomeSecKillAdapter(Context c) {
		mContext=c;
	}
	
	public void setBeans(ArrayList<RSecKill> beans) {
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
		TextView mNowPriceTv;
		TextView mPriceTv;
	} 

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if (convertView==null) {
			convertView=LayoutInflater.from(mContext).inflate(R.layout.home_seckill_item, null);
			holder=new ViewHolder();
			holder.mImageIv=(SmartImageView) convertView.findViewById(R.id.image_iv);
			holder.mNowPriceTv=(TextView) convertView.findViewById(R.id.nowprice_tv);
			holder.mPriceTv=(TextView) convertView.findViewById(R.id.normalprice_tv);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		RSecKill rSecKill = beans.get(position);
		holder.mImageIv.setImageUrl(NetworkConst.DOMAIN+rSecKill.getIconUrl());
		holder.mNowPriceTv.setText("￥"+rSecKill.getPointPrice());
		holder.mPriceTv.setText(" ￥"+rSecKill.getAllPrice()+" ");
		holder.mPriceTv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
		return convertView;
	}

}
