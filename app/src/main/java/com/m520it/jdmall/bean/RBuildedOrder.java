package com.m520it.jdmall.bean;

public class RBuildedOrder {
	
	private String tn;
	private double allPrice;
	private String oid;
	private double freight;
	private String orderNum;
	private double totalPrice;
	private int errorType;
	private int payWay;
	
	public int getPayWay() {
		return payWay;
	}
	public void setPayWay(int payWay) {
		this.payWay = payWay;
	}
	public String getTn() {
		return tn;
	}
	public void setTn(String tn) {
		this.tn = tn;
	}
	public double getAllPrice() {
		return allPrice;
	}
	public void setAllPrice(double allPrice) {
		this.allPrice = allPrice;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public double getFreight() {
		return freight;
	}
	public void setFreight(double freight) {
		this.freight = freight;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getErrorType() {
		return errorType;
	}
	public void setErrorType(int errorType) {
		this.errorType = errorType;
	}
	@Override
	public String toString() {
		return "RBuildedOrder [tn=" + tn + ", allPrice=" + allPrice + ", oid="
				+ oid + ", freight=" + freight + ", orderNum=" + orderNum
				+ ", totalPrice=" + totalPrice + ", errorType=" + errorType
				+ ", payWay=" + payWay + "]";
	}
	
}
