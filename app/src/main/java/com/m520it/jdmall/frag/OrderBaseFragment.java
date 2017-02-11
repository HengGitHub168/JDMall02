package com.m520it.jdmall.frag;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.support.v4.app.Fragment;
import android.view.View;

import com.m520it.jdmall.contrller.OrderController;
import com.m520it.jdmall.pop.LoadingDailog;
import com.m520it.jdmall.ui.xlv.XListView;
import com.m520it.jdmall.ui.xlv.XListView.IXListViewListener;

public abstract class OrderBaseFragment extends Fragment implements IXListViewListener{
	
	protected OrderController mController;
	protected LoadingDailog mLoadingDailog;
	protected View mNullView;
	protected XListView mOrderLv;
	
	public abstract void onShow();
	
	protected void refreshTime() {
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
		mOrderLv.setRefreshTime(formatter.format(new Date()));
		mOrderLv.stopRefresh();
	}
}

