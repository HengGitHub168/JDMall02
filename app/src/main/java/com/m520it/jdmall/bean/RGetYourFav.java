package com.m520it.jdmall.bean;

public class RGetYourFav {
	
	private String productId;
	private String iconUrl;
	private String name;
	private double price;
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
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
	@Override
	public String toString() {
		return "RGetYourFav [productId=" + productId + ", iconUrl=" + iconUrl
				+ ", name=" + name + ", price=" + price + "]";
	}
	
	
}

