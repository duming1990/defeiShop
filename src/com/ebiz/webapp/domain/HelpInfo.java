package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Jin,QingHua
 */
public class HelpInfo extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer h_mod_id;

	private Integer fa_h_mod_id;

	private String title;

	private String title_color;

	private String key_word;

	private Integer pub_user_id;

	private String pub_user_name;

	private Date pub_date;

	private Date modify_date;

	/**
	 * 默认为0
	 */
	private Integer view_count;

	private Integer order_value;

	private Integer is_del;

	private String content;

	private Integer is_common_q;

	public HelpInfo() {

	}

	public HelpInfo(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getH_mod_id() {
		return h_mod_id;
	}

	public void setH_mod_id(Integer h_mod_id) {
		this.h_mod_id = h_mod_id;
	}

	public Integer getFa_h_mod_id() {
		return fa_h_mod_id;
	}

	public void setFa_h_mod_id(Integer faHModId) {
		fa_h_mod_id = faHModId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle_color() {
		return title_color;
	}

	public void setTitle_color(String title_color) {
		this.title_color = title_color;
	}

	public String getKey_word() {
		return key_word;
	}

	public void setKey_word(String key_word) {
		this.key_word = key_word;
	}

	public Integer getPub_user_id() {
		return pub_user_id;
	}

	public void setPub_user_id(Integer pub_user_id) {
		this.pub_user_id = pub_user_id;
	}

	public String getPub_user_name() {
		return pub_user_name;
	}

	public void setPub_user_name(String pub_user_name) {
		this.pub_user_name = pub_user_name;
	}

	public Date getPub_date() {
		return pub_date;
	}

	public void setPub_date(Date pub_date) {
		this.pub_date = pub_date;
	}

	public Date getModify_date() {
		return modify_date;
	}

	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
	}

	public Integer getView_count() {
		return view_count;
	}

	public void setView_count(Integer view_count) {
		this.view_count = view_count;
	}

	public Integer getOrder_value() {
		return order_value;
	}

	public void setOrder_value(Integer order_value) {
		this.order_value = order_value;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getIs_common_q() {
		return is_common_q;
	}

	public void setIs_common_q(Integer isCommonQ) {
		is_common_q = isCommonQ;
	}

}