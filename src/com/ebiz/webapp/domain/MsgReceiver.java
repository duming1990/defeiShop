package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2013-04-17 19:38
 */
public class MsgReceiver extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer msg_id;

	private Integer receiver_user_id;

	private String receiver_user_mobile;

	private Integer is_read;

	private Integer is_reply;

	private Integer is_del;

	private Integer user_id;

	private String msg_title;

	private Date send_time;

	private Integer info_state;

	private String msg_content;

	private String user_name;

	private Integer is_send_all;

	public MsgReceiver() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(Integer msg_id) {
		this.msg_id = msg_id;
	}

	public Integer getReceiver_user_id() {
		return receiver_user_id;
	}

	public void setReceiver_user_id(Integer receiver_user_id) {
		this.receiver_user_id = receiver_user_id;
	}

	public Integer getIs_read() {
		return is_read;
	}

	public void setIs_read(Integer is_read) {
		this.is_read = is_read;
	}

	public Integer getIs_reply() {
		return is_reply;
	}

	public void setIs_reply(Integer is_reply) {
		this.is_reply = is_reply;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

	public Integer getInfo_state() {
		return info_state;
	}

	public void setInfo_state(Integer info_state) {
		this.info_state = info_state;
	}

	public String getMsg_content() {
		return msg_content;
	}

	public void setMsg_content(String msg_content) {
		this.msg_content = msg_content;
	}

	public String getMsg_title() {
		return msg_title;
	}

	public void setMsg_title(String msg_title) {
		this.msg_title = msg_title;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Date getSend_time() {
		return send_time;
	}

	public void setSend_time(Date send_time) {
		this.send_time = send_time;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getReceiver_user_mobile() {
		return receiver_user_mobile;
	}

	public void setReceiver_user_mobile(String receiver_user_mobile) {
		this.receiver_user_mobile = receiver_user_mobile;
	}

	public Integer getIs_send_all() {
		return is_send_all;
	}

	public void setIs_send_all(Integer is_send_all) {
		this.is_send_all = is_send_all;
	}
}