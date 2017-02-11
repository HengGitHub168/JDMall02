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
import android.widget.CheckBox;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.m520it.jdmall.R;
import com.m520it.jdmall.bean.RShopCarList;
import com.m520it.jdmall.cons.NetworkConst;
import com.m520it.jdmall.listener.IShopcarDeleteListener;

public class ShopcarAdapter extends BaseAdapter {
	
	private List<RShopCarList> mBeans=new ArrayList<RShopCarList>();
	public List<Boolean> sCheckedPosition=new ArrayList<Boolean>();
	private Context mContext;
	private IShopcarDeleteListener mListener;
	
	public void setListener(IShopcarDeleteListener mListener) {
		this.mListener = mListener;
	}

	public ShopcarAdapter(Context c) {
		mContext=c;
	}
	
	public void setBeans(List<RShopCarList> mBeans) {
		this.mBeans = mBeans;
		sCheckedPosition=new ArrayList<Boolean>();
		for (int i = 0; i < mBeans.size(); i++) {
			sCheckedPosition.add(false);
		}
	}
	
	public void setCheckedPosition(int position,boolean checked) {
		sCheckedPosition.set(position, checked);
	}
	
	public boolean getCheckedPosition(int position) {
		return sCheckedPosition.get(position);
	}
	
	public String getTotalPrice(){
		double total=0d;
		for (int i = 0; i < mBeans.size(); i++) {
			if (sCheckedPosition.get(i)) {
				total+=mBeans.get(i).getPprice()*mBeans.get(i).getBuyCount();
			}
		}
		return String.valueOf(total);
	}
	
	public int getCheckedCount(){
		int total=0;
		for (int i = 0; i < mBeans.size(); i++) {
			if (sCheckedPosition.get(i)) {
				total++;
			}
		}
		return total;
	}
	

	public void setAllChecked(boolean isChecked) {
		for (int i = 0; i < sCheckedPosition.size(); i++) {
			sCheckedPosition.set(i, isChecked);
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mBeans.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}
	
	class ViewHolder{
		public CheckBox mCheckBox;
		public SmartImageView mProductIv;
		public TextView mProductNameTv;
		public TextView mVersionTv;
		public TextView mPriceTv;
		public TextView mBuyCountTv;
		public Button mDeleteButton;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		ViewHolder holder=null;
		if (convertView==null) {
			convertView=LayoutInflater.from(mContext).inflate(R.layout.shopcar_lv_item, null);
			holder=new ViewHolder();
			holder.mCheckBox=(CheckBox) convertView.findViewById(R.id.cbx);
			holder.mProductIv=(SmartImageView) convertView.findViewById(R.id.product_iv);
			holder.mProductNameTv=(TextView) convertView.findViewById(R.id.name_tv);
			holder.mVersionTv=(TextView) convertView.findViewById(R.id.version_tv);
			holder.mPriceTv=(TextView) convertView.findViewById(R.id.price_tv);
			holder.mBuyCountTv=(TextView) convertView.findViewById(R.id.buyCount_tv);
			holder.mDeleteButton=(Button) convertView.findViewById(R.id.delete_product);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		final RShopCarList bean = mBeans.get(arg0);
		holder.mCheckBox.setChecked(sCheckedPosition.get(arg0));
		holder.mProductIv.setImageUrl(NetworkConst.DOMAIN+bean.getPimageUrl());
		holder.mProductNameTv.setText(bean.getPname());
		holder.mVersionTv.setText(bean.getPversion());
		holder.mPriceTv.setText("￥"+bean.getPprice());
		holder.mBuyCountTv.setText("  X"+bean.getBuyCount());
		holder.mDeleteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mListener!=null) {
					mListener.onDelete(bean.getId());
				}
			}
		});
		return convertView;
	}

	public List<RShopCarList> getSelectedProduct() {
		List<RShopCarList> result=new ArrayList<RShopCarList>();
		for (int i = 0; i < mBeans.size(); i++) {
			if (sCheckedPosition.get(i)) {
				result.add(mBeans.get(i));
			}
		}
		return result;
	}


}
