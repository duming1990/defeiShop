package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2013-12-05 07:20
 */
public class SysOperLog extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer oper_type;

	private String oper_name;

	private Date oper_time;

	private Integer oper_uid;

	private String oper_uname;

	private String oper_uip;

	private String oper_memo;

	private Integer is_del;

	private Integer link_id;

	private Integer pre_number;

	public SysOperLog() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOper_type() {
		return oper_type;
	}

	public void setOper_type(Integer oper_type) {
		this.oper_type = oper_type;
	}

	public String getOper_name() {
		return oper_name;
	}

	public void setOper_name(String oper_name) {
		this.oper_name = oper_name;
	}

	public Date getOper_time() {
		return oper_time;
	}

	public void setOper_time(Date oper_time) {
		this.oper_time = oper_time;
	}

	public Integer getOper_uid() {
		return oper_uid;
	}

	public void setOper_uid(Integer oper_uid) {
		this.oper_uid = oper_uid;
	}

	public String getOper_uname() {
		return oper_uname;
	}

	public void setOper_uname(String oper_uname) {
		this.oper_uname = oper_uname;
	}

	public String getOper_uip() {
		return oper_uip;
	}

	public void setOper_uip(String oper_uip) {
		this.oper_uip = oper_uip;
	}

	public String getOper_memo() {
		return oper_memo;
	}

	public void setOper_memo(String oper_memo) {
		this.oper_memo = oper_memo;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

	public Integer getLink_id() {
		return link_id;
	}

	public void setLink_id(Integer linkId) {
		link_id = linkId;
	}

	public Integer getPre_number() {
		return pre_number;
	}

	public void setPre_number(Integer pre_number) {
		this.pre_number = pre_number;
	}

}