package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:55
 */
public class OrderMergerInfo extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private String out_trade_no;

	private Integer par_id;

	private String trade_index;

	private Date add_date;

	private Integer entp_id;

	private Integer add_user_id;

	private Integer pay_state;

	private Integer is_freeship;

	private Integer flow_type;

	private Integer pay_type;

	private Integer is_price_modify;

	private String trade_no;

	private BigDecimal jfdh_money;

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public OrderMergerInfo() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public Integer getPar_id() {
		return par_id;
	}

	public void setPar_id(Integer par_id) {
		this.par_id = par_id;
	}

	public String getTrade_index() {
		return trade_index;
	}

	public void setTrade_index(String trade_index) {
		this.trade_index = trade_index;
	}

	public Date getAdd_date() {
		return add_date;
	}

	public void setAdd_date(Date add_date) {
		this.add_date = add_date;
	}

	public Integer getEntp_id() {
		return entp_id;
	}

	public void setEntp_id(Integer entp_id) {
		this.entp_id = entp_id;
	}

	public Integer getAdd_user_id() {
		return add_user_id;
	}

	public void setAdd_user_id(Integer add_user_id) {
		this.add_user_id = add_user_id;
	}

	public Integer getPay_state() {
		return pay_state;
	}

	public void setPay_state(Integer pay_state) {
		this.pay_state = pay_state;
	}

	public Integer getIs_freeship() {
		return is_freeship;
	}

	public void setIs_freeship(Integer is_freeship) {
		this.is_freeship = is_freeship;
	}

	public Integer getFlow_type() {
		return flow_type;
	}

	public void setFlow_type(Integer flow_type) {
		this.flow_type = flow_type;
	}

	public Integer getPay_type() {
		return pay_type;
	}

	public void setPay_type(Integer pay_type) {
		this.pay_type = pay_type;
	}

	public Integer getIs_price_modify() {
		return is_price_modify;
	}

	public void setIs_price_modify(Integer is_price_modify) {
		this.is_price_modify = is_price_modify;
	}

	public BigDecimal getJfdh_money() {
		return jfdh_money;
	}

	public void setJfdh_money(BigDecimal jfdh_money) {
		this.jfdh_money = jfdh_money;
	}

}