package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2015-11-21 14:17
 */
public class UserBiRecord extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer link_id;

	private Integer bi_chu_or_ru;

	private Integer bi_type;

	private BigDecimal bi_no_before;

	private BigDecimal bi_no;

	private BigDecimal bi_no_after;

	private Integer bi_get_type;

	private String bi_get_memo;

	private Integer add_user_id;

	private Date add_date;

	private Integer bi_state;

	private Integer is_del;

	private Date del_date;

	private Integer del_user_id;

	private Integer is_fanhuan;

	private Integer line_type;

	private String add_uname;

	private Integer dest_uid;

	private String dest_uname;

	private BigDecimal bi_rate;

	private BigDecimal get_rate;

	private BigDecimal tianfan_no_before;

	private BigDecimal tianfan_no;

	private BigDecimal tianfan_no_after;

	private BigDecimal fuxiao_no_before;

	private BigDecimal fuxiao_no;

	private BigDecimal fuxiao_no_after;

	private BigDecimal welfare_no_before;

	private BigDecimal welfare_no;

	private BigDecimal welfare_no_after;

	private Integer order_id;

	public UserBiRecord() {

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

	public Integer getBi_chu_or_ru() {
		return bi_chu_or_ru;
	}

	public void setBi_chu_or_ru(Integer bi_chu_or_ru) {
		this.bi_chu_or_ru = bi_chu_or_ru;
	}

	public Integer getBi_type() {
		return bi_type;
	}

	public void setBi_type(Integer bi_type) {
		this.bi_type = bi_type;
	}

	public Integer getBi_get_type() {
		return bi_get_type;
	}

	public void setBi_get_type(Integer bi_get_type) {
		this.bi_get_type = bi_get_type;
	}

	public String getBi_get_memo() {
		return bi_get_memo;
	}

	public void setBi_get_memo(String bi_get_memo) {
		this.bi_get_memo = bi_get_memo;
	}

	public Integer getAdd_user_id() {
		return add_user_id;
	}

	public void setAdd_user_id(Integer add_user_id) {
		this.add_user_id = add_user_id;
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

	public Integer getDel_user_id() {
		return del_user_id;
	}

	public void setDel_user_id(Integer del_user_id) {
		this.del_user_id = del_user_id;
	}

	public Integer getBi_state() {
		return bi_state;
	}

	public void setBi_state(Integer bi_state) {
		this.bi_state = bi_state;
	}

	public BigDecimal getBi_no() {
		return bi_no;
	}

	public void setBi_no(BigDecimal bi_no) {
		this.bi_no = bi_no;
	}

	public BigDecimal getBi_no_before() {
		return bi_no_before;
	}

	public void setBi_no_before(BigDecimal bi_no_before) {
		this.bi_no_before = bi_no_before;
	}

	public BigDecimal getBi_no_after() {
		return bi_no_after;
	}

	public void setBi_no_after(BigDecimal bi_no_after) {
		this.bi_no_after = bi_no_after;
	}

	public Integer getDest_uid() {
		return dest_uid;
	}

	public void setDest_uid(Integer dest_uid) {
		this.dest_uid = dest_uid;
	}

	public String getDest_uname() {
		return dest_uname;
	}

	public void setDest_uname(String dest_uname) {
		this.dest_uname = dest_uname;
	}

	public BigDecimal getBi_rate() {
		return bi_rate;
	}

	public void setBi_rate(BigDecimal bi_rate) {
		this.bi_rate = bi_rate;
	}

	public Integer getIs_fanhuan() {
		return is_fanhuan;
	}

	public void setIs_fanhuan(Integer is_fanhuan) {
		this.is_fanhuan = is_fanhuan;
	}

	public Integer getLine_type() {
		return line_type;
	}

	public void setLine_type(Integer line_type) {
		this.line_type = line_type;
	}

	public String getAdd_uname() {
		return add_uname;
	}

	public void setAdd_uname(String add_uname) {
		this.add_uname = add_uname;
	}

	public BigDecimal getGet_rate() {
		return get_rate;
	}

	public void setGet_rate(BigDecimal get_rate) {
		this.get_rate = get_rate;
	}

	public BigDecimal getTianfan_no_before() {
		return tianfan_no_before;
	}

	public void setTianfan_no_before(BigDecimal tianfan_no_before) {
		this.tianfan_no_before = tianfan_no_before;
	}

	public BigDecimal getTianfan_no() {
		return tianfan_no;
	}

	public void setTianfan_no(BigDecimal tianfan_no) {
		this.tianfan_no = tianfan_no;
	}

	public BigDecimal getTianfan_no_after() {
		return tianfan_no_after;
	}

	public void setTianfan_no_after(BigDecimal tianfan_no_after) {
		this.tianfan_no_after = tianfan_no_after;
	}

	public BigDecimal getFuxiao_no_before() {
		return fuxiao_no_before;
	}

	public void setFuxiao_no_before(BigDecimal fuxiao_no_before) {
		this.fuxiao_no_before = fuxiao_no_before;
	}

	public BigDecimal getFuxiao_no() {
		return fuxiao_no;
	}

	public void setFuxiao_no(BigDecimal fuxiao_no) {
		this.fuxiao_no = fuxiao_no;
	}

	public BigDecimal getFuxiao_no_after() {
		return fuxiao_no_after;
	}

	public void setFuxiao_no_after(BigDecimal fuxiao_no_after) {
		this.fuxiao_no_after = fuxiao_no_after;
	}

	public Integer getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}

	public BigDecimal getWelfare_no_before() {
		return welfare_no_before;
	}

	public void setWelfare_no_before(BigDecimal welfare_no_before) {
		this.welfare_no_before = welfare_no_before;
	}

	public BigDecimal getWelfare_no() {
		return welfare_no;
	}

	public void setWelfare_no(BigDecimal welfare_no) {
		this.welfare_no = welfare_no;
	}

	public BigDecimal getWelfare_no_after() {
		return welfare_no_after;
	}

	public void setWelfare_no_after(BigDecimal welfare_no_after) {
		this.welfare_no_after = welfare_no_after;
	}

}