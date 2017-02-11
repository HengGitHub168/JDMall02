package com.m520it.jdmall.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.m520it.jdmall.R;
import com.m520it.jdmall.bean.RReceiveAddress;
import com.m520it.jdmall.listener.IAddressDeleteListener;

public class ChooseAddressAdapter extends BaseAdapter {

	private Context mContext;
	private List<RReceiveAddress> mDatas=new ArrayList<RReceiveAddress>();
	private IAddressDeleteListener mDeleteListener;

	public void setDeleteListener(IAddressDeleteListener deleteListener) {
		this.mDeleteListener = deleteListener;
	}

	public ChooseAddressAdapter(Context c) {
		mContext=c ;
	}

	public void setBeans(List<RReceiveAddress> datas) {
		mDatas=datas;
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	class ViewHolder{
		public ImageView mDefaultTv;
		public TextView mReceiverNameTv;
		public TextView mReceiverPhoneTv;
		public TextView mReceiverAddressTv;
		public TextView mDeleteTv;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if (convertView==null) {
			convertView=LayoutInflater.from(mContext).inflate(R.layout.choose_address_item_view, null);
			holder=new ViewHolder();
			holder.mDefaultTv=(ImageView) convertView.findViewById(R.id.isDeafult_iv);
			holder.mReceiverNameTv=(TextView) convertView.findViewById(R.id.name_tv);
			holder.mReceiverPhoneTv=(TextView) convertView.findViewById(R.id.phone_tv);
			holder.mReceiverAddressTv=(TextView) convertView.findViewById(R.id.address_tv);
			holder.mDeleteTv=(TextView) convertView.findViewById(R.id.delete_tv);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		final RReceiveAddress address = mDatas.get(position);
		holder.mReceiverNameTv.setText(address.getReceiverName());
		if (address.getReceiverPhone()!=null&&address.getReceiverPhone().length()>5) {
			holder.mReceiverPhoneTv.setText("*****"+address.getReceiverPhone().substring(5));
		}
		holder.mReceiverAddressTv.setText(address.getReceiverAddress());
		holder.mDefaultTv.setVisibility(address.getIsDefault()?View.VISIBLE:View.GONE);
		holder.mDeleteTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mDeleteListener!=null) {
					mDeleteListener.onDelete(address.getId());
				}
			}
		});
		return convertView;
	}



}
