package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2016-07-13 15:19
 */
public class OrderReturnMoney extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;
	
	private Integer order_return_id;
	
	private Integer user_id;
	
	private String user_name;
	
	private Date add_date;
	
	private Long order_detail_id;
	
	private Long comm_id;
	
	private BigDecimal price;
	
	private Integer num;
	
	private String comm_name;
	
	private BigDecimal tk_money;
	
	private String tk_remark;
	
	private Integer tk_type;
	
	public OrderReturnMoney() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getOrder_return_id() {
		return order_return_id;
	}

	public void setOrder_return_id(Integer order_return_id) {
		this.order_return_id = order_return_id;
	}
	
	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	public Date getAdd_date() {
		return add_date;
	}

	public void setAdd_date(Date add_date) {
		this.add_date = add_date;
	}
	
	public Long getOrder_detail_id() {
		return order_detail_id;
	}

	public void setOrder_detail_id(Long order_detail_id) {
		this.order_detail_id = order_detail_id;
	}
	
	public Long getComm_id() {
		return comm_id;
	}

	public void setComm_id(Long comm_id) {
		this.comm_id = comm_id;
	}
	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	public String getComm_name() {
		return comm_name;
	}

	public void setComm_name(String comm_name) {
		this.comm_name = comm_name;
	}
	
	public BigDecimal getTk_money() {
		return tk_money;
	}

	public void setTk_money(BigDecimal tk_money) {
		this.tk_money = tk_money;
	}
	
	public String getTk_remark() {
		return tk_remark;
	}

	public void setTk_remark(String tk_remark) {
		this.tk_remark = tk_remark;
	}
	
	public Integer getTk_type() {
		return tk_type;
	}

	public void setTk_type(Integer tk_type) {
		this.tk_type = tk_type;
	}
	
}