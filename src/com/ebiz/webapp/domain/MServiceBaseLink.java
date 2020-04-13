package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2018-06-13 14:06
 */
public class MServiceBaseLink extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;
	
	private Integer link_type;
	
	private Integer link_id;
	
	private String title;
	
	private String title_color;
	
	private Integer title_is_strong;
	
	private String content;
	
	private String link_url;
	
	private String image_path;
	
	private BigDecimal pd_price;
	
	private BigDecimal pd_discount;
	
	private Integer add_uid;
	
	private Date add_time;
	
	private Integer update_uid;
	
	private Date update_time;
	
	private Integer del_uid;
	
	private Date del_time;
	
	private Integer order_value;
	
	private Integer is_del;
	
	private Integer link_url_type;
	
	private Integer entp_id;
	
	private Integer audit_state;
	
	private Integer link_scope;
	
	private Integer pre_number;
	
	private String pre_varchar;
	
	private Date begin_date;
	
	private Date end_date;
	
	private Integer pre_number2;
	
	private Integer cls_id;
	
	private String cls_name;
	
	private String pre_varchar2;
	
	private Integer p_index;
	
	private String p_name;
	
	private Integer comm_id;
	
	private String comm_name;
	
	private Integer par_id;
	
	private Integer par_son_type;
	
	private Integer main_type;
	
	public MServiceBaseLink() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getLink_type() {
		return link_type;
	}

	public void setLink_type(Integer link_type) {
		this.link_type = link_type;
	}
	
	public Integer getLink_id() {
		return link_id;
	}

	public void setLink_id(Integer link_id) {
		this.link_id = link_id;
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
	
	public Integer getTitle_is_strong() {
		return title_is_strong;
	}

	public void setTitle_is_strong(Integer title_is_strong) {
		this.title_is_strong = title_is_strong;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getLink_url() {
		return link_url;
	}

	public void setLink_url(String link_url) {
		this.link_url = link_url;
	}
	
	public String getImage_path() {
		return image_path;
	}

	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}
	
	public BigDecimal getPd_price() {
		return pd_price;
	}

	public void setPd_price(BigDecimal pd_price) {
		this.pd_price = pd_price;
	}
	
	public BigDecimal getPd_discount() {
		return pd_discount;
	}

	public void setPd_discount(BigDecimal pd_discount) {
		this.pd_discount = pd_discount;
	}
	
	public Integer getAdd_uid() {
		return add_uid;
	}

	public void setAdd_uid(Integer add_uid) {
		this.add_uid = add_uid;
	}
	
	public Date getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
	}
	
	public Integer getUpdate_uid() {
		return update_uid;
	}

	public void setUpdate_uid(Integer update_uid) {
		this.update_uid = update_uid;
	}
	
	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	
	public Integer getDel_uid() {
		return del_uid;
	}

	public void setDel_uid(Integer del_uid) {
		this.del_uid = del_uid;
	}
	
	public Date getDel_time() {
		return del_time;
	}

	public void setDel_time(Date del_time) {
		this.del_time = del_time;
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
	
	public Integer getLink_url_type() {
		return link_url_type;
	}

	public void setLink_url_type(Integer link_url_type) {
		this.link_url_type = link_url_type;
	}
	
	public Integer getEntp_id() {
		return entp_id;
	}

	public void setEntp_id(Integer entp_id) {
		this.entp_id = entp_id;
	}
	
	public Integer getAudit_state() {
		return audit_state;
	}

	public void setAudit_state(Integer audit_state) {
		this.audit_state = audit_state;
	}
	
	public Integer getLink_scope() {
		return link_scope;
	}

	public void setLink_scope(Integer link_scope) {
		this.link_scope = link_scope;
	}
	
	public Integer getPre_number() {
		return pre_number;
	}

	public void setPre_number(Integer pre_number) {
		this.pre_number = pre_number;
	}
	
	public String getPre_varchar() {
		return pre_varchar;
	}

	public void setPre_varchar(String pre_varchar) {
		this.pre_varchar = pre_varchar;
	}
	
	public Date getBegin_date() {
		return begin_date;
	}

	public void setBegin_date(Date begin_date) {
		this.begin_date = begin_date;
	}
	
	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	
	public Integer getPre_number2() {
		return pre_number2;
	}

	public void setPre_number2(Integer pre_number2) {
		this.pre_number2 = pre_number2;
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
	
	public String getPre_varchar2() {
		return pre_varchar2;
	}

	public void setPre_varchar2(String pre_varchar2) {
		this.pre_varchar2 = pre_varchar2;
	}
	
	public Integer getP_index() {
		return p_index;
	}

	public void setP_index(Integer p_index) {
		this.p_index = p_index;
	}
	
	public String getP_name() {
		return p_name;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
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
	
	public Integer getPar_id() {
		return par_id;
	}

	public void setPar_id(Integer par_id) {
		this.par_id = par_id;
	}
	
	public Integer getPar_son_type() {
		return par_son_type;
	}

	public void setPar_son_type(Integer par_son_type) {
		this.par_son_type = par_son_type;
	}
	
	public Integer getMain_type() {
		return main_type;
	}

	public void setMain_type(Integer main_type) {
		this.main_type = main_type;
	}
	
}