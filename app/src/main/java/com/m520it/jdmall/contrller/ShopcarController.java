package com.m520it.jdmall.contrller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall.bean.ProvinceCityArea;
import com.m520it.jdmall.bean.RBuildedOrder;
import com.m520it.jdmall.bean.RReceiveAddress;
import com.m520it.jdmall.bean.RResult;
import com.m520it.jdmall.bean.RShopCarList;
import com.m520it.jdmall.bean.SMakeSureOrder;
import com.m520it.jdmall.bean.SReceiverCreate;
import com.m520it.jdmall.cons.IDiyMessage;
import com.m520it.jdmall.cons.NetworkConst;
import com.m520it.jdmall.util.NetworkUtil;

public class ShopcarController extends BaseController {

	public ShopcarController(Context c) {
		super(c);
	}

	@Override
	protected void handleMessage(int action, Object[] values) {
		switch (action) {
			case IDiyMessage.ACTION_GET_SHOPCARS:
				mModelChangeListener.onModelChanged(IDiyMessage.ACTION_GET_SHOPCARS_RESULT,loadShopcars());
				break;
			case IDiyMessage.ACTION_GET_ADDRESS_LIST:
				mModelChangeListener.onModelChanged(IDiyMessage.ACTION_GET_ADDRESS_LIST_RESULT, loadAdressList());
				break;
			case IDiyMessage.ACTION_GET_DEFAULT_ADDRESS:
				mModelChangeListener.onModelChanged(IDiyMessage.ACTION_GET_DEFAULT_ADDRESS_RESULT, loadDefaultAddress());
				break;
			case IDiyMessage.ACTION_GET_PROVINCE:
				mModelChangeListener.onModelChanged(IDiyMessage.ACTION_GET_PROVINCE_RESULT, getProvince());
				break;
			case IDiyMessage.ACTION_GET_CITY:
				mModelChangeListener.onModelChanged(IDiyMessage.ACTION_GET_CITY_RESULT, getCity((String) values[0]));
				break;
			case IDiyMessage.ACTION_GET_AREA:
				mModelChangeListener.onModelChanged(IDiyMessage.ACTION_GET_AREA_RESULT, getArea((String) values[0]));
				break;
			case IDiyMessage.ACTION_ADD_ADDRESS:
				mModelChangeListener.onModelChanged(IDiyMessage.ACTION_ADD_ADDRESS_RESULT,
						addAddress((SReceiverCreate) values[0]));
				break;
			case IDiyMessage.ACTION_DELETE_ADDRESS:
				mModelChangeListener.onModelChanged(IDiyMessage.ACTION_DELETE_ADDRESS_RESULT,
						deleteAddress((String) values[0]));
				break;
			case IDiyMessage.ACTION_DELETE_SHOPCAR_PRODUCT:
				mModelChangeListener.onModelChanged(IDiyMessage.ACTION_DELETE_SHOPCAR_PRODUCT_RESULT,
						deleteShopcarProduct((String) values[0]));
				break;
			case IDiyMessage.ACTION_MAKESURE_ORDER:
				mModelChangeListener.onModelChanged(IDiyMessage.ACTION_MAKESURE_ORDER_RESULT,
						buildOrder((SMakeSureOrder) values[0]));
				break;
		}
	}

	private RBuildedOrder buildOrder(SMakeSureOrder sMakeSureOrder) {
		sMakeSureOrder.setUserId(getUserId());
		HashMap<String, String> paramsMap=new HashMap<String, String>();
		paramsMap.put("detail", JSON.toJSONString(sMakeSureOrder));
		String resJson = NetworkUtil.doPost(NetworkConst.BUILD_ORDER, paramsMap);
		RResult resBean = JSON.parseObject(resJson,RResult.class);
		if (resBean.isSuccess()) {
			return JSON.parseObject(resBean.getResult(),RBuildedOrder.class);
		}
		return null;
	}

	private RResult deleteShopcarProduct(String pid) {
		String urlPath = NetworkConst.DELETE_SHOPCAR_PRODUCT+"?userId="+getUserId()+"&id="+pid;
		String resJson = NetworkUtil.doGet(urlPath);
		return JSON.parseObject(resJson,RResult.class);
	}

	private RResult deleteAddress(String id) {
		String resJson = NetworkUtil.doGet(NetworkConst.DELETE_ADDRESS+id);
		return JSON.parseObject(resJson,RResult.class);
	}

	private RReceiveAddress addAddress(SReceiverCreate sReceiverCreate) {
		String urlStr=NetworkConst.ADD_ADDRESS;
		Map<String, String> map=new HashMap<String, String>();
		map.put("userId", getUserId());
		map.put("name", sReceiverCreate.getName());
		map.put("phone", sReceiverCreate.getPhone());
		map.put("provinceName", sReceiverCreate.getProvinceName());
		map.put("provinceCode", sReceiverCreate.getProvinceCode());
		map.put("cityName", sReceiverCreate.getCityName());
		map.put("cityCode", sReceiverCreate.getCityCode());
		map.put("distName", sReceiverCreate.getDistName());
		map.put("distCode", sReceiverCreate.getDistCode());
		map.put("addressDetails", sReceiverCreate.getAddressDetails());
		map.put("isDefault", sReceiverCreate.isDefault()+"");
		String resJson = NetworkUtil.doPost(urlStr,map);
		RResult resBean = JSON.parseObject(resJson,RResult.class);
		if (resBean.isSuccess()) {
			return JSON.parseObject(resBean.getResult(),RReceiveAddress.class);
		}
		return null;
	}

	private List<ProvinceCityArea> getArea(String code) {
		List<ProvinceCityArea> result=new ArrayList<ProvinceCityArea>();
		String resJson = NetworkUtil.doGet(NetworkConst.GET_AREA+code);
		RResult resBean = JSON.parseObject(resJson,RResult.class);
		if (resBean.isSuccess()) {
			result=JSON.parseArray(resBean.getResult(),ProvinceCityArea.class);
		}
		return result;
	}

	private List<ProvinceCityArea> getCity(String code) {
		List<ProvinceCityArea> result=new ArrayList<ProvinceCityArea>();
		String resJson = NetworkUtil.doGet(NetworkConst.GET_CITY+code);
		RResult resBean = JSON.parseObject(resJson,RResult.class);
		if (resBean.isSuccess()) {
			result=JSON.parseArray(resBean.getResult(),ProvinceCityArea.class);
		}
		return result;
	}

	private List<ProvinceCityArea> getProvince() {
		List<ProvinceCityArea> result=new ArrayList<ProvinceCityArea>();
		String resJson = NetworkUtil.doGet(NetworkConst.GET_PROVINCE);
		RResult resBean = JSON.parseObject(resJson,RResult.class);
		if (resBean.isSuccess()) {
			result=JSON.parseArray(resBean.getResult(),ProvinceCityArea.class);
		}
		return result;
	}

	private List<RReceiveAddress> loadDefaultAddress() {
		List<RReceiveAddress> result=new ArrayList<RReceiveAddress>();
		String resJson = NetworkUtil.doGet(NetworkConst.GET_RECEIVER_ADDRESS+getUserId()+"&isDefault=true");
		RResult resBean = JSON.parseObject(resJson,RResult.class);
		if (resBean.isSuccess()) {
			result=JSON.parseArray(resBean.getResult(),RReceiveAddress.class);
		}
		return result;
	}
	
	private List<RReceiveAddress> loadAdressList() {
		List<RReceiveAddress> result=new ArrayList<RReceiveAddress>();
		String resJson = NetworkUtil.doGet(NetworkConst.GET_RECEIVER_ADDRESS+getUserId());
		RResult resBean = JSON.parseObject(resJson,RResult.class);
		if (resBean.isSuccess()) {
			result=JSON.parseArray(resBean.getResult(),RReceiveAddress.class);
		}
		return result;
	}

	private List<RShopCarList> loadShopcars() {
		List<RShopCarList> result=new ArrayList<RShopCarList>();
		String resJson = NetworkUtil.doGet(NetworkConst.LOAD_SHOPCARS+getUserId());
		RResult resBean = JSON.parseObject(resJson,RResult.class);
		if (resBean.isSuccess()) {
			result=JSON.parseArray(resBean.getResult(),RShopCarList.class);
		}
		return result;
	}

}
