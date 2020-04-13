package com.ebiz.webapp.domain;

import java.io.Serializable;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @version 2014-06-19 10:12
 */
public class ReturnsInfoOrderRelation extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private String p_trade_index;

	private String trade_index;

	private Integer trade_type;

	private Integer id;

	public ReturnsInfoOrderRelation() {

	}

	public String getP_trade_index() {
		return p_trade_index;
	}

	public void setP_trade_index(String p_trade_index) {
		this.p_trade_index = p_trade_index;
	}

	public String getTrade_index() {
		return trade_index;
	}

	public void setTrade_index(String trade_index) {
		this.trade_index = trade_index;
	}

	public Integer getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(Integer trade_type) {
		this.trade_type = trade_type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}