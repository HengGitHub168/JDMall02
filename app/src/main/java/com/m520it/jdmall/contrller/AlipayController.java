package com.m520it.jdmall.contrller;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall.bean.AlipayInfo;
import com.m520it.jdmall.bean.RResult;
import com.m520it.jdmall.cons.IDiyMessage;
import com.m520it.jdmall.cons.NetworkConst;
import com.m520it.jdmall.util.NetworkUtil;

public class AlipayController extends BaseController {


	public AlipayController(Context c) {
		super(c);
	}

	@Override
	protected void handleMessage(int action, Object[] values) {
		switch (action) {
			case IDiyMessage.ACTION_GET_ALIPAY_INFO:
				mModelChangeListener.onModelChanged(IDiyMessage.ACTION_GET_ALIPAY_INFO_RESULT, getAlipayInfo((String)values[0]));
				break;
			case IDiyMessage.ACTION_START_ALIPAY:
				mModelChangeListener.onModelChanged(IDiyMessage.ACTION_START_ALIPAY_RESULT, 
						startAlipay((String)values[0],(String)values[1],(String)values[2],(String)values[3]));
				break;
		}
	}

	private String startAlipay(String account,String pwd,String ppwd,String tn) {
		try {
			String resJson = NetworkUtil.doGet(NetworkConst.START_ALIPAY+"?account="+account+"&apwd="+pwd+"&ppwd="+ppwd+"&tn="+tn+"&userId="+getUserId());
			RResult resBean = JSON.parseObject(resJson,RResult.class);
			if (resBean.isSuccess()) {
				JSONObject jsonObj = new JSONObject(resBean.getResult());
				return jsonObj.getString("oid");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	private AlipayInfo getAlipayInfo(String tn) {
		String resJson = NetworkUtil.doGet(NetworkConst.GET_ALIPAY_INFO+"?tn="+tn+"&userId="+getUserId());
		RResult resBean = JSON.parseObject(resJson,RResult.class);
		if (resBean.isSuccess()) {
			return JSON.parseObject(resBean.getResult(),AlipayInfo.class);
		}
		return null;
	}

}
