package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2015-12-23 17:15
 */
public class Tongji extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;
	
	private Integer tongji_type;
	
	private Integer tongji_year;
	
	private Integer tongji_month;
	
	private BigDecimal tongji_num1;
	
	private BigDecimal tongji_num2;
	
	private BigDecimal tongji_num3;
	
	private String tongji_memo;
	
	private Integer is_del;
	
	private Date add_date;
	
	private Integer add_uid;
	
	private Date modify_date;
	
	private Integer modify_uid;
	
	private Date del_date;
	
	private Integer del_uid;
	
	public Tongji() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getTongji_type() {
		return tongji_type;
	}

	public void setTongji_type(Integer tongji_type) {
		this.tongji_type = tongji_type;
	}
	
	public Integer getTongji_year() {
		return tongji_year;
	}

	public void setTongji_year(Integer tongji_year) {
		this.tongji_year = tongji_year;
	}
	
	public Integer getTongji_month() {
		return tongji_month;
	}

	public void setTongji_month(Integer tongji_month) {
		this.tongji_month = tongji_month;
	}
	
	public BigDecimal getTongji_num1() {
		return tongji_num1;
	}

	public void setTongji_num1(BigDecimal tongji_num1) {
		this.tongji_num1 = tongji_num1;
	}
	
	public BigDecimal getTongji_num2() {
		return tongji_num2;
	}

	public void setTongji_num2(BigDecimal tongji_num2) {
		this.tongji_num2 = tongji_num2;
	}
	
	public BigDecimal getTongji_num3() {
		return tongji_num3;
	}

	public void setTongji_num3(BigDecimal tongji_num3) {
		this.tongji_num3 = tongji_num3;
	}
	
	public String getTongji_memo() {
		return tongji_memo;
	}

	public void setTongji_memo(String tongji_memo) {
		this.tongji_memo = tongji_memo;
	}
	
	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}
	
	public Date getAdd_date() {
		return add_date;
	}

	public void setAdd_date(Date add_date) {
		this.add_date = add_date;
	}
	
	public Integer getAdd_uid() {
		return add_uid;
	}

	public void setAdd_uid(Integer add_uid) {
		this.add_uid = add_uid;
	}
	
	public Date getModify_date() {
		return modify_date;
	}

	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
	}
	
	public Integer getModify_uid() {
		return modify_uid;
	}

	public void setModify_uid(Integer modify_uid) {
		this.modify_uid = modify_uid;
	}
	
	public Date getDel_date() {
		return del_date;
	}

	public void setDel_date(Date del_date) {
		this.del_date = del_date;
	}
	
	public Integer getDel_uid() {
		return del_uid;
	}

	public void setDel_uid(Integer del_uid) {
		this.del_uid = del_uid;
	}
	
}