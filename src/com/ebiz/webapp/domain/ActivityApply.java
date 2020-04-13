package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2019-04-04 11:00
 */
public class ActivityApply extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer link_id;

	private Integer entp_id;

	private Date add_date;

	private Date update_date;

	private Integer audit_state;

	private String remark;

	private String entp_name;

	private Integer user_id;

	private String user_name;

	private String qrcode_path;

	private Integer pay_type;

	private List<ActivityApplyComm> list = new ArrayList<ActivityApplyComm>();

	private List<CommTczhPrice> commTczhPriceList = new ArrayList<CommTczhPrice>();

	public Integer getPay_type() {
		return pay_type;
	}

	public void setPay_type(Integer pay_type) {
		this.pay_type = pay_type;
	}

	public String getQrcode_path() {
		return qrcode_path;
	}

	public void setQrcode_path(String qrcode_path) {
		this.qrcode_path = qrcode_path;
	}

	public List<ActivityApplyComm> getList() {
		return list;
	}

	public void setList(List<ActivityApplyComm> list) {
		this.list = list;
	}

	public ActivityApply() {

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

	public Integer getEntp_id() {
		return entp_id;
	}

	public void setEntp_id(Integer entp_id) {
		this.entp_id = entp_id;
	}

	public Date getAdd_date() {
		return add_date;
	}

	public void setAdd_date(Date add_date) {
		this.add_date = add_date;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public Integer getAudit_state() {
		return audit_state;
	}

	public void setAudit_state(Integer audit_state) {
		this.audit_state = audit_state;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getEntp_name() {
		return entp_name;
	}

	public void setEntp_name(String entp_name) {
		this.entp_name = entp_name;
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

	public List<CommTczhPrice> getCommTczhPriceList() {
		return commTczhPriceList;
	}

	public void setCommTczhPriceList(List<CommTczhPrice> commTczhPriceList) {
		this.commTczhPriceList = commTczhPriceList;
	}

}