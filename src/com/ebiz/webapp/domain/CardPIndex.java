package com.ebiz.webapp.domain;

import java.io.Serializable;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2019-03-19 14:37
 */
public class CardPIndex extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer card_apply_id;

	private Integer service_id;

	private Long p_index;

	private String p_name;

	private String service_name;

	public CardPIndex() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCard_apply_id() {
		return card_apply_id;
	}

	public void setCard_apply_id(Integer card_apply_id) {
		this.card_apply_id = card_apply_id;
	}

	public Long getP_index() {
		return p_index;
	}

	public void setP_index(Long p_index) {
		this.p_index = p_index;
	}

	public String getP_name() {
		return p_name;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
	}

	public Integer getService_id() {
		return service_id;
	}

	public void setService_id(Integer service_id) {
		this.service_id = service_id;
	}

	public String getService_name() {
		return service_name;
	}

	public void setService_name(String service_name) {
		this.service_name = service_name;
	}

}