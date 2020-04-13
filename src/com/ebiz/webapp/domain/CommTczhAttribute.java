package com.ebiz.webapp.domain;

import java.io.Serializable;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2014-05-21 10:41
 */
public class CommTczhAttribute extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer comm_tczh_id;

	private String comm_id;

	private String attr_id;

	private String attr_name;

	private Integer order_value;

	private Integer type;

	private Integer par_id;

	public CommTczhAttribute() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getComm_tczh_id() {
		return comm_tczh_id;
	}

	public void setComm_tczh_id(Integer comm_tczh_id) {
		this.comm_tczh_id = comm_tczh_id;
	}

	public String getComm_id() {
		return comm_id;
	}

	public void setComm_id(String comm_id) {
		this.comm_id = comm_id;
	}

	public String getAttr_id() {
		return attr_id;
	}

	public void setAttr_id(String attr_id) {
		this.attr_id = attr_id;
	}

	public String getAttr_name() {
		return attr_name;
	}

	public void setAttr_name(String attr_name) {
		this.attr_name = attr_name;
	}

	public Integer getOrder_value() {
		return order_value;
	}

	public void setOrder_value(Integer order_value) {
		this.order_value = order_value;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getPar_id() {
		return par_id;
	}

	public void setPar_id(Integer par_id) {
		this.par_id = par_id;
	}

}