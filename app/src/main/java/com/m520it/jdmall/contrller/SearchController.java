package com.m520it.jdmall.contrller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall.bean.RResult;
import com.m520it.jdmall.bean.RSearchProduct;
import com.m520it.jdmall.cons.IDiyMessage;
import com.m520it.jdmall.cons.NetworkConst;
import com.m520it.jdmall.util.NetworkUtil;

import android.content.Context;

public class SearchController extends BaseController {

	public SearchController(Context c) {
		super(c);
	}

	@Override
	protected void handleMessage(int action, Object[] values) {
		switch (action) {
		case IDiyMessage.ACTION_SEARCH_PRODUCT:
			mModelChangeListener.onModelChanged(IDiyMessage.ACTION_SEARCH_PRODUCT_RESULT,
					loadSearchProduct((String)values[0]));
			break;
		}
	}

	private List<RSearchProduct> loadSearchProduct(String keyword) {
		List<RSearchProduct> result=new ArrayList<RSearchProduct>();
		try {
			Map<String, String> paramsMap=new HashMap<String, String>();
			paramsMap.put("keyword", keyword);
			String resJson = NetworkUtil.doPost(NetworkConst.GET_SEARCH_PRODUCTLIST,paramsMap);
			RResult resBean = JSON.parseObject(resJson,RResult.class);
			if (resBean.isSuccess()) {
				JSONObject jsonObject = new JSONObject(resBean.getResult());
				result=JSON.parseArray(jsonObject.getString("rows"),RSearchProduct.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
