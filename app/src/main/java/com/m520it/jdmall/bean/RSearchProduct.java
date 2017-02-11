package com.m520it.jdmall.bean;

public class RSearchProduct {
	
	private String id;//商品 id
	private String iconUrl;//图片地址
	private String name;//名称
	private double price;//价格
	private int favcomRate;//好评率
	private int commentCount;//评论人数
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getFavcomRate() {
		return favcomRate;
	}
	public void setFavcomRate(int favcomRate) {
		this.favcomRate = favcomRate;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	@Override
	public String toString() {
		return "RSearchProduct [id=" + id + ", iconUrl=" + iconUrl + ", name="
				+ name + ", price=" + price + ", favcomRate=" + favcomRate
				+ ", commentCount=" + commentCount + "]";
	}
	
}