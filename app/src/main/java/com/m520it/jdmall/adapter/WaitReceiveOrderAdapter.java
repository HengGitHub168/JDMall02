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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.image.SmartImageView;
import com.m520it.jdmall.R;
import com.m520it.jdmall.bean.ROrderListBean;
import com.m520it.jdmall.cons.NetworkConst;
import com.m520it.jdmall.listener.IOrderListClickListener;

public class WaitReceiveOrderAdapter extends BaseAdapter {

	private List<ROrderListBean> mDatas = new ArrayList<ROrderListBean>();
	private Context mContext;
	private IOrderListClickListener mListener;

	public void setListener(IOrderListClickListener mListener) {
		this.mListener = mListener;
	}

	public WaitReceiveOrderAdapter(Context c) {
		mContext = c;
	}

	public void setDatas(List<ROrderListBean> datas) {
		this.mDatas = datas;
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position-1);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	class ViewHolder {
		public TextView orderNOTv;
		public TextView stateTv;
		public LinearLayout mIconContainer;
		public TextView priceTv;
		public Button doBtn;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holde = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.all_order_item, null);
			holde = new ViewHolder();
			holde.orderNOTv = (TextView) convertView
					.findViewById(R.id.order_no_tv);
			holde.stateTv = (TextView) convertView
					.findViewById(R.id.order_state_tv);
			holde.mIconContainer = (LinearLayout) convertView
					.findViewById(R.id.p_container_ll);
			holde.priceTv = (TextView) convertView.findViewById(R.id.price_tv);
			holde.doBtn = (Button) convertView.findViewById(R.id.do_btn);
			convertView.setTag(holde);
		} else {
			holde = (ViewHolder) convertView.getTag();
		}
		final ROrderListBean bean = mDatas.get(position);
		holde.orderNOTv.setText("订单编号:"+bean.getOrderNum());
		holde.priceTv.setText("￥" + bean.getTotalPrice());
		calcState(holde, bean);
		initChildProductIcon(bean,holde.mIconContainer);
		holde.doBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mListener!=null) {
					mListener.onOrderClick(bean.getOid()+"", v);
				}
			}
		});
		return convertView;
	}

	private void initChildProductIcon(ROrderListBean bean, LinearLayout mIconContainer) {
		List<String> icons = JSON.parseArray(bean.getItems(),String.class);
		for (int i = 0; i < mIconContainer.getChildCount(); i++) {
			mIconContainer.getChildAt(i).setVisibility(View.INVISIBLE);
		}
		for (int i = 0; i < icons.size(); i++) {
			SmartImageView iv = (SmartImageView) mIconContainer.getChildAt(i);
			iv.setImageUrl(NetworkConst.DOMAIN+icons.get(i));
			iv.setVisibility(View.VISIBLE);
		}
	}

	private void calcState(ViewHolder holde, ROrderListBean bean) {
		switch (bean.getStatus()) {
			case -1:
				holde.stateTv.setText("取消订单 ");
				holde.doBtn.setVisibility(View.GONE);
				break;
			case 0:
				holde.stateTv.setText("待支付");
				holde.doBtn.setText("去 付 款");
				holde.doBtn.setVisibility(View.VISIBLE);
				break;
			case 1:
				holde.stateTv.setText("待发货");
				holde.doBtn.setVisibility(View.GONE);
				break;
			case 2:
				holde.stateTv.setText("待收货 ");
				holde.doBtn.setText("确认收货");
				holde.doBtn.setVisibility(View.VISIBLE);
				break;
			case 3:
				holde.stateTv.setText("交易完成 ");
				holde.doBtn.setVisibility(View.GONE);
				break;
		}
	}
}
