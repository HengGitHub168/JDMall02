package com.m520it.jdmall.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.image.SmartImageView;
import com.m520it.jdmall.R;
import com.m520it.jdmall.bean.RCommentDetails;
import com.m520it.jdmall.cons.NetworkConst;
import com.m520it.jdmall.view.RatingBar;

public class CommentAdapter extends BaseAdapter {

	private Context mContext;
	private List<RCommentDetails> mDatas=new ArrayList<RCommentDetails>();

	public CommentAdapter(Context c) {
		mContext=c;
	}
	
	public void setDatas(List<RCommentDetails> datas) {
		this.mDatas = datas;
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}
	
	class ViewHolder{
		public SmartImageView mUserIconIv;
		public TextView mUserNameTv;
		public TextView mCommentTimeTv;
		public RatingBar mRatingBar;
		public LinearLayout mImageContainer;
		public TextView mContentTv;
		public TextView mBuyTimeTv;
		public TextView mProductVersionTv;
		public TextView mLoveCountTv;
		public TextView mSubCommentTv;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder=null;
		if (convertView==null) {
			convertView=LayoutInflater.from(mContext).inflate(R.layout.comment_item_view, null);
			holder=new ViewHolder();
			holder.mUserIconIv=(SmartImageView) convertView.findViewById(R.id.icon_iv);
			holder.mUserNameTv=(TextView) convertView.findViewById(R.id.name_tv);
			holder.mCommentTimeTv=(TextView) convertView.findViewById(R.id.time_tv);
			holder.mRatingBar=(RatingBar) convertView.findViewById(R.id.rating_bar);
			holder.mImageContainer=(LinearLayout) convertView.findViewById(R.id.iamges_container);
			holder.mContentTv=(TextView) convertView.findViewById(R.id.content_tv);
			holder.mBuyTimeTv=(TextView) convertView.findViewById(R.id.buytime_tv);
			holder.mProductVersionTv=(TextView) convertView.findViewById(R.id.buyversion_tv);
			holder.mLoveCountTv=(TextView) convertView.findViewById(R.id.lovecount_tv);
			holder.mSubCommentTv=(TextView) convertView.findViewById(R.id.subcomment_tv);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		RCommentDetails bean = mDatas.get(position);
		holder.mUserIconIv.setImageUrl(NetworkConst.DOMAIN+mDatas.get(position).getUserImg());
		holder.mUserNameTv.setText(bean.getUserName());
		holder.mCommentTimeTv.setText(bean.getCommentTime());
		holder.mRatingBar.setRating(bean.getRate());
		holder.mContentTv.setText(bean.getComment());
		holder.mBuyTimeTv.setText("购买日期:"+bean.getBuyTime());
		holder.mProductVersionTv.setText("版本:"+bean.getProductType());
		holder.mLoveCountTv.setText("喜欢("+bean.getLoveCount()+")");
		holder.mSubCommentTv.setText("回复("+bean.getSubComment()+")");
		initImageContainer(holder.mImageContainer,bean.getImgUrls());
		return convertView;
	}
	
	private void initImageContainer(LinearLayout mImageContainer, String imageJson){
		List<String> imageUrls=JSON.parseArray(imageJson,String.class);
		mImageContainer.setVisibility(imageUrls.size() != 0 ? View.VISIBLE
				: View.GONE);
		for (int i = 0; i < imageUrls.size(); i++) {
			SmartImageView childView = (SmartImageView) mImageContainer
					.getChildAt(i);
			childView.setImageUrl(NetworkConst.DOMAIN + imageUrls.get(i));
		}
	}

}
