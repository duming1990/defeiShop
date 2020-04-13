package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2015-11-21 14:17
 */
public class UserRelation extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;
	
	private Integer user_id;
	
	private Integer user_par_id;
	
	private Integer user_root_id;
	
	private Date add_date;
	
	private Integer add_user_id;
	
	public UserRelation() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	
	public Integer getUser_par_id() {
		return user_par_id;
	}

	public void setUser_par_id(Integer user_par_id) {
		this.user_par_id = user_par_id;
	}
	
	public Integer getUser_root_id() {
		return user_root_id;
	}

	public void setUser_root_id(Integer user_root_id) {
		this.user_root_id = user_root_id;
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
	
}