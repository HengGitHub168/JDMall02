package com.m520it.jdmall.contrller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall.bean.OrderDetailsBean;
import com.m520it.jdmall.bean.ROrderListBean;
import com.m520it.jdmall.bean.RResult;
import com.m520it.jdmall.cons.IDiyMessage;
import com.m520it.jdmall.cons.NetworkConst;
import com.m520it.jdmall.util.NetworkUtil;

public class OrderController extends BaseController {

	public OrderController(Context c) {
		super(c);
	}

	@Override
	protected void handleMessage(int action, Object[] values) {
		switch (action) {
			case IDiyMessage.ACTION_GET_ALL_ORDER:
				mModelChangeListener.onModelChanged(
						IDiyMessage.ACTION_GET_ALL_ORDER_RESULT, getOrderList(-2));
				break;
			case IDiyMessage.ACTION_GET_WAITPAY_ORDER:
				mModelChangeListener.onModelChanged(
						IDiyMessage.ACTION_GET_WAITPAY_ORDER_RESULT,
						getOrderList(0));
				break;
			case IDiyMessage.ACTION_GET_WAITRECEIVE_ORDER:
				mModelChangeListener.onModelChanged(
						IDiyMessage.ACTION_GET_WAITRECEIVE_ORDER_RESULT,
						getOrderList(2));
				break;
			case IDiyMessage.ACTION_GET_WAITSURE_ORDER:
				mModelChangeListener.onModelChanged(
						IDiyMessage.ACTION_GET_WAITSURE_ORDER_RESULT,
						getOrderList(3));
				break;
			case IDiyMessage.ACTION_GET_ORDER_DETAILS:
				mModelChangeListener.onModelChanged(
						IDiyMessage.ACTION_GET_ORDER_DETAILS_RESULT,
						getOrderDetails((String) values[0]));
				break;
			case IDiyMessage.ACTION_CANCEL_ORDER:
				mModelChangeListener.onModelChanged(
						IDiyMessage.ACTION_CANCEL_ORDER_RESULT,cancelOrder((String) values[0]));
				break;
			case IDiyMessage.ACTION_SURE_ORDER:
				mModelChangeListener.onModelChanged(
						IDiyMessage.ACTION_SURE_ORDER_RESULT,confirmOrder((String) values[0]));
				break;
		}
	}

	private RResult confirmOrder(String oid) {
		String urlPath = NetworkConst.CONFIRM_ORDER+"?userId="+getUserId()+"&oid="+oid;
		String resJson = NetworkUtil.doGet(urlPath);
		return JSON.parseObject(resJson,RResult.class);
	}

	private RResult cancelOrder(String oid) {
		HashMap<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("oid", oid);
		paramsMap.put("userId", getUserId());
		String resJson = NetworkUtil.doPost(NetworkConst.CANCEL_ORDER,
				paramsMap);
		return JSON.parseObject(resJson, RResult.class);
	}

	private List<ROrderListBean> getOrderList(int index) {
		List<ROrderListBean> beans = new ArrayList<ROrderListBean>();
		String urlPath = NetworkConst.GET_ORDER_LIST + "?userId=" + getUserId();
		urlPath += index >= -1 ? ("&status=" + index) : "";
		String resJson = NetworkUtil.doGet(urlPath);
		RResult resBean = JSON.parseObject(resJson, RResult.class);
		if (resBean.isSuccess()) {
			beans = JSON.parseArray(resBean.getResult(), ROrderListBean.class);
		}
		return beans;
	}

	private OrderDetailsBean getOrderDetails(String orderId) {
		String resJson = NetworkUtil.doGet(NetworkConst.GET_ORDER_DETAILS
				+ orderId);
		System.out.println("orderId= "+NetworkConst.GET_ORDER_DETAILS
				+ orderId);
		System.out.println("resJson"+resJson);
		RResult resBean = JSON.parseObject(resJson, RResult.class);
		if (resBean.isSuccess()) {
			return JSON
					.parseObject(resBean.getResult(), OrderDetailsBean.class);
		}
		return null;
	}

}
