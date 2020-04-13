package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2012-05-30 08:53
 */
public class BaseClass extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer cls_id;

	private Integer cls_scope;

	private String cls_name;

	private Integer par_id;

	private Integer root_id;

	private Integer order_value;

	private Integer is_lock;

	private Integer is_del;

	private Date add_date;

	private Integer add_user_id;

	private String add_user_name;

	private Date update_date;

	private Integer update_user_id;

	private Date del_date;

	private Integer del_user_id;

	private Integer template_type;

	private Integer link_id;

	private Integer cls_level;

	private String cls_code;

	private String cls_url;

	private List<CommInfo> commInfoList;

	private Integer is_show;

	private String image_path;

	public String getImage_path() {
		return image_path;
	}

	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}

	public BaseClass() {

	}

	public Integer getLink_id() {
		return link_id;
	}

	public void setLink_id(Integer linkId) {
		link_id = linkId;
	}

	public Integer getCls_id() {
		return cls_id;
	}

	public void setCls_id(Integer cls_id) {
		this.cls_id = cls_id;
	}

	public Integer getCls_scope() {
		return cls_scope;
	}

	public void setCls_scope(Integer cls_scope) {
		this.cls_scope = cls_scope;
	}

	public String getCls_name() {
		return cls_name;
	}

	public void setCls_name(String cls_name) {
		this.cls_name = cls_name;
	}

	public Integer getPar_id() {
		return par_id;
	}

	public void setPar_id(Integer par_id) {
		this.par_id = par_id;
	}

	public Integer getRoot_id() {
		return root_id;
	}

	public void setRoot_id(Integer root_id) {
		this.root_id = root_id;
	}

	public Integer getOrder_value() {
		return order_value;
	}

	public void setOrder_value(Integer order_value) {
		this.order_value = order_value;
	}

	public Integer getIs_lock() {
		return is_lock;
	}

	public void setIs_lock(Integer is_lock) {
		this.is_lock = is_lock;
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

	public Integer getTemplate_type() {
		return template_type;
	}

	public void setTemplate_type(Integer template_type) {
		this.template_type = template_type;
	}

	public Integer getCls_level() {
		return cls_level;
	}

	public void setCls_level(Integer cls_level) {
		this.cls_level = cls_level;
	}

	public String getCls_code() {
		return cls_code;
	}

	public void setCls_code(String cls_code) {
		this.cls_code = cls_code;
	}

	public List<CommInfo> getCommInfoList() {
		return commInfoList;
	}

	public void setCommInfoList(List<CommInfo> commInfoList) {
		this.commInfoList = commInfoList;
	}

	public String getCls_url() {
		return cls_url;
	}

	public void setCls_url(String cls_url) {
		this.cls_url = cls_url;
	}

	public Integer getIs_show() {
		return is_show;
	}

	public void setIs_show(Integer is_show) {
		this.is_show = is_show;
	}

}