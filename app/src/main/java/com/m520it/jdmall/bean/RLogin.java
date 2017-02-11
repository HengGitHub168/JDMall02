package com.m520it.jdmall.bean;

public class RLogin {
	private String id;
	private String userName;
	private String userIcon;
	private int userLevel;// 1-注册会员、2-铜牌会员、3-银牌会员、4-金牌会员、5-钻石会员
	private int waitPayCount;// 待付款数
	private int waitReceiveCount;// 待收货数
	private String errorMsg;

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

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public int getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(int userLevel) {
		this.userLevel = userLevel;
	}

	public int getWaitPayCount() {
		return waitPayCount;
	}

	public void setWaitPayCount(int waitPayCount) {
		this.waitPayCount = waitPayCount;
	}

	public int getWaitReceiveCount() {
		return waitReceiveCount;
	}

	public void setWaitReceiveCount(int waitReceiveCount) {
		this.waitReceiveCount = waitReceiveCount;
	}

	@Override
	public String toString() {
		return "RLogin [id=" + id + ", userName=" + userName + ", userIcon="
				+ userIcon + ", userLevel=" + userLevel + ", waitPayCount="
				+ waitPayCount + ", waitReceiveCount=" + waitReceiveCount
				+ ", errorMsg=" + errorMsg + "]";
	}
	
}
