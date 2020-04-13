package com.ebiz.webapp.domain;

import java.io.Serializable;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2012-05-19 17:25
 */
public class BaseBrandInfo extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer add_user_id;

	private Integer audit_state;

	private Integer brand_id;

	private String brand_label;

	private String brand_logo;

	private String brand_name;

	private String brand_name_cn;

	private String brand_name_en;

	private Integer entp_id;

	private Integer is_del;

	private Integer is_have_stores;

	private Integer is_lock;

	private Integer order_sort;

	private String stores_url;

	public BaseBrandInfo() {

	}

	public Integer getAdd_user_id() {
		return add_user_id;
	}

	public Integer getAudit_state() {
		return audit_state;
	}

	public Integer getBrand_id() {
		return brand_id;
	}

	public String getBrand_label() {
		return brand_label;
	}

	public String getBrand_logo() {
		return brand_logo;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public String getBrand_name_cn() {
		return brand_name_cn;
	}

	public String getBrand_name_en() {
		return brand_name_en;
	}

	public Integer getEntp_id() {
		return entp_id;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public Integer getIs_have_stores() {
		return is_have_stores;
	}

	public Integer getIs_lock() {
		return is_lock;
	}

	public Integer getOrder_sort() {
		return order_sort;
	}

	public String getStores_url() {
		return stores_url;
	}

	public void setAdd_user_id(Integer addUserId) {
		add_user_id = addUserId;
	}

	public void setAudit_state(Integer auditState) {
		audit_state = auditState;
	}

	public void setBrand_id(Integer brand_id) {
		this.brand_id = brand_id;
	}

	public void setBrand_label(String brand_label) {
		this.brand_label = brand_label;
	}

	public void setBrand_logo(String brand_logo) {
		this.brand_logo = brand_logo;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public void setBrand_name_cn(String brand_name_cn) {
		this.brand_name_cn = brand_name_cn;
	}

	public void setBrand_name_en(String brand_name_en) {
		this.brand_name_en = brand_name_en;
	}

	public void setEntp_id(Integer entpId) {
		entp_id = entpId;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

	public void setIs_have_stores(Integer is_have_stores) {
		this.is_have_stores = is_have_stores;
	}

	public void setIs_lock(Integer is_lock) {
		this.is_lock = is_lock;
	}

	public void setOrder_sort(Integer order_sort) {
		this.order_sort = order_sort;
	}

	public void setStores_url(String stores_url) {
		this.stores_url = stores_url;
	}

}