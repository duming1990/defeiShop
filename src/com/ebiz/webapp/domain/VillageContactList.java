package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2018-01-26 15:45
 */
public class VillageContactList extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;
	
	private Integer contact_user_id;
	
	private String contact_user_name;
	
	private String nick_name;
	
	private Integer group_id;
	
	private Date add_date;
	
	private Integer add_user_id;
	
	private String add_user_name;
	
	private Integer is_del;
	
	private Date del_date;
	
	private Integer del_user_id;
	
	private String del_user_name;
	
	private Date update_date;
	
	private Integer update_user_id;
	
	private String update_user_name;
	
	public VillageContactList() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getContact_user_id() {
		return contact_user_id;
	}

	public void setContact_user_id(Integer contact_user_id) {
		this.contact_user_id = contact_user_id;
	}
	
	public String getContact_user_name() {
		return contact_user_name;
	}

	public void setContact_user_name(String contact_user_name) {
		this.contact_user_name = contact_user_name;
	}
	
	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	
	public Integer getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
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
	
	public String getAdd_user_name() {
		return add_user_name;
	}

	public void setAdd_user_name(String add_user_name) {
		this.add_user_name = add_user_name;
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
	
	public String getDel_user_name() {
		return del_user_name;
	}

	public void setDel_user_name(String del_user_name) {
		this.del_user_name = del_user_name;
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
	
	public String getUpdate_user_name() {
		return update_user_name;
	}

	public void setUpdate_user_name(String update_user_name) {
		this.update_user_name = update_user_name;
	}
	
}