package com.m520it.jdmall.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m520it.jdmall.R;
import com.m520it.jdmall.listener.IBottomItemClickListener;

public class BottomBar extends LinearLayout implements OnClickListener {

	private TextView mMainTv;
	private TextView mCategoryTv;
	private TextView mShopCarTv;
	private TextView mMineTv;
	private ImageView mMainIv;
	private ImageView mCategoryIv;
	private ImageView mShopCarIv;
	private ImageView mMineIv;
	private LinearLayout mMainLl;
	private LinearLayout mCategoryLl;
	private LinearLayout mShopCarLl;
	private LinearLayout mMineLl;
	
	private int mTabItemId;
	private IBottomItemClickListener mClickListener;
	
	public void setClickListener(IBottomItemClickListener clickListener) {
		this.mClickListener = clickListener;
	}

	//如果一个类通过布局的方式创建 那么会调用2个参数的构造器 如下
	public BottomBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
//	当布局加载完毕后 会回调此方法 一般该方法用来初始化子控件
	protected void onFinishInflate() {
		super.onFinishInflate();
		mMainTv=(TextView) findViewById(R.id.frag_main);
		mMainIv=(ImageView) findViewById(R.id.frag_main_iv);
		mMainLl=(LinearLayout) findViewById(R.id.frag_main_ll);
		mMainLl.setOnClickListener(this);
		mCategoryTv=(TextView) findViewById(R.id.frag_category);
		mCategoryIv=(ImageView) findViewById(R.id.frag_category_iv);
		mCategoryLl=(LinearLayout) findViewById(R.id.frag_category_ll);
		mCategoryLl.setOnClickListener(this);
		mShopCarTv=(TextView) findViewById(R.id.frag_shopcar);
		mShopCarIv=(ImageView) findViewById(R.id.frag_shopcar_iv);
		mShopCarLl=(LinearLayout) findViewById(R.id.frag_shopcar_ll);
		mShopCarLl.setOnClickListener(this);
		mMineTv=(TextView) findViewById(R.id.frag_mine);
		mMineIv=(ImageView) findViewById(R.id.frag_mine_iv);
		mMineLl=(LinearLayout) findViewById(R.id.frag_mine_ll);
		mMineLl.setOnClickListener(this);
		
		mMainTv.setSelected(true);
		mMainIv.setSelected(true);
	}

	@Override
	public void onClick(View v) {
		if (mTabItemId==v.getId()) {
			return;
		}
		mTabItemId=v.getId();
		changeTabItemStyle(v);
		if (mClickListener!=null) {
			mClickListener.onItemClick(v);
		}
	}

	/**
	 * 修改底部按钮样式
	 */
	private void changeTabItemStyle(View v) {
		mMainTv.setSelected(v.getId()==R.id.frag_main_ll);
		mMainIv.setSelected(v.getId()==R.id.frag_main_ll);
		mCategoryTv.setSelected(v.getId()==R.id.frag_category_ll);
		mCategoryIv.setSelected(v.getId()==R.id.frag_category_ll);
		mShopCarTv.setSelected(v.getId()==R.id.frag_shopcar_ll);
		mShopCarIv.setSelected(v.getId()==R.id.frag_shopcar_ll);
		mMineTv.setSelected(v.getId()==R.id.frag_mine_ll);
		mMineIv.setSelected(v.getId()==R.id.frag_mine_ll);
	}

}
