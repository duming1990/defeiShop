package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2013-05-27 14:06
 */
public class BaseLink extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Date add_time;

	private Integer add_uid;

	private String content;

	private Date del_time;

	private Integer del_uid;

	private Integer id;

	private String image_path;

	private Integer is_del;

	private Integer link_type;

	private String link_url;

	private Integer order_value;

	private BigDecimal pd_discount;

	private BigDecimal pd_price;

	private String title;

	private String title_color;

	private Integer title_is_strong;

	private Date update_time;

	private Integer update_uid;

	private Integer link_url_type;

	private Integer entp_id;

	private Integer pre_number;

	private Integer link_scope;

	private String pre_varchar;

	private Date begin_date;

	private Date end_date;

	private List<BaseLink> BaseLinkList;

	private List<BaseClass> baseClassList;

	private List<CommInfo> commInfoList;

	private String comm_name;

	private Integer comm_id;

	private Integer p_index;

	private String p_name;

	private Integer is_quanguo;

	private Integer par_id;

	private Integer par_son_type;

	public List<CommInfo> getCommInfoList() {
		return commInfoList;
	}

	public void setCommInfoList(List<CommInfo> commInfoList) {
		this.commInfoList = commInfoList;
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

	public BaseLink() {

	}

	public Date getAdd_time() {
		return add_time;
	}

	public Integer getAdd_uid() {
		return add_uid;
	}

	public String getContent() {
		return content;
	}

	public Date getDel_time() {
		return del_time;
	}

	public Integer getDel_uid() {
		return del_uid;
	}

	public Integer getId() {
		return id;
	}

	public String getImage_path() {
		return image_path;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public String getLink_url() {
		return link_url;
	}

	public Integer getOrder_value() {
		return order_value;
	}

	public BigDecimal getPd_discount() {
		return pd_discount;
	}

	public BigDecimal getPd_price() {
		return pd_price;
	}

	public String getTitle() {
		return title;
	}

	public String getTitle_color() {
		return title_color;
	}

	public Integer getTitle_is_strong() {
		return title_is_strong;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public Integer getUpdate_uid() {
		return update_uid;
	}

	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
	}

	public void setAdd_uid(Integer add_uid) {
		this.add_uid = add_uid;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setDel_time(Date del_time) {
		this.del_time = del_time;
	}

	public void setDel_uid(Integer del_uid) {
		this.del_uid = del_uid;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

	public void setLink_url(String link_url) {
		this.link_url = link_url;
	}

	public void setOrder_value(Integer order_value) {
		this.order_value = order_value;
	}

	public void setPd_discount(BigDecimal pd_discount) {
		this.pd_discount = pd_discount;
	}

	public void setPd_price(BigDecimal pd_price) {
		this.pd_price = pd_price;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTitle_color(String title_color) {
		this.title_color = title_color;
	}

	public void setTitle_is_strong(Integer title_is_strong) {
		this.title_is_strong = title_is_strong;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public void setUpdate_uid(Integer update_uid) {
		this.update_uid = update_uid;
	}

	public Integer getLink_url_type() {
		return link_url_type;
	}

	public void setLink_url_type(Integer linkUrlType) {
		link_url_type = linkUrlType;
	}

	public Integer getEntp_id() {
		return entp_id;
	}

	public void setEntp_id(Integer entp_id) {
		this.entp_id = entp_id;
	}

	public Integer getLink_type() {
		return link_type;
	}

	public void setLink_type(Integer link_type) {
		this.link_type = link_type;
	}

	public Integer getLink_scope() {
		return link_scope;
	}

	public void setLink_scope(Integer link_scope) {
		this.link_scope = link_scope;
	}

	public List<BaseLink> getBaseLinkList() {
		return BaseLinkList;
	}

	public void setBaseLinkList(List<BaseLink> baseLinkList) {
		BaseLinkList = baseLinkList;
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

	public String getComm_name() {
		return comm_name;
	}

	public void setComm_name(String comm_name) {
		this.comm_name = comm_name;
	}

	public Integer getComm_id() {
		return comm_id;
	}

	public void setComm_id(Integer comm_id) {
		this.comm_id = comm_id;
	}

	public Integer getIs_quanguo() {
		return is_quanguo;
	}

	public void setIs_quanguo(Integer is_quanguo) {
		this.is_quanguo = is_quanguo;
	}

	public List<BaseClass> getBaseClassList() {
		return baseClassList;
	}

	public void setBaseClassList(List<BaseClass> baseClassList) {
		this.baseClassList = baseClassList;
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

}