package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2016-08-07 15:05
 */
public class YhqInfo extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private String yhq_name;

	private Integer yhq_type;

	private Integer yhq_sygz;

	private Integer yhq_number;

	private Integer yhq_number_now;

	private String sygz_id;

	private BigDecimal yhq_sytj;

	private BigDecimal yhq_money;

	private Integer is_del;

	private Date yhq_start_date;

	private Date yhq_end_date;

	private Date add_date;

	private Integer add_user_id;

	private String add_user_name;

	private Date del_date;

	private Integer del_user_id;

	private Integer own_entp_id;

	private String own_entp_name;

	private Integer is_limited;

	private Integer limited_number;

	private Date update_date;

	private Integer update_user_id;

	private String update_user_name;

	private String sygz_names;

	private String qrcpde_path;

	public YhqInfo() {

	}

	public String getQrcpde_path() {
		return qrcpde_path;
	}

	public void setQrcpde_path(String qrcpde_path) {
		this.qrcpde_path = qrcpde_path;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getYhq_name() {
		return yhq_name;
	}

	public void setYhq_name(String yhq_name) {
		this.yhq_name = yhq_name;
	}

	public Integer getYhq_type() {
		return yhq_type;
	}

	public void setYhq_type(Integer yhq_type) {
		this.yhq_type = yhq_type;
	}

	public Integer getYhq_sygz() {
		return yhq_sygz;
	}

	public void setYhq_sygz(Integer yhq_sygz) {
		this.yhq_sygz = yhq_sygz;
	}

	public Integer getYhq_number() {
		return yhq_number;
	}

	public void setYhq_number(Integer yhq_number) {
		this.yhq_number = yhq_number;
	}

	public Integer getYhq_number_now() {
		return yhq_number_now;
	}

	public void setYhq_number_now(Integer yhq_number_now) {
		this.yhq_number_now = yhq_number_now;
	}

	public String getSygz_id() {
		return sygz_id;
	}

	public void setSygz_id(String sygz_id) {
		this.sygz_id = sygz_id;
	}

	public BigDecimal getYhq_sytj() {
		return yhq_sytj;
	}

	public void setYhq_sytj(BigDecimal yhq_sytj) {
		this.yhq_sytj = yhq_sytj;
	}

	public BigDecimal getYhq_money() {
		return yhq_money;
	}

	public void setYhq_money(BigDecimal yhq_money) {
		this.yhq_money = yhq_money;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

	public Date getYhq_start_date() {
		return yhq_start_date;
	}

	public void setYhq_start_date(Date yhq_start_date) {
		this.yhq_start_date = yhq_start_date;
	}

	public Date getYhq_end_date() {
		return yhq_end_date;
	}

	public void setYhq_end_date(Date yhq_end_date) {
		this.yhq_end_date = yhq_end_date;
	}

	public Date getAdd_date() {
		return add_date;
	}

	public void setAdd_date(Date add_date) {
		this.add_date = add_date;
	}

	public Integer getAdd_user_id() {
		return add_user_id;
	}

	public void setAdd_user_id(Integer add_user_id) {
		this.add_user_id = add_user_id;
	}

	public String getAdd_user_name() {
		return add_user_name;
	}

	public void setAdd_user_name(String add_user_name) {
		this.add_user_name = add_user_name;
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

	public Integer getOwn_entp_id() {
		return own_entp_id;
	}

	public void setOwn_entp_id(Integer own_entp_id) {
		this.own_entp_id = own_entp_id;
	}

	public String getOwn_entp_name() {
		return own_entp_name;
	}

	public void setOwn_entp_name(String own_entp_name) {
		this.own_entp_name = own_entp_name;
	}

	public Integer getIs_limited() {
		return is_limited;
	}

	public void setIs_limited(Integer is_limited) {
		this.is_limited = is_limited;
	}

	public Integer getLimited_number() {
		return limited_number;
	}

	public void setLimited_number(Integer limited_number) {
		this.limited_number = limited_number;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public Integer getUpdate_user_id() {
		return update_user_id;
	}

	public void setUpdate_user_id(Integer update_user_id) {
		this.update_user_id = update_user_id;
	}

	public String getUpdate_user_name() {
		return update_user_name;
	}

	public void setUpdate_user_name(String update_user_name) {
		this.update_user_name = update_user_name;
	}

	public String getSygz_names() {
		return sygz_names;
	}

	public void setSygz_names(String sygz_names) {
		this.sygz_names = sygz_names;
	}

}