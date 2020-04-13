package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2016-06-16 18:18
 */
public class RwHbInfo extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;
	
	private String title;
	
	private Integer hb_class;
	
	private Integer hb_type;
	
	private Integer share_user_money;
	
	private Integer hb_money;
	
	private Integer min_money;
	
	private Integer max_money;
	
	private Integer hb_max_count;
	
	private Integer hb_rec_count;
	
	private Integer link_id;
	
	private Date effect_start_date;
	
	private Date effect_end_date;
	
	private Integer is_end;
	
	private Date add_date;
	
	private Integer add_uid;
	
	private Date del_date;
	
	private Integer del_uid;
	
	private Integer is_del;
	
	private String remark;
	
	public RwHbInfo() {

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
	
	public Integer getHb_rec_count() {
		return hb_rec_count;
	}

	public void setHb_rec_count(Integer hb_rec_count) {
		this.hb_rec_count = hb_rec_count;
	}
	
	public Integer getLink_id() {
		return link_id;
	}

	public void setLink_id(Integer link_id) {
		this.link_id = link_id;
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
	
	public Integer getIs_end() {
		return is_end;
	}

	public void setIs_end(Integer is_end) {
		this.is_end = is_end;
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