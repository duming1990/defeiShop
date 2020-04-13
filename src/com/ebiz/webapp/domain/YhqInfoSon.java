package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2016-08-07 15:05
 */
public class YhqInfoSon extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer link_id;

	private Integer yhq_state;

	private Date get_date;

	private Date use_date;

	private String link_trade_index;

	private Integer link_user_id;

	private Integer is_used;

	private Integer own_entp_id;

	private String own_entp_name;

	private Date yhq_end_date;

	private Date yhq_start_date;

	private Integer comm_id;

	public YhqInfoSon() {

	}

	public Date getYhq_end_date() {
		return yhq_end_date;
	}

	public void setYhq_end_date(Date yhq_end_date) {
		this.yhq_end_date = yhq_end_date;
	}

	public Date getYhq_start_date() {
		return yhq_start_date;
	}

	public void setYhq_start_date(Date yhq_start_date) {
		this.yhq_start_date = yhq_start_date;
	}

	public Integer getComm_id() {
		return comm_id;
	}

	public void setComm_id(Integer comm_id) {
		this.comm_id = comm_id;
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

	public Integer getYhq_state() {
		return yhq_state;
	}

	public void setYhq_state(Integer yhq_state) {
		this.yhq_state = yhq_state;
	}

	public Date getGet_date() {
		return get_date;
	}

	public void setGet_date(Date get_date) {
		this.get_date = get_date;
	}

	public Date getUse_date() {
		return use_date;
	}

	public void setUse_date(Date use_date) {
		this.use_date = use_date;
	}

	public String getLink_trade_index() {
		return link_trade_index;
	}

	public void setLink_trade_index(String link_trade_index) {
		this.link_trade_index = link_trade_index;
	}

	public Integer getLink_user_id() {
		return link_user_id;
	}

	public void setLink_user_id(Integer link_user_id) {
		this.link_user_id = link_user_id;
	}

	public Integer getIs_used() {
		return is_used;
	}

	public void setIs_used(Integer is_used) {
		this.is_used = is_used;
	}

	public Integer getOwn_entp_id() {
		return own_entp_id;
	}

	public void setOwn_entp_id(Integer own_entp_id) {
		this.own_entp_id = own_entp_id;
	}

	public String getOwn_entp_name() {
		return own_entp_name;
	}

	public void setOwn_entp_name(String own_entp_name) {
		this.own_entp_name = own_entp_name;
	}

}