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
import com.m520it.jdmall.bean.OrderDetailsBean;
import com.m520it.jdmall.bean.OrderProducts;
import com.m520it.jdmall.cons.NetworkConst;

public class OrderDetailsProductsAdapter extends BaseAdapter {

	private List<OrderProducts> mDatas = new ArrayList<OrderProducts>();
	private Context mContext;

	public OrderDetailsProductsAdapter(Context c) {
		mContext = c;
	}

	public void setDatas(List<OrderProducts> datas) {
		this.mDatas = datas;
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

	class ViewHolder {
		public SmartImageView iconIv;
		public TextView pNameTv;
		public TextView buyCountTv;
		public TextView priceTv;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holde = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.order_details_products_item, null);
			holde = new ViewHolder();
			holde.iconIv=(SmartImageView) convertView.findViewById(R.id.p_icon_iv);
			holde.pNameTv=(TextView) convertView.findViewById(R.id.p_name_iv);
			holde.buyCountTv=(TextView) convertView.findViewById(R.id.p_buycount_iv);
			holde.priceTv=(TextView) convertView.findViewById(R.id.p_price_iv);
			convertView.setTag(holde);
		} else {
			holde = (ViewHolder) convertView.getTag();
		}
		OrderProducts bean = mDatas.get(position);
		holde.iconIv.setImageUrl(NetworkConst.DOMAIN+bean.getPiconUrl());
		holde.pNameTv.setText(bean.getPname());
		holde.buyCountTv.setText("X "+bean.getBuyCount());
		holde.priceTv.setText("￥ "+bean.getAmount());
		return convertView;
	}

}
