package com.m520it.jdmall.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall.R;

public class ProductVersionAdapter extends BaseAdapter {
	
	private List<String> mDatas=new ArrayList<String>();
	private Context mContext;
	public int mPosition=-1;
	
	public ProductVersionAdapter(Context c) {
		this.mContext = c;
	}
	
	public String getSelectedItem() {
		return mPosition!=-1?mDatas.get(mPosition):"";
	}

	public void setDatas(String datas) {
		List<String> parseArray = JSON.parseArray(datas,String.class);
		if (parseArray!=null) {
			this.mDatas = parseArray;
		}
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
		Button productVersionTv=null;
		if (convertView==null) {
			convertView=LayoutInflater.from(mContext).inflate(R.layout.brand_lv_item_tv, null);
			productVersionTv=(Button) convertView.findViewById(R.id.brand_tv);
			convertView.setTag(productVersionTv);
		}else {
			productVersionTv=(Button) convertView.getTag();
		}
		productVersionTv.setText(mDatas.get(position));
		productVersionTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mPosition=position;
				notifyDataSetChanged();
			}
		});
		productVersionTv.setSelected(mPosition!=-1&&mPosition==position);
		return convertView;
	}

}
