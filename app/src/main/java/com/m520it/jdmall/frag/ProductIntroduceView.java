package com.m520it.jdmall.frag;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.loopj.android.image.SmartImageView;
import com.m520it.jdmall.R;
import com.m520it.jdmall.activity.ProductDetailsActivity;
import com.m520it.jdmall.adapter.GoodCommentAdapter;
import com.m520it.jdmall.adapter.ProductVersionAdapter;
import com.m520it.jdmall.bean.RProductComment;
import com.m520it.jdmall.bean.RProductInfo;
import com.m520it.jdmall.cons.IDiyMessage;
import com.m520it.jdmall.cons.NetworkConst;
import com.m520it.jdmall.contrller.ProductDetailsController;
import com.m520it.jdmall.listener.IModelChangeListener;
import com.m520it.jdmall.listener.INumberInputListener;
import com.m520it.jdmall.listener.IScrollViewListener;
import com.m520it.jdmall.pop.LoadingDailog;
import com.m520it.jdmall.util.FixedViewUtil;
import com.m520it.jdmall.view.NumberInputView;
import com.m520it.jdmall.view.ObservableScrollView;

public class ProductIntroduceView extends Fragment implements
		IModelChangeListener, OnClickListener, IScrollViewListener, INumberInputListener {

	private ViewPager mImageVp;
	private TextView mVpIndicTv;
	private TextView mNameTv;
	private TextView mSelfSaleTv;
	private TextView mDescTv;
	private TextView mPriceTv;
	private TextView mRecommendBuyTv;
	private NumberInputView mNumberInputView;
	private GridView mProductVersionsGv;
	private ProductVersionAdapter mProductVersionAdapter;
	private ObservableScrollView mScrollView;
	private View mScrollIndicTv;
	private ListView mGoodCommentLv;
	private GoodCommentAdapter mGoodCommentAdapter;
	private Object mProductId;
	private Timer mTimer;
	private LoadingDailog mLoadingDailog;
	private ProductDetailsController mController;
	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
				case IDiyMessage.ACTION_GET_PRODUCT_INTRODUCE_RESULT:
					handleProductIntroduce((RProductInfo) msg.obj);
					break;
				case IDiyMessage.ACTION_GET_RECOMMEND_GOOD_COMMENT_RESULT:
					handlRecommendComment((List<RProductComment>) msg.obj);
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.product_introduce_view, null);
	}

	protected void handlRecommendComment(List<RProductComment> datas) {
		mGoodCommentAdapter.setDatas(datas);
		mGoodCommentAdapter.notifyDataSetChanged();
		FixedViewUtil.setListViewHeightBasedOnChildren(mGoodCommentLv);
	}

	protected void handleProductIntroduce(RProductInfo info) {
		if (info == null) {
			Toast.makeText(getActivity(), "服务器出小差了！获取信息失败", 800).show();
			getActivity().finish();
		} else {
			initScrollImages(info);
			mNameTv.setText(info.getName());
			mSelfSaleTv.setVisibility(info.isIfSaleOneself() ? View.VISIBLE
					: View.INVISIBLE);
			mDescTv.setText(info.getRecomProduct());
			mPriceTv.setText("￥" + info.getPrice());
			mProductVersionAdapter.setDatas(info.getTypeList());
			mProductVersionAdapter.notifyDataSetChanged();
			FixedViewUtil.setListViewHeightBasedOnChildren(mProductVersionsGv,
					1);
			mNumberInputView.setMax(info.getStockCount());
		}
		if (mLoadingDailog.isShowing()) {
			mLoadingDailog.dismiss();
		}
	}
	
	public String getSelectedType(){
		return mProductVersionAdapter.getSelectedItem();
	}

	private void initScrollImages(RProductInfo info) {
		final ImageAdapter imagesAdapter = new ImageAdapter(info.getImgUrls());
		mImageVp.setAdapter(imagesAdapter);
		final int count = imagesAdapter.getCount();
		mVpIndicTv.setText("1/" + count);
		mTimer=new Timer();
		mTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						int currItem = mImageVp.getCurrentItem();
						currItem++;
						if (currItem > count - 1) {
							currItem = 0;
						}
						mImageVp.setCurrentItem(currItem);
						mVpIndicTv.setText((currItem + 1) + "/" + count);
					}
				});
			}
		}, 3000, 3000);
	}
	
	@Override
	public void onStop() {
		super.onStop();
		mTimer.cancel();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mController = new ProductDetailsController(getActivity());
		mController.setIModelChangeListener(this);
		mLoadingDailog=new LoadingDailog(getActivity(), R.style.CustomDialog);
		mImageVp = (ViewPager) getActivity().findViewById(R.id.asvp);
		mVpIndicTv = (TextView) getActivity().findViewById(R.id.vp_indic_tv);
		mNameTv = (TextView) getActivity().findViewById(R.id.name_tv);
		mSelfSaleTv = (TextView) getActivity().findViewById(R.id.self_sale_tv);
		mDescTv = (TextView) getActivity().findViewById(R.id.desc_tv);
		mRecommendBuyTv = (TextView) getActivity().findViewById(
				R.id.recommend_buy_tv);
		mRecommendBuyTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		mPriceTv = (TextView) getActivity().findViewById(R.id.price_tv);
		mProductVersionsGv = (GridView) getActivity().findViewById(
				R.id.product_versions_gv);
		mProductVersionAdapter = new ProductVersionAdapter(getActivity());
		mProductVersionsGv.setAdapter(mProductVersionAdapter);
		mNumberInputView = (NumberInputView) getActivity().findViewById(
				R.id.number_input_et);
		mNumberInputView.setListener(this);
		mScrollIndicTv = getActivity().findViewById(R.id.scroll_to_top_indic);
		mScrollIndicTv.setOnClickListener(this);
		mScrollView = (ObservableScrollView) getActivity().findViewById(
				R.id.scrollview);
		mScrollView.setScrollViewListener(this);
		mGoodCommentLv = (ListView) getActivity().findViewById(
				R.id.good_comment_lv);
		mGoodCommentAdapter = new GoodCommentAdapter(getActivity());
		mGoodCommentLv.setAdapter(mGoodCommentAdapter);

		mLoadingDailog.show();
		mProductId = ((ProductDetailsActivity) getActivity()).mProductId;
		mController.sendAsyncMessage(IDiyMessage.ACTION_GET_PRODUCT_INTRODUCE,
				mProductId);
		mController.sendAsyncMessage(
				IDiyMessage.ACTION_GET_RECOMMEND_GOOD_COMMENT, mProductId);
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action, values[0]).sendToTarget();
	}

	@Override
	public void onClick(View v) {
		mScrollView.scrollTo(0, 0);
		mScrollIndicTv.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onScrollChanged(ObservableScrollView scrollView, int x, int y,
			int oldx, int oldy) {
		if (y < 40) {
			mScrollIndicTv.setVisibility(View.INVISIBLE);
		} else {
			mScrollIndicTv.setVisibility(View.VISIBLE);
		}
	}

	private class ImageAdapter extends PagerAdapter {

		private List<SmartImageView> imageViews = new ArrayList<SmartImageView>();

		public ImageAdapter(String imageJson) {
			List<String> imageUrls = JSON.parseArray(imageJson, String.class);
			for (int i = 0; i < imageUrls.size(); i++) {
				SmartImageView iv = new SmartImageView(getActivity());
				iv.setId(i);
				iv.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
				iv.setScaleType(ScaleType.FIT_CENTER);
				iv.setImageUrl(NetworkConst.DOMAIN + imageUrls.get(i));
				imageViews.add(iv);
			}
		}

		@Override
		public int getCount() {
			return imageViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(imageViews.get(position));
			return imageViews.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(imageViews.get(position));
		}
	}

	@Override
	public void onTextChange(int buyCount) {
		((ProductDetailsActivity)getActivity()).mBuyCount=buyCount;
	}

}
