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
import android.widget.Toast;

import com.m520it.jdmall.R;
import com.m520it.jdmall.bean.RResult;
import com.m520it.jdmall.cons.IDiyMessage;
import com.m520it.jdmall.cons.IntentValues;
import com.m520it.jdmall.contrller.ProductDetailsController;
import com.m520it.jdmall.frag.ProductCommentView;
import com.m520it.jdmall.frag.ProductDetailsView;
import com.m520it.jdmall.frag.ProductIntroduceView;
import com.m520it.jdmall.listener.IModelChangeListener;

public class ProductDetailsActivity extends FragmentActivity implements
		OnClickListener, OnPageChangeListener, IModelChangeListener {

	private ViewPager mVp;
	private View mIntroduceView;
	private View mDetailsView;
	private View mCommentView;
	private int mCurrentItemId;
	private ProductAdapter mProductAdapter;
	public String mProductId;
	public int mBuyCount=1;
	private ProductDetailsController mController;
	private Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (msg.what==IDiyMessage.ACTION_ADD2SHOPCAR_RESULT) {
				RResult result=(RResult) msg.obj;
				if (result.isSuccess()) {
					Toast.makeText(ProductDetailsActivity.this, "加入购物车成功!", 800).show();
				}else{
					Toast.makeText(ProductDetailsActivity.this, result.getErrorMsg(), 800).show();
				}
			}
		}
	};
	
	public void goBack(View view){
		finish();
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_productdetails);
		
		mProductId=getIntent().getStringExtra(IntentValues.TO_PRODUCT_DETAILS_KEY);
		mController=new ProductDetailsController(this);
		mController.setIModelChangeListener(this);
		
		mVp = (ViewPager) findViewById(R.id.vp);
		mProductAdapter = new ProductAdapter(getSupportFragmentManager());
		mVp.setAdapter(mProductAdapter);

		findViewById(R.id.introduce_ll).setOnClickListener(this);
		findViewById(R.id.details_ll).setOnClickListener(this);
		findViewById(R.id.comment_ll).setOnClickListener(this);
		mIntroduceView=findViewById(R.id.introduce_view);
		mDetailsView=findViewById(R.id.details_view);
		mCommentView=findViewById(R.id.comment_tv);
		
		mVp.setOnPageChangeListener(this);
	}

	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
	public class ProductAdapter extends FragmentPagerAdapter {


		public ProductAdapter(FragmentManager fm) {
			super(fm);
			fragments.add(new ProductIntroduceView());
			fragments.add(new ProductDetailsView());
			fragments.add(new ProductCommentView());
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
		if (mCurrentItemId==v.getId()) {
			return;
		}
		mCurrentItemId=v.getId();
		mIntroduceView.setVisibility(View.INVISIBLE);
		mDetailsView.setVisibility(View.INVISIBLE);
		mCommentView.setVisibility(View.INVISIBLE);
		switch (mCurrentItemId) {
		case R.id.introduce_ll:
			mIntroduceView.setVisibility(View.VISIBLE);
			mVp.setCurrentItem(0);
			break;
		case R.id.details_ll:
			mDetailsView.setVisibility(View.VISIBLE);
			mVp.setCurrentItem(1);
			break;
		case R.id.comment_ll:
			mCommentView.setVisibility(View.VISIBLE);
			mVp.setCurrentItem(2);
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
		mIntroduceView.setVisibility(View.INVISIBLE);
		mDetailsView.setVisibility(View.INVISIBLE);
		mCommentView.setVisibility(View.INVISIBLE);
		switch (position) {
		case 0:
			mIntroduceView.setVisibility(View.VISIBLE);
			break;
		case 1:
			mDetailsView.setVisibility(View.VISIBLE);
			break;
		case 2:
			mCommentView.setVisibility(View.VISIBLE);
			break;
		}
	}
	
	public void add2ShopCar(View view){
		ProductIntroduceView introduceView=(ProductIntroduceView) fragments.get(0);
		mController.sendAsyncMessage(IDiyMessage.ACTION_ADD2SHOPCAR, mProductId,mBuyCount,introduceView.getSelectedType());
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action,values[0]).sendToTarget();
	}
	
}
