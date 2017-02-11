package com.m520it.jdmall.pop;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.m520it.jdmall.R;
import com.m520it.jdmall.bean.RBuildedOrder;
import com.m520it.jdmall.listener.IBuildOrderListener;

public class BuidOrderDailog extends AlertDialog implements OnClickListener {

	private TextView mOrderNoTv;
	private TextView mTotalPriceTv;
	private TextView mFreightTv;
	private TextView mActualPriceTv;
	private IBuildOrderListener mListener;
	private RBuildedOrder mBean;
	
	public void setListener(IBuildOrderListener mListener) {
		this.mListener = mListener;
	}

	public BuidOrderDailog(Context context, int theme,RBuildedOrder bean) {
		super(context, theme);
		mBean=bean;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.build_order_pop_view);
		mOrderNoTv=(TextView)findViewById(R.id.order_no_tv);
		mTotalPriceTv=(TextView)findViewById(R.id.total_price_tv);
		mFreightTv=(TextView)findViewById(R.id.freight_tv);
		mActualPriceTv=(TextView)findViewById(R.id.actual_price_tv);
		findViewById(R.id.cancal_btn).setOnClickListener(this);
		findViewById(R.id.sure_btn).setOnClickListener(this);
		setBean(mBean);
	}

	public void setBean(RBuildedOrder bean) {
		mOrderNoTv.setText(" 订单编号: "+bean.getOrderNum());
		mTotalPriceTv.setText(" 总价:  ￥ "+bean.getAllPrice());
		mFreightTv.setText(" 运费: ￥ "+bean.getFreight());
		mActualPriceTv.setText(" 实付: ￥ "+bean.getTotalPrice());
	}

	@Override
	public void onClick(View v) {
		if (mListener!=null) {
			if (v.getId()==R.id.cancal_btn) {
				mListener.onCancel();
			}else if (v.getId()==R.id.sure_btn) {
				mListener.onSure(mBean);
			}
		}
	}
	
	
}
