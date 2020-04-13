package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2014-05-27 16:43
 */
public class UserScoreRecord extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer link_id;

	private Integer score_type;

	private BigDecimal hd_score_before;

	private BigDecimal hd_score;

	private BigDecimal hd_score_after;

	private Integer add_user_id;

	private Date add_date;

	private Integer is_del;

	private Date del_date;

	private Integer del_user_id;

	public UserScoreRecord() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLink_id() {
		return link_id;
	}

	public void setLink_id(Integer link_id) {
		this.link_id = link_id;
	}

	public Integer getScore_type() {
		return score_type;
	}

	public void setScore_type(Integer score_type) {
		this.score_type = score_type;
	}

	public BigDecimal getHd_score() {
		return hd_score;
	}

	public void setHd_score(BigDecimal hd_score) {
		this.hd_score = hd_score;
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

	public BigDecimal getHd_score_before() {
		return hd_score_before;
	}

	public void setHd_score_before(BigDecimal hd_score_before) {
		this.hd_score_before = hd_score_before;
	}

	public BigDecimal getHd_score_after() {
		return hd_score_after;
	}

	public void setHd_score_after(BigDecimal hd_score_after) {
		this.hd_score_after = hd_score_after;
	}

}