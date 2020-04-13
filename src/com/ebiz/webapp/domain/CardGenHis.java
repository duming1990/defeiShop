package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2019-03-19 14:37
 */
public class CardGenHis extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer card_apply_id;

	private String gen_no;

	private Integer card_type;

	private BigDecimal card_amount;

	private String pre_code;

	private String b_code_no;

	private String e_code_no;

	private Integer gen_count;

	private Integer used_count;

	private Integer info_state;

	private String remark;

	private Integer order_value;

	private Integer is_system;

	private Integer add_uid;

	private Date add_date;

	private Integer modify_uid;

	private Date modify_date;

	private Integer is_del;

	private Date del_date;

	private Integer del_uid;

	private Integer own_service_id;

	private String own_service_name;

	public CardGenHis() {

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

	public String getGen_no() {
		return gen_no;
	}

	public void setGen_no(String gen_no) {
		this.gen_no = gen_no;
	}

	public Integer getCard_type() {
		return card_type;
	}

	public void setCard_type(Integer card_type) {
		this.card_type = card_type;
	}

	public BigDecimal getCard_amount() {
		return card_amount;
	}

	public void setCard_amount(BigDecimal card_amount) {
		this.card_amount = card_amount;
	}

	public String getPre_code() {
		return pre_code;
	}

	public void setPre_code(String pre_code) {
		this.pre_code = pre_code;
	}

	public String getB_code_no() {
		return b_code_no;
	}

	public void setB_code_no(String b_code_no) {
		this.b_code_no = b_code_no;
	}

	public String getE_code_no() {
		return e_code_no;
	}

	public void setE_code_no(String e_code_no) {
		this.e_code_no = e_code_no;
	}

	public Integer getGen_count() {
		return gen_count;
	}

	public void setGen_count(Integer gen_count) {
		this.gen_count = gen_count;
	}

	public Integer getUsed_count() {
		return used_count;
	}

	public void setUsed_count(Integer used_count) {
		this.used_count = used_count;
	}

	public Integer getInfo_state() {
		return info_state;
	}

	public void setInfo_state(Integer info_state) {
		this.info_state = info_state;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getOrder_value() {
		return order_value;
	}

	public void setOrder_value(Integer order_value) {
		this.order_value = order_value;
	}

	public Integer getIs_system() {
		return is_system;
	}

	public void setIs_system(Integer is_system) {
		this.is_system = is_system;
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

	public Integer getModify_uid() {
		return modify_uid;
	}

	public void setModify_uid(Integer modify_uid) {
		this.modify_uid = modify_uid;
	}

	public Date getModify_date() {
		return modify_date;
	}

	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
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

	public Integer getOwn_service_id() {
		return own_service_id;
	}

	public void setOwn_service_id(Integer own_service_id) {
		this.own_service_id = own_service_id;
	}

	public String getOwn_service_name() {
		return own_service_name;
	}

	public void setOwn_service_name(String own_service_name) {
		this.own_service_name = own_service_name;
	}

}