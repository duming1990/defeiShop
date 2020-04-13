package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2019-03-19 14:37
 */
public class CardApply extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private String title;

	private String apply_no;

	private Integer sevice_center_info_id;

	private Integer card_count;

	private BigDecimal card_amount;

	private Date start_date;

	private Date end_date;

	private Integer is_entity;

	private Integer add_uid;

	private Date add_date;

	private Integer is_del;

	private Date del_date;

	private Integer del_uid;

	private Date update_date;

	private Integer update_uid;

	private String remark;

	private Integer audit_state;

	private Integer audit_user_id;

	private Date audit_date;

	private String audit_desc;

	private String rel_address;

	private String rel_name;

	private String rel_mobile;

	private String main_pic;

	private String service_name;

	public CardApply() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getApply_no() {
		return apply_no;
	}

	public void setApply_no(String apply_no) {
		this.apply_no = apply_no;
	}

	public Integer getSevice_center_info_id() {
		return sevice_center_info_id;
	}

	public void setSevice_center_info_id(Integer sevice_center_info_id) {
		this.sevice_center_info_id = sevice_center_info_id;
	}

	public Integer getCard_count() {
		return card_count;
	}

	public void setCard_count(Integer card_count) {
		this.card_count = card_count;
	}

	public BigDecimal getCard_amount() {
		return card_amount;
	}

	public void setCard_amount(BigDecimal card_amount) {
		this.card_amount = card_amount;
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

	public Integer getIs_entity() {
		return is_entity;
	}

	public void setIs_entity(Integer is_entity) {
		this.is_entity = is_entity;
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

	public Integer getAudit_state() {
		return audit_state;
	}

	public void setAudit_state(Integer audit_state) {
		this.audit_state = audit_state;
	}

	public Integer getAudit_user_id() {
		return audit_user_id;
	}

	public void setAudit_user_id(Integer audit_user_id) {
		this.audit_user_id = audit_user_id;
	}

	public Date getAudit_date() {
		return audit_date;
	}

	public void setAudit_date(Date audit_date) {
		this.audit_date = audit_date;
	}

	public String getAudit_desc() {
		return audit_desc;
	}

	public void setAudit_desc(String audit_desc) {
		this.audit_desc = audit_desc;
	}

	public String getRel_address() {
		return rel_address;
	}

	public void setRel_address(String rel_address) {
		this.rel_address = rel_address;
	}

	public String getRel_name() {
		return rel_name;
	}

	public void setRel_name(String rel_name) {
		this.rel_name = rel_name;
	}

	public String getRel_mobile() {
		return rel_mobile;
	}

	public void setRel_mobile(String rel_mobile) {
		this.rel_mobile = rel_mobile;
	}

	public String getMain_pic() {
		return main_pic;
	}

	public void setMain_pic(String main_pic) {
		this.main_pic = main_pic;
	}

	public String getService_name() {
		return service_name;
	}

	public void setService_name(String service_name) {
		this.service_name = service_name;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public Integer getUpdate_uid() {
		return update_uid;
	}

	public void setUpdate_uid(Integer update_uid) {
		this.update_uid = update_uid;
	}

}