package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2018-01-26 15:45
 */
public class VillageDynamicRecord extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer village_id;

	private Integer link_id;

	private Integer to_user_id;

	private String to_user_name;

	private Integer comm_id;

	private String comm_name;

	private Integer record_type;

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

	private String remark;

	public VillageDynamicRecord() {

	}

	public Integer getVillage_id() {
		return village_id;
	}

	public void setVillage_id(Integer village_id) {
		this.village_id = village_id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public void setLink_id(Integer dynamic_id) {
		this.link_id = dynamic_id;
	}

	public Integer getTo_user_id() {
		return to_user_id;
	}

	public void setTo_user_id(Integer focus_user_id) {
		this.to_user_id = focus_user_id;
	}

	public String getTo_user_name() {
		return to_user_name;
	}

	public void setTo_user_name(String focus_user_name) {
		this.to_user_name = focus_user_name;
	}

	public Integer getComm_id() {
		return comm_id;
	}

	public void setComm_id(Integer comm_id) {
		this.comm_id = comm_id;
	}

	public String getComm_name() {
		return comm_name;
	}

	public void setComm_name(String comm_name) {
		this.comm_name = comm_name;
	}

	public Integer getRecord_type() {
		return record_type;
	}

	public void setRecord_type(Integer record_type) {
		this.record_type = record_type;
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