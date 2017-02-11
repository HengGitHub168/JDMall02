package com.m520it.jdmall.bean;

public class RProductComment {
	private int rate;//星星 最多5个
	private String comment;
	private String userName;
	private String imgUrls;
	private String time;
	private int type;//1-好评 2-中评 3-差评
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getImgUrls() {
		return imgUrls;
	}
	public void setImgUrls(String imgUrls) {
		this.imgUrls = imgUrls;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "RProductComment [rate=" + rate + ", comment=" + comment
				+ ", userName=" + userName + ", imgUrls=" + imgUrls + ", time="
				+ time + ", type=" + type + "]";
	}
	
}
