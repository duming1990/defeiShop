package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2016-07-14 11:42
 */
public class OrderReturnInfo extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer order_id;

	private Integer order_state;

	private String return_desc;

	private Integer audit_state;

	private Integer audit_user_id;

	private Date audit_date;

	private Integer user_id;

	private String user_name;

	private Date add_date;

	private Integer is_del;

	private Date del_date;

	private Integer is_confirm;

	private String memo;

	private Integer return_type;

	private Integer expect_return_way;

	private Integer return_way;

	private Integer fh_fee;

	private String th_wl_company;

	private String th_wl_no;

	private BigDecimal th_wl_fee;

	private Integer order_type;

	private Integer order_detail_id;

	private Integer comm_id;

	private BigDecimal price;

	private Integer num;

	private String comm_name;

	private BigDecimal tk_money;

	private BigDecimal tk_fee;

	private Integer tk_status;

	private String return_no;

	private String return_addr;

	private String return_link_man;

	private String return_tel;

	private String hh_wl_no;

	private String hh_wl_company;

	private String audit_note;

	private Integer entp_id;

	private String trade_index;

	private Integer return_money_type;

	private BigDecimal return_real_money;

	private BigDecimal return_bi_dianzi;

	public BigDecimal getReturn_real_money() {
		return return_real_money;
	}

	public void setReturn_real_money(BigDecimal return_real_money) {
		this.return_real_money = return_real_money;
	}

	public BigDecimal getReturn_bi_dianzi() {
		return return_bi_dianzi;
	}

	public void setReturn_bi_dianzi(BigDecimal return_bi_dianzi) {
		this.return_bi_dianzi = return_bi_dianzi;
	}

	public Integer getReturn_money_type() {
		return return_money_type;
	}

	public void setReturn_money_type(Integer return_money_type) {
		this.return_money_type = return_money_type;
	}

	public Integer getOrder_state() {
		return order_state;
	}

	public void setOrder_state(Integer order_state) {
		this.order_state = order_state;
	}

	public String getTrade_index() {
		return trade_index;
	}

	public void setTrade_index(String trade_index) {
		this.trade_index = trade_index;
	}

	public Integer getEntp_id() {
		return entp_id;
	}

	public void setEntp_id(Integer entp_id) {
		this.entp_id = entp_id;
	}

	public OrderReturnInfo() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}

	public String getReturn_desc() {
		return return_desc;
	}

	public void setReturn_desc(String return_desc) {
		this.return_desc = return_desc;
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

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
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

	public Integer getIs_confirm() {
		return is_confirm;
	}

	public void setIs_confirm(Integer is_confirm) {
		this.is_confirm = is_confirm;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getReturn_type() {
		return return_type;
	}

	public void setReturn_type(Integer return_type) {
		this.return_type = return_type;
	}

	public Integer getExpect_return_way() {
		return expect_return_way;
	}

	public void setExpect_return_way(Integer expect_return_way) {
		this.expect_return_way = expect_return_way;
	}

	public Integer getReturn_way() {
		return return_way;
	}

	public void setReturn_way(Integer return_way) {
		this.return_way = return_way;
	}

	public Integer getFh_fee() {
		return fh_fee;
	}

	public void setFh_fee(Integer fh_fee) {
		this.fh_fee = fh_fee;
	}

	public String getTh_wl_company() {
		return th_wl_company;
	}

	public void setTh_wl_company(String th_wl_company) {
		this.th_wl_company = th_wl_company;
	}

	public String getTh_wl_no() {
		return th_wl_no;
	}

	public void setTh_wl_no(String th_wl_no) {
		this.th_wl_no = th_wl_no;
	}

	public BigDecimal getTh_wl_fee() {
		return th_wl_fee;
	}

	public void setTh_wl_fee(BigDecimal th_wl_fee) {
		this.th_wl_fee = th_wl_fee;
	}

	public Integer getOrder_type() {
		return order_type;
	}

	public void setOrder_type(Integer order_type) {
		this.order_type = order_type;
	}

	public Integer getOrder_detail_id() {
		return order_detail_id;
	}

	public void setOrder_detail_id(Integer order_detail_id) {
		this.order_detail_id = order_detail_id;
	}

	public Integer getComm_id() {
		return comm_id;
	}

	public void setComm_id(Integer comm_id) {
		this.comm_id = comm_id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getComm_name() {
		return comm_name;
	}

	public void setComm_name(String comm_name) {
		this.comm_name = comm_name;
	}

	public BigDecimal getTk_money() {
		return tk_money;
	}

	public void setTk_money(BigDecimal tk_money) {
		this.tk_money = tk_money;
	}

	public BigDecimal getTk_fee() {
		return tk_fee;
	}

	public void setTk_fee(BigDecimal tk_fee) {
		this.tk_fee = tk_fee;
	}

	public Integer getTk_status() {
		return tk_status;
	}

	public void setTk_status(Integer tk_status) {
		this.tk_status = tk_status;
	}

	public String getReturn_no() {
		return return_no;
	}

	public void setReturn_no(String return_no) {
		this.return_no = return_no;
	}

	public String getReturn_addr() {
		return return_addr;
	}

	public void setReturn_addr(String return_addr) {
		this.return_addr = return_addr;
	}

	public String getReturn_link_man() {
		return return_link_man;
	}

	public void setReturn_link_man(String return_link_man) {
		this.return_link_man = return_link_man;
	}

	public String getReturn_tel() {
		return return_tel;
	}

	public void setReturn_tel(String return_tel) {
		this.return_tel = return_tel;
	}

	public String getHh_wl_no() {
		return hh_wl_no;
	}

	public void setHh_wl_no(String hh_wl_no) {
		this.hh_wl_no = hh_wl_no;
	}

	public String getHh_wl_company() {
		return hh_wl_company;
	}

	public void setHh_wl_company(String hh_wl_company) {
		this.hh_wl_company = hh_wl_company;
	}

	public Integer getAudit_user_id() {
		return audit_user_id;
	}

	public void setAudit_user_id(Integer audit_user_id) {
		this.audit_user_id = audit_user_id;
	}

	public String getAudit_note() {
		return audit_note;
	}

	public void setAudit_note(String audit_note) {
		this.audit_note = audit_note;
	}

}