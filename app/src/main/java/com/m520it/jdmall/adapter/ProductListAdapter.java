package com.m520it.jdmall.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.m520it.jdmall.R;
import com.m520it.jdmall.bean.RSearchProduct;
import com.m520it.jdmall.cons.NetworkConst;

public class ProductListAdapter extends BaseAdapter {
	
	private List<RSearchProduct> mDatas=new ArrayList<RSearchProduct>();
	private Context mContext;
	
	public ProductListAdapter(Context c) {
		mContext=c;
	}
	
	public void setDatas(List<RSearchProduct> mDatas) {
		this.mDatas = mDatas;
	}

	@Override
	public int getCount() {
		return mDatas.size(); 
	}

	@Override
	public Object getItem(int arg0) {
		return mDatas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}
	
	class ViewHolde{
		SmartImageView iconIv;
		TextView nameTv;
		TextView commentRateTv;
		TextView priceTv;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		ViewHolde holde=null;
		if (convertView==null) {
			convertView=LayoutInflater.from(mContext).inflate(R.layout.product_lv_item, null);
			holde=new ViewHolde();
			holde.iconIv=(SmartImageView) convertView.findViewById(R.id.product_iv);
			holde.nameTv=(TextView) convertView.findViewById(R.id.name_tv);
			holde.commentRateTv=(TextView) convertView.findViewById(R.id.commrate_tv);
			holde.priceTv=(TextView) convertView.findViewById(R.id.price_tv);
			convertView.setTag(holde);
		}else {
			holde = (ViewHolde) convertView.getTag();
		}
		RSearchProduct bean = mDatas.get(arg0);
		holde.iconIv.setImageUrl(NetworkConst.DOMAIN+bean.getIconUrl());
		holde.nameTv.setText(bean.getName());
		holde.commentRateTv.setText(bean.getCommentCount()+"条评价  好评"+bean.getFavcomRate()+"%");
		holde.priceTv.setText("￥"+bean.getPrice());
		return convertView;
	}

}
