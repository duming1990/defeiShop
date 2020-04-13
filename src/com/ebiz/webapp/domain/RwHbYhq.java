package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2016-06-16 18:18
 */
public class RwHbYhq extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;
	
	private Integer hb_id;
	
	private Integer yhq_id;
	
	private Date add_date;
	
	private Integer add_uid;
	
	public RwHbYhq() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getHb_id() {
		return hb_id;
	}

	public void setHb_id(Integer hb_id) {
		this.hb_id = hb_id;
	}
	
	public Integer getYhq_id() {
		return yhq_id;
	}

	public void setYhq_id(Integer yhq_id) {
		this.yhq_id = yhq_id;
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
	
}