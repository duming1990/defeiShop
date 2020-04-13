package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:55
 */
public class ShippingAddress extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private String rel_name;

	private String rel_phone;

	private String rel_tel;

	private Integer rel_province;

	private Integer rel_city;

	private Integer rel_region;

	private String rel_addr;

	private String rel_email;

	private Integer rel_zip;

	private Integer is_default;

	private Integer is_del;

	private Date add_date;

	private Integer add_user_id;

	private Date update_date;

	private Integer update_user_id;

	private Date del_date;

	private Integer del_user_id;

	private Long rel_region_four;

	private InvoiceInfo invoiceInfo = new InvoiceInfo();

	public InvoiceInfo getInvoiceInfo() {
		return invoiceInfo;
	}

	public void setInvoiceInfo(InvoiceInfo invoiceInfo) {
		this.invoiceInfo = invoiceInfo;
	}

	public ShippingAddress() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRel_name() {
		return rel_name;
	}

	public void setRel_name(String rel_name) {
		this.rel_name = rel_name;
	}

	public String getRel_phone() {
		return rel_phone;
	}

	public void setRel_phone(String rel_phone) {
		this.rel_phone = rel_phone;
	}

	public String getRel_tel() {
		return rel_tel;
	}

	public void setRel_tel(String rel_tel) {
		this.rel_tel = rel_tel;
	}

	public Integer getRel_province() {
		return rel_province;
	}

	public void setRel_province(Integer rel_province) {
		this.rel_province = rel_province;
	}

	public Integer getRel_city() {
		return rel_city;
	}

	public void setRel_city(Integer rel_city) {
		this.rel_city = rel_city;
	}

	public Integer getRel_region() {
		return rel_region;
	}

	public void setRel_region(Integer rel_region) {
		this.rel_region = rel_region;
	}

	public String getRel_addr() {
		return rel_addr;
	}

	public void setRel_addr(String rel_addr) {
		this.rel_addr = rel_addr;
	}

	public String getRel_email() {
		return rel_email;
	}

	public void setRel_email(String rel_email) {
		this.rel_email = rel_email;
	}

	public Integer getRel_zip() {
		return rel_zip;
	}

	public void setRel_zip(Integer rel_zip) {
		this.rel_zip = rel_zip;
	}

	public Integer getIs_default() {
		return is_default;
	}

	public void setIs_default(Integer is_default) {
		this.is_default = is_default;
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

	public Long getRel_region_four() {
		return rel_region_four;
	}

	public void setRel_region_four(Long rel_region_four) {
		this.rel_region_four = rel_region_four;
	}

}