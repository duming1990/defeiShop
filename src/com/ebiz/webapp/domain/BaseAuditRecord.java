package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Jiang,JiaYong
 * @date 2016-01-11 20:03:37
 */
public class BaseAuditRecord extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer opt_type;

	private Integer link_id;

	private String link_table;

	private String opt_note;

	private Integer add_user_id;

	private String add_user_name;

	private Date add_date;

	private Integer audit_state;

	private Integer audit_user_id;

	private String audit_user_name;

	private Date audit_date;

	private String audit_note;

	private String remark;

	public BaseAuditRecord() {

	}

	/**
	 * @val ID
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @val ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @val 操作类型
	 */
	public Integer getOpt_type() {
		return opt_type;
	}

	/**
	 * @val 操作类型
	 */
	public void setOpt_type(Integer opt_type) {
		this.opt_type = opt_type;
	}

	/**
	 * @val 关联ID
	 */
	public Integer getLink_id() {
		return link_id;
	}

	/**
	 * @val 关联ID
	 */
	public void setLink_id(Integer link_id) {
		this.link_id = link_id;
	}

	/**
	 * @val 关联表
	 */
	public String getLink_table() {
		return link_table;
	}

	/**
	 * @val 关联表
	 */
	public void setLink_table(String link_table) {
		this.link_table = link_table;
	}

	/**
	 * @val 操作说明
	 */
	public String getOpt_note() {
		return opt_note;
	}

	/**
	 * @val 操作说明
	 */
	public void setOpt_note(String opt_note) {
		this.opt_note = opt_note;
	}

	/**
	 * @val 添加/申请人ID
	 */
	public Integer getAdd_user_id() {
		return add_user_id;
	}

	/**
	 * @val 添加/申请人ID
	 */
	public void setAdd_user_id(Integer add_user_id) {
		this.add_user_id = add_user_id;
	}

	/**
	 * @val 添加/申请人
	 */
	public String getAdd_user_name() {
		return add_user_name;
	}

	/**
	 * @val 添加/申请人
	 */
	public void setAdd_user_name(String add_user_name) {
		this.add_user_name = add_user_name;
	}

	/**
	 * @val 添加/申请时间
	 */
	public Date getAdd_date() {
		return add_date;
	}

	/**
	 * @val 添加/申请时间
	 */
	public void setAdd_date(Date add_date) {
		this.add_date = add_date;
	}

	/**
	 * @val 状态0：待审核（默认），1：审核通过（修改对应企业的状态），-1：审核不通过（对应企业信息不变）
	 */
	public Integer getAudit_state() {
		return audit_state;
	}

	/**
	 * @val 状态0：待审核（默认），1：审核通过（修改对应企业的状态），-1：审核不通过（对应企业信息不变）
	 */
	public void setAudit_state(Integer audit_state) {
		this.audit_state = audit_state;
	}

	/**
	 * @val 审核人ID
	 */
	public Integer getAudit_user_id() {
		return audit_user_id;
	}

	/**
	 * @val 审核人ID
	 */
	public void setAudit_user_id(Integer audit_user_id) {
		this.audit_user_id = audit_user_id;
	}

	/**
	 * @val 审核人
	 */
	public String getAudit_user_name() {
		return audit_user_name;
	}

	/**
	 * @val 审核人
	 */
	public void setAudit_user_name(String audit_user_name) {
		this.audit_user_name = audit_user_name;
	}

	/**
	 * @val 审核时间
	 */
	public Date getAudit_date() {
		return audit_date;
	}

	/**
	 * @val 审核时间
	 */
	public void setAudit_date(Date audit_date) {
		this.audit_date = audit_date;
	}

	/**
	 * @val 审核意见
	 */
	public String getAudit_note() {
		return audit_note;
	}

	/**
	 * @val 审核意见
	 */
	public void setAudit_note(String audit_note) {
		this.audit_note = audit_note;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}