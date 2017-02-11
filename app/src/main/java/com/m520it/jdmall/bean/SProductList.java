package com.m520it.jdmall.bean;

public class SProductList {

	public static final int SEARCH_ALL = 1;
	public static final int SEARCH_NEW = 2;
	public static final int SEARCH_COMMENT_UP2DOWN = 3;
	public static final int SEARCH_SALE = 1;
	public static final int SEARCH_PRICE_UP = 2;
	public static final int SEARCH_PRICE_DOWN = 3;
	
	private String categoryId;
	private int filterType=SEARCH_ALL;//1-综合  2-新品 3-评价
	private int sortType;//1-销量  2-价格高到低  3-价格低到高
	
	//0-代表无选择  1代表京东配送 2-代表货到付款 4-代表仅看有货   
	//3代表条件1+2  5代表条件1+4  6代表条件2+4
	private int deliverChoose;
	
	private int minPrice;
	private int maxPrice;
	private String brandId;
	
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public int getFilterType() {
		return filterType;
	}
	public void setFilterType(int filterType) {
		this.filterType = filterType;
	}
	public int getSortType() {
		return sortType;
	}
	public void setSortType(int sortType) {
		this.sortType = sortType;
	}
	public int getDeliverChoose() {
		return deliverChoose;
	}
	public void setDeliverChoose(int deliverChoose) {
		this.deliverChoose = deliverChoose;
	}
	public int getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}
	public int getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	@Override
	public String toString() {
		return "SProductList [categoryId=" + categoryId + ", filterType="
				+ filterType + ", sortType=" + sortType + ", deliverChoose="
				+ deliverChoose + ", minPrice=" + minPrice + ", maxPrice="
				+ maxPrice + ", brandId=" + brandId + "]";
	}
	
}
