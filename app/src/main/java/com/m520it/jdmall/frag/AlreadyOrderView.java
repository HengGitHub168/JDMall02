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
import com.m520it.jdmall.activity.OrderDetailsActivity;
import com.m520it.jdmall.adapter.AlreadyOrderAdapter;
import com.m520it.jdmall.bean.ROrderListBean;
import com.m520it.jdmall.cons.IDiyMessage;
import com.m520it.jdmall.cons.IntentValues;
import com.m520it.jdmall.contrller.OrderController;
import com.m520it.jdmall.listener.IModelChangeListener;
import com.m520it.jdmall.pop.LoadingDailog;
import com.m520it.jdmall.ui.xlv.XListView;

public class AlreadyOrderView extends OrderBaseFragment 
			implements IModelChangeListener, OnItemClickListener {
	
	private AlreadyOrderAdapter mAdapter;
	private Handler mHandler=new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case IDiyMessage.ACTION_GET_WAITSURE_ORDER_RESULT:
					handleLoadWaitSureOrders((List<ROrderListBean>)msg.obj);
					break;
			}
		}
		
	};
	
	private void handleLoadWaitSureOrders(List<ROrderListBean> datas) {
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
		return inflater.inflate(R.layout.frag_wait_sure, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mController=new OrderController(getActivity());
		mController.setIModelChangeListener(this);
		mLoadingDailog=new LoadingDailog(getActivity(),R.style.CustomDialog);
		mNullView=getActivity().findViewById(R.id.wait_sure_null_view);
		mOrderLv=(XListView) getActivity().findViewById(R.id.wait_sure_lv);
		mOrderLv.setPullRefreshEnable(true);
		mOrderLv.setPullLoadEnable(false);
		mOrderLv.setXListViewListener(this);
		refreshTime();
		mAdapter=new AlreadyOrderAdapter(getActivity());
		mOrderLv.setAdapter(mAdapter);
		mOrderLv.setOnItemClickListener(this);
	}

	@Override
	public void onShow() {
		mLoadingDailog.show();
		mController.sendAsyncMessage(IDiyMessage.ACTION_GET_WAITSURE_ORDER, 0);
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action,values[0]).sendToTarget();
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
		mController.sendAsyncMessage(IDiyMessage.ACTION_GET_WAITSURE_ORDER, 0);
	}

	@Override
	public void onLoadMore() {
		
	}
}
