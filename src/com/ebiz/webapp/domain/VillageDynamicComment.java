package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2018-01-26 15:45
 */
public class VillageDynamicComment extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer link_id;

	private Integer link_user_id;

	private String content;

	private String link_user_name;

	private Integer comment_type;

	private Integer audit_state;

	private Date audit_date;

	private Integer audit_user_id;

	private String audit_user_name;

	private Date add_date;

	private Integer add_user_id;

	private String add_user_name;

	private Integer is_del;

	private Date del_date;

	private Integer del_user_id;

	private String del_user_name;

	private Date update_date;

	private Integer update_user_id;

	private String update_user_name;

	public VillageDynamicComment() {

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

	public void setLink_id(Integer dynamic_id) {
		this.link_id = dynamic_id;
	}

	public Integer getLink_user_id() {
		return link_user_id;
	}

	public void setLink_user_id(Integer link_user_id) {
		this.link_user_id = link_user_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLink_user_name() {
		return link_user_name;
	}

	public void setLink_user_name(String link_user_name) {
		this.link_user_name = link_user_name;
	}

	public Integer getComment_type() {
		return comment_type;
	}

	public void setComment_type(Integer comment_type) {
		this.comment_type = comment_type;
	}

	public Integer getAudit_state() {
		return audit_state;
	}

	public void setAudit_state(Integer audit_state) {
		this.audit_state = audit_state;
	}

	public Date getAudit_date() {
		return audit_date;
	}

	public void setAudit_date(Date audit_date) {
		this.audit_date = audit_date;
	}

	public Integer getAudit_user_id() {
		return audit_user_id;
	}

	public void setAudit_user_id(Integer audit_user_id) {
		this.audit_user_id = audit_user_id;
	}

	public String getAudit_user_name() {
		return audit_user_name;
	}

	public void setAudit_user_name(String audit_user_name) {
		this.audit_user_name = audit_user_name;
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

	public String getDel_user_name() {
		return del_user_name;
	}

	public void setDel_user_name(String del_user_name) {
		this.del_user_name = del_user_name;
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

}