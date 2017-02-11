package com.m520it.jdmall.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.m520it.jdmall.R;
import com.m520it.jdmall.frag.AllOrderView;
import com.m520it.jdmall.frag.OrderBaseFragment;
import com.m520it.jdmall.frag.WaitPayOrderView;
import com.m520it.jdmall.frag.WaitReceiveOrderView;
import com.m520it.jdmall.frag.AlreadyOrderView;
import com.m520it.jdmall.listener.IModelChangeListener;

public class OrderListActivity extends FragmentActivity implements
		OnClickListener, OnPageChangeListener {

	private ViewPager mVp;
	private View mAllIndicView;
	private View mWaitPayView;
	private View mWaitReceiveView;
	private View mWaitSureView;
	private View mAllOrderLl;
	private View mWaitPayOrderLl;
	private View mWaitReceiveOrderLl;
	private View mWaitSureOrderLl;
	private OrderContainerAdapter mOrderContainerAdapter;

	public void goBack(View view) {
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_orderlist);

		mVp = (ViewPager) findViewById(R.id.vp);
		mOrderContainerAdapter = new OrderContainerAdapter(
				getSupportFragmentManager());
		mVp.setAdapter(mOrderContainerAdapter);
		mVp.setOnPageChangeListener(this);

		mAllOrderLl = findViewById(R.id.all_order_ll);
		mAllOrderLl.setOnClickListener(this);
		mWaitPayOrderLl = findViewById(R.id.wait_pay_ll);
		mWaitPayOrderLl.setOnClickListener(this);
		mWaitReceiveOrderLl = findViewById(R.id.wait_receive_ll);
		mWaitReceiveOrderLl.setOnClickListener(this);
		mWaitSureOrderLl = findViewById(R.id.wait_sure_ll);
		mWaitSureOrderLl.setOnClickListener(this);
		mAllIndicView = findViewById(R.id.all_order_view);
		mWaitPayView = findViewById(R.id.wait_pay_view);
		mWaitReceiveView = findViewById(R.id.wait_receive_view);
		mWaitSureView = findViewById(R.id.wait_sure_view);
		
	}

	public static class OrderContainerAdapter extends FragmentPagerAdapter {

		public ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		
		public OrderContainerAdapter(FragmentManager fm) {
			super(fm);
			fragments.add(new AllOrderView());
			fragments.add(new WaitPayOrderView());
			fragments.add(new WaitReceiveOrderView());
			fragments.add(new AlreadyOrderView());
		}

		@Override
		public Fragment getItem(int arg0) {
			return fragments.get(arg0);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.all_order_ll:
				mVp.setCurrentItem(0);
				break;
			case R.id.wait_pay_ll:
				mVp.setCurrentItem(1);
				break;
			case R.id.wait_receive_ll:
				mVp.setCurrentItem(2);
				break;
			case R.id.wait_sure_ll:
				mVp.setCurrentItem(3);
				break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {
		mAllIndicView.setVisibility(View.INVISIBLE);
		mWaitPayView.setVisibility(View.INVISIBLE);
		mWaitReceiveView.setVisibility(View.INVISIBLE);
		mWaitSureView.setVisibility(View.INVISIBLE);
		switch (position) {
		case 0:
			mAllIndicView.setVisibility(View.VISIBLE);
			break;
		case 1:
			mWaitPayView.setVisibility(View.VISIBLE);
			break;
		case 2:
			mWaitReceiveView.setVisibility(View.VISIBLE);
			break;
		case 3:
			mWaitSureView.setVisibility(View.VISIBLE);
			break;
		}
		((OrderBaseFragment) mOrderContainerAdapter.fragments.get(position)).onShow();
	}

}
