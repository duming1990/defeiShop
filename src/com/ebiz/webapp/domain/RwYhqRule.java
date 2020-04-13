package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2016-06-16 18:18
 */
public class RwYhqRule extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;
	
	private String title;
	
	private Integer min_money;
	
	private Integer amount;
	
	private Integer rule_type;
	
	private Integer effect_count;
	
	private Integer is_del;
	
	private Date add_date;
	
	private Integer add_uid;
	
	private Date modify_date;
	
	private Integer modify_uid;
	
	private Date del_date;
	
	private Integer del_uid;
	
	private String remark;
	
	public RwYhqRule() {

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
	
	public Integer getRule_type() {
		return rule_type;
	}

	public void setRule_type(Integer rule_type) {
		this.rule_type = rule_type;
	}
	
	public Integer getEffect_count() {
		return effect_count;
	}

	public void setEffect_count(Integer effect_count) {
		this.effect_count = effect_count;
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
	
	public Integer getAdd_uid() {
		return add_uid;
	}

	public void setAdd_uid(Integer add_uid) {
		this.add_uid = add_uid;
	}
	
	public Date getModify_date() {
		return modify_date;
	}

	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
	}
	
	public Integer getModify_uid() {
		return modify_uid;
	}

	public void setModify_uid(Integer modify_uid) {
		this.modify_uid = modify_uid;
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
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}