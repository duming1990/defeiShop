package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2017-04-21 14:39
 */
public class BaseComminfoTags extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private String tag_name;

	private Integer tag_type;

	private Integer is_show;

	private Integer order_value;

	private Integer is_lock;

	private Integer is_del;

	private Date add_date;

	private Integer add_user_id;

	private Integer cls_id;

	private String cls_name;

	private String image_path;

	private String add_user_name;

	private Date update_date;

	private Integer update_user_id;

	private Date del_date;

	private Integer del_user_id;

	private Integer link_entp_id;

	private Integer p_index;

	private Integer index_lock;

	private List<CommInfo> commInfoList = new ArrayList<CommInfo>();

	public BaseComminfoTags() {

	}

	public Integer getIndex_lock() {
		return index_lock;
	}

	public void setIndex_lock(Integer index_lock) {
		this.index_lock = index_lock;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTag_name() {
		return tag_name;
	}

	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}

	public Integer getTag_type() {
		return tag_type;
	}

	public void setTag_type(Integer tag_type) {
		this.tag_type = tag_type;
	}

	public Integer getIs_show() {
		return is_show;
	}

	public void setIs_show(Integer is_show) {
		this.is_show = is_show;
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

	public Integer getCls_id() {
		return cls_id;
	}

	public void setCls_id(Integer cls_id) {
		this.cls_id = cls_id;
	}

	public String getCls_name() {
		return cls_name;
	}

	public void setCls_name(String cls_name) {
		this.cls_name = cls_name;
	}

	public String getImage_path() {
		return image_path;
	}

	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}

	public Integer getLink_entp_id() {
		return link_entp_id;
	}

	public void setLink_entp_id(Integer link_entp_id) {
		this.link_entp_id = link_entp_id;
	}

	public Integer getP_index() {
		return p_index;
	}

	public void setP_index(Integer p_index) {
		this.p_index = p_index;
	}

	public List<CommInfo> getCommInfoList() {
		return commInfoList;
	}

	public void setCommInfoList(List<CommInfo> commInfoList) {
		this.commInfoList = commInfoList;
	}

}