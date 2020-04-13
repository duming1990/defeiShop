package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2018-08-22 14:47
 */
public class VillageTour extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;
	
	private Long p_index;
	
	private String tour_name;
	
	private String tour_sub_name;
	
	private Date add_date;
	
	private Integer add_user_id;
	
	private String add_user_name;
	
	private Date update_date;
	
	private Integer update_user_id;
	
	private Date del_date;
	
	private Integer del_user_id;
	
	private Integer is_del;
	
	private Date tour_start_date;
	
	private Date tour_end_date;
	
	private Integer audit_state;
	
	private Integer audit_user_id;
	
	private Date audit_date;
	
	private String audit_desc;
	
	private String position_latlng;
	
	private String tour_logo;
	
	private String tour_content;
	
	private String tour_traffic;
	
	public VillageTour() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Long getP_index() {
		return p_index;
	}

	public void setP_index(Long p_index) {
		this.p_index = p_index;
	}
	
	public String getTour_name() {
		return tour_name;
	}

	public void setTour_name(String tour_name) {
		this.tour_name = tour_name;
	}
	
	public String getTour_sub_name() {
		return tour_sub_name;
	}

	public void setTour_sub_name(String tour_sub_name) {
		this.tour_sub_name = tour_sub_name;
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
	
	public String getAdd_user_name() {
		return add_user_name;
	}

	public void setAdd_user_name(String add_user_name) {
		this.add_user_name = add_user_name;
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
	
	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}
	
	public Date getTour_start_date() {
		return tour_start_date;
	}

	public void setTour_start_date(Date tour_start_date) {
		this.tour_start_date = tour_start_date;
	}
	
	public Date getTour_end_date() {
		return tour_end_date;
	}

	public void setTour_end_date(Date tour_end_date) {
		this.tour_end_date = tour_end_date;
	}
	
	public Integer getAudit_state() {
		return audit_state;
	}

	public void setAudit_state(Integer audit_state) {
		this.audit_state = audit_state;
	}
	
	public Integer getAudit_user_id() {
		return audit_user_id;
	}

	public void setAudit_user_id(Integer audit_user_id) {
		this.audit_user_id = audit_user_id;
	}
	
	public Date getAudit_date() {
		return audit_date;
	}

	public void setAudit_date(Date audit_date) {
		this.audit_date = audit_date;
	}
	
	public String getAudit_desc() {
		return audit_desc;
	}

	public void setAudit_desc(String audit_desc) {
		this.audit_desc = audit_desc;
	}
	
	public String getPosition_latlng() {
		return position_latlng;
	}

	public void setPosition_latlng(String position_latlng) {
		this.position_latlng = position_latlng;
	}
	
	public String getTour_logo() {
		return tour_logo;
	}

	public void setTour_logo(String tour_logo) {
		this.tour_logo = tour_logo;
	}
	
	public String getTour_content() {
		return tour_content;
	}

	public void setTour_content(String tour_content) {
		this.tour_content = tour_content;
	}
	
	public String getTour_traffic() {
		return tour_traffic;
	}

	public void setTour_traffic(String tour_traffic) {
		this.tour_traffic = tour_traffic;
	}
	
}