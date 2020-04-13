package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2014-05-21 10:41
 */
public class CommTczhPrice extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Date add_date;

	private Integer add_user_id;

	private Date update_date;

	private String tczh_name;

	private String comm_id;

	private BigDecimal comm_price;

	private BigDecimal comm_weight;

	private BigDecimal orig_price;

	private List<CommTczhAttribute> commTczhAttributeList;

	private List<CommTczhPrice> commTczhPriceList;

	private Integer id;

	private Integer order_value;

	private Integer inventory;

	private BigDecimal user_score_percent;

	private BigDecimal cost_price;

	private Integer total_input_stock;

	private Integer tczh_type;

	private Integer buyer_limit_num;

	private Integer par_id;

	private Integer attr_id;

	private String comm_barcode;

	private Integer early_warning_value;

	private BigDecimal group_price;

	public BigDecimal getGroup_price() {
		return group_price;
	}

	public void setGroup_price(BigDecimal group_price) {
		this.group_price = group_price;
	}

	public CommTczhPrice() {

	}

	public Date getAdd_date() {
		return add_date;
	}

	public Integer getAdd_user_id() {
		return add_user_id;
	}

	public String getComm_id() {
		return comm_id;
	}

	public BigDecimal getComm_price() {
		return comm_price;
	}

	public List<CommTczhAttribute> getCommTczhAttributeList() {
		return commTczhAttributeList;
	}

	public List<CommTczhPrice> getCommTczhPriceList() {
		return commTczhPriceList;
	}

	public Integer getId() {
		return id;
	}

	public Integer getOrder_value() {
		return order_value;
	}

	public void setAdd_date(Date add_date) {
		this.add_date = add_date;
	}

	public void setAdd_user_id(Integer add_user_id) {
		this.add_user_id = add_user_id;
	}

	public void setComm_id(String comm_id) {
		this.comm_id = comm_id;
	}

	public void setComm_price(BigDecimal comm_price) {
		this.comm_price = comm_price;
	}

	public void setCommTczhAttributeList(List<CommTczhAttribute> commTczhAttributeList) {
		this.commTczhAttributeList = commTczhAttributeList;
	}

	public void setCommTczhPriceList(List<CommTczhPrice> commTczhPriceList) {
		this.commTczhPriceList = commTczhPriceList;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setOrder_value(Integer order_value) {
		this.order_value = order_value;
	}

	public Integer getInventory() {
		return inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}

	public BigDecimal getUser_score_percent() {
		return user_score_percent;
	}

	public void setUser_score_percent(BigDecimal user_score_percent) {
		this.user_score_percent = user_score_percent;
	}

	public BigDecimal getCost_price() {
		return cost_price;
	}

	public void setCost_price(BigDecimal cost_price) {
		this.cost_price = cost_price;
	}

	public BigDecimal getOrig_price() {
		return orig_price;
	}

	public void setOrig_price(BigDecimal orig_price) {
		this.orig_price = orig_price;
	}

	public Integer getTotal_input_stock() {
		return total_input_stock;
	}

	public void setTotal_input_stock(Integer total_input_stock) {
		this.total_input_stock = total_input_stock;
	}

	public Integer getTczh_type() {
		return tczh_type;
	}

	public void setTczh_type(Integer tczh_type) {
		this.tczh_type = tczh_type;
	}

	public Integer getBuyer_limit_num() {
		return buyer_limit_num;
	}

	public void setBuyer_limit_num(Integer buyer_limit_num) {
		this.buyer_limit_num = buyer_limit_num;
	}

	public Integer getPar_id() {
		return par_id;
	}

	public void setPar_id(Integer par_id) {
		this.par_id = par_id;
	}

	public String getTczh_name() {
		return tczh_name;
	}

	public void setTczh_name(String tczh_name) {
		this.tczh_name = tczh_name;
	}

	public Integer getAttr_id() {
		return attr_id;
	}

	public void setAttr_id(Integer attr_id) {
		this.attr_id = attr_id;
	}

	public String getComm_barcode() {
		return comm_barcode;
	}

	public void setComm_barcode(String comm_barcode) {
		this.comm_barcode = comm_barcode;
	}

	public Integer getEarly_warning_value() {
		return early_warning_value;
	}

	public void setEarly_warning_value(Integer early_warning_value) {
		this.early_warning_value = early_warning_value;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public BigDecimal getComm_weight() {
		return comm_weight;
	}

	public void setComm_weight(BigDecimal comm_weight) {
		this.comm_weight = comm_weight;
	}

}