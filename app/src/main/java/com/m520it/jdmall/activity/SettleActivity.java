package com.m520it.jdmall.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.image.SmartImageView;
import com.m520it.jdmall.R;
import com.m520it.jdmall.bean.RBuildedOrder;
import com.m520it.jdmall.bean.RReceiveAddress;
import com.m520it.jdmall.bean.RShopCarList;
import com.m520it.jdmall.bean.SMakeSureOrder;
import com.m520it.jdmall.bean.SMakeSureOrderProduct;
import com.m520it.jdmall.cons.IDiyMessage;
import com.m520it.jdmall.cons.IntentValues;
import com.m520it.jdmall.cons.NetworkConst;
import com.m520it.jdmall.contrller.ShopcarController;
import com.m520it.jdmall.frag.ShopCarFragment;
import com.m520it.jdmall.listener.IBuildOrderListener;
import com.m520it.jdmall.listener.IModelChangeListener;
import com.m520it.jdmall.pop.BuidOrderDailog;

public class SettleActivity extends BaseActivity 
			implements IModelChangeListener, OnClickListener, IBuildOrderListener {
	
	private TextView mReceiverNameTv;
	private TextView mReceiverPhoneTv;
	private TextView mReceiverAddressTv;
	private LinearLayout mProductIconContainer;
	private TextView mAllPriceValTv;
	private TextView mPayOnlineTv;
	private TextView mPayWhenGetTv;
	private TextView mPayMoneyTv;
	private RelativeLayout mHasReceiverRl;
	private RelativeLayout mNoReceiverRl;
	private TextView mTotalPsizeTv;
	private String mTotalPrice;
	public static final int CHOOSE_ADDRESS_REQ=0x001;
	public static final int CHOOSE_ADDRESS_SUCCESS=0x002;
	public static final int ADD_ADDRESS_REQ=0x003;
	public static final int ADD_ADDRESS_SUCCESS=0x004;
	private RReceiveAddress mChooseAddress;
	private ArrayList<RShopCarList> mProducts;
	private ShopcarController mController;
	private int mPayType=-1;
	private BuidOrderDailog mBuidOrderDailog;
	
	private Handler mHandler=new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case IDiyMessage.ACTION_GET_DEFAULT_ADDRESS_RESULT:
					handleLoadDefaultAddress((List<RReceiveAddress>)msg.obj);
					break;
				case IDiyMessage.ACTION_MAKESURE_ORDER_RESULT:
					showOrderInfoPopWindow((RBuildedOrder)msg.obj);
					break;
			}
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settle);
		initController();
		
		//收货地址
		mReceiverNameTv=(TextView) findViewById(R.id.name_tv);
		mReceiverPhoneTv=(TextView) findViewById(R.id.phone_tv);
		mReceiverAddressTv=(TextView) findViewById(R.id.address_tv);
		mHasReceiverRl=(RelativeLayout) findViewById(R.id.has_receiver_rl);
		mNoReceiverRl=(RelativeLayout) findViewById(R.id.no_receiver_rl);
		//商品列表
		mProductIconContainer=(LinearLayout) findViewById(R.id.product_container_ll);
		mTotalPsizeTv=(TextView) findViewById(R.id.total_psize_tv);
		//商品价格
		mAllPriceValTv=(TextView) findViewById(R.id.all_price_val_tv);
		//支付方式两个按钮
		mPayOnlineTv=(TextView) findViewById(R.id.pay_online_tv);
		mPayWhenGetTv=(TextView) findViewById(R.id.pay_whenget_tv);
		mPayOnlineTv.setOnClickListener(this);
		mPayWhenGetTv.setOnClickListener(this);
		//底部实付款金额
		mPayMoneyTv=(TextView) findViewById(R.id.pay_money_tv);
		
		if (getIntent()!=null) {
			mProducts=(ArrayList<RShopCarList>)getIntent()
					.getSerializableExtra(IntentValues.TO_SETTLE_PRODUCTS);
			showProductList(mProducts);
			mTotalPrice = getIntent().getStringExtra(IntentValues.TO_SETTLE_TOTAL_PRICE);
			mAllPriceValTv.setText("￥"+mTotalPrice);
			mPayMoneyTv.setText("实付款:￥"+mTotalPrice);
		}
		mLodingDailog.show();
		mController.sendAsyncMessage(IDiyMessage.ACTION_GET_DEFAULT_ADDRESS, 0);
		
	}

	private void initController() {
		mController=new ShopcarController(this);
		mController.setIModelChangeListener(this);
	}
	
	//显示购买的商品列表
	private void showProductList(ArrayList<RShopCarList> products) {
		int size=products.size()<mProductIconContainer.getChildCount()?
						products.size():mProductIconContainer.getChildCount();
		for (int i = 0; i < size; i++) {
			LinearLayout itemContainer = (LinearLayout) mProductIconContainer.getChildAt(i);
			SmartImageView piv=(SmartImageView)itemContainer.findViewById(R.id.piv);
			piv.setImageUrl(NetworkConst.DOMAIN+products.get(i).getPimageUrl());
			TextView psize=(TextView)itemContainer.findViewById(R.id.psize);
			psize.setText("x "+products.get(i).getBuyCount());
		}
		mTotalPsizeTv.setText("共"+size+"件");
	}
	
	protected void showOrderInfoPopWindow(RBuildedOrder buildedOrder) {
		if (buildedOrder!=null) {
			mBuidOrderDailog = new BuidOrderDailog(this, R.style.CustomDialog,buildedOrder);
			mBuidOrderDailog.setListener(this);
			mBuidOrderDailog.show();
		}
	}

	private void handleLoadDefaultAddress(List<RReceiveAddress> datas) {
		if (datas.size()>0) {
			mChooseAddress = datas.get(0);
			mReceiverNameTv.setText(mChooseAddress.getReceiverName());
			mReceiverPhoneTv.setText("*****"+mChooseAddress.getReceiverPhone().substring(5));
			mReceiverAddressTv.setText(mChooseAddress.getReceiverAddress());
		}
		mHasReceiverRl.setVisibility(datas.size()>0?View.VISIBLE:View.GONE);
		mNoReceiverRl.setVisibility(datas.size()>0?View.GONE:View.VISIBLE);
		if (mLodingDailog.isShowing()) {
			mLodingDailog.dismiss();
		}
	}

	public void chooseAddress(View view){
		Intent intent=new Intent(this,ChooseAddressActivity.class);
		startActivityForResult(intent, CHOOSE_ADDRESS_REQ);
	}
	
	public void addAddress(View view){
		Intent intent=new Intent(this,AddAddressActivity.class);
		startActivityForResult(intent, ADD_ADDRESS_REQ);
	}
	
	public void submitClick(View view){
		if (mChooseAddress==null) {
			Toast.makeText(this, "请设置收货人信息", 800).show();
			return;
		}else if (mProducts==null||mProducts.size()==0) {
			Toast.makeText(this, "商品信息出错 请重新下单", 800).show();
			return;
		}else if (mPayType!=0&&mPayType!=1) {
			Toast.makeText(this, "请选择支付方式", 800).show();
			return;
		}
		mController.sendAsyncMessage(IDiyMessage.ACTION_MAKESURE_ORDER, getSOrderParams());
	}

	private SMakeSureOrder getSOrderParams() {
		String addressId=mChooseAddress.getId();
		ArrayList<SMakeSureOrderProduct> products=new ArrayList<SMakeSureOrderProduct>();
		for (int i = 0; i < mProducts.size(); i++) {
			RShopCarList itemBean = mProducts.get(i);
			products.add(new SMakeSureOrderProduct(itemBean.getBuyCount(), itemBean.getPversion(), itemBean.getPid()));
		}
		int payWay=mPayOnlineTv.isSelected()?0:1;
		SMakeSureOrder order=new SMakeSureOrder(products, payWay, addressId);
		return order;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode==CHOOSE_ADDRESS_REQ&&resultCode==CHOOSE_ADDRESS_SUCCESS) {
			mChooseAddress=(RReceiveAddress) data.getSerializableExtra(IntentValues.TO_CHOOSE_ADDRESS_RETURN_KEY);
		}else if (requestCode==ADD_ADDRESS_REQ&&resultCode==ADD_ADDRESS_SUCCESS) {
			mChooseAddress=(RReceiveAddress) data.getSerializableExtra(IntentValues.RETURN_ADD_ADDRESS_KEY);
		}
		if (mChooseAddress!=null) {
			mReceiverNameTv.setText(mChooseAddress.getReceiverName());
			mReceiverPhoneTv.setText(mChooseAddress.getReceiverPhone());
			mReceiverAddressTv.setText(mChooseAddress.getReceiverAddress());
			mHasReceiverRl.setVisibility(View.VISIBLE);
			mNoReceiverRl.setVisibility(View.GONE);
		}
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action,values[0]).sendToTarget();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.pay_online_tv:
				mPayType=0;
				mPayOnlineTv.setSelected(true);
				mPayWhenGetTv.setSelected(false);
				break;
			case R.id.pay_whenget_tv:
				mPayType=1;
				mPayOnlineTv.setSelected(false);
				mPayWhenGetTv.setSelected(true);
				break;
		}
	}

	@Override
	public void onSure(RBuildedOrder order) {
		if (mBuidOrderDailog!=null&&mBuidOrderDailog.isShowing()) {
			mBuidOrderDailog.dismiss();
		}
		if (order.getPayWay()==0) {
			Intent intent=new Intent(this,AlipayActivity.class);
			intent.putExtra(IntentValues.TO_ALIPAY_KEY, order.getTn());
			startActivity(intent);
			finish();
		}else if (order.getPayWay()==1) {
			Intent intent=new Intent(this,OrderDetailsActivity.class);
			intent.putExtra(IntentValues.TO_ORDER_DETAILS_KEY, order.getOid());
			startActivity(intent);
			Toast.makeText(this, "恭喜您下单成功！", 800).show();
			finish();
		}
	}

	@Override
	public void onCancel() {
		Toast.makeText(this, "请到订单列表查看未支付订单", 800).show();
		if (mBuidOrderDailog!=null&&mBuidOrderDailog.isShowing()) {
			mBuidOrderDailog.dismiss();
		}
		setResult(ShopCarFragment.TO_SETTLE_KEY_RES);
		finish();
	}
	
}
