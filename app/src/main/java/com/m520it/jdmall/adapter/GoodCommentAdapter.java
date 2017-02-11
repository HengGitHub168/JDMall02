package com.m520it.jdmall.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.image.SmartImageView;
import com.m520it.jdmall.R;
import com.m520it.jdmall.bean.RProductComment;
import com.m520it.jdmall.cons.NetworkConst;
import com.m520it.jdmall.view.RatingBar;

public class GoodCommentAdapter extends BaseAdapter {

	private Context mContext;
	private List<RProductComment> mDatas = new ArrayList<RProductComment>();

	public GoodCommentAdapter(Context c) {
		mContext = c;
	}

	public void setDatas(List<RProductComment> datas) {
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

	class ViewHolder {
		public RatingBar mRatingBar;
		public TextView mUserNameTv;
		public TextView mContentTv;
		public LinearLayout mImageContainer;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.recommend_comment_item_view, null);
			holder = new ViewHolder();
			holder.mUserNameTv = (TextView) convertView
					.findViewById(R.id.name_tv);
			holder.mRatingBar = (RatingBar) convertView
					.findViewById(R.id.rating_bar);
			holder.mContentTv = (TextView) convertView
					.findViewById(R.id.content_tv);
			holder.mImageContainer = (LinearLayout) convertView
					.findViewById(R.id.iamges_container);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		RProductComment bean = mDatas.get(position);
		holder.mUserNameTv.setText(bean.getUserName());
		holder.mRatingBar.setRating(bean.getRate());
		holder.mContentTv.setText(bean.getComment());
		initImageContainer(holder.mImageContainer, bean.getImgUrls());
		return convertView;
	}

	private void initImageContainer(LinearLayout mImageContainer,String imageJson) {
		List<String> imageUrls = JSON.parseArray(imageJson, String.class);
		mImageContainer.setVisibility(imageUrls.size() != 0 ? View.VISIBLE
				: View.GONE);
		for (int i = 0; i < imageUrls.size(); i++) {
			SmartImageView childView = (SmartImageView) mImageContainer
					.getChildAt(i);
			childView.setImageUrl(NetworkConst.DOMAIN + imageUrls.get(i));
		}
	}

}
