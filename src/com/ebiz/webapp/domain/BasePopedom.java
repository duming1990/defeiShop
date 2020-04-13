package com.ebiz.webapp.domain;

import java.io.Serializable;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2012-02-14 10:56
 */
public class BasePopedom extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer ppdm_code;

	private String ppdm_detail;

	private String ppdm_desc;

	private Integer is_base;

	public BasePopedom() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPpdm_code() {
		return ppdm_code;
	}

	public void setPpdm_code(Integer ppdm_code) {
		this.ppdm_code = ppdm_code;
	}

	public String getPpdm_detail() {
		return ppdm_detail;
	}

	public void setPpdm_detail(String ppdm_detail) {
		this.ppdm_detail = ppdm_detail;
	}

	public String getPpdm_desc() {
		return ppdm_desc;
	}

	public void setPpdm_desc(String ppdm_desc) {
		this.ppdm_desc = ppdm_desc;
	}

	public Integer getIs_base() {
		return is_base;
	}

	public void setIs_base(Integer is_base) {
		this.is_base = is_base;
	}

}