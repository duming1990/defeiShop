package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2014-09-22 11:54
 */
public class RankTop extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer top_type;

	private Integer link_type;

	private Integer link_id;

	private String link_name;

	private BigDecimal top_money;

	private Integer top_count;

	private Integer top_tx;

	public RankTop() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTop_type() {
		return top_type;
	}

	public void setTop_type(Integer top_type) {
		this.top_type = top_type;
	}

	public Integer getLink_type() {
		return link_type;
	}

	public void setLink_type(Integer link_type) {
		this.link_type = link_type;
	}

	public Integer getLink_id() {
		return link_id;
	}

	public void setLink_id(Integer link_id) {
		this.link_id = link_id;
	}

	public String getLink_name() {
		return link_name;
	}

	public void setLink_name(String link_name) {
		this.link_name = link_name;
	}

	public BigDecimal getTop_money() {
		return top_money;
	}

	public void setTop_money(BigDecimal top_money) {
		this.top_money = top_money;
	}

	public Integer getTop_count() {
		return top_count;
	}

	public void setTop_count(Integer top_count) {
		this.top_count = top_count;
	}

	public Integer getTop_tx() {
		return top_tx;
	}

	public void setTop_tx(Integer top_tx) {
		this.top_tx = top_tx;
	}

}