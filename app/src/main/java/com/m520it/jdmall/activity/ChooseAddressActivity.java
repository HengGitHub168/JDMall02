package com.m520it.jdmall.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.m520it.jdmall.R;
import com.m520it.jdmall.adapter.ChooseAddressAdapter;
import com.m520it.jdmall.bean.RReceiveAddress;
import com.m520it.jdmall.bean.RResult;
import com.m520it.jdmall.cons.IDiyMessage;
import com.m520it.jdmall.cons.IntentValues;
import com.m520it.jdmall.contrller.ShopcarController;
import com.m520it.jdmall.listener.IAddressDeleteListener;
import com.m520it.jdmall.listener.IModelChangeListener;

public class ChooseAddressActivity extends BaseActivity 
		implements IModelChangeListener, OnItemClickListener,IAddressDeleteListener {
	
	private ListView mLv;
	private ChooseAddressAdapter mAdapter;
	private ShopcarController mController;
	private Handler mHandler=new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case IDiyMessage.ACTION_GET_ADDRESS_LIST_RESULT:
					handleReceiverAddressList((List<RReceiveAddress>)msg.obj);
					break;
				case IDiyMessage.ACTION_DELETE_ADDRESS_RESULT:
					handleDeleteAddress((RResult)msg.obj);
					break;
			}
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_activity);
		
		mController=new ShopcarController(this);
		mController.setIModelChangeListener(this);
		
		mLv=(ListView) findViewById(R.id.lv);
		mAdapter=new ChooseAddressAdapter(this);
		mAdapter.setDeleteListener(this);
		mLv.setAdapter(mAdapter);
		mLv.setOnItemClickListener(this);
		mLodingDailog.show();
		mController.sendAsyncMessage(IDiyMessage.ACTION_GET_ADDRESS_LIST, 0);
	}

	protected void handleDeleteAddress(RResult result) {
		if (result.isSuccess()) {
			mController.sendAsyncMessage(IDiyMessage.ACTION_GET_ADDRESS_LIST, 0);
		}else {
			Toast.makeText(this, "删除地址失败 请重新再试", 800).show();
		}
	}

	protected void handleReceiverAddressList(List<RReceiveAddress> datas) {
		mAdapter.setBeans(datas);
		mAdapter.notifyDataSetChanged();
		if (mLodingDailog.isShowing()) {
			mLodingDailog.dismiss();
		}
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action,values[0]).sendToTarget();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		RReceiveAddress item = (RReceiveAddress) mAdapter.getItem(position);
		Intent intent=new Intent();
		intent.putExtra(IntentValues.TO_CHOOSE_ADDRESS_RETURN_KEY, item);
		setResult(SettleActivity.CHOOSE_ADDRESS_SUCCESS,intent);
		finish();
	}

	@Override
	public void onDelete(String id) {
		mLodingDailog.show();
		mController.sendAsyncMessage(IDiyMessage.ACTION_DELETE_ADDRESS, id);
	}
	
}
