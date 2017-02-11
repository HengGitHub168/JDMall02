package com.m520it.jdmall.cons;

public class NetworkConst {
	
	public static final String DOMAIN = "http://192.168.1.101:8080";
//	public static final String DOMAIN = "http://mall.520it.com";
	
	public static final String LOGIN = DOMAIN+"/login?";
	
	public static final String REGIST = DOMAIN+"/regist?";
	
	public static final String RESET = DOMAIN+"/reset?";

	public static final String LOAD_AD_1 = DOMAIN+"/banner?adKind=1";
	
	public static final String LOAD_AD_2 = DOMAIN+"/banner?adKind=2";
	
	public static final String LOAD_SECKILL = DOMAIN+"/seckill";
	
	public static final String LOAD_RECOMMEND_PRODUCT = DOMAIN+"/getYourFav";
	
	public static final String LOAD_TOP_CATEGORY = DOMAIN+"/category";
	
	public static final String LOAD_SUB_CATEGORY = DOMAIN+"/category?parentId=";
	
	public static final String LOAD_BRAND_LIST = DOMAIN+"/brand?categoryId=";
	
	public static final String LOAD_PRODUCT_LIST = DOMAIN+"/searchProduct";
	
	public static final String LOAD_PRODUCT_INTRODUCE = DOMAIN+"/productInfo?id=";
	
	public static final String LOAD_RECOMMEND_COMMENT = DOMAIN+"/productComment?type=1&startIndex=0&endIndex=10&productId=";
	
	public static final String LOAD_PRODUCTDETAILS_URL = DOMAIN+"/productDetail?productId=";
	
	public static final String LOAD_COMMENT_COUNT = DOMAIN+"/commentCount?productId=";
	
	public static final String LOAD_COMMENT_WITH_TYPE = DOMAIN+"/commentDetail?";
	
	public static final String LOAD_SHOPCARS = DOMAIN+"/shopCar?userId=";
	
	public static final String ADD_2_SHOPCAR = DOMAIN+"/toShopCar";
	
	public static final String GET_RECEIVER_ADDRESS = DOMAIN+"/receiveAddress?userId=";
	
	public static final String GET_PROVINCE = DOMAIN+"/province";
	
	public static final String GET_CITY = DOMAIN+"/city?fcode=";
	
	public static final String GET_AREA = DOMAIN+"/area?fcode=";
	
	public static final String ADD_ADDRESS = DOMAIN+"/addAddress";
	
	public static final String GET_SEARCH_PRODUCTLIST = DOMAIN+"/searchProduct";
	
	public static final String DELETE_ADDRESS = DOMAIN+"/delAddress?id=";
	
	public static final String DELETE_SHOPCAR_PRODUCT = DOMAIN+"/delShopCar";
	
	public static final String GET_ORDER_LIST = DOMAIN+"/getOrderByStatus";
	
	public static final String BUILD_ORDER = DOMAIN+"/addOrder";
	
	public static final String GET_ORDER_DETAILS = DOMAIN+"/getOrderDetail?id=";
	
	public static final String CANCEL_ORDER = DOMAIN+"/cancelOrder";
	
	public static final String CONFIRM_ORDER = DOMAIN+"/confirmOrder";
	
	public static final String GET_ALIPAY_INFO = DOMAIN+"/getPayInfo";
	
	public static final String START_ALIPAY = DOMAIN+"/pay";
}
