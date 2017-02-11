package com.m520it.jdmall.bean;

public class RBaseCategory {
	
	private String id;//分类id
	private String name;//分类名称
	private String bannerUrl;//分类的图片
	public RBaseCategory() {
	}
	public RBaseCategory(String id, String name, String bannerUrl) {
		this.id = id;
		this.name = name;
		this.bannerUrl = bannerUrl;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBannerUrl() {
		return bannerUrl;
	}
	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}
	@Override
	public String toString() {
		return "RBaseCategory [id=" + id + ", name=" + name + ", bannerUrl="
				+ bannerUrl + "]";
	}
	
}
