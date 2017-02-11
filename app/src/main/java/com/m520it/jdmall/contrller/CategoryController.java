package com.m520it.jdmall.contrller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall.bean.BrandBean;
import com.m520it.jdmall.bean.RBaseCategory;
import com.m520it.jdmall.bean.RResult;
import com.m520it.jdmall.bean.RSearchProduct;
import com.m520it.jdmall.bean.RSubCategory;
import com.m520it.jdmall.bean.SProductList;
import com.m520it.jdmall.cons.IDiyMessage;
import com.m520it.jdmall.cons.NetworkConst;
import com.m520it.jdmall.util.NetworkUtil;

public class CategoryController extends BaseController {

	public CategoryController(Context c) {
		super(c);
	}

	@Override
	protected void handleMessage(int action, Object[] values) {
		switch (action) {
		case IDiyMessage.ACTION_GET_TOP_CAREGORY:
			mModelChangeListener.onModelChanged(IDiyMessage.ACTION_GET_TOP_CAREGORY_RESULT, loadTopCategory());
			break;
		case IDiyMessage.ACTION_GET_SUB_CAREGORY:
			mModelChangeListener.onModelChanged(IDiyMessage.ACTION_GET_SUB_CAREGORY_RESULT, loadSubCategory((String) values[0]));
			break;
		case IDiyMessage.ACTION_LOAD_BRANDS:
			mModelChangeListener.onModelChanged(IDiyMessage.ACTION_LOAD_BRANDS_RESULT, loadBrandList((String) values[0]));
			break;
		case IDiyMessage.ACTION_GET_PRODUCT_LIST:
			mModelChangeListener.onModelChanged(IDiyMessage.ACTION_GET_PRODUCT_LIST_RESULT, loadProducts((SProductList) values[0]));
			break;
		}
	}

	private List<RSearchProduct> loadProducts(SProductList sProductList) {
		List<RSearchProduct> result =new ArrayList<RSearchProduct>();
		String urlStr=NetworkConst.LOAD_PRODUCT_LIST+"?categoryId="+sProductList.getCategoryId();
		int filterType = sProductList.getFilterType();
		int sortType = sProductList.getSortType();
		int maxPrice = sProductList.getMaxPrice();
		int minPrice = sProductList.getMinPrice();
		int deliverChoose = sProductList.getDeliverChoose();
		String brandId = sProductList.getBrandId();
		if (filterType==1||filterType==2||filterType==3) {
			urlStr+="&filterType="+filterType;
		}
		if (sortType==1||sortType==2||sortType==3) {
			urlStr+="&sortType="+sortType;
		} 
		if(maxPrice!=0&&minPrice!=0){
			urlStr+="&minPrice="+minPrice+"&maxPrice="+maxPrice;
		}
		if (!TextUtils.isEmpty(brandId)) {
			urlStr+="&brandId="+brandId;
		}
		if (deliverChoose!=0) {
			urlStr+="&deliverChoose="+deliverChoose;
		}
		try {
			String resJson = NetworkUtil.doGet(urlStr);
			RResult resBean = JSON.parseObject(resJson,RResult.class);
			if (resBean.isSuccess()) {
				JSONObject jobj=new JSONObject(resBean.getResult());
				result=JSON.parseArray(jobj.getString("rows"),RSearchProduct.class);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	private List<BrandBean> loadBrandList(String categoryId) {
		List<BrandBean> result=new ArrayList<BrandBean>();
		String resJson = NetworkUtil.doGet(NetworkConst.LOAD_BRAND_LIST+categoryId);
		RResult resBean = JSON.parseObject(resJson,RResult.class);
		if (resBean.isSuccess()) {
			result = JSON.parseArray(resBean.getResult(), BrandBean.class);
		}
		return result;
	}

	private List<RSubCategory> loadSubCategory(String parentId) {
		List<RSubCategory> result=new ArrayList<RSubCategory>();
		String resJson = NetworkUtil.doGet(NetworkConst.LOAD_SUB_CATEGORY+parentId);
		RResult resBean = JSON.parseObject(resJson,RResult.class);
		if (resBean.isSuccess()) {
			result=JSON.parseArray(resBean.getResult(),RSubCategory.class);
		}
		return result;
	}

	private List<RBaseCategory> loadTopCategory() {
		List<RBaseCategory> result=new ArrayList<RBaseCategory>();
		String resJson = NetworkUtil.doGet(NetworkConst.LOAD_TOP_CATEGORY);
		RResult resBean = JSON.parseObject(resJson,RResult.class);
		if (resBean.isSuccess()) {
			String beansJson = resBean.getResult();
			result=JSON.parseArray(beansJson,RBaseCategory.class);
		}
		return result;
	}

}
