package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2016-06-16 18:18
 */
public class RwHbRule extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer update_uid;

	private String title;

	private Integer hb_class;

	private Integer hb_type;

	private Integer share_user_money;

	private Integer hb_money;

	private Integer min_money;

	private Integer max_money;

	private Integer hb_max_count;

	private Integer effect_count;

	private Integer is_closed;

	private Date add_date;

	private Integer add_uid;

	private Date del_date;

	private Date update_date;

	private Integer del_uid;

	private Integer is_del;

	private String remark;

	public RwHbRule() {

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

	public Integer getHb_class() {
		return hb_class;
	}

	public void setHb_class(Integer hb_class) {
		this.hb_class = hb_class;
	}

	public Integer getHb_type() {
		return hb_type;
	}

	public void setHb_type(Integer hb_type) {
		this.hb_type = hb_type;
	}

	public Integer getShare_user_money() {
		return share_user_money;
	}

	public void setShare_user_money(Integer share_user_money) {
		this.share_user_money = share_user_money;
	}

	public Integer getHb_money() {
		return hb_money;
	}

	public void setHb_money(Integer hb_money) {
		this.hb_money = hb_money;
	}

	public Integer getMin_money() {
		return min_money;
	}

	public void setMin_money(Integer min_money) {
		this.min_money = min_money;
	}

	public Integer getMax_money() {
		return max_money;
	}

	public void setMax_money(Integer max_money) {
		this.max_money = max_money;
	}

	public Integer getHb_max_count() {
		return hb_max_count;
	}

	public void setHb_max_count(Integer hb_max_count) {
		this.hb_max_count = hb_max_count;
	}

	public Integer getEffect_count() {
		return effect_count;
	}

	public void setEffect_count(Integer effect_count) {
		this.effect_count = effect_count;
	}

	public Integer getIs_closed() {
		return is_closed;
	}

	public void setIs_closed(Integer is_closed) {
		this.is_closed = is_closed;
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

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public Integer getUpdate_uid() {
		return update_uid;
	}

	public void setUpdate_uid(Integer update_uid) {
		this.update_uid = update_uid;
	}

}