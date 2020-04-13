package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:54
 */
public class WlOrderInfo extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer order_id;

	private Integer wl_comp_id;

	private String wl_code;

	private String wl_comp_name;

	private String waybill_no;

	private Integer entp_id;

	private Integer order_value;

	private Integer is_lock;

	private Integer is_del;

	private Date add_date;

	private Integer add_user_id;

	private Date update_date;

	private Integer update_user_id;

	private String link_man;

	private Integer tel;

	private Integer order_state;

	private Integer wl_type;

	public WlOrderInfo() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}

	public Integer getWl_comp_id() {
		return wl_comp_id;
	}

	public void setWl_comp_id(Integer wl_comp_id) {
		this.wl_comp_id = wl_comp_id;
	}

	public String getWl_code() {
		return wl_code;
	}

	public void setWl_code(String wl_code) {
		this.wl_code = wl_code;
	}

	public String getWl_comp_name() {
		return wl_comp_name;
	}

	public void setWl_comp_name(String wl_comp_name) {
		this.wl_comp_name = wl_comp_name;
	}

	public String getWaybill_no() {
		return waybill_no;
	}

	public void setWaybill_no(String waybill_no) {
		this.waybill_no = waybill_no;
	}

	public Integer getEntp_id() {
		return entp_id;
	}

	public void setEntp_id(Integer entp_id) {
		this.entp_id = entp_id;
	}

	public Integer getOrder_value() {
		return order_value;
	}

	public void setOrder_value(Integer order_value) {
		this.order_value = order_value;
	}

	public Integer getIs_lock() {
		return is_lock;
	}

	public void setIs_lock(Integer is_lock) {
		this.is_lock = is_lock;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

	public Date getAdd_date() {
		return add_date;
	}

	public void setAdd_date(Date add_date) {
		this.add_date = add_date;
	}

	public Integer getAdd_user_id() {
		return add_user_id;
	}

	public void setAdd_user_id(Integer add_user_id) {
		this.add_user_id = add_user_id;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public Integer getUpdate_user_id() {
		return update_user_id;
	}

	public void setUpdate_user_id(Integer update_user_id) {
		this.update_user_id = update_user_id;
	}

	public String getLink_man() {
		return link_man;
	}

	public void setLink_man(String link_man) {
		this.link_man = link_man;
	}

	public Integer getTel() {
		return tel;
	}

	public void setTel(Integer tel) {
		this.tel = tel;
	}

	public Integer getOrder_state() {
		return order_state;
	}

	public void setOrder_state(Integer order_state) {
		this.order_state = order_state;
	}

	public void setWl_type(Integer wl_type) {
		this.wl_type = wl_type;
	}

	public Integer getWl_type() {
		return wl_type;
	}

}