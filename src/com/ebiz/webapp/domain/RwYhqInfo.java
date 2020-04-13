package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2016-06-16 18:18
 */
public class RwYhqInfo extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;
	
	private String title;
	
	private Integer origin_type;
	
	private Integer hb_class;
	
	private Integer min_money;
	
	private Integer amount;
	
	private Integer ruel_type;
	
	private Date effect_start_date;
	
	private Date effect_end_date;
	
	private Integer is_used;
	
	private Date used_date;
	
	private Integer link_id;
	
	private Date add_date;
	
	private Integer add_uid;
	
	private Date del_date;
	
	private Integer del_uid;
	
	private Integer is_del;
	
	private String remark;
	
	public RwYhqInfo() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public Integer getOrigin_type() {
		return origin_type;
	}

	public void setOrigin_type(Integer origin_type) {
		this.origin_type = origin_type;
	}
	
	public Integer getHb_class() {
		return hb_class;
	}

	public void setHb_class(Integer hb_class) {
		this.hb_class = hb_class;
	}
	
	public Integer getMin_money() {
		return min_money;
	}

	public void setMin_money(Integer min_money) {
		this.min_money = min_money;
	}
	
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public Integer getRuel_type() {
		return ruel_type;
	}

	public void setRuel_type(Integer ruel_type) {
		this.ruel_type = ruel_type;
	}
	
	public Date getEffect_start_date() {
		return effect_start_date;
	}

	public void setEffect_start_date(Date effect_start_date) {
		this.effect_start_date = effect_start_date;
	}
	
	public Date getEffect_end_date() {
		return effect_end_date;
	}

	public void setEffect_end_date(Date effect_end_date) {
		this.effect_end_date = effect_end_date;
	}
	
	public Integer getIs_used() {
		return is_used;
	}

	public void setIs_used(Integer is_used) {
		this.is_used = is_used;
	}
	
	public Date getUsed_date() {
		return used_date;
	}

	public void setUsed_date(Date used_date) {
		this.used_date = used_date;
	}
	
	public Integer getLink_id() {
		return link_id;
	}

	public void setLink_id(Integer link_id) {
		this.link_id = link_id;
	}
	
	public Date getAdd_date() {
		return add_date;
	}

	public void setAdd_date(Date add_date) {
		this.add_date = add_date;
	}
	
	public Integer getAdd_uid() {
		return add_uid;
	}

	public void setAdd_uid(Integer add_uid) {
		this.add_uid = add_uid;
	}
	
	public Date getDel_date() {
		return del_date;
	}

	public void setDel_date(Date del_date) {
		this.del_date = del_date;
	}
	
	public Integer getDel_uid() {
		return del_uid;
	}

	public void setDel_uid(Integer del_uid) {
		this.del_uid = del_uid;
	}
	
	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}