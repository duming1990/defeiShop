package com.kameila.koala.model;

public class OrderProduct {
	private int quantity;
	private int sku_id;
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getSku_id() {
		return sku_id;
	}
	public void setSku_id(int sku_id) {
		this.sku_id = sku_id;
	}
	@Override
	public String toString() {
		return "OrderProduct [quantity=" + quantity + ", sku_id=" + sku_id + "]";
	}
}
