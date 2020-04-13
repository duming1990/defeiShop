package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2012-03-21 17:16
 */
public class QaInfo extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer q_type;

	private Integer is_nx;

	private Integer q_user_id;

	private String q_name;

	private String q_tel;

	private String q_email;

	private String q_addr;

	private String q_ip;

	private String q_title;

	private String q_content;

	private Date q_date;

	private String a_content;

	private Integer a_uid;

	private String a_uname;

	private Date a_date;

	private Integer order_value;

	private Integer qa_state;

	public QaInfo() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQ_type() {
		return q_type;
	}

	public void setQ_type(Integer q_type) {
		this.q_type = q_type;
	}

	public Integer getIs_nx() {
		return is_nx;
	}

	public void setIs_nx(Integer is_nx) {
		this.is_nx = is_nx;
	}

	public Integer getQ_user_id() {
		return q_user_id;
	}

	public void setQ_user_id(Integer q_user_id) {
		this.q_user_id = q_user_id;
	}

	public String getQ_name() {
		return q_name;
	}

	public void setQ_name(String q_name) {
		this.q_name = q_name;
	}

	public String getQ_tel() {
		return q_tel;
	}

	public void setQ_tel(String q_tel) {
		this.q_tel = q_tel;
	}

	public String getQ_email() {
		return q_email;
	}

	public void setQ_email(String q_email) {
		this.q_email = q_email;
	}

	public String getQ_addr() {
		return q_addr;
	}

	public void setQ_addr(String q_addr) {
		this.q_addr = q_addr;
	}

	public String getQ_ip() {
		return q_ip;
	}

	public void setQ_ip(String q_ip) {
		this.q_ip = q_ip;
	}

	public String getQ_title() {
		return q_title;
	}

	public void setQ_title(String q_title) {
		this.q_title = q_title;
	}

	public String getQ_content() {
		return q_content;
	}

	public void setQ_content(String q_content) {
		this.q_content = q_content;
	}

	public Date getQ_date() {
		return q_date;
	}

	public void setQ_date(Date q_date) {
		this.q_date = q_date;
	}

	public String getA_content() {
		return a_content;
	}

	public void setA_content(String a_content) {
		this.a_content = a_content;
	}

	public Integer getA_uid() {
		return a_uid;
	}

	public void setA_uid(Integer a_uid) {
		this.a_uid = a_uid;
	}

	public String getA_uname() {
		return a_uname;
	}

	public void setA_uname(String a_uname) {
		this.a_uname = a_uname;
	}

	public Date getA_date() {
		return a_date;
	}

	public void setA_date(Date a_date) {
		this.a_date = a_date;
	}

	public Integer getOrder_value() {
		return order_value;
	}

	public void setOrder_value(Integer order_value) {
		this.order_value = order_value;
	}

	public Integer getQa_state() {
		return qa_state;
	}

	public void setQa_state(Integer qa_state) {
		this.qa_state = qa_state;
	}

}