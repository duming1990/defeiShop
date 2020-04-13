package com.kameila.koala.model;

public class ProductSku {
	private int id; //卡美啦skuID
	private String category; //所属类目
	private double price; //供货价
	private String product_title; //商品名
	private int inventory; //库存
	private String size_title; //规格
	private String brand_title; //品牌名
	private String pic; //商品图链接
	private String summary; //商品说明
	private int spu_id; //卡美啦spu_id
	private int region; //1保税仓发货,2海外直邮,3国内仓发货
	private String style_title; //款式名称
	private int need_identity; //购买该商品是否需要身份证:0不需要，1需要身份证及正反面照片2只需要身份证号码
	private int limit_amount; //单笔订单限购数量
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getProduct_title() {
		return product_title;
	}
	public void setProduct_title(String product_title) {
		this.product_title = product_title;
	}
	public int getInventory() {
		return inventory;
	}
	public void setInventory(int inventory) {
		this.inventory = inventory;
	}
	public String getSize_title() {
		return size_title;
	}
	public void setSize_title(String size_title) {
		this.size_title = size_title;
	}
	public String getBrand_title() {
		return brand_title;
	}
	public void setBrand_title(String brand_title) {
		this.brand_title = brand_title;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public int getSpu_id() {
		return spu_id;
	}
	public void setSpu_id(int spu_id) {
		this.spu_id = spu_id;
	}
	public int getRegion() {
		return region;
	}
	public void setRegion(int region) {
		this.region = region;
	}
	public String getStyle_title() {
		return style_title;
	}
	public void setStyle_title(String style_title) {
		this.style_title = style_title;
	}
	public int getNeed_identity() {
		return need_identity;
	}
	public void setNeed_identity(int need_identity) {
		this.need_identity = need_identity;
	}
	public int getLimit_amount() {
		return limit_amount;
	}
	public void setLimit_amount(int limit_amount) {
		this.limit_amount = limit_amount;
	}
	@Override
	public String toString() {
		return "ProductSku [id=" + id + ", category=" + category + ", price=" + price + ", product_title="
				+ product_title + ", inventory=" + inventory + ", size_title=" + size_title + ", brand_title="
				+ brand_title + ", pic=" + pic + ", summary=" + summary + ", spu_id=" + spu_id + ", region=" + region
				+ ", style_title=" + style_title + ", need_identity=" + need_identity + ", limit_amount=" + limit_amount
				+ "]";
	}
}
