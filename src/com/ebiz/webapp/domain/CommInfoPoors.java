package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2018-01-22 10:47
 */
public class CommInfoPoors extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer comm_id;

	private Integer poor_id;

	private Date add_date;

	private Integer add_user_id;

	private List<PdImgs> pdImgsList;

	private List<PdImgs> commImgsList;
	
	private Integer is_temp;

	public List<PdImgs> getCommImgsList() {
		return commImgsList;
	}

	public void setCommImgsList(List<PdImgs> commImgsList) {
		this.commImgsList = commImgsList;
	}

	public List<PdImgs> getPdImgsList() {
		return pdImgsList;
	}

	public void setPdImgsList(List<PdImgs> pdImgsList) {
		this.pdImgsList = pdImgsList;
	}

	public CommInfoPoors() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getComm_id() {
		return comm_id;
	}

	public void setComm_id(Integer comm_id) {
		this.comm_id = comm_id;
	}

	public Integer getPoor_id() {
		return poor_id;
	}

	public void setPoor_id(Integer poor_id) {
		this.poor_id = poor_id;
	}

	public Date getAdd_date() {
		return add_date;
	}

	public void setAdd_date(Date add_date) {
		this.add_date = add_date;
	}

	public Integer getAdd_user_id() {
		return add_user_id;
	}

	public void setAdd_user_id(Integer add_user_id) {
		this.add_user_id = add_user_id;
	}

	public Integer getIs_temp() {
		return is_temp;
	}

	public void setIs_temp(Integer is_temp) {
		this.is_temp = is_temp;
	}

}