package com.m520it.jdmall.view;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.m520it.jdmall.R;
import com.m520it.jdmall.activity.ProductListActivity;
import com.m520it.jdmall.bean.RBaseCategory;
import com.m520it.jdmall.bean.RSubCategory;
import com.m520it.jdmall.cons.IDiyMessage;
import com.m520it.jdmall.cons.IntentValues;
import com.m520it.jdmall.cons.NetworkConst;
import com.m520it.jdmall.contrller.CategoryController;
import com.m520it.jdmall.listener.IModelChangeListener;
import com.m520it.jdmall.listener.IViewContainer;
import com.m520it.jdmall.pop.LoadingDailog;
import com.m520it.jdmall.ui.FlexiScrollView;

public class SubCategoryView extends FlexiScrollView 
							implements IViewContainer, IModelChangeListener {
	
	private LoadingDailog mLoadingDailog;
	private RBaseCategory mTopCategory;//该对象是左边列表传递过来的，主要是获取分类id 和右边头部的图片
	private LinearLayout mChildContainerLl;//布局里的容器id
	private CategoryController mController;
	private Handler mHandler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case IDiyMessage.ACTION_GET_SUB_CAREGORY_RESULT:
				handleLoadSubCategory((ArrayList<RSubCategory>) msg.obj);
				break;
			}
		}
	};
	
	public SubCategoryView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	private void handleLoadSubCategory(ArrayList<RSubCategory> subCategorys) {
		//1.删除原来内部的所有子控件
		mChildContainerLl.removeAllViews();
		if (subCategorys.size()!=0) {
			//2.添加头部图片
			SmartImageView iv=new SmartImageView(getContext());
			iv.setImageUrl(NetworkConst.DOMAIN+mTopCategory.getBannerUrl());
			iv.setLayoutParams(new LinearLayout.LayoutParams(-1,110));
			iv.setScaleType(ScaleType.FIT_XY);
			mChildContainerLl.addView(iv);
			for (int i = 0; i < subCategorys.size(); i++) {
				//添加2级分类文本  如裙装 上装 下装等
				RSubCategory subCategory = subCategorys.get(i);
				TextView titleTv=new TextView(getContext());
				LinearLayout.LayoutParams titleTvParams = new LinearLayout.LayoutParams(-2, -2);
				titleTvParams.setMargins(0, 30, 0, 0);
				titleTv.setLayoutParams(titleTvParams);
				titleTv.setText(subCategory.getName()+"");
				mChildContainerLl.addView(titleTv);
				//添加9宫格
				ArrayList<RBaseCategory> thirdCategory = subCategory.getThirdCategory();
				int lineNum = thirdCategory.size()/3;
				lineNum=thirdCategory.size()%3!=0?lineNum+1:lineNum;
				for (int j = 0; j < lineNum; j++) {
					//根据行号为2级分类创建行
					LinearLayout lineLl=new LinearLayout(getContext());
					lineLl.setOrientation(LinearLayout.HORIZONTAL);
					LinearLayout.LayoutParams lineParams=new LinearLayout.LayoutParams(-1, -2);
					lineLl.setLayoutParams(lineParams);
					//添加第 1 列
					addColumn(thirdCategory, 3*j, lineLl);
					//添加第2列
					if (3*j+1<thirdCategory.size()-1) {
						addColumn(thirdCategory, 3*j+1, lineLl);
					}
					//添加第3列
					if (3*j+2<thirdCategory.size()-1) {
						addColumn(thirdCategory, 3*j+2, lineLl);
					}
					mChildContainerLl.addView(lineLl);
				}
			}
		}
		if (mLoadingDailog.isShowing()) {
			mLoadingDailog.dismiss();
		}
	}

	private void addColumn(final ArrayList<RBaseCategory> thirdCategory, final int columnIndex,LinearLayout lineLl) {
		LinearLayout column=new LinearLayout(getContext());
		column.setOrientation(LinearLayout.VERTICAL);
		column.setLayoutParams(new LinearLayout.LayoutParams(getWidth()/3,-2));
		lineLl.addView(column);
		
		SmartImageView bannerIv=new SmartImageView(getContext());
		bannerIv.setImageUrl(NetworkConst.DOMAIN+thirdCategory.get(columnIndex).getBannerUrl());
		bannerIv.setLayoutParams(new LinearLayout.LayoutParams(-1, getWidth()/3));
		column.addView(bannerIv);
		
		TextView nameTv=new TextView(getContext());
		nameTv.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
		nameTv.setText(thirdCategory.get(columnIndex).getName());
		nameTv.setGravity(Gravity.CENTER_HORIZONTAL);
		column.addView(nameTv);
		
		column.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String categoryId = thirdCategory.get(columnIndex).getId();
				Intent intent=new Intent(getContext(),ProductListActivity.class);
				intent.putExtra(IntentValues.TO_PRODUCT_LIST_KEY, categoryId);
				intent.putExtra(IntentValues.TOP_CATEGORY, mTopCategory.getId());
				getContext().startActivity(intent);
			}
		});
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mLoadingDailog=new LoadingDailog(getContext(), R.style.CustomDialog);
		mController=new CategoryController(getContext());
		mController.setIModelChangeListener(this);
		mChildContainerLl=(LinearLayout) findViewById(R.id.child_container_ll);
	}

	@Override
	public void show(Object... values) {
		mLoadingDailog.show();
		mTopCategory = (RBaseCategory) values[0];
		mController.sendAsyncMessage(IDiyMessage.ACTION_GET_SUB_CAREGORY, mTopCategory.getId());
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action,values[0]).sendToTarget();
	}

}
