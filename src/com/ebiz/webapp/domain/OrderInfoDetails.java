package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:55
 */
public class OrderInfoDetails extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer cls_id;

	private String cls_name;

	private Integer comm_id;

	private String comm_name;

	private Integer comm_tczh_id;

	private String comm_tczh_name;

	private Integer delivery_p_index;

	private Integer delivery_way;

	private Integer entp_id;

	private Integer good_count;

	private BigDecimal good_price;

	private Integer good_state;

	private BigDecimal good_sum_price;

	private String good_unit;

	private Integer id;

	private Integer is_price_modify;

	private BigDecimal matflow_price;

	private Integer order_id;

	private Integer order_type;

	private Integer pd_id;

	private String pd_name;

	private Date price_modify_date;

	private BigDecimal price_modify_old;

	private String price_modify_remark;

	private BigDecimal matflow_price_old;

	private String wl_comp_name;

	private Date add_date;

	private BigDecimal activity_price;

	private String activity_title;

	private Date order_info_add_date;

	private List<OrderInfoDetails> entpOrderInfoDetailslist;

	private Integer has_comment;

	private Integer huizhuan_rule;

	private BigDecimal cost_price;

	private Integer is_tuihuo;

	private Integer is_hd_comm;

	private BigDecimal profit_money;

	private Integer comm_tg_id;

	private BigDecimal sum_red_money;

	private BigDecimal sum_rebate_money;

	private BigDecimal sum_aid_money;

	private Integer poor_id;

	private Integer is_comment;

	private Integer fp_state;

	private BigDecimal actual_money;

	private Integer yhq_son_id;

	private BigDecimal yhq_money;

	public Integer getYhq_son_id() {
		return yhq_son_id;
	}

	public void setYhq_son_id(Integer yhq_son_id) {
		this.yhq_son_id = yhq_son_id;
	}

	public BigDecimal getYhq_money() {
		return yhq_money;
	}

	public void setYhq_money(BigDecimal yhq_money) {
		this.yhq_money = yhq_money;
	}

	public BigDecimal getActual_money() {
		return actual_money;
	}

	public void setActual_money(BigDecimal actual_money) {
		this.actual_money = actual_money;
	}

	public Integer getIs_comment() {
		return is_comment;
	}

	public void setIs_comment(Integer is_comment) {
		this.is_comment = is_comment;
	}

	public BigDecimal getSum_red_money() {
		return sum_red_money;
	}

	public void setSum_red_money(BigDecimal sum_red_money) {
		this.sum_red_money = sum_red_money;
	}

	public Integer getIs_tuihuo() {
		return is_tuihuo;
	}

	public void setIs_tuihuo(Integer is_tuihuo) {
		this.is_tuihuo = is_tuihuo;
	}

	public OrderInfoDetails() {

	}

	public Integer getCls_id() {
		return cls_id;
	}

	public String getCls_name() {
		return cls_name;
	}

	public Integer getComm_id() {
		return comm_id;
	}

	public String getComm_name() {
		return comm_name;
	}

	public Integer getComm_tczh_id() {
		return comm_tczh_id;
	}

	public String getComm_tczh_name() {
		return comm_tczh_name;
	}

	public Integer getDelivery_p_index() {
		return delivery_p_index;
	}

	public Integer getDelivery_way() {
		return delivery_way;
	}

	public Integer getEntp_id() {
		return entp_id;
	}

	public Integer getGood_count() {
		return good_count;
	}

	public BigDecimal getGood_price() {
		return good_price;
	}

	public Integer getGood_state() {
		return good_state;
	}

	public BigDecimal getGood_sum_price() {
		return good_sum_price;
	}

	public String getGood_unit() {
		return good_unit;
	}

	public Integer getId() {
		return id;
	}

	public Integer getIs_price_modify() {
		return is_price_modify;
	}

	public BigDecimal getMatflow_price() {
		return matflow_price;
	}

	public Integer getOrder_id() {
		return order_id;
	}

	public Integer getOrder_type() {
		return order_type;
	}

	public Integer getPd_id() {
		return pd_id;
	}

	public String getPd_name() {
		return pd_name;
	}

	public Date getPrice_modify_date() {
		return price_modify_date;
	}

	public BigDecimal getPrice_modify_old() {
		return price_modify_old;
	}

	public String getPrice_modify_remark() {
		return price_modify_remark;
	}

	public String getWl_comp_name() {
		return wl_comp_name;
	}

	public void setCls_id(Integer cls_id) {
		this.cls_id = cls_id;
	}

	public void setCls_name(String cls_name) {
		this.cls_name = cls_name;
	}

	public void setComm_id(Integer comm_id) {
		this.comm_id = comm_id;
	}

	public void setComm_name(String comm_name) {
		this.comm_name = comm_name;
	}

	public void setComm_tczh_id(Integer comm_tczh_id) {
		this.comm_tczh_id = comm_tczh_id;
	}

	public void setComm_tczh_name(String comm_tczh_name) {
		this.comm_tczh_name = comm_tczh_name;
	}

	public void setDelivery_p_index(Integer delivery_p_index) {
		this.delivery_p_index = delivery_p_index;
	}

	public void setDelivery_way(Integer delivery_way) {
		this.delivery_way = delivery_way;
	}

	public void setEntp_id(Integer entp_id) {
		this.entp_id = entp_id;
	}

	public void setGood_count(Integer good_count) {
		this.good_count = good_count;
	}

	public void setGood_price(BigDecimal good_price) {
		this.good_price = good_price;
	}

	public void setGood_state(Integer good_state) {
		this.good_state = good_state;
	}

	public void setGood_sum_price(BigDecimal good_sum_price) {
		this.good_sum_price = good_sum_price;
	}

	public void setGood_unit(String good_unit) {
		this.good_unit = good_unit;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIs_price_modify(Integer is_price_modify) {
		this.is_price_modify = is_price_modify;
	}

	public void setMatflow_price(BigDecimal matflow_price) {
		this.matflow_price = matflow_price;
	}

	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}

	public void setOrder_type(Integer order_type) {
		this.order_type = order_type;
	}

	public void setPd_id(Integer pd_id) {
		this.pd_id = pd_id;
	}

	public void setPd_name(String pd_name) {
		this.pd_name = pd_name;
	}

	public void setPrice_modify_date(Date price_modify_date) {
		this.price_modify_date = price_modify_date;
	}

	public void setPrice_modify_old(BigDecimal price_modify_old) {
		this.price_modify_old = price_modify_old;
	}

	public void setPrice_modify_remark(String price_modify_remark) {
		this.price_modify_remark = price_modify_remark;
	}

	public void setWl_comp_name(String wl_comp_name) {
		this.wl_comp_name = wl_comp_name;
	}

	public Date getAdd_date() {
		return add_date;
	}

	public void setAdd_date(Date add_date) {
		this.add_date = add_date;
	}

	public List<OrderInfoDetails> getEntpOrderInfoDetailslist() {
		return entpOrderInfoDetailslist;
	}

	public void setEntpOrderInfoDetailslist(List<OrderInfoDetails> entpOrderInfoDetailslist) {
		this.entpOrderInfoDetailslist = entpOrderInfoDetailslist;
	}

	public BigDecimal getActivity_price() {
		return activity_price;
	}

	public void setActivity_price(BigDecimal activity_price) {
		this.activity_price = activity_price;
	}

	public String getActivity_title() {
		return activity_title;
	}

	public void setActivity_title(String activity_title) {
		this.activity_title = activity_title;
	}

	public Date getOrder_info_add_date() {
		return order_info_add_date;
	}

	public void setOrder_info_add_date(Date orderInfoAddDate) {
		order_info_add_date = orderInfoAddDate;
	}

	public BigDecimal getMatflow_price_old() {
		return matflow_price_old;
	}

	public void setMatflow_price_old(BigDecimal matflow_price_old) {
		this.matflow_price_old = matflow_price_old;
	}

	public Integer getHas_comment() {
		return has_comment;
	}

	public void setHas_comment(Integer has_comment) {
		this.has_comment = has_comment;
	}

	public Integer getHuizhuan_rule() {
		return huizhuan_rule;
	}

	public void setHuizhuan_rule(Integer huizhuan_rule) {
		this.huizhuan_rule = huizhuan_rule;
	}

	public BigDecimal getCost_price() {
		return cost_price;
	}

	public void setCost_price(BigDecimal cost_price) {
		this.cost_price = cost_price;
	}

	public Integer getIs_hd_comm() {
		return is_hd_comm;
	}

	public void setIs_hd_comm(Integer is_hd_comm) {
		this.is_hd_comm = is_hd_comm;
	}

	public BigDecimal getProfit_money() {
		return profit_money;
	}

	public void setProfit_money(BigDecimal profit_money) {
		this.profit_money = profit_money;
	}

	public Integer getComm_tg_id() {
		return comm_tg_id;
	}

	public void setComm_tg_id(Integer comm_tg_id) {
		this.comm_tg_id = comm_tg_id;
	}

	public BigDecimal getSum_rebate_money() {
		return sum_rebate_money;
	}

	public void setSum_rebate_money(BigDecimal sum_rebate_money) {
		this.sum_rebate_money = sum_rebate_money;
	}

	public BigDecimal getSum_aid_money() {
		return sum_aid_money;
	}

	public void setSum_aid_money(BigDecimal sum_aid_money) {
		this.sum_aid_money = sum_aid_money;
	}

	public Integer getPoor_id() {
		return poor_id;
	}

	public void setPoor_id(Integer poor_id) {
		this.poor_id = poor_id;
	}

	public Integer getFp_state() {
		return fp_state;
	}

	public void setFp_state(Integer fp_state) {
		this.fp_state = fp_state;
	}

}