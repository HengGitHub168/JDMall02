package com.m520it.jdmall.frag;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.m520it.jdmall.R;
import com.m520it.jdmall.activity.SettleActivity;
import com.m520it.jdmall.adapter.ShopcarAdapter;
import com.m520it.jdmall.bean.RResult;
import com.m520it.jdmall.bean.RShopCarList;
import com.m520it.jdmall.cons.IDiyMessage;
import com.m520it.jdmall.cons.IntentValues;
import com.m520it.jdmall.contrller.ShopcarController;
import com.m520it.jdmall.listener.IModelChangeListener;
import com.m520it.jdmall.listener.IShopcarDeleteListener;

/**
 *	主页-购物车页
 */
public class ShopCarFragment extends BaseFragment implements IModelChangeListener, OnClickListener, 
					OnItemClickListener, OnCheckedChangeListener,IShopcarDeleteListener {
	
	private ListView mShopcarLv;
	private ShopcarAdapter mAdapter;
	private CheckBox mAllCbx;
	private TextView mAllMoneyTv;
	private TextView mSettleTv;
	private View mNullView;
	public static final int TO_SETTLE_KEY_REQ=2;
	public static final int TO_SETTLE_KEY_RES=4;
	private ShopcarController mController;
	private Handler mHandler=new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case IDiyMessage.ACTION_GET_SHOPCARS_RESULT:
					handleLoadShopcars((List<RShopCarList>)msg.obj);
					break;
				case IDiyMessage.ACTION_DELETE_SHOPCAR_PRODUCT_RESULT:
					handleDeleteShopcar((RResult)msg.obj);
					break;
			}
		}
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_shopcar, null, false);
	}
	
	protected void handleDeleteShopcar(RResult result) {
		if (result.isSuccess()) {
			mLoadingDailog.show();
			mController.sendAsyncMessage(IDiyMessage.ACTION_GET_SHOPCARS, 0);
		}
	}

	protected void handleLoadShopcars(List<RShopCarList> obj) {
		if (obj.size()==0) {
			mNullView.setVisibility(View.VISIBLE);
			mShopcarLv.setVisibility(View.GONE);
		}else {
			mAdapter.setBeans(obj);
			mAdapter.notifyDataSetChanged();
			mNullView.setVisibility(View.GONE);
			mShopcarLv.setVisibility(View.VISIBLE);
		}
		if (mLoadingDailog.isShowing()) {
			mLoadingDailog.dismiss();
		}
	}

	@Override
	public void onActivityCreated(Bundle arg0) {
		super.onActivityCreated(arg0);
		mController=new ShopcarController(getActivity());
		mController.setIModelChangeListener(this);
		
		mNullView=getActivity().findViewById(R.id.null_view);
		mShopcarLv=(ListView) getActivity().findViewById(R.id.shopcar_lv);
		mAdapter=new ShopcarAdapter(getActivity());
		mAdapter.setListener(this);
		mShopcarLv.setOnItemClickListener(this);
		mShopcarLv.setAdapter(mAdapter);
		mAllCbx=(CheckBox) getActivity().findViewById(R.id.all_cbx);
		mAllCbx.setOnCheckedChangeListener(this);
		mAllMoneyTv=(TextView) getActivity().findViewById(R.id.all_money_tv);
		mSettleTv=(TextView) getActivity().findViewById(R.id.settle_tv);
		mSettleTv.setOnClickListener(this);
		mLoadingDailog.show();
		mController.sendAsyncMessage(IDiyMessage.ACTION_GET_SHOPCARS, 0);
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action,values[0]).sendToTarget();
	}

	@Override
	public void onClick(View v) {
		if (mAdapter.getCheckedCount()==0) {
			Toast.makeText(getActivity(),"请选择商品", 800).show();
			return;
		}
		Intent intent=new Intent(getActivity(),SettleActivity.class);
		ArrayList<RShopCarList> datas = (ArrayList<RShopCarList>) mAdapter.getSelectedProduct();
		intent.putExtra(IntentValues.TO_SETTLE_PRODUCTS, datas);
		intent.putExtra(IntentValues.TO_SETTLE_TOTAL_PRICE, mAdapter.getTotalPrice());
		this.startActivityForResult(intent,TO_SETTLE_KEY_REQ);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		boolean checkedPosition = mAdapter.getCheckedPosition(position);
		mAdapter.setCheckedPosition(position, !checkedPosition);
		mAdapter.notifyDataSetChanged();
		mAllMoneyTv.setText("￥"+mAdapter.getTotalPrice());
		mSettleTv.setText("去结算("+mAdapter.getCheckedCount()+")");
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		mAdapter.setAllChecked(isChecked);
		mAllMoneyTv.setText("总额: ￥"+mAdapter.getTotalPrice());
		mSettleTv.setText("去结算("+mAdapter.getCheckedCount()+")");
	}

	@Override
	public void onDelete(String id) {
		mController.sendAsyncMessage(IDiyMessage.ACTION_DELETE_SHOPCAR_PRODUCT, id);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==TO_SETTLE_KEY_REQ&&resultCode==TO_SETTLE_KEY_RES) {
			mLoadingDailog.show();
			mController.sendAsyncMessage(IDiyMessage.ACTION_GET_SHOPCARS, 0);
		}
	}
	
}
