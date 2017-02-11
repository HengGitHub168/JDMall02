package com.m520it.jdmall.bean;


public class RCommentDetails {
	private String id;
	private String userName;
	private String userLevel;
	private String userImg;
	private String commentTime;
	private int rate;// 星星 最多5个
	private String comment;
	private String imgUrls;
	private String buyTime;
	private String productType;// 产品类型
	private int loveCount;// 喜欢个数
	private int subComment;// 回复评论数
	
	public RCommentDetails() {
	}
	public RCommentDetails(String id, String userName, String userLevel,
			String commentTime, int rate, String comment, String imgUrls,
			String buyTime, String productType, int loveCount, int subComment) {
		this.id = id;
		this.userName = userName;
		this.userLevel = userLevel;
		this.commentTime = commentTime;
		this.rate = rate;
		this.comment = comment;
		this.imgUrls = imgUrls;
		this.buyTime = buyTime;
		this.productType = productType;
		this.loveCount = loveCount;
		this.subComment = subComment;
	}
	public String getUserImg() {
		return userImg;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}
	public String getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}
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
	public String getImgUrls() {
		return imgUrls;
	}
	public void setImgUrls(String imgUrls) {
		this.imgUrls = imgUrls;
	}
	public String getBuyTime() {
		return buyTime;
	}
	public void setBuyTime(String buyTime) {
		this.buyTime = buyTime;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public int getLoveCount() {
		return loveCount;
	}
	public void setLoveCount(int loveCount) {
		this.loveCount = loveCount;
	}
	public int getSubComment() {
		return subComment;
	}
	public void setSubComment(int subComment) {
		this.subComment = subComment;
	}
	
}
