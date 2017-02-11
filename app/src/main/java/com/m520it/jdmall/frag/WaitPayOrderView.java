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

import com.m520it.jdmall.R;
import com.m520it.jdmall.activity.AlipayActivity;
import com.m520it.jdmall.activity.OrderDetailsActivity;
import com.m520it.jdmall.adapter.WaitPayOrderAdapter;
import com.m520it.jdmall.bean.ROrderListBean;
import com.m520it.jdmall.cons.IDiyMessage;
import com.m520it.jdmall.cons.IntentValues;
import com.m520it.jdmall.contrller.OrderController;
import com.m520it.jdmall.listener.IModelChangeListener;
import com.m520it.jdmall.listener.IOrderListClickListener;
import com.m520it.jdmall.pop.LoadingDailog;
import com.m520it.jdmall.ui.xlv.XListView;

public class WaitPayOrderView extends OrderBaseFragment 
		implements IModelChangeListener,IOrderListClickListener, OnItemClickListener {
	
	private WaitPayOrderAdapter mAdapter;
	private Handler mHandler=new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case IDiyMessage.ACTION_GET_WAITPAY_ORDER_RESULT:
					handleLoadWaitPayOrders((List<ROrderListBean>)msg.obj);
					break;
			
			}
		}
	};
	
	/**
	 *	处理页面显示结果
	 */
	private void handleLoadWaitPayOrders(List<ROrderListBean> datas) {
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.frag_wait_pay, null);
	}
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		initController();
		
		mLoadingDailog=new LoadingDailog(getActivity(),R.style.CustomDialog);
		mNullView=getActivity().findViewById(R.id.wait_pay_null_view);
		
		//初始化列表
		mOrderLv=(XListView) getActivity().findViewById(R.id.wait_pay_order_lv);
		mOrderLv.setPullRefreshEnable(true);
		mOrderLv.setPullLoadEnable(false);
		mOrderLv.setXListViewListener(this);
		refreshTime();
		mAdapter=new WaitPayOrderAdapter(getActivity());
		mAdapter.setListClickListener(this);
		mOrderLv.setAdapter(mAdapter);
		mOrderLv.setOnItemClickListener(this);

	}

	private void initController() {
		mController=new OrderController(getActivity());
		mController.setIModelChangeListener(this);
	}

	@Override
	public void onShow() {
		//发送网络请求
		mLoadingDailog.show();
		mController.sendAsyncMessage(IDiyMessage.ACTION_GET_WAITPAY_ORDER, 0);
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action,values[0]).sendToTarget();
	}

	//点击列表子项进入订单详情
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ROrderListBean bean=(ROrderListBean) mAdapter.getItem(position);
		Intent intent=new Intent(getActivity(),OrderDetailsActivity.class);
		intent.putExtra(IntentValues.TO_ORDER_DETAILS_KEY, bean.getOid()+"");
		startActivity(intent);
	}

	//下拉刷新界面
	@Override
	public void onRefresh() {
		mController.sendAsyncMessage(IDiyMessage.ACTION_GET_WAITPAY_ORDER, 0);
	}

	@Override
	public void onLoadMore() {
		
	}
	
	//点击去付款  启动支付宝界面去支付
	@Override
	public void onOrderClick(String tn, View view) {
		Intent intent=new Intent(getActivity(),AlipayActivity.class);
		intent.putExtra(IntentValues.TO_ALIPAY_KEY, tn);
		startActivity(intent);
	}
}
