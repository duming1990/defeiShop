package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:54
 */
public class WlCompInfo extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private String wl_code;

	private String wl_comp_name;

	private String wl_comp_en_name;

	private Integer p_index;

	private String addr;

	private String link_man;

	private String tel;

	private String fax;

	private Integer order_value;

	private Integer is_lock;

	private Integer is_del;

	private Date add_date;

	private Integer add_user_id;

	private Date update_date;

	private Integer update_user_id;

	private Date del_date;

	private Integer del_user_id;

	private String wl_comp_url;

	private Integer is_collaborate;

	private String remark;

	private Integer comp_type;

	private String p_alpha;

	public WlCompInfo() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWl_code() {
		return wl_code;
	}

	public void setWl_code(String wl_code) {
		this.wl_code = wl_code;
	}

	public String getWl_comp_name() {
		return wl_comp_name;
	}

	public void setWl_comp_name(String wl_comp_name) {
		this.wl_comp_name = wl_comp_name;
	}

	public String getWl_comp_en_name() {
		return wl_comp_en_name;
	}

	public void setWl_comp_en_name(String wl_comp_en_name) {
		this.wl_comp_en_name = wl_comp_en_name;
	}

	public Integer getP_index() {
		return p_index;
	}

	public void setP_index(Integer p_index) {
		this.p_index = p_index;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getLink_man() {
		return link_man;
	}

	public void setLink_man(String link_man) {
		this.link_man = link_man;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
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

	public String getWl_comp_url() {
		return wl_comp_url;
	}

	public void setWl_comp_url(String wl_comp_url) {
		this.wl_comp_url = wl_comp_url;
	}

	public Integer getIs_collaborate() {
		return is_collaborate;
	}

	public void setIs_collaborate(Integer is_collaborate) {
		this.is_collaborate = is_collaborate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getComp_type() {
		return comp_type;
	}

	public void setComp_type(Integer comp_type) {
		this.comp_type = comp_type;
	}

	public String getP_alpha() {
		return p_alpha;
	}

	public void setP_alpha(String p_alpha) {
		this.p_alpha = p_alpha;
	}

}