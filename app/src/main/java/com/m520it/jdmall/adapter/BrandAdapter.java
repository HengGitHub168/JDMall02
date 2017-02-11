package com.m520it.jdmall.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.m520it.jdmall.R;
import com.m520it.jdmall.bean.BrandBean;

public class BrandAdapter extends BaseAdapter {
	
	private ArrayList<BrandBean> mDatas=new ArrayList<BrandBean>();
	private Context mContext;
	public int mPosition=-1;
	
	public BrandAdapter(Context c) {
		this.mContext = c;
	}
	
	public void setDatas(ArrayList<BrandBean> mDatas) {
		this.mDatas = mDatas;
	}
	
	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Button brandNameTv=null;
		if (convertView==null) {
			convertView=LayoutInflater.from(mContext).inflate(R.layout.brand_lv_item_tv, null);
			brandNameTv=(Button) convertView.findViewById(R.id.brand_tv);
			convertView.setTag(brandNameTv);
		}else {
			brandNameTv=(Button) convertView.getTag();
		}
		brandNameTv.setText(mDatas.get(position).getName());
		brandNameTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mPosition=position;
				notifyDataSetChanged();
			}
		});
		brandNameTv.setSelected(mPosition!=-1&&mPosition==position);
		return convertView;
	}

}
