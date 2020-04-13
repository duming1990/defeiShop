package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2019-03-19 14:37
 */
public class CardInfo extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer card_apply_id;

	private String apply_no;

	private String card_no;

	private String card_ac;

	private String card_pwd;

	private Integer card_type;

	private Integer user_id;

	private Date user_date;

	private Integer entp_id;

	private Date entp_date;

	private BigDecimal card_amount;

	private BigDecimal card_cash;

	private Integer card_state;

	private Date start_date;

	private Date end_date;

	private String dis_memo;

	private Integer order_value;

	private Integer add_uid;

	private Date add_date;

	private Integer is_del;

	private Date del_date;

	private Integer del_uid;

	private String remark;

	private Integer is_send;

	private String received_name;

	private String received_mobile;

	private Integer sevice_center_info_id;

	private Integer gen_id;

	private String gen_no;

	public CardInfo() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCard_apply_id() {
		return card_apply_id;
	}

	public void setCard_apply_id(Integer card_apply_id) {
		this.card_apply_id = card_apply_id;
	}

	public String getApply_no() {
		return apply_no;
	}

	public void setApply_no(String apply_no) {
		this.apply_no = apply_no;
	}

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public String getCard_ac() {
		return card_ac;
	}

	public void setCard_ac(String card_ac) {
		this.card_ac = card_ac;
	}

	public String getCard_pwd() {
		return card_pwd;
	}

	public void setCard_pwd(String card_pwd) {
		this.card_pwd = card_pwd;
	}

	public Integer getCard_type() {
		return card_type;
	}

	public void setCard_type(Integer card_type) {
		this.card_type = card_type;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Date getUser_date() {
		return user_date;
	}

	public void setUser_date(Date user_date) {
		this.user_date = user_date;
	}

	public Integer getEntp_id() {
		return entp_id;
	}

	public void setEntp_id(Integer entp_id) {
		this.entp_id = entp_id;
	}

	public Date getEntp_date() {
		return entp_date;
	}

	public void setEntp_date(Date entp_date) {
		this.entp_date = entp_date;
	}

	public BigDecimal getCard_amount() {
		return card_amount;
	}

	public void setCard_amount(BigDecimal card_amount) {
		this.card_amount = card_amount;
	}

	public BigDecimal getCard_cash() {
		return card_cash;
	}

	public void setCard_cash(BigDecimal card_cash) {
		this.card_cash = card_cash;
	}

	public Integer getCard_state() {
		return card_state;
	}

	public void setCard_state(Integer card_state) {
		this.card_state = card_state;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public String getDis_memo() {
		return dis_memo;
	}

	public void setDis_memo(String dis_memo) {
		this.dis_memo = dis_memo;
	}

	public Integer getOrder_value() {
		return order_value;
	}

	public void setOrder_value(Integer order_value) {
		this.order_value = order_value;
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

	public Integer getDel_uid() {
		return del_uid;
	}

	public void setDel_uid(Integer del_uid) {
		this.del_uid = del_uid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getIs_send() {
		return is_send;
	}

	public void setIs_send(Integer is_send) {
		this.is_send = is_send;
	}

	public String getReceived_name() {
		return received_name;
	}

	public void setReceived_name(String received_name) {
		this.received_name = received_name;
	}

	public String getReceived_mobile() {
		return received_mobile;
	}

	public void setReceived_mobile(String received_mobile) {
		this.received_mobile = received_mobile;
	}

	public Integer getSevice_center_info_id() {
		return sevice_center_info_id;
	}

	public void setSevice_center_info_id(Integer sevice_center_info_id) {
		this.sevice_center_info_id = sevice_center_info_id;
	}

	public String getGen_no() {
		return gen_no;
	}

	public void setGen_no(String gen_no) {
		this.gen_no = gen_no;
	}

	public Integer getGen_id() {
		return gen_id;
	}

	public void setGen_id(Integer gen_id) {
		this.gen_id = gen_id;
	}

}