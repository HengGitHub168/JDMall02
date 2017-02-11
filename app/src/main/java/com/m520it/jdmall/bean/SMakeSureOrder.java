package com.m520it.jdmall.bean;

import java.util.ArrayList;

public class SMakeSureOrder {
	
	private ArrayList<SMakeSureOrderProduct> products;
	private int payWay;
	private String userId;
	private String addrId;
	
	public SMakeSureOrder(ArrayList<SMakeSureOrderProduct> products,int payWay, String addrId) {
		this.products = products;
		this.payWay = payWay;
		this.addrId = addrId;
	}
	public ArrayList<SMakeSureOrderProduct> getProducts() {
		return products;
	}
	public void setProducts(ArrayList<SMakeSureOrderProduct> products) {
		this.products = products;
	}
	public int getPayWay() {
		return payWay;
	}
	public void setPayWay(int payWay) {
		this.payWay = payWay;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAddrId() {
		return addrId;
	}
	public void setAddrId(String addrId) {
		this.addrId = addrId;
	}
	
	
	
}
