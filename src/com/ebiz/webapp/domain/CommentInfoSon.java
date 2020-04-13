package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2017-05-09 19:12
 */
public class CommentInfoSon extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;
	
	private Integer type;
	
	private Integer par_id;
	
	private Integer son_id;
	
	private String content;
	
	private Integer top_count;
	
	private Integer down_count;
	
	private Date add_date;
	
	private Integer add_user_id;
	
	private String add_user_name;
	
	private String add_user_logo;
	
	private String add_user_ip;
	
	private Integer to_user_id;
	
	private String to_user_name;
	
	private String to_user_logo;
	
	private Date up_date;
	
	private Integer up_user_id;
	
	private String up_user_name;
	
	private String up_user_logo;
	
	private Integer is_del;
	
	private Date del_date;
	
	private Integer del_user_id;
	
	private String del_user_name;
	
	private Integer is_entp;
	
	private Integer entp_id;
	
	private String entp_name;
	
	private String entp_logo;
	
	public CommentInfoSon() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public Integer getPar_id() {
		return par_id;
	}

	public void setPar_id(Integer par_id) {
		this.par_id = par_id;
	}
	
	public Integer getSon_id() {
		return son_id;
	}

	public void setSon_id(Integer son_id) {
		this.son_id = son_id;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public Integer getTop_count() {
		return top_count;
	}

	public void setTop_count(Integer top_count) {
		this.top_count = top_count;
	}
	
	public Integer getDown_count() {
		return down_count;
	}

	public void setDown_count(Integer down_count) {
		this.down_count = down_count;
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
	
	public String getAdd_user_logo() {
		return add_user_logo;
	}

	public void setAdd_user_logo(String add_user_logo) {
		this.add_user_logo = add_user_logo;
	}
	
	public String getAdd_user_ip() {
		return add_user_ip;
	}

	public void setAdd_user_ip(String add_user_ip) {
		this.add_user_ip = add_user_ip;
	}
	
	public Integer getTo_user_id() {
		return to_user_id;
	}

	public void setTo_user_id(Integer to_user_id) {
		this.to_user_id = to_user_id;
	}
	
	public String getTo_user_name() {
		return to_user_name;
	}

	public void setTo_user_name(String to_user_name) {
		this.to_user_name = to_user_name;
	}
	
	public String getTo_user_logo() {
		return to_user_logo;
	}

	public void setTo_user_logo(String to_user_logo) {
		this.to_user_logo = to_user_logo;
	}
	
	public Date getUp_date() {
		return up_date;
	}

	public void setUp_date(Date up_date) {
		this.up_date = up_date;
	}
	
	public Integer getUp_user_id() {
		return up_user_id;
	}

	public void setUp_user_id(Integer up_user_id) {
		this.up_user_id = up_user_id;
	}
	
	public String getUp_user_name() {
		return up_user_name;
	}

	public void setUp_user_name(String up_user_name) {
		this.up_user_name = up_user_name;
	}
	
	public String getUp_user_logo() {
		return up_user_logo;
	}

	public void setUp_user_logo(String up_user_logo) {
		this.up_user_logo = up_user_logo;
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
	
	public Integer getDel_user_id() {
		return del_user_id;
	}

	public void setDel_user_id(Integer del_user_id) {
		this.del_user_id = del_user_id;
	}
	
	public String getDel_user_name() {
		return del_user_name;
	}

	public void setDel_user_name(String del_user_name) {
		this.del_user_name = del_user_name;
	}
	
	public Integer getIs_entp() {
		return is_entp;
	}

	public void setIs_entp(Integer is_entp) {
		this.is_entp = is_entp;
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
	
	public String getEntp_logo() {
		return entp_logo;
	}

	public void setEntp_logo(String entp_logo) {
		this.entp_logo = entp_logo;
	}
	
}