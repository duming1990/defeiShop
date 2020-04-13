package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2018-06-12 12:25
 */
public class EntpDuiZhang extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private BigDecimal sum_order_money;

	private BigDecimal sum_money;

	private Integer entp_id;

	private String entp_name;

	private Integer add_user_id;

	private String add_user_name;

	private Date add_date;

	private Integer update_user_id;

	private Date update_date;

	private Integer del_user_id;

	private Date del_date;

	private Integer is_del;

	private Integer confirm_state;

	private Date confirm_date;

	private String confirm_desc;

	private Integer confirm_user_id;

	private Date add_check_date;

	private Date end_check_date;

	// 以前的不知道 15 付款 20退款
	private Integer is_check;

	// 退款备注
	private String refundRemarks;

	// 付款备注

	private BigDecimal cash_pay;

	private BigDecimal cash_rate;

	private String pay_remarks;

	/**
	 * 结算类型 0线上 90线下
	 */
	private Integer order_type;

	public Integer getOrder_type() {
		return order_type;
	}

	public void setOrder_type(Integer order_type) {
		this.order_type = order_type;
	}

	public String getPay_remarks() {
		return pay_remarks;
	}

	public void setPay_remarks(String pay_remarks) {
		this.pay_remarks = pay_remarks;
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

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getRefundRemarks() {
		return refundRemarks;
	}

	public void setRefundRemarks(String refundRemarks) {
		this.refundRemarks = refundRemarks;
	}

	public Date getAdd_check_date() {
		return add_check_date;
	}

	public void setAdd_check_date(Date add_check_date) {
		this.add_check_date = add_check_date;
	}

	public Date getEnd_check_date() {
		return end_check_date;
	}

	public void setEnd_check_date(Date end_check_date) {
		this.end_check_date = end_check_date;
	}

	public Integer getIs_check() {
		return is_check;
	}

	public void setIs_check(Integer is_check) {
		this.is_check = is_check;
	}

	public EntpDuiZhang() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getSum_money() {
		return sum_money;
	}

	public void setSum_money(BigDecimal sum_money) {
		this.sum_money = sum_money;
	}

	public Integer getEntp_id() {
		return entp_id;
	}

	public void setEntp_id(Integer entp_id) {
		this.entp_id = entp_id;
	}

	public String getEntp_name() {
		return entp_name;
	}

	public void setEntp_name(String entp_name) {
		this.entp_name = entp_name;
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

	public Date getAdd_date() {
		return add_date;
	}

	public void setAdd_date(Date add_date) {
		this.add_date = add_date;
	}

	public Integer getUpdate_user_id() {
		return update_user_id;
	}

	public void setUpdate_user_id(Integer update_user_id) {
		this.update_user_id = update_user_id;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public Integer getDel_user_id() {
		return del_user_id;
	}

	public void setDel_user_id(Integer del_user_id) {
		this.del_user_id = del_user_id;
	}

	public Date getDel_date() {
		return del_date;
	}

	public void setDel_date(Date del_date) {
		this.del_date = del_date;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

	public Integer getConfirm_state() {
		return confirm_state;
	}

	public void setConfirm_state(Integer confirm_state) {
		this.confirm_state = confirm_state;
	}

	public Date getConfirm_date() {
		return confirm_date;
	}

	public void setConfirm_date(Date confirm_date) {
		this.confirm_date = confirm_date;
	}

	public String getConfirm_desc() {
		return confirm_desc;
	}

	public void setConfirm_desc(String confirm_desc) {
		this.confirm_desc = confirm_desc;
	}

	public Integer getConfirm_user_id() {
		return confirm_user_id;
	}

	public void setConfirm_user_id(Integer confirm_user_id) {
		this.confirm_user_id = confirm_user_id;
	}

	public BigDecimal getSum_order_money() {
		return sum_order_money;
	}

	public void setSum_order_money(BigDecimal sum_order_money) {
		this.sum_order_money = sum_order_money;
	}

}