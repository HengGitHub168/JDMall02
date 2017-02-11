package com.m520it.jdmall.bean;

public class RBanner {
	
	private int id;//如果是商品详情 则返回商品id,如果是分类详情 则返回以及3级分类id
	private int type;//1-跳转到网页  2-跳转到商品详情 3-跳转到分类去
	private String adUrl;
	private String webUrl;//如果是网页 则返回网页的Url 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getAdUrl() {
		return adUrl;
	}
	public void setAdUrl(String adUrl) {
		this.adUrl = adUrl;
	}
	public String getWebUrl() {
		return webUrl;
	}
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
	
	
}

