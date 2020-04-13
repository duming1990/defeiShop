package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2016-07-13 15:19
 */
public class OrderReturnMsg extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer order_return_id;

	private String content;

	private Integer user_id;

	private String user_name;

	private Date add_date;

	private Integer info_type;

	private Integer par_id;

	private Integer audit_state;

	private Integer order_type;

	private Integer is_del;

	private Integer tk_status;

	private Integer return_type;

	private Date audit_date;

	public Integer getOrder_type() {
		return order_type;
	}

	public void setOrder_type(Integer order_type) {
		this.order_type = order_type;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

	public Integer getTk_status() {
		return tk_status;
	}

	public void setTk_status(Integer tk_status) {
		this.tk_status = tk_status;
	}

	public Integer getReturn_type() {
		return return_type;
	}

	public void setReturn_type(Integer return_type) {
		this.return_type = return_type;
	}

	public Date getAudit_date() {
		return audit_date;
	}

	public void setAudit_date(Date audit_date) {
		this.audit_date = audit_date;
	}

	public Integer getAudit_state() {
		return audit_state;
	}

	public void setAudit_state(Integer audit_state) {
		this.audit_state = audit_state;
	}

	public OrderReturnMsg() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrder_return_id() {
		return order_return_id;
	}

	public void setOrder_return_id(Integer order_return_id) {
		this.order_return_id = order_return_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Date getAdd_date() {
		return add_date;
	}

	public void setAdd_date(Date add_date) {
		this.add_date = add_date;
	}

	public Integer getInfo_type() {
		return info_type;
	}

	public void setInfo_type(Integer info_type) {
		this.info_type = info_type;
	}

	public Integer getPar_id() {
		return par_id;
	}

	public void setPar_id(Integer par_id) {
		this.par_id = par_id;
	}

}