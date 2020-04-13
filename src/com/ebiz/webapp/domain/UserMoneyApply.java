package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2015-11-21 14:17
 */
public class UserMoneyApply extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer user_id;

	private BigDecimal cash_count;

	private Integer cash_type;

	private Integer add_uid;

	private Date add_date;

	private String add_memo;

	private Integer audit_uid;

	private Date audit_date;

	private String audit_memo;

	private Integer info_state;

	private Integer is_del;

	private Date del_date;

	private Integer del_user_id;

	private BigDecimal cash_pay;

	private String real_name;

	private String mobile;

	private String bank_name;

	private String bank_account;

	private String bank_account_name;

	private BigDecimal cash_count_before;

	private BigDecimal cash_count_after;

	private BigDecimal cash_count_lock;

	private Date tuikuan_date;

	private String tuikuan_memo;

	private String remark;

	private String proof_img;

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getBank_account() {
		return bank_account;
	}

	public void setBank_account(String bank_account) {
		this.bank_account = bank_account;
	}

	public String getBank_account_name() {
		return bank_account_name;
	}

	public void setBank_account_name(String bank_account_name) {
		this.bank_account_name = bank_account_name;
	}

	public BigDecimal getCash_pay() {
		return cash_pay;
	}

	public void setCash_pay(BigDecimal cash_pay) {
		this.cash_pay = cash_pay;
	}

	public BigDecimal getCash_rate() {
		return cash_rate;
	}

	public void setCash_rate(BigDecimal cash_rate) {
		this.cash_rate = cash_rate;
	}

	private BigDecimal cash_rate;

	public UserMoneyApply() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public BigDecimal getCash_count() {
		return cash_count;
	}

	public void setCash_count(BigDecimal cash_count) {
		this.cash_count = cash_count;
	}

	public Integer getCash_type() {
		return cash_type;
	}

	public void setCash_type(Integer cash_type) {
		this.cash_type = cash_type;
	}

	public Integer getAdd_uid() {
		return add_uid;
	}

	public void setAdd_uid(Integer add_uid) {
		this.add_uid = add_uid;
	}

	public Date getAdd_date() {
		return add_date;
	}

	public void setAdd_date(Date add_date) {
		this.add_date = add_date;
	}

	public String getAdd_memo() {
		return add_memo;
	}

	public void setAdd_memo(String add_memo) {
		this.add_memo = add_memo;
	}

	public Integer getAudit_uid() {
		return audit_uid;
	}

	public void setAudit_uid(Integer audit_uid) {
		this.audit_uid = audit_uid;
	}

	public Date getAudit_date() {
		return audit_date;
	}

	public void setAudit_date(Date audit_date) {
		this.audit_date = audit_date;
	}

	public String getAudit_memo() {
		return audit_memo;
	}

	public void setAudit_memo(String audit_memo) {
		this.audit_memo = audit_memo;
	}

	public Integer getInfo_state() {
		return info_state;
	}

	public void setInfo_state(Integer info_state) {
		this.info_state = info_state;
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

	public BigDecimal getCash_count_before() {
		return cash_count_before;
	}

	public void setCash_count_before(BigDecimal cash_count_before) {
		this.cash_count_before = cash_count_before;
	}

	public BigDecimal getCash_count_after() {
		return cash_count_after;
	}

	public void setCash_count_after(BigDecimal cash_count_after) {
		this.cash_count_after = cash_count_after;
	}

	public BigDecimal getCash_count_lock() {
		return cash_count_lock;
	}

	public void setCash_count_lock(BigDecimal cash_count_lock) {
		this.cash_count_lock = cash_count_lock;
	}

	public Date getTuikuan_date() {
		return tuikuan_date;
	}

	public void setTuikuan_date(Date tuikuan_date) {
		this.tuikuan_date = tuikuan_date;
	}

	public String getTuikuan_memo() {
		return tuikuan_memo;
	}

	public void setTuikuan_memo(String tuikuan_memo) {
		this.tuikuan_memo = tuikuan_memo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProof_img() {
		return proof_img;
	}

	public void setProof_img(String proof_img) {
		this.proof_img = proof_img;
	}

}