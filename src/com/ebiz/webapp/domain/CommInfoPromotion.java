package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2017-04-21 17:10
 */
public class CommInfoPromotion extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;
	
	private Integer comm_id;
	
	private BigDecimal prom_price;
	
	private Long comm_tczh_id;
	
	private String comm_tczh_ids;
	
	private Integer f_ma;
	
	private String comm_title;
	
	private String comm_sub_title;
	
	private Integer promotion_type;
	
	private Date start_time;
	
	private Date end_time;
	
	private Integer add_user_id;
	
	private Date add_date;
	
	private Date audit_date;
	
	private Integer audit_user_id;
	
	private Integer is_buyer_limit;
	
	private Integer buyer_limit_num;
	
	private Integer prom_inventory;
	
	private Integer prom_sale_acount;
	
	private Integer own_entp_id;
	
	private Integer is_del;
	
	private Date update_date;
	
	private Integer update_user_id;
	
	private Date del_date;
	
	private Integer del_user_id;
	
	private Integer audit_state;
	
	private BigDecimal no_dist_price;
	
	private String audit_desc;
	
	private String tg_tczh_ids;
	
	public CommInfoPromotion() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getComm_id() {
		return comm_id;
	}

	public void setComm_id(Integer comm_id) {
		this.comm_id = comm_id;
	}
	
	public BigDecimal getProm_price() {
		return prom_price;
	}

	public void setProm_price(BigDecimal prom_price) {
		this.prom_price = prom_price;
	}
	
	public Long getComm_tczh_id() {
		return comm_tczh_id;
	}

	public void setComm_tczh_id(Long comm_tczh_id) {
		this.comm_tczh_id = comm_tczh_id;
	}
	
	public String getComm_tczh_ids() {
		return comm_tczh_ids;
	}

	public void setComm_tczh_ids(String comm_tczh_ids) {
		this.comm_tczh_ids = comm_tczh_ids;
	}
	
	public Integer getF_ma() {
		return f_ma;
	}

	public void setF_ma(Integer f_ma) {
		this.f_ma = f_ma;
	}
	
	public String getComm_title() {
		return comm_title;
	}

	public void setComm_title(String comm_title) {
		this.comm_title = comm_title;
	}
	
	public String getComm_sub_title() {
		return comm_sub_title;
	}

	public void setComm_sub_title(String comm_sub_title) {
		this.comm_sub_title = comm_sub_title;
	}
	
	public Integer getPromotion_type() {
		return promotion_type;
	}

	public void setPromotion_type(Integer promotion_type) {
		this.promotion_type = promotion_type;
	}
	
	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}
	
	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
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
	
	public Date getAudit_date() {
		return audit_date;
	}

	public void setAudit_date(Date audit_date) {
		this.audit_date = audit_date;
	}
	
	public Integer getAudit_user_id() {
		return audit_user_id;
	}

	public void setAudit_user_id(Integer audit_user_id) {
		this.audit_user_id = audit_user_id;
	}
	
	public Integer getIs_buyer_limit() {
		return is_buyer_limit;
	}

	public void setIs_buyer_limit(Integer is_buyer_limit) {
		this.is_buyer_limit = is_buyer_limit;
	}
	
	public Integer getBuyer_limit_num() {
		return buyer_limit_num;
	}

	public void setBuyer_limit_num(Integer buyer_limit_num) {
		this.buyer_limit_num = buyer_limit_num;
	}
	
	public Integer getProm_inventory() {
		return prom_inventory;
	}

	public void setProm_inventory(Integer prom_inventory) {
		this.prom_inventory = prom_inventory;
	}
	
	public Integer getProm_sale_acount() {
		return prom_sale_acount;
	}

	public void setProm_sale_acount(Integer prom_sale_acount) {
		this.prom_sale_acount = prom_sale_acount;
	}
	
	public Integer getOwn_entp_id() {
		return own_entp_id;
	}

	public void setOwn_entp_id(Integer own_entp_id) {
		this.own_entp_id = own_entp_id;
	}
	
	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
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
	
	public Integer getAudit_state() {
		return audit_state;
	}

	public void setAudit_state(Integer audit_state) {
		this.audit_state = audit_state;
	}
	
	public BigDecimal getNo_dist_price() {
		return no_dist_price;
	}

	public void setNo_dist_price(BigDecimal no_dist_price) {
		this.no_dist_price = no_dist_price;
	}
	
	public String getAudit_desc() {
		return audit_desc;
	}

	public void setAudit_desc(String audit_desc) {
		this.audit_desc = audit_desc;
	}
	
	public String getTg_tczh_ids() {
		return tg_tczh_ids;
	}

	public void setTg_tczh_ids(String tg_tczh_ids) {
		this.tg_tczh_ids = tg_tczh_ids;
	}
	
}