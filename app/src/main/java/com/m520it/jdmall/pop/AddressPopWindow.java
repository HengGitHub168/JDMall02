package com.m520it.jdmall.pop;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.m520it.jdmall.R;
import com.m520it.jdmall.bean.ProvinceCityArea;
import com.m520it.jdmall.cons.IDiyMessage;
import com.m520it.jdmall.contrller.ShopcarController;
import com.m520it.jdmall.listener.IAddressPopListener;
import com.m520it.jdmall.listener.IModelChangeListener;

public class AddressPopWindow implements IPopWindow, OnClickListener, IModelChangeListener, OnItemClickListener{
	
	private PopupWindow mPw;
	private View mContentView;
	private ListView mProvinceLv;
	private ListView mAreaLv;
	private ListView mCityLv;
	private IAddressPopListener mListener;
	private List<ProvinceCityArea> mProvinces;
	private List<ProvinceCityArea> mCities;
	private List<ProvinceCityArea> mAreas;
	public ProvinceCityArea mProvince,mCity,mArea;
	private ShopcarController mController;
	private Context mContext;
	private Handler mHandler=new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case IDiyMessage.ACTION_GET_PROVINCE_RESULT:
					handleProvinceDatas((List<ProvinceCityArea>)msg.obj);
					break;
				case IDiyMessage.ACTION_GET_CITY_RESULT:
					handleCityDatas((List<ProvinceCityArea>)msg.obj);
					break;
				case IDiyMessage.ACTION_GET_AREA_RESULT:
					handleAreaDatas((List<ProvinceCityArea>)msg.obj);
					break;
			}
		}
		
	};

	public void setListener(IAddressPopListener mListener) {
		this.mListener = mListener;
	}

	protected void handleAreaDatas(List<ProvinceCityArea> datas) {
		mAreas=datas;
		ArrayList<String> objects=new ArrayList<String>();
		for (int i = 0; i < mAreas.size(); i++) {
			objects.add(mAreas.get(i).getName());
		}
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,
				android.R.id.text1, objects);
		mAreaLv.setAdapter(adapter);
	}

	protected void handleCityDatas(List<ProvinceCityArea> datas) {
		mCities=datas;
		ArrayList<String> objects=new ArrayList<String>();
		for (int i = 0; i < mCities.size(); i++) {
			objects.add(mCities.get(i).getName());
		}
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,
				android.R.id.text1, objects);
		mCityLv.setAdapter(adapter);
	}

	protected void handleProvinceDatas(List<ProvinceCityArea> datas) {
		mProvinces=datas;
		ArrayList<String> objects=new ArrayList<String>();
		for (int i = 0; i < mProvinces.size(); i++) {
			objects.add(mProvinces.get(i).getName());
		}
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,
				android.R.id.text1, objects);
		mProvinceLv.setAdapter(adapter);
	}

	public AddressPopWindow(Context c) {
		mContext=c;
		initView(c);
	}
	
	@Override
	public void onShow(View anchor) {
		if (!mPw.isShowing()) {
			mPw.showAtLocation(anchor, Gravity.BOTTOM, 0, 0);
		}
	}

	@Override
	public void dismiss() {
		if (mPw.isShowing()) {
			mPw.dismiss();
		}
	}

	@Override
	public void initView(Context c) {
		mController=new ShopcarController(c);
		mController.setIModelChangeListener(this);
		
		mContentView=LayoutInflater.from(c).inflate(R.layout.address_pop_view, null);
		mContentView.findViewById(R.id.left_v).setOnClickListener(this);
		mContentView.findViewById(R.id.submit_tv).setOnClickListener(this);
		mProvinceLv=(ListView)mContentView.findViewById(R.id.province_lv);
		mProvinceLv.setOnItemClickListener(this);
		mCityLv=(ListView)mContentView.findViewById(R.id.city_lv);
		mCityLv.setOnItemClickListener(this);
		mAreaLv=(ListView)mContentView.findViewById(R.id.dist_lv);
		mAreaLv.setOnItemClickListener(this);
		mPw=new PopupWindow(mContentView,-1,-2);
		mPw.setFocusable(true);
		mPw.setOutsideTouchable(true);
		mPw.setBackgroundDrawable(new BitmapDrawable());
		mPw.update();
		
		mController.sendAsyncMessage(IDiyMessage.ACTION_GET_PROVINCE, 0);
	}

	@Override
	public void onClick(View v) {
		if (mListener!=null) {
			mListener.onChooseFinish();
		}
		dismiss();
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action,values[0]).sendToTarget();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (parent==mProvinceLv) {
			mProvince = mProvinces.get(position);
			mCity=null;
			mArea=null;
			mController.sendAsyncMessage(IDiyMessage.ACTION_GET_CITY, mProvince.getCode());
		}else if (parent==mCityLv) {
			mCity = mCities.get(position);
			mArea=null;
			mController.sendAsyncMessage(IDiyMessage.ACTION_GET_AREA, mCity.getCode());
		}else if (parent==mAreaLv) {
			mArea = mAreas.get(position);
		}
	}
	
}
