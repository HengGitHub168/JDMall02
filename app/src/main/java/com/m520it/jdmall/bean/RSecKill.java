package com.m520it.jdmall.bean;

public class RSecKill {
	
	private String iconUrl;
	private double pointPrice;//现价
	private double allPrice;//原价
	private int type;//1-抢年货  2-超值  3-热卖
	private int timeLeft;//当前剩余时间 可直接返回多少分钟
	
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public double getPointPrice() {
		return pointPrice;
	}
	public void setPointPrice(double pointPrice) {
		this.pointPrice = pointPrice;
	}
	public double getAllPrice() {
		return allPrice;
	}
	public void setAllPrice(double allPrice) {
		this.allPrice = allPrice;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getTimeLeft() {
		return timeLeft;
	}
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}
	@Override
	public String toString() {
		return "RSecKill [productUrl=" + iconUrl + ", pointPrice="
				+ pointPrice + ", allPrice=" + allPrice + ", type=" + type
				+ ", timeLeft=" + timeLeft + "]";
	}
	
}

