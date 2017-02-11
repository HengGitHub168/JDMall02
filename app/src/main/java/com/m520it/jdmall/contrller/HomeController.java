package com.m520it.jdmall.contrller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall.bean.RBanner;
import com.m520it.jdmall.bean.RGetYourFav;
import com.m520it.jdmall.bean.RResult;
import com.m520it.jdmall.bean.RSecKill;
import com.m520it.jdmall.cons.IDiyMessage;
import com.m520it.jdmall.cons.NetworkConst;
import com.m520it.jdmall.util.NetworkUtil;

public class HomeController extends BaseController {

	public HomeController(Context c) {
		super(c);
	}

	@Override
	protected void handleMessage(int action, Object[] values) {
		switch (action) {
			case IDiyMessage.ACTION_LOAD_AD1:
				mModelChangeListener.onModelChanged(IDiyMessage.ACTION_LOAD_AD1_RESULT, loadAd(NetworkConst.LOAD_AD_1));
				break;
			case IDiyMessage.ACTION_LOAD_AD2:
				mModelChangeListener.onModelChanged(IDiyMessage.ACTION_LOAD_AD2_RESULT, loadAd(NetworkConst.LOAD_AD_2));
				break;
			case IDiyMessage.ACTION_SECKILL:
				mModelChangeListener.onModelChanged(IDiyMessage.ACTION_SECKILL_RESULT, loadSenKill());
				break;
			case IDiyMessage.ACTION_RECOMMEND:
				mModelChangeListener.onModelChanged(IDiyMessage.ACTION_RECOMMEND_RESULT, loadRecommendProduct());
				break;
		}
		
	}

	private List<RGetYourFav> loadRecommendProduct() {
		List<RGetYourFav> result = new ArrayList<RGetYourFav>();
		try {
			String resJson = NetworkUtil.doGet(NetworkConst.LOAD_RECOMMEND_PRODUCT);
			RResult resBean=JSON.parseObject(resJson,RResult.class);
			if (resBean.isSuccess()) {
				JSONObject jsonObject = new JSONObject(resBean.getResult());
				String childBeanJson =jsonObject.getString("rows");
				result=JSON.parseArray(childBeanJson,RGetYourFav.class);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	private List<RSecKill> loadSenKill() {
		List<RSecKill> result = new ArrayList<RSecKill>();
		try {
			String resJson = NetworkUtil.doGet(NetworkConst.LOAD_SECKILL);
			RResult resBean=JSON.parseObject(resJson,RResult.class);
			if (resBean.isSuccess()) {
				JSONObject jsonObject = new JSONObject(resBean.getResult());
				String childBeanJson =jsonObject.getString("rows");
				result=JSON.parseArray(childBeanJson,RSecKill.class);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	private List<RBanner> loadAd(String url) {
		List<RBanner> result = new ArrayList<RBanner>();
		String resJson = NetworkUtil.doGet(url);
		RResult resultBean=JSON.parseObject(resJson,RResult.class);
		if (resultBean.isSuccess()) {
			result=JSON.parseArray(resultBean.getResult(),RBanner.class);
		}
		return result;
	}

}
