package com.m520it.jdmall.bean;

import java.util.ArrayList;

public class SimpleOrderBean {
	
	private String orderId;
	private int orderState;//0-待付款  1-待发货 2-待收货 3-待确认
	private String storeName;
	private ArrayList<String> iconUrls;
	private double totalPrice;
	
	public SimpleOrderBean() {
	}
	
	public SimpleOrderBean(String orderId, int orderState, String storeName,
			ArrayList<String> iconUrls, double totalPrice) {
		this.orderId = orderId;
		this.orderState = orderState;
		this.storeName = storeName;
		this.iconUrls = iconUrls;
		this.totalPrice = totalPrice;
	}
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public int getOrderState() {
		return orderState;
	}
	public void setOrderState(int orderState) {
		this.orderState = orderState;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public ArrayList<String> getIconUrls() {
		return iconUrls;
	}
	public void setIconUrls(ArrayList<String> iconUrls) {
		this.iconUrls = iconUrls;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	@Override
	public String toString() {
		return "SimpleOrderBean [orderId=" + orderId + ", orderState="
				+ orderState + ", storeName=" + storeName + ", iconUrls="
				+ iconUrls + ", totalPrice=" + totalPrice + "]";
	}
	
}
