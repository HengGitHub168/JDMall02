package com.m520it.jdmall.bean;

public class OrderDetailsBean {
	
	private String address;
	private String oid;
	private String orderNum;
	private int payWay;
	private int status;
	private String tn;
	private double totalPrice;
	private double freight;
	private String buyTime;
	private String items;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public int getPayWay() {
		return payWay;
	}
	public void setPayWay(int payWay) {
		this.payWay = payWay;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	public double getFreight() {
		return freight;
	}
	public void setFreight(double freight) {
		this.freight = freight;
	}
	public String getBuyTime() {
		return buyTime;
	}
	public void setBuyTime(String buyTime) {
		this.buyTime = buyTime;
	}
	public String getItems() {
		return items;
	}
	public void setItems(String items) {
		this.items = items;
	}
	@Override
	public String toString() {
		return "OrderProductsBean [address=" + address + ", oid=" + oid
				+ ", orderNum=" + orderNum + ", payWay=" + payWay + ", status="
				+ status + ", tn=" + tn + ", totalPrice=" + totalPrice
				+ ", freight=" + freight + ", buyTime=" + buyTime + ", items="
				+ items + "]";
	}
	
}
