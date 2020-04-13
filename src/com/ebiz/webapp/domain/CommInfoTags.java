package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2017-04-21 14:39
 */
public class CommInfoTags extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer tag_id;

	private String tag_name;

	private Integer comm_id;

	private Date add_date;

	private Long add_user_id;

	private Integer tag_type;

	private Integer order_value;
	
	private Integer p_index;

	public CommInfoTags() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTag_id() {
		return tag_id;
	}

	public void setTag_id(Integer tag_id) {
		this.tag_id = tag_id;
	}

	public String getTag_name() {
		return tag_name;
	}

	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}

	public Integer getComm_id() {
		return comm_id;
	}

	public void setComm_id(Integer comm_id) {
		this.comm_id = comm_id;
	}

	public Date getAdd_date() {
		return add_date;
	}

	public void setAdd_date(Date add_date) {
		this.add_date = add_date;
	}

	public Long getAdd_user_id() {
		return add_user_id;
	}

	public void setAdd_user_id(Long add_user_id) {
		this.add_user_id = add_user_id;
	}

	public Integer getTag_type() {
		return tag_type;
	}

	public void setTag_type(Integer tag_type) {
		this.tag_type = tag_type;
	}

	public Integer getOrder_value() {
		return order_value;
	}

	public void setOrder_value(Integer order_value) {
		this.order_value = order_value;
	}

	public Integer getP_index() {
		return p_index;
	}

	public void setP_index(Integer p_index) {
		this.p_index = p_index;
	}

}