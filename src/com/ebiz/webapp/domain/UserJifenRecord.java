package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2016-01-02 22:32
 */
public class UserJifenRecord extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer jifen_type;

	private Integer user_id;

	private String user_name;

	private Integer user_level;

	private String user_level_name;

	private Integer card_id;

	private String card_no;

	private Integer user_par_id;

	private String user_par_name;

	private Integer user_par_level;

	private String user_par_level_name;

	private BigDecimal opt_b_score;

	private BigDecimal opt_c_score;

	private BigDecimal opt_a_score;

	private BigDecimal opt_b_dianzibi;

	private BigDecimal opt_c_dianzibi;

	private BigDecimal opt_a_dianzibi;

	private Integer is_del;

	private Date add_date;

	private String remark;

	private Integer order_id;

	private String trade_index;

	private Integer order_type;

	private BigDecimal opt_b_tianfan;

	private BigDecimal opt_c_tianfan;

	private BigDecimal opt_a_tianfan;

	private BigDecimal rest_tianfan;

	private String xiadan_uname;

	private Integer xiadan_uid;

	public UserJifenRecord() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getJifen_type() {
		return jifen_type;
	}

	public void setJifen_type(Integer jifen_type) {
		this.jifen_type = jifen_type;
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

	public Integer getUser_level() {
		return user_level;
	}

	public void setUser_level(Integer user_level) {
		this.user_level = user_level;
	}

	public String getUser_level_name() {
		return user_level_name;
	}

	public void setUser_level_name(String user_level_name) {
		this.user_level_name = user_level_name;
	}

	public Integer getCard_id() {
		return card_id;
	}

	public void setCard_id(Integer card_id) {
		this.card_id = card_id;
	}

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public Integer getUser_par_id() {
		return user_par_id;
	}

	public void setUser_par_id(Integer user_par_id) {
		this.user_par_id = user_par_id;
	}

	public String getUser_par_name() {
		return user_par_name;
	}

	public void setUser_par_name(String user_par_name) {
		this.user_par_name = user_par_name;
	}

	public Integer getUser_par_level() {
		return user_par_level;
	}

	public void setUser_par_level(Integer user_par_level) {
		this.user_par_level = user_par_level;
	}

	public String getUser_par_level_name() {
		return user_par_level_name;
	}

	public void setUser_par_level_name(String user_par_level_name) {
		this.user_par_level_name = user_par_level_name;
	}

	public BigDecimal getOpt_b_score() {
		return opt_b_score;
	}

	public void setOpt_b_score(BigDecimal opt_b_score) {
		this.opt_b_score = opt_b_score;
	}

	public BigDecimal getOpt_c_score() {
		return opt_c_score;
	}

	public void setOpt_c_score(BigDecimal opt_c_score) {
		this.opt_c_score = opt_c_score;
	}

	public BigDecimal getOpt_a_score() {
		return opt_a_score;
	}

	public void setOpt_a_score(BigDecimal opt_a_score) {
		this.opt_a_score = opt_a_score;
	}

	public BigDecimal getOpt_b_dianzibi() {
		return opt_b_dianzibi;
	}

	public void setOpt_b_dianzibi(BigDecimal opt_b_dianzibi) {
		this.opt_b_dianzibi = opt_b_dianzibi;
	}

	public BigDecimal getOpt_c_dianzibi() {
		return opt_c_dianzibi;
	}

	public void setOpt_c_dianzibi(BigDecimal opt_c_dianzibi) {
		this.opt_c_dianzibi = opt_c_dianzibi;
	}

	public BigDecimal getOpt_a_dianzibi() {
		return opt_a_dianzibi;
	}

	public void setOpt_a_dianzibi(BigDecimal opt_a_dianzibi) {
		this.opt_a_dianzibi = opt_a_dianzibi;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}

	public String getTrade_index() {
		return trade_index;
	}

	public void setTrade_index(String trade_index) {
		this.trade_index = trade_index;
	}

	public Integer getOrder_type() {
		return order_type;
	}

	public void setOrder_type(Integer order_type) {
		this.order_type = order_type;
	}

	public BigDecimal getOpt_b_tianfan() {
		return opt_b_tianfan;
	}

	public void setOpt_b_tianfan(BigDecimal opt_b_tianfan) {
		this.opt_b_tianfan = opt_b_tianfan;
	}

	public BigDecimal getOpt_c_tianfan() {
		return opt_c_tianfan;
	}

	public void setOpt_c_tianfan(BigDecimal opt_c_tianfan) {
		this.opt_c_tianfan = opt_c_tianfan;
	}

	public BigDecimal getOpt_a_tianfan() {
		return opt_a_tianfan;
	}

	public void setOpt_a_tianfan(BigDecimal opt_a_tianfan) {
		this.opt_a_tianfan = opt_a_tianfan;
	}

	public BigDecimal getRest_tianfan() {
		return rest_tianfan;
	}

	public void setRest_tianfan(BigDecimal rest_tianfan) {
		this.rest_tianfan = rest_tianfan;
	}

	public String getXiadan_uname() {
		return xiadan_uname;
	}

	public void setXiadan_uname(String xiadan_uname) {
		this.xiadan_uname = xiadan_uname;
	}

	public Integer getXiadan_uid() {
		return xiadan_uid;
	}

	public void setXiadan_uid(Integer xiadan_uid) {
		this.xiadan_uid = xiadan_uid;
	}

}