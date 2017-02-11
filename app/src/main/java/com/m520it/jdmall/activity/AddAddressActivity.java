package com.m520it.jdmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.m520it.jdmall.R;
import com.m520it.jdmall.bean.ProvinceCityArea;
import com.m520it.jdmall.bean.RReceiveAddress;
import com.m520it.jdmall.bean.SReceiverCreate;
import com.m520it.jdmall.cons.IDiyMessage;
import com.m520it.jdmall.cons.IntentValues;
import com.m520it.jdmall.contrller.ShopcarController;
import com.m520it.jdmall.listener.IAddressPopListener;
import com.m520it.jdmall.listener.IModelChangeListener;
import com.m520it.jdmall.pop.AddressPopWindow;

public class AddAddressActivity extends BaseActivity implements IModelChangeListener,IAddressPopListener {
	
	private EditText mReceiverNameEt;
	private EditText mReceiverPhoneEt;
	private EditText mAddressDtailsEt;
	private AddressPopWindow mPopwin;
	private TextView mAddressTv;
	private CheckBox mDefaultCbx;
	private ShopcarController mController;
	private Handler mHandler=new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			if (msg.what==IDiyMessage.ACTION_ADD_ADDRESS_RESULT) {
				Toast.makeText(AddAddressActivity.this, "添加收货人地址成功", 800).show();
				Intent intent=new Intent();
				intent.putExtra(IntentValues.RETURN_ADD_ADDRESS_KEY, (RReceiveAddress) msg.obj);
				setResult(SettleActivity.ADD_ADDRESS_SUCCESS, intent);
				AddAddressActivity.this.finish();
			}
			if (mLodingDailog.isShowing()) {
				mLodingDailog.dismiss();
			}
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_address);
		
		mController=new ShopcarController(this);
		mController.setIModelChangeListener(this);
		
		mReceiverNameEt=(EditText) findViewById(R.id.name_et);
		mReceiverPhoneEt=(EditText) findViewById(R.id.phone_et);
		mAddressDtailsEt=(EditText) findViewById(R.id.address_details_et);
		mAddressTv=(TextView) findViewById(R.id.choose_province_tv);
		mDefaultCbx=(CheckBox) findViewById(R.id.default_cbx);
		mPopwin=new AddressPopWindow(this);
		mPopwin.setListener(this);
	}
	
	public void saveAddress(View view){
		String name=mReceiverNameEt.getText().toString();
		String phone=mReceiverPhoneEt.getText().toString();
		String details=mAddressDtailsEt.getText().toString();
		ProvinceCityArea province = mPopwin.mProvince;
		ProvinceCityArea city = mPopwin.mCity;
		ProvinceCityArea area = mPopwin.mArea;
		if (TextUtils.isEmpty(name)||TextUtils.isEmpty(phone)||
				TextUtils.isEmpty(details)||province==null||
				city==null||area==null) {
			Toast.makeText(this, "请填写完整的信息", 800).show();
			return;
		}
		mLodingDailog.show();
		SReceiverCreate bean=new SReceiverCreate(name, phone, province.getName(), province.getCode(),
				city.getName(), city.getCode(), area.getName(), area.getCode(), details, mDefaultCbx.isChecked());
		mController.sendAsyncMessage(IDiyMessage.ACTION_ADD_ADDRESS, bean);
		
	}
	
	public void reGetAddress(View view){
		mPopwin.onShow(findViewById(R.id.parent_view));
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action,values[0]).sendToTarget();
	}

	@Override
	public void onChooseFinish() {
		ProvinceCityArea mProvince = mPopwin.mProvince;
		ProvinceCityArea mCity = mPopwin.mCity;
		ProvinceCityArea mArea = mPopwin.mArea;
		if (mProvince!=null&&mCity!=null&&mArea!=null) {
			mAddressTv.setText(mProvince.getName()+mCity.getName()+mArea.getName());
		}
	}
	
}
