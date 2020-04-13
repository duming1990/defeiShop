package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2014-05-22 19:36
 */
public class Freight extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer entp_id;

	private String fre_title;

	private Integer p_index;

	private Integer delivery_time;

	private Integer valuation;

	private Integer area_limit;

	private Date update_date;

	private Date add_date;

	private Integer is_del;

	private Date del_date;

	private Integer del_user_id;

	private List<FreightDetail> freightDetailList = new ArrayList<FreightDetail>();

	// 是否包邮
	private Integer is_freeshipping;

	private Integer is_open_freeShipping_money;

	private Integer open_money_freeShipping;

	public List<FreightDetail> getFreightDetailList() {
		return freightDetailList;
	}

	public void setFreightDetailList(List<FreightDetail> freightDetailList) {
		this.freightDetailList = freightDetailList;
	}

	public Freight() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEntp_id() {
		return entp_id;
	}

	public void setEntp_id(Integer entp_id) {
		this.entp_id = entp_id;
	}

	public String getFre_title() {
		return fre_title;
	}

	public void setFre_title(String fre_title) {
		this.fre_title = fre_title;
	}

	public Integer getP_index() {
		return p_index;
	}

	public void setP_index(Integer p_index) {
		this.p_index = p_index;
	}

	public Integer getDelivery_time() {
		return delivery_time;
	}

	public void setDelivery_time(Integer delivery_time) {
		this.delivery_time = delivery_time;
	}

	public Integer getValuation() {
		return valuation;
	}

	public void setValuation(Integer valuation) {
		this.valuation = valuation;
	}

	public Integer getArea_limit() {
		return area_limit;
	}

	public void setArea_limit(Integer area_limit) {
		this.area_limit = area_limit;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public Date getAdd_date() {
		return add_date;
	}

	public void setAdd_date(Date add_date) {
		this.add_date = add_date;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
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

	public Integer getIs_freeshipping() {
		return is_freeshipping;
	}

	public void setIs_freeshipping(Integer is_freeshipping) {
		this.is_freeshipping = is_freeshipping;
	}

	public Integer getIs_open_freeShipping_money() {
		return is_open_freeShipping_money;
	}

	public void setIs_open_freeShipping_money(Integer is_open_freeShipping_money) {
		this.is_open_freeShipping_money = is_open_freeShipping_money;
	}

	public Integer getOpen_money_freeShipping() {
		return open_money_freeShipping;
	}

	public void setOpen_money_freeShipping(Integer open_money_freeShipping) {
		this.open_money_freeShipping = open_money_freeShipping;
	}

}