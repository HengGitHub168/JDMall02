package com.m520it.jdmall.bean;

import java.util.ArrayList;

public class RSubCategory {
	
	private String id;//2级分类的id
	private String name;//2级分类名称 如 裙装 上装  下装
	//3级分类列表 其类的定义跟1级分类一样 里面包含了3级分类的图片 名称  id 
	private ArrayList<RBaseCategory> thirdCategory;
	
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
	public ArrayList<RBaseCategory> getThirdCategory() {
		return thirdCategory;
	}
	public void setThirdCategory(ArrayList<RBaseCategory> thirdCategory) {
		this.thirdCategory = thirdCategory;
	}
	@Override
	public String toString() {
		return "RSubCategory [id=" + id + ", name=" + name + ", thirdCategory="
				+ thirdCategory + "]";
	}
	
}

