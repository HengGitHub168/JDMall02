package com.m520it.jdmall.frag;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.m520it.jdmall.R;
import com.m520it.jdmall.activity.ProductDetailsActivity;
import com.m520it.jdmall.activity.ProductSearchActivity;
import com.m520it.jdmall.adapter.HomeAdAdapter;
import com.m520it.jdmall.adapter.HomeSecKillAdapter;
import com.m520it.jdmall.adapter.RecommendAdapter;
import com.m520it.jdmall.bean.RBanner;
import com.m520it.jdmall.bean.RGetYourFav;
import com.m520it.jdmall.bean.RSecKill;
import com.m520it.jdmall.cons.IDiyMessage;
import com.m520it.jdmall.cons.IntentValues;
import com.m520it.jdmall.cons.NetworkConst;
import com.m520it.jdmall.contrller.HomeController;
import com.m520it.jdmall.listener.IModelChangeListener;
import com.m520it.jdmall.ui.HorizontalListView;
import com.m520it.jdmall.util.FixedViewUtil;

/**
 * 主页-首页
 */
public class HomeFragment extends BaseFragment implements IModelChangeListener,
		OnItemClickListener, OnClickListener {

	private ImageView mScanIv;
	private ImageView mMessageIv;
	private RelativeLayout mAdRl;
	private ViewPager mAdVp;
	private LinearLayout mAdIndicator;
	private HomeAdAdapter mAdAdapter;
	private SmartImageView mAd2Iv;
	private HorizontalListView mListView;
	private HomeSecKillAdapter mSecKillAdapter;
	private GridView mGridView;
	private RecommendAdapter mRecommendAdapter;
	private EditText mSearchEt;
	private HomeController mController;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case IDiyMessage.ACTION_LOAD_AD1_RESULT:
				handleLoadAd1Result((ArrayList<RBanner>) msg.obj);
				break;
			case IDiyMessage.ACTION_LOAD_AD2_RESULT:
				handleLoadAd2Result((ArrayList<RBanner>) msg.obj);
				break;
			case IDiyMessage.ACTION_SECKILL_RESULT:
				handleSeckill((ArrayList<RSecKill>) msg.obj);
				break;
			case IDiyMessage.ACTION_RECOMMEND_RESULT:
				handleRecommendProduct((ArrayList<RGetYourFav>) msg.obj);
				break;
			case IDiyMessage.ACTION_SCROLL_AD:
				handleScrollAd(msg);
				break;
			case 0x123:
				mSecKillTv.setText("00时"+timeLeft+"分");
				if (timeLeft>0) {
					timeLeft--;
				}
				break;
			}
		}
	};
	private TextView mSecKillTv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home, null, false);
	}

	private void handleRecommendProduct(ArrayList<RGetYourFav> getYourFavs) {
		mRecommendAdapter.setBeans(getYourFavs);
		mRecommendAdapter.notifyDataSetChanged();
		FixedViewUtil.setListViewHeightBasedOnChildren(mGridView, 2);
		final ScrollView mScrollView = (ScrollView) getActivity().findViewById(
				R.id.scrollbar);

		mScrollView.post(new Runnable() {
			public void run() {
				mScrollView.scrollTo(0, 0);
			}
		});
	}

	int timeLeft = 0;
	private Timer mTimer;
	private void handleSeckill(ArrayList<RSecKill> secKills) {
		if (secKills.size()!=0) {
			timeLeft = secKills.get(2).getTimeLeft();
			new Timer().schedule(new TimerTask() {
				
				@Override
				public void run() {
					mHandler.sendEmptyMessage(0x123);
				}
			}, 0, 60*1000);
		}
		mSecKillAdapter.setBeans(secKills);
		mSecKillAdapter.notifyDataSetChanged();
	}

	private void handleLoadAd2Result(ArrayList<RBanner> banners) {
		if (banners.size() > 0) {
			mAd2Iv.setImageUrl(NetworkConst.DOMAIN + banners.get(0).getAdUrl());
			mAd2Iv.setVisibility(View.VISIBLE);
		}
	}

	private void handleLoadAd1Result(final ArrayList<RBanner> banners) {
		if (banners.size() == 0) {
			mAdVp.setVisibility(View.GONE);
		} else {
			mAdAdapter.setBeans(banners);
			mAdAdapter.notifyDataSetChanged();
			for (int i = 0; i < banners.size(); i++) {
				ImageView indicatorIv = new ImageView(getActivity());
				indicatorIv.setBackgroundResource(R.drawable.ad_indicator_bg);
				LayoutParams params = new LayoutParams(15, 15);
				params.setMargins(25, 0, 0, 0);
				indicatorIv.setLayoutParams(params);
				mAdIndicator.addView(indicatorIv, i);
			}
			mAdRl.setVisibility(View.VISIBLE);
			mTimer = new Timer();
			mTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					scrollAdBar(banners);
				}
			}, 0, 3000);
		}
		if (mLoadingDailog.isShowing()) {
			mLoadingDailog.dismiss();
		}
	}
	
	@Override
	public void onStop() {
		super.onStop();
		mTimer.cancel();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mController = new HomeController(getActivity());
		mController.setIModelChangeListener(this);

		mSearchEt = (EditText) getActivity().findViewById(R.id.search_et);
		mSearchEt.setOnClickListener(this);
		mScanIv = (ImageView) getActivity().findViewById(R.id.scan_iv);
		mMessageIv = (ImageView) getActivity().findViewById(R.id.message_iv);
		mScanIv.setOnClickListener(this);
		mMessageIv.setOnClickListener(this);

		mAdRl = (RelativeLayout) getActivity().findViewById(R.id.ad_rl);
		mAdVp = (ViewPager) getActivity().findViewById(R.id.ad_vp);
		mAdAdapter = new HomeAdAdapter(getActivity());
		mAdVp.setAdapter(mAdAdapter);
		mAdIndicator = (LinearLayout) getActivity().findViewById(
				R.id.ad_indicator);
		mAd2Iv = (SmartImageView) getActivity().findViewById(R.id.ad2_iv);

		mSecKillTv = (TextView) getActivity().findViewById(R.id.seckill_tv);
		mListView = (HorizontalListView) getActivity().findViewById(
				R.id.horizon_listview);
		mSecKillAdapter = new HomeSecKillAdapter(getActivity());
		mListView.setAdapter(mSecKillAdapter);
		mListView.setOnItemClickListener(this);

		mGridView = (GridView) getActivity().findViewById(R.id.recommend_gv);
		mRecommendAdapter = new RecommendAdapter(getActivity());
		mGridView.setAdapter(mRecommendAdapter);
		mGridView.setOnItemClickListener(this);
		mLoadingDailog.show();
		mController.sendAsyncMessage(IDiyMessage.ACTION_LOAD_AD1, 0);
		mController.sendAsyncMessage(IDiyMessage.ACTION_LOAD_AD2, 0);
		mController.sendAsyncMessage(IDiyMessage.ACTION_SECKILL, 0);
		mController.sendAsyncMessage(IDiyMessage.ACTION_RECOMMEND, 0);

	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action, values[0]).sendToTarget();
	}

	private void scrollAdBar(final ArrayList<RBanner> banners) {
		mHandler.obtainMessage(IDiyMessage.ACTION_SCROLL_AD, banners)
				.sendToTarget();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
		RGetYourFav product = (RGetYourFav) mRecommendAdapter.getItem(position);
		intent.putExtra(IntentValues.TO_PRODUCT_DETAILS_KEY,
				product.getProductId());
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.search_et) {
			Intent intent = new Intent(getActivity(),
					ProductSearchActivity.class);
			startActivity(intent);
		} else {
			showBuidingTip();
		}
	}

	private void handleScrollAd(Message msg) {
		ArrayList<RBanner> banners = (ArrayList<RBanner>) msg.obj;
		int position = mAdVp.getCurrentItem();
		++position;
		if (position > banners.size() - 1) {
			position = 0;
		}
		mAdVp.setCurrentItem(position);

		for (int i = 0; i < mAdIndicator.getChildCount(); i++) {
			View childView = mAdIndicator.getChildAt(i);
			childView.setEnabled(i == position);
		}
	}

}
