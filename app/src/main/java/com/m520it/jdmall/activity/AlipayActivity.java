package com.m520it.jdmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.m520it.jdmall.R;
import com.m520it.jdmall.bean.AlipayInfo;
import com.m520it.jdmall.cons.IDiyMessage;
import com.m520it.jdmall.cons.IntentValues;
import com.m520it.jdmall.contrller.AlipayController;
import com.m520it.jdmall.listener.IAlipayClickListener;
import com.m520it.jdmall.listener.IModelChangeListener;
import com.m520it.jdmall.pop.AlipayPopWindow;
import com.m520it.jdmall.pop.LoadingDailog;

public class AlipayActivity extends BaseActivity implements IModelChangeListener,IAlipayClickListener {
	
	private AlipayPopWindow mAlipayPopWindow;
	private TextView mPayPriceTv;
	private TextView mOrderDescTv;
	private TextView mDealTimeTv;
	private TextView mDealNOTv;
	private String mOrderTn;
	private AlipayController mController;
	private Handler mHandler=new Handler(){
		
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case IDiyMessage.ACTION_GET_ALIPAY_INFO_RESULT:
					handleLoadAlipayInfo((AlipayInfo)msg.obj);
					break;
				case IDiyMessage.ACTION_START_ALIPAY_RESULT:
					handlStartAlipay(msg.obj);
					break;
			}
			
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alipay);
		
		mOrderTn=getIntent().getStringExtra(IntentValues.TO_ALIPAY_KEY);
		mLodingDailog=new LoadingDailog(this, R.style.CustomDialog);
		mController=new AlipayController(this);
		mController.setIModelChangeListener(this);
		
		mPayPriceTv=(TextView)findViewById(R.id.pay_price_tv);
		mOrderDescTv=(TextView)findViewById(R.id.order_desc_val_tv);
		mDealTimeTv=(TextView)findViewById(R.id.deal_time_val_tv);
		mDealNOTv=(TextView)findViewById(R.id.deal_no_val_tv);
		
		mController.sendAsyncMessage(IDiyMessage.ACTION_GET_ALIPAY_INFO, mOrderTn);
		
	}
	
	protected void handlStartAlipay(Object obj) {
		if (obj!=null) {
			String oid=(String) obj;
			Intent intent=new Intent(this,OrderDetailsActivity.class);
			intent.putExtra(IntentValues.TO_ORDER_DETAILS_KEY, oid);
			startActivity(intent);
			Toast.makeText(this, "恭喜您下单成功！", 800).show();
			finish();
		}else {
			Toast.makeText(this, "支付失败 请在订单列表中继续支付！", 800).show();
			finish();
		}
	}

	private void handleLoadAlipayInfo(AlipayInfo info) {
		if (info!=null) {
			mPayPriceTv.setText("￥"+info.getTotalPrice());
			mOrderDescTv.setText(info.getPname());
			mDealNOTv.setText(info.getTn());
		}else {
			Toast.makeText(this, "支付信息出错啦", 800).show();
			finish();
		}
	}

	public void payClick(View view){
		mAlipayPopWindow = new AlipayPopWindow(this);
		mAlipayPopWindow.setListener(this);
		View containerView=findViewById(R.id.container);
		mAlipayPopWindow.onShow(containerView);
	}
	
	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action,values[0]).sendToTarget();
	}

	@Override
	public void startPay(String account, String pwd, String ppwd) {
		mController.sendAsyncMessage(IDiyMessage.ACTION_START_ALIPAY, account,pwd,ppwd,mOrderTn);
	}
	
}

