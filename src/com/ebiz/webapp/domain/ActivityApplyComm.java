package com.ebiz.webapp.domain;

import java.io.Serializable;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2019-04-04 11:00
 */
public class ActivityApplyComm extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer activity_id;

	private Integer activity_apply_id;

	private Integer comm_id;

	private String comm_name;

	private Integer entp_id;

	private Integer comm_tczh_id;

	public ActivityApplyComm() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(Integer activity_id) {
		this.activity_id = activity_id;
	}

	public Integer getActivity_apply_id() {
		return activity_apply_id;
	}

	public void setActivity_apply_id(Integer activity_apply_id) {
		this.activity_apply_id = activity_apply_id;
	}

	public Integer getComm_id() {
		return comm_id;
	}

	public void setComm_id(Integer comm_id) {
		this.comm_id = comm_id;
	}

	public String getComm_name() {
		return comm_name;
	}

	public void setComm_name(String comm_name) {
		this.comm_name = comm_name;
	}

	public Integer getEntp_id() {
		return entp_id;
	}

	public void setEntp_id(Integer entp_id) {
		this.entp_id = entp_id;
	}

	public Integer getComm_tczh_id() {
		return comm_tczh_id;
	}

	public void setComm_tczh_id(Integer comm_tczh_id) {
		this.comm_tczh_id = comm_tczh_id;
	}

}