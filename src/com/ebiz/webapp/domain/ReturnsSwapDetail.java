package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @version 2014-06-20 15:49
 */
public class ReturnsSwapDetail extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer order_info_details_id;

	private Integer apply_type;

	private Integer comm_id;

	private String comm_name;

	private Integer comm_count;

	private String return_desc;

	private String return_addr;

	private Integer user_id;

	private String user_name;

	private Date add_date;

	private Integer is_del;

	private Date del_date;

	private String memo;

	private BigDecimal comm_price;

	private Integer returns_info_id;

	private OrderInfoDetails de;

	public OrderInfoDetails getDe() {
		return de;
	}

	public void setDe(OrderInfoDetails detail) {
		this.de = detail;
	}

	private List<ReturnsInfoFj> fjList = new ArrayList<ReturnsInfoFj>();

	public List<ReturnsInfoFj> getFjList() {
		return fjList;
	}

	public void setFjList(List<ReturnsInfoFj> fjList) {
		this.fjList = fjList;
	}

	public ReturnsSwapDetail() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrder_info_details_id() {
		return order_info_details_id;
	}

	public void setOrder_info_details_id(Integer order_info_details_id) {
		this.order_info_details_id = order_info_details_id;
	}

	public Integer getApply_type() {
		return apply_type;
	}

	public void setApply_type(Integer apply_type) {
		this.apply_type = apply_type;
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

	public Integer getComm_count() {
		return comm_count;
	}

	public void setComm_count(Integer comm_count) {
		this.comm_count = comm_count;
	}

	public String getReturn_desc() {
		return return_desc;
	}

	public void setReturn_desc(String return_desc) {
		this.return_desc = return_desc;
	}

	public String getReturn_addr() {
		return return_addr;
	}

	public void setReturn_addr(String return_addr) {
		this.return_addr = return_addr;
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

	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

	public Date getDel_date() {
		return del_date;
	}

	public void setDel_date(Date del_date) {
		this.del_date = del_date;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public BigDecimal getComm_price() {
		return comm_price;
	}

	public void setComm_price(BigDecimal comm_price) {
		this.comm_price = comm_price;
	}

	public Integer getReturns_info_id() {
		return returns_info_id;
	}

	public void setReturns_info_id(Integer returns_info_id) {
		this.returns_info_id = returns_info_id;
	}

}