package com.m520it.jdmall.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.m520it.jdmall.R;
import com.m520it.jdmall.adapter.BrandAdapter;
import com.m520it.jdmall.adapter.ProductListAdapter;
import com.m520it.jdmall.bean.BrandBean;
import com.m520it.jdmall.bean.RSearchProduct;
import com.m520it.jdmall.bean.SProductList;
import com.m520it.jdmall.cons.IDiyMessage;
import com.m520it.jdmall.cons.IntentValues;
import com.m520it.jdmall.contrller.CategoryController;
import com.m520it.jdmall.listener.IModelChangeListener;
import com.m520it.jdmall.listener.IProductsSortListener;
import com.m520it.jdmall.pop.ProductsSortPopWindow;
import com.m520it.jdmall.util.FixedViewUtil;

/**
 * 产品列表
 */
public class ProductListActivity extends BaseActivity implements
			OnClickListener, IModelChangeListener,IProductsSortListener {
	
	private ListView mProductLv;
	private ProductListAdapter mAdapter;
	private TextView mAllIndicatorTv;
	private TextView mSaleIndicatorTv;
	private TextView mPriceIndicatorTv;
	private TextView mChooseIndicatorTv;
	private DrawerLayout mDrawerLayout;
	private ProductsSortPopWindow mPopWindow;
	
	private ScrollView mSlideView;
	private TextView mJDTakeTv;
	private TextView mPayWhenReceiveTv;
	private TextView mJustHasStockTv;
	private EditText mMinPriceTv;
	private EditText mMaxPriceTv;
	private GridView mBrandGv;
	private BrandAdapter mBrandAdapter;
	
	private String mSuperCategoryId;
	private String mTopCategoryId;
	private View mNullView;
	private SProductList mProductList;
	
	private CategoryController mController;
	private Handler mHandler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case IDiyMessage.ACTION_LOAD_BRANDS_RESULT:
					handleLoadBrands(msg);
					break;
				case IDiyMessage.ACTION_GET_PRODUCT_LIST_RESULT:
					handleLoadProductList((List<RSearchProduct>)msg.obj);
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_list);
		mNullView=findViewById(R.id.null_view);
		mTopCategoryId=getIntent().getStringExtra(IntentValues.TOP_CATEGORY);
		mSuperCategoryId=getIntent().getStringExtra(IntentValues.TO_PRODUCT_LIST_KEY);
		mProductList=new SProductList();
		mProductList.setCategoryId(mSuperCategoryId);
		mController=new CategoryController(this);
		mController.setIModelChangeListener(this);
		initMainView();
		initeSlideView();
		mLodingDailog.show();
		mController.sendAsyncMessage(IDiyMessage.ACTION_LOAD_BRANDS, mTopCategoryId);
		mController.sendAsyncMessage(IDiyMessage.ACTION_GET_PRODUCT_LIST, mProductList);
	}

	private void handleLoadProductList(List<RSearchProduct> products) {
		if (products.size()==0) {
			mNullView.setVisibility(View.VISIBLE);
			mProductLv.setVisibility(View.GONE);
		}else {
			mAdapter.setDatas(products);
			mAdapter.notifyDataSetChanged();
			mNullView.setVisibility(View.GONE);
			mProductLv.setVisibility(View.VISIBLE);
		}
		if (mLodingDailog.isShowing()) {
			mLodingDailog.dismiss();
		}
	}

	/**
	 * 	初始化滑动页面
	 */
	private void initeSlideView() {
		mDrawerLayout=(DrawerLayout) findViewById(R.id.drawerlayout);
		mSlideView=(ScrollView) findViewById(R.id.slide_view);
		mJDTakeTv=(TextView) findViewById(R.id.jd_take_tv);
		mPayWhenReceiveTv=(TextView) findViewById(R.id.paywhenreceive_tv);
		mJustHasStockTv=(TextView) findViewById(R.id.justhasstock_tv);
		mJDTakeTv.setOnClickListener(this);
		mPayWhenReceiveTv.setOnClickListener(this);
		mJustHasStockTv.setOnClickListener(this);
		mMinPriceTv=(EditText) findViewById(R.id.minPrice_et);
		mMaxPriceTv=(EditText) findViewById(R.id.maxPrice_et);
		mBrandGv=(GridView) findViewById(R.id.gv);
		mBrandAdapter=new BrandAdapter(this);
		mBrandGv.setAdapter(mBrandAdapter);
	}

	/**
	 * 	初始化主页
	 */
	private void initMainView() {
		mAllIndicatorTv = (TextView) findViewById(R.id.all_indicator);
		mSaleIndicatorTv = (TextView) findViewById(R.id.sale_indicator);
		mPriceIndicatorTv = (TextView) findViewById(R.id.price_indicator);
		mChooseIndicatorTv = (TextView) findViewById(R.id.choose_indicator);
		mAllIndicatorTv.setOnClickListener(this);
		mSaleIndicatorTv.setOnClickListener(this);
		mPriceIndicatorTv.setOnClickListener(this);
		mChooseIndicatorTv.setOnClickListener(this);
		mPopWindow=new ProductsSortPopWindow(this);
		mPopWindow.setListener(this);

		mProductLv = (ListView) findViewById(R.id.product_lv);
		mAdapter = new ProductListAdapter(this);
		mProductLv.setAdapter(mAdapter);
		mProductLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(ProductListActivity.this,
						ProductDetailsActivity.class);
				RSearchProduct product = (RSearchProduct) mAdapter.getItem(position);
				intent.putExtra(IntentValues.TO_PRODUCT_DETAILS_KEY, product.getId());
				startActivity(intent);
				finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.all_indicator:
				showIndicatorType(v);
				mPopWindow.onShow(v);
				break;
			case R.id.sale_indicator:
				showIndicatorType(v);
				mProductList.setSortType(SProductList.SEARCH_SALE);
				mProductList.setFilterType(0);
				mLodingDailog.show();
				mController.sendAsyncMessage(IDiyMessage.ACTION_GET_PRODUCT_LIST, mProductList);
				break;
			case R.id.price_indicator:
				showIndicatorType(v);
				mProductList.setFilterType(0);
				if (mProductList.getSortType()==SProductList.SEARCH_PRICE_DOWN
						||mProductList.getSortType()==SProductList.SEARCH_SALE
						||mProductList.getSortType()==0) {
					mProductList.setSortType(SProductList.SEARCH_PRICE_UP);
				}else if (mProductList.getSortType()==SProductList.SEARCH_PRICE_UP
						||mProductList.getSortType()==SProductList.SEARCH_SALE
						||mProductList.getSortType()==0) {
					mProductList.setSortType(SProductList.SEARCH_PRICE_DOWN);
				}
				mLodingDailog.show();
				mController.sendAsyncMessage(IDiyMessage.ACTION_GET_PRODUCT_LIST, mProductList);
				break;
			case R.id.choose_indicator:
				mDrawerLayout.openDrawer(mSlideView);
				break;
			case R.id.jd_take_tv:
				v.setSelected(!v.isSelected());
				break;
			case R.id.paywhenreceive_tv:
				v.setSelected(!v.isSelected());
				break;
			case R.id.justhasstock_tv:
				v.setSelected(!v.isSelected());
				break;
		}
	}

	private void showIndicatorType(View v) {
		mAllIndicatorTv.setSelected(false);
		mSaleIndicatorTv.setSelected(false);
		mPriceIndicatorTv.setSelected(false);
		mChooseIndicatorTv.setSelected(false);
		v.setSelected(true);
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action,values[0]).sendToTarget();
	}

	@Override
	public void onSortChange(int type) {
		mProductList.setFilterType(type);
		if (type==SProductList.SEARCH_ALL) {
			mAllIndicatorTv.setText("综合");
		}else if (type==SProductList.SEARCH_NEW) {
			mAllIndicatorTv.setText("最新");
		}else if (type==SProductList.SEARCH_COMMENT_UP2DOWN) {
			mAllIndicatorTv.setText("评论");
		}
		mLodingDailog.show();
		mController.sendAsyncMessage(IDiyMessage.ACTION_GET_PRODUCT_LIST, mProductList);
	}

	private void handleLoadBrands(Message msg) {
		mBrandAdapter.setDatas((ArrayList<BrandBean>) msg.obj);
		mBrandAdapter.notifyDataSetChanged();
		FixedViewUtil.setListViewHeightBasedOnChildren(mBrandGv,3);
	}
	
	public void chooseSearchClick(View view){
		int serviceType=0;
		if (mJDTakeTv.isSelected()) {
			serviceType+=1;
		}
		if (mPayWhenReceiveTv.isSelected()) {
			serviceType+=2;
		}
		if (mJustHasStockTv.isSelected()) {
			serviceType+=4;
		}
		mProductList.setDeliverChoose(serviceType);
		if (!(mMinPriceTv.getText().toString()).equals("")
				&&!(mMaxPriceTv.getText().toString()).equals("")) {
			int minPrice=Integer.parseInt(mMinPriceTv.getText().toString());
			int maxPrice=Integer.parseInt(mMaxPriceTv.getText().toString());
			if (minPrice>0&&maxPrice>0&&minPrice<maxPrice) {
				mProductList.setMinPrice(minPrice);
				mProductList.setMaxPrice(maxPrice);
			}
		}
		int mPosition = mBrandAdapter.mPosition;
		if (mPosition!=-1) {
			BrandBean brand=(BrandBean) mBrandAdapter.getItem(mPosition);
			mProductList.setBrandId(brand.getId()+"");
		}
		mDrawerLayout.closeDrawer(mSlideView);
		mLodingDailog.show();
		mController.sendAsyncMessage(IDiyMessage.ACTION_GET_PRODUCT_LIST, mProductList);
	}
	
	public void resetClick(View view){
		mJDTakeTv.setSelected(false);
		mPayWhenReceiveTv.setSelected(false);
		mJustHasStockTv.setSelected(false);
		mMinPriceTv.setText("");
		mMaxPriceTv.setText("");
		mBrandAdapter.mPosition=-1;
		mBrandAdapter.notifyDataSetChanged();
		mProductList.setDeliverChoose(0);
		mProductList.setMinPrice(0);
		mProductList.setMaxPrice(0);
		mProductList.setBrandId("");
		mDrawerLayout.closeDrawer(mSlideView);
		mLodingDailog.show();
		mController.sendAsyncMessage(IDiyMessage.ACTION_GET_PRODUCT_LIST, mProductList);
	}

}
