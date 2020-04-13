package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2018-07-02 10:24
 */
public class ShortMessage extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private String title;

	private String message;

	private Integer is_all;

	private Integer is_del;

	private Date add_date;

	private Integer add_user_id;

	private Date del_date;

	private String message1;

	private String message2;

	private String message3;

	private String message4;

	public List<ShortMessageReceiver> getShortMessageReceiver() {
		return ShortMessageReceiver;
	}

	public void setShortMessageReceiver(List<ShortMessageReceiver> shortMessageReceiver) {
		ShortMessageReceiver = shortMessageReceiver;
	}

	private Integer del_user_id;

	private List<ShortMessageReceiver> ShortMessageReceiver;

	public ShortMessage() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getIs_all() {
		return is_all;
	}

	public void setIs_all(Integer is_all) {
		this.is_all = is_all;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
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

	public String getMessage1() {
		return message1;
	}

	public void setMessage1(String message1) {
		this.message1 = message1;
	}

	public String getMessage2() {
		return message2;
	}

	public void setMessage2(String message2) {
		this.message2 = message2;
	}

	public String getMessage3() {
		return message3;
	}

	public void setMessage3(String message3) {
		this.message3 = message3;
	}

	public String getMessage4() {
		return message4;
	}

	public void setMessage4(String message4) {
		this.message4 = message4;
	}

}