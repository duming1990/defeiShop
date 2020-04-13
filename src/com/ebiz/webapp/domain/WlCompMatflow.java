package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:54
 */
public class WlCompMatflow extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer wl_comp_id;

	private String wl_code;

	private Integer src_p_index;

	private Integer dest_p_index;

	private Integer charge_type;

	private String data_src;

	private String pd_types;

	private Integer have_service;

	private BigDecimal first_kilo_fee;

	private BigDecimal other_kilo_fee;

	private Integer min_volume;

	private Integer max_volume;

	private BigDecimal volume_fee;

	private BigDecimal base_price;

	private Integer order_value;

	private Integer is_lock;

	private Integer is_del;

	private Date add_date;

	private Integer add_user_id;

	private Date update_date;

	private Integer update_user_id;

	private Date del_date;

	private Integer del_user_id;

	private Integer matflow_type;

	public WlCompMatflow() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getSrc_p_index() {
		return src_p_index;
	}

	public void setSrc_p_index(Integer src_p_index) {
		this.src_p_index = src_p_index;
	}

	public Integer getDest_p_index() {
		return dest_p_index;
	}

	public void setDest_p_index(Integer dest_p_index) {
		this.dest_p_index = dest_p_index;
	}

	public Integer getCharge_type() {
		return charge_type;
	}

	public void setCharge_type(Integer charge_type) {
		this.charge_type = charge_type;
	}

	public String getData_src() {
		return data_src;
	}

	public void setData_src(String data_src) {
		this.data_src = data_src;
	}

	public String getPd_types() {
		return pd_types;
	}

	public void setPd_types(String pd_types) {
		this.pd_types = pd_types;
	}

	public Integer getHave_service() {
		return have_service;
	}

	public void setHave_service(Integer have_service) {
		this.have_service = have_service;
	}

	public BigDecimal getFirst_kilo_fee() {
		return first_kilo_fee;
	}

	public void setFirst_kilo_fee(BigDecimal first_kilo_fee) {
		this.first_kilo_fee = first_kilo_fee;
	}

	public BigDecimal getOther_kilo_fee() {
		return other_kilo_fee;
	}

	public void setOther_kilo_fee(BigDecimal other_kilo_fee) {
		this.other_kilo_fee = other_kilo_fee;
	}

	public Integer getMin_volume() {
		return min_volume;
	}

	public void setMin_volume(Integer min_volume) {
		this.min_volume = min_volume;
	}

	public Integer getMax_volume() {
		return max_volume;
	}

	public void setMax_volume(Integer max_volume) {
		this.max_volume = max_volume;
	}

	public BigDecimal getVolume_fee() {
		return volume_fee;
	}

	public void setVolume_fee(BigDecimal volume_fee) {
		this.volume_fee = volume_fee;
	}

	public BigDecimal getBase_price() {
		return base_price;
	}

	public void setBase_price(BigDecimal base_price) {
		this.base_price = base_price;
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

	public Date getDel_date() {
		return del_date;
	}

	public void setDel_date(Date del_date) {
		this.del_date = del_date;
	}

	public Integer getDel_user_id() {
		return del_user_id;
	}

	public void setDel_user_id(Integer del_user_id) {
		this.del_user_id = del_user_id;
	}

	public Integer getMatflow_type() {
		return matflow_type;
	}

	public void setMatflow_type(Integer matflow_type) {
		this.matflow_type = matflow_type;
	}

}