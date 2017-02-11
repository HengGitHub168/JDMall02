package com.m520it.jdmall.frag;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.m520it.jdmall.R;
import com.m520it.jdmall.activity.ProductDetailsActivity;
import com.m520it.jdmall.adapter.CommentAdapter;
import com.m520it.jdmall.bean.RCommentCount;
import com.m520it.jdmall.bean.RCommentDetails;
import com.m520it.jdmall.bean.SProductList;
import com.m520it.jdmall.cons.IDiyMessage;
import com.m520it.jdmall.contrller.ProductDetailsController;
import com.m520it.jdmall.listener.IModelChangeListener;

public class ProductCommentView extends Fragment implements OnClickListener, IModelChangeListener{
	
	private TextView mAllCommentTv;
	private TextView mAllCommentTip;
	private TextView mPositiveCommentTv;
	private TextView mPositiveCommentTip;
	private TextView mCenterCommentTv;
	private TextView mCenterCommentTip;
	private TextView mNegativeCommentTv;
	private TextView mNegativeCommentTip;
	private TextView mHasImageCommentTv;
	private TextView mHasImageCommentTip;
	private ListView mCommentLv;
	private CommentAdapter mCommentAdapter;
	
	public static final int ALL_COMMENT_TYPE=0;
	public static final int GOOD_COMMENT_TYPE=1;
	public static final int CENTER_COMMENT_TYPE=2;
	public static final int NEGATIVE_COMMENT_TYPE=3;
	public static final int HASIMG_COMMENT_TYPE=4;
	
	private String mProductId;
	private ProductDetailsController mController;
	private Handler mHandler=new Handler(){
		
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case IDiyMessage.ACTION_RECOMMEND_COUNT_RESULT:
					handleLoadCommentCount((RCommentCount)msg.obj);
					break;
				case IDiyMessage.ACTION_GET_COMMENT_WITH_TYPE_RESULT:
					handlGetCommentWithType((List<RCommentDetails>) msg.obj);
					break;
			}
		}
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.product_comment_view,null);
	}
	
	protected void handlGetCommentWithType(List<RCommentDetails> datas) {
		mCommentAdapter.setDatas(datas);
		mCommentAdapter.notifyDataSetChanged();
	}

	protected void handleLoadCommentCount(RCommentCount commentCount) {
		mAllCommentTv.setText(commentCount.getAllComment()+"");
		mPositiveCommentTv.setText(commentCount.getPositiveCom()+"");
		mCenterCommentTv.setText(commentCount.getModerateCom()+"");
		mNegativeCommentTv.setText(commentCount.getNegativeCom()+"");
		mHasImageCommentTv.setText(commentCount.getHasImgCom()+"");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		mProductId=((ProductDetailsActivity)getActivity()).mProductId;
		mController=new ProductDetailsController(getActivity());
		mController.setIModelChangeListener(this);
		
		mAllCommentTv=(TextView) getActivity().findViewById(R.id.all_comment_tv);
		mPositiveCommentTv=(TextView) getActivity().findViewById(R.id.positive_comment_tv);
		mCenterCommentTv=(TextView) getActivity().findViewById(R.id.center_comment_tv);
		mNegativeCommentTv=(TextView) getActivity().findViewById(R.id.nagetive_comment_tv);
		mHasImageCommentTv=(TextView) getActivity().findViewById(R.id.has_image_comment_tv);
		
		mAllCommentTip=(TextView) getActivity().findViewById(R.id.all_comment_tip);
		mPositiveCommentTip=(TextView) getActivity().findViewById(R.id.positive_comment_tip);
		mCenterCommentTip=(TextView) getActivity().findViewById(R.id.center_comment_tip);
		mNegativeCommentTip=(TextView) getActivity().findViewById(R.id.nagetive_comment_tip);
		mHasImageCommentTip=(TextView) getActivity().findViewById(R.id.has_image_comment_tip);
		
		getActivity().findViewById(R.id.all_comment_ll).setOnClickListener(this);
		getActivity().findViewById(R.id.positive_comment_ll).setOnClickListener(this);
		getActivity().findViewById(R.id.center_comment_ll).setOnClickListener(this);
		getActivity().findViewById(R.id.nagetive_comment_ll).setOnClickListener(this);
		getActivity().findViewById(R.id.has_image_comment_ll).setOnClickListener(this);
		mCommentLv=(ListView) getActivity().findViewById(R.id.lv);
		mCommentAdapter=new CommentAdapter(getActivity());
		mCommentLv.setAdapter(mCommentAdapter);
		
		mController.sendAsyncMessage(IDiyMessage.ACTION_RECOMMEND_COUNT,mProductId);
		
		getActivity().findViewById(R.id.all_comment_ll).performClick();
		
	}

	@Override
	public void onClick(View v) {
		resetCommentTitleStyle();
		switch (v.getId()) {
		case R.id.all_comment_ll:
			mAllCommentTv.setSelected(true);
			mAllCommentTip.setSelected(true);
			mController.sendAsyncMessage(IDiyMessage.ACTION_GET_COMMENT_WITH_TYPE, mProductId,ALL_COMMENT_TYPE);
			break;
		case R.id.positive_comment_ll:
			mPositiveCommentTv.setSelected(true);
			mPositiveCommentTip.setSelected(true);
			mController.sendAsyncMessage(IDiyMessage.ACTION_GET_COMMENT_WITH_TYPE, mProductId,GOOD_COMMENT_TYPE);
			break;
		case R.id.center_comment_ll:
			mCenterCommentTv.setSelected(true);
			mCenterCommentTip.setSelected(true);
			mController.sendAsyncMessage(IDiyMessage.ACTION_GET_COMMENT_WITH_TYPE, mProductId,CENTER_COMMENT_TYPE);
			break;
		case R.id.nagetive_comment_ll:
			mNegativeCommentTv.setSelected(true);
			mNegativeCommentTip.setSelected(true);
			mController.sendAsyncMessage(IDiyMessage.ACTION_GET_COMMENT_WITH_TYPE, mProductId,NEGATIVE_COMMENT_TYPE);
			break;
		case R.id.has_image_comment_ll:
			mHasImageCommentTv.setSelected(true);
			mHasImageCommentTip.setSelected(true);
			mController.sendAsyncMessage(IDiyMessage.ACTION_GET_COMMENT_WITH_TYPE, mProductId,HASIMG_COMMENT_TYPE);
			break;
		}
	}
	
	private void resetCommentTitleStyle() {
		mAllCommentTv.setSelected(false);
		mAllCommentTip.setSelected(false);
		mPositiveCommentTv.setSelected(false);
		mPositiveCommentTip.setSelected(false);
		mCenterCommentTv.setSelected(false);
		mCenterCommentTip.setSelected(false);
		mNegativeCommentTv.setSelected(false);
		mNegativeCommentTip.setSelected(false);
		mHasImageCommentTv.setSelected(false);
		mHasImageCommentTip.setSelected(false);
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action,values[0]).sendToTarget();
	}
	
}
