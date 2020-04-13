package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2014-08-08 09:55
 */
public class DiscountStores extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private String discount_name;

	private Integer discount_type;

	private Date discount_begin_date;

	private Date discount_end_date;

	private Integer discount_tj;

	private Integer discount_tj_content;

	private Integer discount_method;

	private String discount_type_content;

	private String discount_type_content2;

	private Integer by_type;

	private String p_indexs;

	private Integer discount_comm_type;

	private String cls_ids;

	private String discount_comm_ids;

	private Integer entp_id;

	private String entp_name;

	private Integer add_user_id;

	private Date add_date;

	private Date update_date;

	private Date del_date;

	private Integer update_user_id;

	private Integer del_user_id;

	private Integer is_del;

	private Integer order_value;

	private String cls_names;

	private Integer hd_show_type;

	private String hy_details_content;

	private Integer is_open;

	private Integer is_time_gift;

	private Integer time_gift_inventory;

	public DiscountStores() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDiscount_name() {
		return discount_name;
	}

	public void setDiscount_name(String discount_name) {
		this.discount_name = discount_name;
	}

	public Integer getDiscount_type() {
		return discount_type;
	}

	public void setDiscount_type(Integer discount_type) {
		this.discount_type = discount_type;
	}

	public Date getDiscount_begin_date() {
		return discount_begin_date;
	}

	public void setDiscount_begin_date(Date discount_begin_date) {
		this.discount_begin_date = discount_begin_date;
	}

	public Date getDiscount_end_date() {
		return discount_end_date;
	}

	public void setDiscount_end_date(Date discount_end_date) {
		this.discount_end_date = discount_end_date;
	}

	public Integer getDiscount_tj() {
		return discount_tj;
	}

	public void setDiscount_tj(Integer discount_tj) {
		this.discount_tj = discount_tj;
	}

	public Integer getDiscount_tj_content() {
		return discount_tj_content;
	}

	public void setDiscount_tj_content(Integer discount_tj_content) {
		this.discount_tj_content = discount_tj_content;
	}

	public Integer getDiscount_method() {
		return discount_method;
	}

	public void setDiscount_method(Integer discount_method) {
		this.discount_method = discount_method;
	}

	public String getDiscount_type_content() {
		return discount_type_content;
	}

	public void setDiscount_type_content(String discount_type_content) {
		this.discount_type_content = discount_type_content;
	}

	public String getDiscount_type_content2() {
		return discount_type_content2;
	}

	public void setDiscount_type_content2(String discount_type_content2) {
		this.discount_type_content2 = discount_type_content2;
	}

	public Integer getBy_type() {
		return by_type;
	}

	public void setBy_type(Integer by_type) {
		this.by_type = by_type;
	}

	public String getP_indexs() {
		return p_indexs;
	}

	public void setP_indexs(String p_indexs) {
		this.p_indexs = p_indexs;
	}

	public Integer getDiscount_comm_type() {
		return discount_comm_type;
	}

	public void setDiscount_comm_type(Integer discount_comm_type) {
		this.discount_comm_type = discount_comm_type;
	}

	public String getCls_ids() {
		return cls_ids;
	}

	public void setCls_ids(String cls_ids) {
		this.cls_ids = cls_ids;
	}

	public String getDiscount_comm_ids() {
		return discount_comm_ids;
	}

	public void setDiscount_comm_ids(String discount_comm_ids) {
		this.discount_comm_ids = discount_comm_ids;
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

	public Date getAdd_date() {
		return add_date;
	}

	public void setAdd_date(Date add_date) {
		this.add_date = add_date;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public Date getDel_date() {
		return del_date;
	}

	public void setDel_date(Date del_date) {
		this.del_date = del_date;
	}

	public Integer getUpdate_user_id() {
		return update_user_id;
	}

	public void setUpdate_user_id(Integer update_user_id) {
		this.update_user_id = update_user_id;
	}

	public Integer getDel_user_id() {
		return del_user_id;
	}

	public void setDel_user_id(Integer del_user_id) {
		this.del_user_id = del_user_id;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

	public Integer getOrder_value() {
		return order_value;
	}

	public void setOrder_value(Integer order_value) {
		this.order_value = order_value;
	}

	public String getCls_names() {
		return cls_names;
	}

	public void setCls_names(String cls_names) {
		this.cls_names = cls_names;
	}

	public Integer getHd_show_type() {
		return hd_show_type;
	}

	public void setHd_show_type(Integer hd_show_type) {
		this.hd_show_type = hd_show_type;
	}

	public String getHy_details_content() {
		return hy_details_content;
	}

	public void setHy_details_content(String hy_details_content) {
		this.hy_details_content = hy_details_content;
	}

	public Integer getIs_open() {
		return is_open;
	}

	public void setIs_open(Integer is_open) {
		this.is_open = is_open;
	}

	public Integer getIs_time_gift() {
		return is_time_gift;
	}

	public void setIs_time_gift(Integer is_time_gift) {
		this.is_time_gift = is_time_gift;
	}

	public Integer getTime_gift_inventory() {
		return time_gift_inventory;
	}

	public void setTime_gift_inventory(Integer time_gift_inventory) {
		this.time_gift_inventory = time_gift_inventory;
	}

}