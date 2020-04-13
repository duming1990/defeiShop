package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2013-06-22 15:29
 */
public class CommentInfo extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer link_id;

	private Integer comm_type;

	private String comm_title;

	private String comm_content;

	private String comm_reply;

	private Date comm_time;

	private String comm_ip;

	private Integer comm_uid;

	private String comm_uname;

	private Integer comm_score;

	private Integer order_value;

	private Integer comm_state;

	private String comm_tag;

	private String comm_good;

	private String comm_bad;

	private String comm_experience;

	private Date buy_date;

	private Integer comm_level;

	private Integer order_id;

	private Integer order_details_id;

	private Integer link_f_id;

	private Integer comm_tczh_id;

	private Integer has_pic;

	private Integer reply_uid;

	private String reply_content;

	private Date reply_date;

	private List<CommentInfo> commentInfoList;

	private Integer entp_id;

	private String comm_name;

	private String comm_tczh_name;

	public String getComm_name() {
		return comm_name;
	}

	public void setComm_name(String comm_name) {
		this.comm_name = comm_name;
	}

	public String getComm_tczh_name() {
		return comm_tczh_name;
	}

	public void setComm_tczh_name(String comm_tczh_name) {
		this.comm_tczh_name = comm_tczh_name;
	}

	public Integer getEntp_id() {
		return entp_id;
	}

	public void setEntp_id(Integer entp_id) {
		this.entp_id = entp_id;
	}

	public Integer getOrder_details_id() {
		return order_details_id;
	}

	public void setOrder_details_id(Integer order_details_id) {
		this.order_details_id = order_details_id;
	}

	public List<CommentInfo> getCommentInfoList() {
		return commentInfoList;
	}

	public void setCommentInfoList(List<CommentInfo> commentInfoList) {
		this.commentInfoList = commentInfoList;
	}

	public CommentInfo() {

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

	public Integer getComm_type() {
		return comm_type;
	}

	public void setComm_type(Integer comm_type) {
		this.comm_type = comm_type;
	}

	public String getComm_title() {
		return comm_title;
	}

	public void setComm_title(String comm_title) {
		this.comm_title = comm_title;
	}

	public String getComm_content() {
		return comm_content;
	}

	public void setComm_content(String comm_content) {
		this.comm_content = comm_content;
	}

	public String getComm_reply() {
		return comm_reply;
	}

	public void setComm_reply(String comm_reply) {
		this.comm_reply = comm_reply;
	}

	public Date getComm_time() {
		return comm_time;
	}

	public void setComm_time(Date comm_time) {
		this.comm_time = comm_time;
	}

	public String getComm_ip() {
		return comm_ip;
	}

	public void setComm_ip(String comm_ip) {
		this.comm_ip = comm_ip;
	}

	public Integer getComm_uid() {
		return comm_uid;
	}

	public void setComm_uid(Integer comm_uid) {
		this.comm_uid = comm_uid;
	}

	public String getComm_uname() {
		return comm_uname;
	}

	public void setComm_uname(String comm_uname) {
		this.comm_uname = comm_uname;
	}

	public Integer getComm_score() {
		return comm_score;
	}

	public void setComm_score(Integer comm_score) {
		this.comm_score = comm_score;
	}

	public Integer getOrder_value() {
		return order_value;
	}

	public void setOrder_value(Integer order_value) {
		this.order_value = order_value;
	}

	public Integer getComm_state() {
		return comm_state;
	}

	public void setComm_state(Integer comm_state) {
		this.comm_state = comm_state;
	}

	public String getComm_tag() {
		return comm_tag;
	}

	public void setComm_tag(String comm_tag) {
		this.comm_tag = comm_tag;
	}

	public String getComm_good() {
		return comm_good;
	}

	public void setComm_good(String comm_good) {
		this.comm_good = comm_good;
	}

	public String getComm_bad() {
		return comm_bad;
	}

	public void setComm_bad(String comm_bad) {
		this.comm_bad = comm_bad;
	}

	public String getComm_experience() {
		return comm_experience;
	}

	public void setComm_experience(String comm_experience) {
		this.comm_experience = comm_experience;
	}

	public Date getBuy_date() {
		return buy_date;
	}

	public void setBuy_date(Date buy_date) {
		this.buy_date = buy_date;
	}

	public Integer getComm_level() {
		return comm_level;
	}

	public void setComm_level(Integer comm_level) {
		this.comm_level = comm_level;
	}

	public Integer getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Integer orderId) {
		order_id = orderId;
	}

	public Integer getLink_f_id() {
		return link_f_id;
	}

	public void setLink_f_id(Integer link_f_id) {
		this.link_f_id = link_f_id;
	}

	public Integer getComm_tczh_id() {
		return comm_tczh_id;
	}

	public void setComm_tczh_id(Integer comm_tczh_id) {
		this.comm_tczh_id = comm_tczh_id;
	}

	public Integer getHas_pic() {
		return has_pic;
	}

	public void setHas_pic(Integer has_pic) {
		this.has_pic = has_pic;
	}

	public Integer getReply_uid() {
		return reply_uid;
	}

	public void setReply_uid(Integer reply_uid) {
		this.reply_uid = reply_uid;
	}

	public String getReply_content() {
		return reply_content;
	}

	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}

	public Date getReply_date() {
		return reply_date;
	}

	public void setReply_date(Date reply_date) {
		this.reply_date = reply_date;
	}

}