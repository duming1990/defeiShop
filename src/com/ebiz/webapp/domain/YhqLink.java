package com.ebiz.webapp.domain;

import java.io.Serializable;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2018-05-22 14:59
 */
public class YhqLink extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;
	
	private Integer yhq_id;
	
	private Integer comm_id;
	
	public YhqLink() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getYhq_id() {
		return yhq_id;
	}

	public void setYhq_id(Integer yhq_id) {
		this.yhq_id = yhq_id;
	}
	
	public Integer getComm_id() {
		return comm_id;
	}

	public void setComm_id(Integer comm_id) {
		this.comm_id = comm_id;
	}
	
}