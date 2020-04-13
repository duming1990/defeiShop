package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2012-03-30 15:43
 */
public class MailServer extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private String s_address;

	private Integer s_type;

	private String mail_suffix;

	private String login_addr;

	private Integer order_value;

	private Date add_date;

	private Integer is_del;

	public MailServer() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getS_address() {
		return s_address;
	}

	public void setS_address(String s_address) {
		this.s_address = s_address;
	}

	public Integer getS_type() {
		return s_type;
	}

	public void setS_type(Integer s_type) {
		this.s_type = s_type;
	}

	public String getMail_suffix() {
		return mail_suffix;
	}

	public void setMail_suffix(String mail_suffix) {
		this.mail_suffix = mail_suffix;
	}

	public String getLogin_addr() {
		return login_addr;
	}

	public void setLogin_addr(String login_addr) {
		this.login_addr = login_addr;
	}

	public Integer getOrder_value() {
		return order_value;
	}

	public void setOrder_value(Integer order_value) {
		this.order_value = order_value;
	}

	public Date getAdd_date() {
		return add_date;
	}

	public void setAdd_date(Date add_date) {
		this.add_date = add_date;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

}