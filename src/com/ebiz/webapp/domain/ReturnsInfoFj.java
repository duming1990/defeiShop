package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @version 2014-06-20 09:55
 */
public class ReturnsInfoFj extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer returns_info_id;

	private Integer fj_type;

	private String fj_addr;

	private Date add_date;

	private Integer c_returns_info_id;

	public ReturnsInfoFj() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getReturns_info_id() {
		return returns_info_id;
	}

	public void setReturns_info_id(Integer returns_info_id) {
		this.returns_info_id = returns_info_id;
	}

	public Integer getFj_type() {
		return fj_type;
	}

	public void setFj_type(Integer fj_type) {
		this.fj_type = fj_type;
	}

	public String getFj_addr() {
		return fj_addr;
	}

	public void setFj_addr(String fj_addr) {
		this.fj_addr = fj_addr;
	}

	public Date getAdd_date() {
		return add_date;
	}

	public void setAdd_date(Date add_date) {
		this.add_date = add_date;
	}

	public Integer getC_returns_info_id() {
		return c_returns_info_id;
	}

	public void setC_returns_info_id(Integer c_returns_info_id) {
		this.c_returns_info_id = c_returns_info_id;
	}

}