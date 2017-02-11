package com.m520it.jdmall.contrller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall.bean.RCommentCount;
import com.m520it.jdmall.bean.RCommentDetails;
import com.m520it.jdmall.bean.RProductComment;
import com.m520it.jdmall.bean.RProductInfo;
import com.m520it.jdmall.bean.RResult;
import com.m520it.jdmall.cons.IDiyMessage;
import com.m520it.jdmall.cons.NetworkConst;
import com.m520it.jdmall.frag.ProductCommentView;
import com.m520it.jdmall.util.NetworkUtil;

public class ProductDetailsController extends BaseController {

	public ProductDetailsController(Context c) {
		super(c);
	}

	@Override
	protected void handleMessage(int action, Object[] values) {
		switch (action) {
		case IDiyMessage.ACTION_GET_PRODUCT_INTRODUCE:
			mModelChangeListener.onModelChanged(
					IDiyMessage.ACTION_GET_PRODUCT_INTRODUCE_RESULT,
					getProductIntroduce((String) values[0]));
			break;
		case IDiyMessage.ACTION_GET_RECOMMEND_GOOD_COMMENT:
			mModelChangeListener.onModelChanged(
					IDiyMessage.ACTION_GET_RECOMMEND_GOOD_COMMENT_RESULT,
					getRecommendComment((String) values[0]));
			break;
		case IDiyMessage.ACTION_RECOMMEND_COUNT:
			mModelChangeListener.onModelChanged(
					IDiyMessage.ACTION_RECOMMEND_COUNT_RESULT,
					getCommentCount((String) values[0]));
			break;
		case IDiyMessage.ACTION_GET_COMMENT_WITH_TYPE:
			mModelChangeListener
					.onModelChanged(
							IDiyMessage.ACTION_GET_COMMENT_WITH_TYPE_RESULT,
							getCommentWithType((String) values[0],(Integer) values[1]));
			break;
		case IDiyMessage.ACTION_ADD2SHOPCAR:
			mModelChangeListener.onModelChanged(
					IDiyMessage.ACTION_ADD2SHOPCAR_RESULT,add2Shopcar((String) values[0],(Integer) values[1], (String) values[2]));
			break;
		}
	}

	private RResult add2Shopcar(String productId,int buyCount,String type) {
		HashMap<String, String> params=new HashMap<String, String>();
		params.put("productId", productId);
		params.put("userId", getUserId());
		params.put("buyCount", buyCount+"");
		params.put("pversion", type);
		String resJson = NetworkUtil.doPost(NetworkConst.ADD_2_SHOPCAR,params);
		return JSON.parseObject(resJson,RResult.class);
	}

	private List<RCommentDetails> getCommentWithType(String productId, int type) {
		List<RCommentDetails> result = new ArrayList<RCommentDetails>();
		String url = NetworkConst.LOAD_COMMENT_WITH_TYPE + "productId="
				+ productId;
		if (type != ProductCommentView.HASIMG_COMMENT_TYPE) {
			url += "&type=" + type;
		} else {
			url += "&type=" + ProductCommentView.ALL_COMMENT_TYPE
					+ "&hasImgCom=" + true;
		}
		String resJson = NetworkUtil.doGet(url);
		RResult resBean = JSON.parseObject(resJson, RResult.class);
		if (resBean.isSuccess()) {
			return JSON.parseArray(resBean.getResult(), RCommentDetails.class);
		}
		return result;
	}

	private RCommentCount getCommentCount(String productId) {
		String resJson = NetworkUtil.doGet(NetworkConst.LOAD_COMMENT_COUNT
				+ productId);
		RResult resBean = JSON.parseObject(resJson, RResult.class);
		if (resBean.isSuccess()) {
			return JSON.parseObject(resBean.getResult(), RCommentCount.class);
		}
		return null;
	}

	private RProductInfo getProductIntroduce(String productId) {
		String resJson = NetworkUtil.doGet(NetworkConst.LOAD_PRODUCT_INTRODUCE
				+ productId);
		RResult resBean = JSON.parseObject(resJson, RResult.class);
		if (resBean.isSuccess()) {
			return JSON.parseObject(resBean.getResult(), RProductInfo.class);
		}
		return null;
	}

	private List<RProductComment> getRecommendComment(String productId) {
		List<RProductComment> result = new ArrayList<RProductComment>();
		String resJson = NetworkUtil.doGet(NetworkConst.LOAD_RECOMMEND_COMMENT
				+ productId);
		RResult resBean = JSON.parseObject(resJson, RResult.class);
		if (resBean.isSuccess()) {
			return JSON.parseArray(resBean.getResult(), RProductComment.class);
		}
		return result;
	}

}
