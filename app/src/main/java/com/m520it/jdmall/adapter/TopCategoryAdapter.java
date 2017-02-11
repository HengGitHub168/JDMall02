package com.m520it.jdmall.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.m520it.jdmall.R;
import com.m520it.jdmall.bean.RBaseCategory;

public class TopCategoryAdapter extends BaseAdapter {
	
	private Context mContext;
	public int mPosition;
	private ArrayList<RBaseCategory> mDatas=new ArrayList<RBaseCategory>();
	
	public TopCategoryAdapter(Context c) {
		mContext=c;
	}

	public void setBeans(ArrayList<RBaseCategory> rBaseCategorys) {
		mDatas=rBaseCategorys;
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
		return position;
	}
	
	class ViewHolder{
		public TextView titleTv;
		public View dividerTv;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if (convertView==null) {
			convertView=LayoutInflater.from(mContext).inflate(R.layout.top_category_item, null);
			holder=new ViewHolder();
			holder.titleTv=(TextView) convertView.findViewById(R.id.tv);
			holder.dividerTv=(View) convertView.findViewById(R.id.divider);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		//设置左边的红色线条和背景颜色
		if (mPosition==position) {
			holder.titleTv.setBackgroundResource(R.drawable.tongcheng_all_bg01);
			holder.dividerTv.setVisibility(View.INVISIBLE);
		}else {
			holder.titleTv.setBackgroundColor(0xf4f4f4);
			holder.dividerTv.setVisibility(View.VISIBLE);
		}
		holder.titleTv.setText(mDatas.get(position).getName());
		//文字的颜色是一个Selector 设置文字的颜色
		holder.titleTv.setSelected(mPosition==position);
		return convertView;
	}
	
}

