package com.kameila.koala.model;

import java.util.List;

public class OrderReponse {
	private String kml_order_no;
	private String order_no;
	private double total_fee;
	private double delivery_fee;
	private double product_fee;
	private List<OrderProduct> product;
	
	public OrderReponse(){
		super();
	}
	
	public OrderReponse(String kml_order_no, String order_no, double total_fee, double delivery_fee, double product_fee,
			List<OrderProduct> product) {
		super();
		this.kml_order_no = kml_order_no;
		this.order_no = order_no;
		this.total_fee = total_fee;
		this.delivery_fee = delivery_fee;
		this.product_fee = product_fee;
		this.product = product;
	}



	public String getKml_order_no() {
		return kml_order_no;
	}
	public void setKml_order_no(String kml_order_no) {
		this.kml_order_no = kml_order_no;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public double getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(double total_fee) {
		this.total_fee = total_fee;
	}
	public double getDelivery_fee() {
		return delivery_fee;
	}
	public void setDelivery_fee(double delivery_fee) {
		this.delivery_fee = delivery_fee;
	}
	public double getProduct_fee() {
		return product_fee;
	}
	public void setProduct_fee(double product_fee) {
		this.product_fee = product_fee;
	}
	public List<OrderProduct> getProduct() {
		return product;
	}
	public void setProduct(List<OrderProduct> product) {
		this.product = product;
	}
	@Override
	public String toString() {
		return "OrderReponse [kml_order_no=" + kml_order_no + ", order_no=" + order_no + ", total_fee=" + total_fee
				+ ", delivery_fee=" + delivery_fee + ", product_fee=" + product_fee + ", product=" + product + "]";
	}
}
