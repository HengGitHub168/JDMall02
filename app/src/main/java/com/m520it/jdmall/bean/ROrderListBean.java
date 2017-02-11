package com.m520it.jdmall.bean;


public class ROrderListBean {
	
	private String items;
	private int oid;
	private String orderNum;
	private String tn;
	private double totalPrice;
	private int status;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getItems() {
		return items;
	}
	public void setItems(String items) {
		this.items = items;
	}
	public int getOid() {
		return oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getTn() {
		return tn;
	}
	public void setTn(String tn) {
		this.tn = tn;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	@Override
	public String toString() {
		return "ROrderListBean [items=" + items + ", oid=" + oid
				+ ", orderNum=" + orderNum + ", tn=" + tn + ", totalPrice="
				+ totalPrice + ", status=" + status + "]";
	}
	
}
