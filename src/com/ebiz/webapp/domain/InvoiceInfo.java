package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:54
 */
public class InvoiceInfo extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer shipping_id;

	private Integer invoice_type;

	private String invoices_payable;

	private String vat_companyname;

	private String vat_code;

	private String vat_address;

	private String vat_phone;

	private String vat_bankname;

	private String vat_bankaccount;

	private String consignee_name;

	private String consignee_mobile;

	private Integer consignee_p_index;

	private String consignee_p_name;

	private String consignee_address;

	private Integer is_del;

	private Date add_date;

	private Integer add_user_id;

	private Date update_date;

	private Integer update_user_id;

	private Date del_date;

	private Integer del_user_id;

	public InvoiceInfo() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getShipping_id() {
		return shipping_id;
	}

	public void setShipping_id(Integer shipping_id) {
		this.shipping_id = shipping_id;
	}

	public Integer getInvoice_type() {
		return invoice_type;
	}

	public void setInvoice_type(Integer invoice_type) {
		this.invoice_type = invoice_type;
	}

	public String getInvoices_payable() {
		return invoices_payable;
	}

	public void setInvoices_payable(String invoices_payable) {
		this.invoices_payable = invoices_payable;
	}

	public String getVat_companyname() {
		return vat_companyname;
	}

	public void setVat_companyname(String vat_companyname) {
		this.vat_companyname = vat_companyname;
	}

	public String getVat_code() {
		return vat_code;
	}

	public void setVat_code(String vat_code) {
		this.vat_code = vat_code;
	}

	public String getVat_address() {
		return vat_address;
	}

	public void setVat_address(String vat_address) {
		this.vat_address = vat_address;
	}

	public String getVat_phone() {
		return vat_phone;
	}

	public void setVat_phone(String vat_phone) {
		this.vat_phone = vat_phone;
	}

	public String getVat_bankname() {
		return vat_bankname;
	}

	public void setVat_bankname(String vat_bankname) {
		this.vat_bankname = vat_bankname;
	}

	public String getVat_bankaccount() {
		return vat_bankaccount;
	}

	public void setVat_bankaccount(String vat_bankaccount) {
		this.vat_bankaccount = vat_bankaccount;
	}

	public String getConsignee_name() {
		return consignee_name;
	}

	public void setConsignee_name(String consignee_name) {
		this.consignee_name = consignee_name;
	}

	public String getConsignee_mobile() {
		return consignee_mobile;
	}

	public void setConsignee_mobile(String consignee_mobile) {
		this.consignee_mobile = consignee_mobile;
	}

	public Integer getConsignee_p_index() {
		return consignee_p_index;
	}

	public void setConsignee_p_index(Integer consignee_p_index) {
		this.consignee_p_index = consignee_p_index;
	}

	public String getConsignee_p_name() {
		return consignee_p_name;
	}

	public void setConsignee_p_name(String consignee_p_name) {
		this.consignee_p_name = consignee_p_name;
	}

	public String getConsignee_address() {
		return consignee_address;
	}

	public void setConsignee_address(String consignee_address) {
		this.consignee_address = consignee_address;
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

}