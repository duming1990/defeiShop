package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2013-04-17 19:38
 */
public class Msg extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer user_id;

	private Integer send_user_id;

	private Integer msg_type;

	private String msg_title;

	private String msg_content;

	private Date send_time;

	private Integer info_state;

	private Integer is_need_replay;

	private Integer is_replay_msg;

	private Integer replay_id;

	private Integer is_closed;

	private Integer is_send_all;

	private String reply_content;

	private Date reply_time;

	private Integer reply_user_id;

	private String reply_user_name;

	private String user_name;

	private Integer p_index;

	private String p_name;

	public Date getReply_time() {
		return reply_time;
	}

	public void setReply_time(Date reply_time) {
		this.reply_time = reply_time;
	}

	public Integer getReply_user_id() {
		return reply_user_id;
	}

	public void setReply_user_id(Integer reply_user_id) {
		this.reply_user_id = reply_user_id;
	}

	public String getReply_content() {
		return reply_content;
	}

	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}

	private List<MsgReceiver> msgReceiverList;

	private List<UserInfo> userInfoList;

	private List<Msg> msgList;

	public Msg() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getMsg_title() {
		return msg_title;
	}

	public void setMsg_title(String msg_title) {
		this.msg_title = msg_title;
	}

	public String getMsg_content() {
		return msg_content;
	}

	public void setMsg_content(String msg_content) {
		this.msg_content = msg_content;
	}

	public Date getSend_time() {
		return send_time;
	}

	public void setSend_time(Date send_time) {
		this.send_time = send_time;
	}

	public Integer getInfo_state() {
		return info_state;
	}

	public void setInfo_state(Integer info_state) {
		this.info_state = info_state;
	}

	public Integer getIs_need_replay() {
		return is_need_replay;
	}

	public void setIs_need_replay(Integer is_need_replay) {
		this.is_need_replay = is_need_replay;
	}

	public Integer getIs_replay_msg() {
		return is_replay_msg;
	}

	public void setIs_replay_msg(Integer is_replay_msg) {
		this.is_replay_msg = is_replay_msg;
	}

	public Integer getReplay_id() {
		return replay_id;
	}

	public void setReplay_id(Integer replay_id) {
		this.replay_id = replay_id;
	}

	public Integer getIs_closed() {
		return is_closed;
	}

	public void setIs_closed(Integer is_closed) {
		this.is_closed = is_closed;
	}

	public List<MsgReceiver> getMsgReceiverList() {
		return msgReceiverList;
	}

	public void setMsgReceiverList(List<MsgReceiver> msgReceiverList) {
		this.msgReceiverList = msgReceiverList;
	}

	public List<UserInfo> getUserInfoList() {
		return userInfoList;
	}

	public void setUserInfoList(List<UserInfo> userInfoList) {
		this.userInfoList = userInfoList;
	}

	public List<Msg> getMsgList() {
		return msgList;
	}

	public void setMsgList(List<Msg> msgList) {
		this.msgList = msgList;
	}

	public Integer getIs_send_all() {
		return is_send_all;
	}

	public void setIs_send_all(Integer isSendAll) {
		is_send_all = isSendAll;
	}

	public Integer getMsg_type() {
		return msg_type;
	}

	public void setMsg_type(Integer msg_type) {
		this.msg_type = msg_type;
	}

	public String getReply_user_name() {
		return reply_user_name;
	}

	public void setReply_user_name(String reply_user_name) {
		this.reply_user_name = reply_user_name;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
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

	public Integer getSend_user_id() {
		return send_user_id;
	}

	public void setSend_user_id(Integer send_user_id) {
		this.send_user_id = send_user_id;
	}

}