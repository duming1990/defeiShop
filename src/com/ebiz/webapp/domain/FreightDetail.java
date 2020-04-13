package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2014-05-22 19:37
 */
public class FreightDetail extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer fre_id;

	private String area_name;

	private String area_pindex;

	private BigDecimal first_weight;

	private BigDecimal first_price;

	private BigDecimal sed_weight;

	private BigDecimal sed_price;

	private Integer delivery_way;

	public FreightDetail() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFre_id() {
		return fre_id;
	}

	public void setFre_id(Integer fre_id) {
		this.fre_id = fre_id;
	}

	public String getArea_name() {
		return area_name;
	}

	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}

	public String getArea_pindex() {
		return area_pindex;
	}

	public void setArea_pindex(String area_pindex) {
		this.area_pindex = area_pindex;
	}

	public BigDecimal getFirst_weight() {
		return first_weight;
	}

	public void setFirst_weight(BigDecimal first_weight) {
		this.first_weight = first_weight;
	}

	public BigDecimal getFirst_price() {
		return first_price;
	}

	public void setFirst_price(BigDecimal first_price) {
		this.first_price = first_price;
	}

	public BigDecimal getSed_weight() {
		return sed_weight;
	}

	public void setSed_weight(BigDecimal sed_weight) {
		this.sed_weight = sed_weight;
	}

	public BigDecimal getSed_price() {
		return sed_price;
	}

	public void setSed_price(BigDecimal sed_price) {
		this.sed_price = sed_price;
	}

	public Integer getDelivery_way() {
		return delivery_way;
	}

	public void setDelivery_way(Integer delivery_way) {
		this.delivery_way = delivery_way;
	}

}