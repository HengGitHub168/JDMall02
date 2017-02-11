package com.m520it.jdmall.frag;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.m520it.jdmall.R;
import com.m520it.jdmall.activity.AlipayActivity;
import com.m520it.jdmall.activity.OrderDetailsActivity;
import com.m520it.jdmall.adapter.AllOrderAdapter;
import com.m520it.jdmall.bean.ROrderListBean;
import com.m520it.jdmall.bean.RResult;
import com.m520it.jdmall.cons.IDiyMessage;
import com.m520it.jdmall.cons.IntentValues;
import com.m520it.jdmall.contrller.OrderController;
import com.m520it.jdmall.listener.IModelChangeListener;
import com.m520it.jdmall.listener.IOrderListClickListener;
import com.m520it.jdmall.pop.LoadingDailog;
import com.m520it.jdmall.ui.xlv.XListView;

public class AllOrderView extends OrderBaseFragment 
		implements IModelChangeListener,IOrderListClickListener, OnItemClickListener {
	
	private AllOrderAdapter mAdapter;
	private Handler mHandler=new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case IDiyMessage.ACTION_GET_ALL_ORDER_RESULT:
					handleLoadAllOrders((List<ROrderListBean>)msg.obj);
					break;
				case IDiyMessage.ACTION_SURE_ORDER_RESULT:
					handleConfirmOrder((RResult)msg.obj);
					break;
			}
		}
		
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.frag_all_order, null);
	}
	
	protected void handleConfirmOrder(RResult result) {
		if (result.isSuccess()) {
			mController.sendAsyncMessage(IDiyMessage.ACTION_GET_ALL_ORDER, 0);
		}else {
			Toast.makeText(getActivity(), "确认订单失败!", 800).show();
		}
	}

	protected void handleLoadAllOrders(List<ROrderListBean> datas) {
		refreshTime();
		mAdapter.setDatas(datas);
		mAdapter.notifyDataSetChanged();
		mNullView.setVisibility(datas.size()!=0?View.GONE:View.VISIBLE);
		mOrderLv.setVisibility(datas.size()!=0?View.VISIBLE:View.GONE);
		if (mLoadingDailog.isShowing()) {
			mLoadingDailog.dismiss();
		}
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		mController=new OrderController(getActivity());
		mController.setIModelChangeListener(this);
		mLoadingDailog=new LoadingDailog(getActivity(),R.style.CustomDialog);
		mNullView=getActivity().findViewById(R.id.all_order_null_view);
		mOrderLv=(XListView) getActivity().findViewById(R.id.all_order_lv);
		mOrderLv.setPullRefreshEnable(true);
		mOrderLv.setPullLoadEnable(false);
		mOrderLv.setXListViewListener(this);
		refreshTime();
		mAdapter=new AllOrderAdapter(getActivity());
		mAdapter.setListClickListener(this);
		mOrderLv.setAdapter(mAdapter);
		mOrderLv.setOnItemClickListener(this);
		mLoadingDailog.show();
		mController.sendAsyncMessage(IDiyMessage.ACTION_GET_ALL_ORDER, 0);
	}

	@Override
	public void onShow() {
		mLoadingDailog.show();
		mController.sendAsyncMessage(IDiyMessage.ACTION_GET_ALL_ORDER, 0);
	}
	
	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action,values[0]).sendToTarget();
	}

	@Override
	public void onOrderClick(String msg,View tv) {
		String btnName = ((TextView)tv).getText().toString();
		if (btnName.equals("去 付 款")) {
			Intent intent=new Intent(getActivity(),AlipayActivity.class);
			intent.putExtra(IntentValues.TO_ALIPAY_KEY, msg);
			startActivity(intent);
		}else if (btnName.equals("确认收货")) {
			mLoadingDailog.show();
			mController.sendAsyncMessage(IDiyMessage.ACTION_SURE_ORDER, msg);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ROrderListBean bean=(ROrderListBean) mAdapter.getItem(position);
		Intent intent=new Intent(getActivity(),OrderDetailsActivity.class);
		intent.putExtra(IntentValues.TO_ORDER_DETAILS_KEY, bean.getOid()+"");
		startActivity(intent);
	}

	@Override
	public void onRefresh() {
		mController.sendAsyncMessage(IDiyMessage.ACTION_GET_ALL_ORDER, 0);
	}

	@Override
	public void onLoadMore() {
		
	}
	
}
