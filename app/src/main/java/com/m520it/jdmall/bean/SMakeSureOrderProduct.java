package com.m520it.jdmall.bean;

public class SMakeSureOrderProduct {
	
	private int buyCount;
	private String type;
	private String pid;
	
	public SMakeSureOrderProduct(int buyCount, String type, String pid) {
		this.buyCount = buyCount;
		this.type = type;
		this.pid = pid;
	}
	public int getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
}
