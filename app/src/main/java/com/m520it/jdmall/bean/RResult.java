package com.m520it.jdmall.bean;

public class RResult {

	private boolean success;
	private String errorMsg;
	private String result;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "RResult [success=" + success + ", errorMsg=" + errorMsg
				+ ", result=" + result + "]";
	}
	
	
	
}
