package com.m520it.jdmall.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;

import com.m520it.jdmall.R;
import com.m520it.jdmall.frag.CategoryFragment;
import com.m520it.jdmall.frag.HomeFragment;
import com.m520it.jdmall.frag.MimeFragment;
import com.m520it.jdmall.frag.ShopCarFragment;
import com.m520it.jdmall.listener.IBottomItemClickListener;
import com.m520it.jdmall.ui.BottomBar;

/**
 * 主界面
 */
public class MainActivity extends FragmentActivity implements
		IBottomItemClickListener {

	private BottomBar mBottomBar;
	private HomeFragment mMainFragment;
	private CategoryFragment mCategoryFragment;
	private ShopCarFragment mShopCarFragment;
	private MimeFragment mMineFragment;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initUI();
	}

	private void initUI() {
		initTopBarChilds();
		mBottomBar = (BottomBar) findViewById(R.id.bottom_bar);
		mBottomBar.setClickListener(this);
	}

	private void initTopBarChilds() {
		mMainFragment=new HomeFragment();
		mCategoryFragment=new CategoryFragment();
		mShopCarFragment=new ShopCarFragment();
		mMineFragment=new MimeFragment();
		replaceTopBarChild(mMainFragment);
	}

	private void replaceTopBarChild(Fragment f) {
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
		transaction.replace(R.id.top_bar, f);
		transaction.commit();
	}

	@Override
	public void onItemClick(View view) {
		switch (view.getId()) {
		case R.id.frag_main_ll:
			replaceTopBarChild(mMainFragment);
			break;
		case R.id.frag_category_ll:
			replaceTopBarChild(mCategoryFragment);
			break;
		case R.id.frag_shopcar_ll:
			replaceTopBarChild(mShopCarFragment);
			break;
		case R.id.frag_mine_ll:
			replaceTopBarChild(mMineFragment);
			break;
		}
	}

}
