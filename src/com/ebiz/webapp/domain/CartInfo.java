package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:54
 */
public class CartInfo extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer cart_type;

	private Integer pd_id;

	private String pd_name;

	private Integer cls_id;

	private String cls_name;

	private BigDecimal pd_price;

	private Integer pd_count;

	private Integer entp_id;

	private String entp_name;

	private Integer user_id;

	private String user_name;

	private Date add_date;

	private Integer is_del;

	private Date del_date;

	private BigDecimal matflow_price;

	private String wl_comp_name;

	private Integer comm_id;

	private String comm_name;

	private Integer comm_tczh_id;

	private String comm_tczh_name;

	private BigDecimal service_single_money;

	private BigDecimal comm_weight;

	private Integer fre_id;

	private Integer gn_type;

	private Integer delivery_p_index;

	private Integer delivery_way;

	private Integer activity_price_id;

	private Integer qdyh_id;

	private Integer discount_tj;

	private Integer discount_method;

	private String discount_type_content;

	private String discount_tj_content;

	private Integer flag_qdyh;

	private Integer yhq_id;

	private Integer yhq_tj;

	private String yhq_tj_money;

	private String yhq_money;

	private Integer flag_yhq;

	private Integer comm_tg_id;

	private BigDecimal red_money;

	public BigDecimal getRed_money() {
		return red_money;
	}

	public void setRed_money(BigDecimal red_money) {
		this.red_money = red_money;
	}

	public Integer getActivity_price_id() {
		return activity_price_id;
	}

	public void setActivity_price_id(Integer activity_price_id) {
		this.activity_price_id = activity_price_id;
	}

	public Integer getDelivery_way() {
		return delivery_way;
	}

	public void setDelivery_way(Integer delivery_way) {
		this.delivery_way = delivery_way;
	}

	public CartInfo() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCart_type() {
		return cart_type;
	}

	public void setCart_type(Integer cart_type) {
		this.cart_type = cart_type;
	}

	public Integer getPd_id() {
		return pd_id;
	}

	public void setPd_id(Integer pd_id) {
		this.pd_id = pd_id;
	}

	public String getPd_name() {
		return pd_name;
	}

	public void setPd_name(String pd_name) {
		this.pd_name = pd_name;
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

	public BigDecimal getPd_price() {
		return pd_price;
	}

	public void setPd_price(BigDecimal pd_price) {
		this.pd_price = pd_price;
	}

	public Integer getPd_count() {
		return pd_count;
	}

	public void setPd_count(Integer pd_count) {
		this.pd_count = pd_count;
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

	public BigDecimal getMatflow_price() {
		return matflow_price;
	}

	public void setMatflow_price(BigDecimal matflow_price) {
		this.matflow_price = matflow_price;
	}

	public String getWl_comp_name() {
		return wl_comp_name;
	}

	public void setWl_comp_name(String wl_comp_name) {
		this.wl_comp_name = wl_comp_name;
	}

	public Integer getComm_id() {
		return comm_id;
	}

	public void setComm_id(Integer comm_id) {
		this.comm_id = comm_id;
	}

	public String getComm_name() {
		return comm_name;
	}

	public void setComm_name(String comm_name) {
		this.comm_name = comm_name;
	}

	public Integer getComm_tczh_id() {
		return comm_tczh_id;
	}

	public void setComm_tczh_id(Integer comm_tczh_id) {
		this.comm_tczh_id = comm_tczh_id;
	}

	public String getComm_tczh_name() {
		return comm_tczh_name;
	}

	public void setComm_tczh_name(String comm_tczh_name) {
		this.comm_tczh_name = comm_tczh_name;
	}

	public BigDecimal getService_single_money() {
		return service_single_money;
	}

	public void setService_single_money(BigDecimal service_single_money) {
		this.service_single_money = service_single_money;
	}

	public BigDecimal getComm_weight() {
		return comm_weight;
	}

	public void setComm_weight(BigDecimal comm_weight) {
		this.comm_weight = comm_weight;
	}

	public Integer getFre_id() {
		return fre_id;
	}

	public void setFre_id(Integer fre_id) {
		this.fre_id = fre_id;
	}

	public Integer getGn_type() {
		return gn_type;
	}

	public void setGn_type(Integer gn_type) {
		this.gn_type = gn_type;
	}

	public Integer getDelivery_p_index() {
		return delivery_p_index;
	}

	public void setDelivery_p_index(Integer delivery_p_index) {
		this.delivery_p_index = delivery_p_index;
	}

	public Integer getQdyh_id() {
		return qdyh_id;
	}

	public void setQdyh_id(Integer qdyh_id) {
		this.qdyh_id = qdyh_id;
	}

	public Integer getDiscount_tj() {
		return discount_tj;
	}

	public void setDiscount_tj(Integer discount_tj) {
		this.discount_tj = discount_tj;
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

	public String getDiscount_tj_content() {
		return discount_tj_content;
	}

	public void setDiscount_tj_content(String discount_tj_content) {
		this.discount_tj_content = discount_tj_content;
	}

	public Integer getYhq_id() {
		return yhq_id;
	}

	public void setYhq_id(Integer yhq_id) {
		this.yhq_id = yhq_id;
	}

	public Integer getYhq_tj() {
		return yhq_tj;
	}

	public void setYhq_tj(Integer yhq_tj) {
		this.yhq_tj = yhq_tj;
	}

	public String getYhq_tj_money() {
		return yhq_tj_money;
	}

	public void setYhq_tj_money(String yhq_tj_money) {
		this.yhq_tj_money = yhq_tj_money;
	}

	public String getYhq_money() {
		return yhq_money;
	}

	public void setYhq_money(String yhq_money) {
		this.yhq_money = yhq_money;
	}

	public Integer getFlag_qdyh() {
		return flag_qdyh;
	}

	public void setFlag_qdyh(Integer flag_qdyh) {
		this.flag_qdyh = flag_qdyh;
	}

	public Integer getFlag_yhq() {
		return flag_yhq;
	}

	public void setFlag_yhq(Integer flag_yhq) {
		this.flag_yhq = flag_yhq;
	}

	public Integer getComm_tg_id() {
		return comm_tg_id;
	}

	public void setComm_tg_id(Integer comm_tg_id) {
		this.comm_tg_id = comm_tg_id;
	}

}