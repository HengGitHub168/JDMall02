package com.m520it.jdmall.bean;

import java.io.Serializable;

public class RReceiveAddress implements Serializable{

	private static final long serialVersionUID = -6878375321546410058L;
	
	private String id;
	private String receiverName;
	private String receiverPhone;
	private String receiverAddress;
	private boolean isDefault;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	@Override
	public String toString() {
		return "RReceiveAddress [id=" + id + ", receiverName=" + receiverName
				+ ", receiverPhone=" + receiverPhone + ", receiverAddress="
				+ receiverAddress + ", isDefault=" + isDefault + "]";
	}
	
}
