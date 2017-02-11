package com.m520it.jdmall.bean;

import java.io.Serializable;

public class RShopCarList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1137310479744059430L;
	private String id;
	private String pid;//产品id
	private String pimageUrl;//图片
	private String pname;
	private String stockCount;//当前库存
	private int storeId;//店铺id
	private String storeName;//店铺名称
	private int buyCount;//购买数量
	private String  pversion;//型号
	private double pprice;//价格
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPimageUrl() {
		return pimageUrl;
	}
	public void setPimageUrl(String pimageUrl) {
		this.pimageUrl = pimageUrl;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getStockCount() {
		return stockCount;
	}
	public void setStockCount(String stockCount) {
		this.stockCount = stockCount;
	}
	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public int getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}
	public String getPversion() {
		return pversion;
	}
	public void setPversion(String pversion) {
		this.pversion = pversion;
	}
	public double getPprice() {
		return pprice;
	}
	public void setPprice(double pprice) {
		this.pprice = pprice;
	}
	@Override
	public String toString() {
		return "RShopCarList [id=" + id + ", pid=" + pid + ", pimageUrl="
				+ pimageUrl + ", pname=" + pname + ", stockCount=" + stockCount
				+ ", storeId=" + storeId + ", storeName=" + storeName
				+ ", buyCount=" + buyCount + ", pversion=" + pversion
				+ ", pprice=" + pprice + "]";
	}
	
	
}
