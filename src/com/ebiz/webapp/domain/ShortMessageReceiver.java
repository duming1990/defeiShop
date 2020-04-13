package com.ebiz.webapp.domain;

import java.io.Serializable;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2018-07-02 10:38
 */
public class ShortMessageReceiver extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;
	
	private Integer msg_id;
	
	private Integer receiver_user_id;
	
	public ShortMessageReceiver() {

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
	
}