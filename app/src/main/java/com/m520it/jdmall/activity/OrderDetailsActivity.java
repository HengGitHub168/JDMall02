package com.m520it.jdmall.activity;

import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall.R;
import com.m520it.jdmall.adapter.OrderDetailsProductsAdapter;
import com.m520it.jdmall.bean.OrderDetailsBean;
import com.m520it.jdmall.bean.OrderProducts;
import com.m520it.jdmall.bean.RReceiveAddress;
import com.m520it.jdmall.bean.RResult;
import com.m520it.jdmall.cons.IDiyMessage;
import com.m520it.jdmall.cons.IntentValues;
import com.m520it.jdmall.contrller.OrderController;
import com.m520it.jdmall.listener.IModelChangeListener;
import com.m520it.jdmall.util.FixedViewUtil;

public class OrderDetailsActivity extends BaseActivity implements
		IModelChangeListener, OnClickListener {

	private ListView mProductsLv;
	private OrderDetailsProductsAdapter mAdapter;
	private OrderController mController;
	private TextView mOrderNOTv;
	private TextView mReceiveNameTv;
	private TextView mReceivePhoneTv;
	private TextView mReceiveAddressTv;
	private TextView mTotalPriceTv;
	private TextView mTakePriceTv;
	private TextView mActualPriceTv;
	private Button mCancleOrderTv;
	private TextView mDoBtnTv;
	private TextView mStatusLv;
	private String mOrderId;
	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case IDiyMessage.ACTION_GET_ORDER_DETAILS_RESULT:
					handleLoadOrderDetails((OrderDetailsBean) msg.obj);
					break;
				case IDiyMessage.ACTION_CANCEL_ORDER_RESULT:
					handleCancelOrder((RResult)msg.obj);
					break;
				case IDiyMessage.ACTION_SURE_ORDER_RESULT:
					handleConfirmOrder((RResult)msg.obj);
					break;
			}
		}
	};
	
	private void handleLoadOrderDetails(OrderDetailsBean bean) {
		if (mLodingDailog.isShowing()) {
			mLodingDailog.dismiss();
		}
		if (bean != null) {
			mOrderNOTv.setText("订单编号:" + bean.getOrderNum());
			RReceiveAddress address = JSON.parseObject(bean.getAddress(),
					RReceiveAddress.class);
			mReceiveNameTv.setText(address.getReceiverName());
			mReceivePhoneTv.setText(address.getReceiverPhone());
			mReceiveAddressTv.setText(address.getReceiverAddress());
			List<OrderProducts> items = JSON.parseArray(bean.getItems(),
					OrderProducts.class);
			mAdapter.setDatas(items);
			mAdapter.notifyDataSetChanged();
			FixedViewUtil.setListViewHeightBasedOnChildren(mProductsLv);
			mTotalPriceTv.setText("￥" + (bean.getTotalPrice()-bean.getFreight()));
			mTakePriceTv.setText("￥" + bean.getFreight());
			mActualPriceTv.setText("￥" + bean.getTotalPrice());
			handleStatus(bean.getStatus());
		} else {
			Toast.makeText(this, "订单参数出错 ！", 800).show();
			finish();
		}
	}

	protected void handleCancelOrder(RResult result) {
		if (result.isSuccess()) {
			Toast.makeText(this, "取消订单成功", 800).show();
			mController.sendAsyncMessage(IDiyMessage.ACTION_GET_ORDER_DETAILS,
					mOrderId);
		}else {
			Toast.makeText(this,result.getErrorMsg(), 800).show();
		}
	}

	protected void handleConfirmOrder(RResult result) {
		if (result.isSuccess()) {
			mLodingDailog.show();
			mController.sendAsyncMessage(IDiyMessage.ACTION_GET_ORDER_DETAILS,
					mOrderId);
		}else {
			Toast.makeText(this, "确认订单失败!", 800).show();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_details);

		mOrderId = getIntent()
				.getStringExtra(IntentValues.TO_ORDER_DETAILS_KEY);
		mController = new OrderController(this);
		mController.setIModelChangeListener(this);

		mOrderNOTv = (TextView) findViewById(R.id.order_no_tv);
		mReceiveNameTv = (TextView) findViewById(R.id.receive_name_tv);
		mReceivePhoneTv = (TextView) findViewById(R.id.receive_phone_tv);
		mReceiveAddressTv = (TextView) findViewById(R.id.receive_address_tv);
		mTotalPriceTv = (TextView) findViewById(R.id.total_price_val_tv);
		mTakePriceTv = (TextView) findViewById(R.id.take_price_val_tv);
		mActualPriceTv = (TextView) findViewById(R.id.actual_price_val_tv);
		mCancleOrderTv = (Button) findViewById(R.id.cancle_order_tv);
		mCancleOrderTv.setOnClickListener(this);
		mDoBtnTv = (TextView) findViewById(R.id.to_pay_tv);
		mDoBtnTv.setOnClickListener(this);
		mStatusLv = (TextView) findViewById(R.id.status_tv);
		mProductsLv = (ListView) findViewById(R.id.products_lv);
		mAdapter = new OrderDetailsProductsAdapter(this);
		mProductsLv.setAdapter(mAdapter);

		mLodingDailog.show();
		mController.sendAsyncMessage(IDiyMessage.ACTION_GET_ORDER_DETAILS,
				mOrderId);

	}

	private void handleStatus(int status) {
		switch (status) {
		case -1:
			mStatusLv.setText("取消订单");
			mDoBtnTv.setVisibility(View.GONE);
			mCancleOrderTv.setVisibility(View.GONE);
			break;
		case 0:
			mStatusLv.setText("待支付");
			mDoBtnTv.setText("去支付");
			mDoBtnTv.setVisibility(View.VISIBLE);
			mCancleOrderTv.setVisibility(View.VISIBLE);
			break;
		case 1:
			mStatusLv.setText("待发货");
			mDoBtnTv.setVisibility(View.GONE);
			mCancleOrderTv.setVisibility(View.GONE);
			break;
		case 2:
			mStatusLv.setText("待收货 ");
			mDoBtnTv.setText("确认收货");
			mDoBtnTv.setVisibility(View.VISIBLE);
			mCancleOrderTv.setVisibility(View.GONE);
			break;
		case 3:
			mStatusLv.setText("完成交易 ");
			mDoBtnTv.setVisibility(View.GONE);
			mCancleOrderTv.setVisibility(View.GONE);
			break;
		}
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action, values[0]).sendToTarget();
	}

	@Override
	public void onClick(View v) {
		if (v.getId()==R.id.cancle_order_tv) {
			mController.sendAsyncMessage(IDiyMessage.ACTION_CANCEL_ORDER, mOrderId);
		}else if (v.getId()==R.id.to_pay_tv) {
			handleDoBtn((TextView)v);
		}
	}

	private void handleDoBtn(TextView btn) {
		if (btn.getText().toString().equals("去支付")) {
			//TODO  进入支付宝界面进行支付
		}else if (btn.getText().toString().equals("确认收货")) {
			mController.sendAsyncMessage(IDiyMessage.ACTION_SURE_ORDER, mOrderId);
		}
	}

}
