package com.m520it.jdmall.bean;

public class SReceiverCreate {
	
	private String name;
	private String phone;
	//省市区包括系统编号
	private String provinceName;
	private String provinceCode;
	private String cityName;
	private String cityCode;
	private String distName;
	private String distCode;
	
	private String addressDetails;
	private boolean isDefault;
	
	public SReceiverCreate() {
	}
	public SReceiverCreate(String name, String phone, String provinceName,
			String provinceCode, String cityName, String cityCode,
			String distName, String distCode, String addressDetails,
			boolean isDefault) {
		this.name = name;
		this.phone = phone;
		this.provinceName = provinceName;
		this.provinceCode = provinceCode;
		this.cityName = cityName;
		this.cityCode = cityCode;
		this.distName = distName;
		this.distCode = distCode;
		this.addressDetails = addressDetails;
		this.isDefault = isDefault;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getDistName() {
		return distName;
	}
	public void setDistName(String distName) {
		this.distName = distName;
	}
	public String getDistCode() {
		return distCode;
	}
	public void setDistCode(String distCode) {
		this.distCode = distCode;
	}
	public String getAddressDetails() {
		return addressDetails;
	}
	public void setAddressDetails(String addressDetails) {
		this.addressDetails = addressDetails;
	}
	public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	@Override
	public String toString() {
		return "SReceiverCreate [name=" + name + ", phone=" + phone
				+ ", provinceName=" + provinceName + ", provinceCode="
				+ provinceCode + ", cityName=" + cityName + ", cityCode="
				+ cityCode + ", distName=" + distName + ", distCode="
				+ distCode + ", addressDetails=" + addressDetails
				+ ", isDefault=" + isDefault + "]";
	}
	
}
