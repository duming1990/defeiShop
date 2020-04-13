package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2018-03-01 15:13
 */
public class PoorZeRen extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer link_id;

	private String name;

	private String dan_wei_name;

	private String dan_wei_relation;

	private String mobile;

	private Integer is_del;

	private Date add_date;

	private Integer add_user_id;

	private String add_user_name;

	private Date update_date;

	private Integer update_user_id;

	private String update_user_name;

	private Date del_date;

	private Integer del_user_id;

	private String del_user_name;

	private List<PoorZeRen> ZeRenList = new ArrayList<PoorZeRen>();

	public PoorZeRen() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLink_id() {
		return link_id;
	}

	public void setLink_id(Integer link_id) {
		this.link_id = link_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDan_wei_name() {
		return dan_wei_name;
	}

	public void setDan_wei_name(String dan_wei_name) {
		this.dan_wei_name = dan_wei_name;
	}

	public String getDan_wei_relation() {
		return dan_wei_relation;
	}

	public void setDan_wei_relation(String dan_wei_relation) {
		this.dan_wei_relation = dan_wei_relation;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getAdd_user_name() {
		return add_user_name;
	}

	public void setAdd_user_name(String add_user_name) {
		this.add_user_name = add_user_name;
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

	public List<PoorZeRen> getZeRenList() {
		return ZeRenList;
	}

	public void setZeRenList(List<PoorZeRen> zeRenList) {
		ZeRenList = zeRenList;
	}

}