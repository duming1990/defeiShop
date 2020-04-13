package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2011-04-20 15:36
 */
public class BaseData extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer type;

	private String type_name;

	private String remark;

	private Integer order_value;

	private Integer is_lock;

	private Integer is_del;

	private Date add_date;

	private Integer add_user_id;

	private Date update_date;

	private Integer update_user_id;

	private Date del_date;

	private Integer del_user_id;

	private Integer pre_number;

	private Integer pre_number2;

	private Integer pre_number3;

	private BigDecimal pre_decimal1;

	private BigDecimal pre_decimal2;

	private String pre_varchar_1;

	private String pre_varchar_2;

	private String pre_varchar_3;

	private String img_path;

	public Integer getPre_number3() {
		return pre_number3;
	}

	public void setPre_number3(Integer pre_number3) {
		this.pre_number3 = pre_number3;
	}

	public BaseData() {

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

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getOrder_value() {
		return order_value;
	}

	public void setOrder_value(Integer order_value) {
		this.order_value = order_value;
	}

	public Integer getIs_lock() {
		return is_lock;
	}

	public void setIs_lock(Integer is_lock) {
		this.is_lock = is_lock;
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

	public Integer getPre_number2() {
		return pre_number2;
	}

	public void setPre_number2(Integer pre_number2) {
		this.pre_number2 = pre_number2;
	}

	public Integer getPre_number() {
		return pre_number;
	}

	public void setPre_number(Integer preNumber) {
		pre_number = preNumber;
	}

	public BigDecimal getPre_decimal1() {
		return pre_decimal1;
	}

	public void setPre_decimal1(BigDecimal pre_decimal1) {
		this.pre_decimal1 = pre_decimal1;
	}

	public BigDecimal getPre_decimal2() {
		return pre_decimal2;
	}

	public void setPre_decimal2(BigDecimal pre_decimal2) {
		this.pre_decimal2 = pre_decimal2;
	}

	public String getPre_varchar_1() {
		return pre_varchar_1;
	}

	public void setPre_varchar_1(String pre_varchar_1) {
		this.pre_varchar_1 = pre_varchar_1;
	}

	public String getPre_varchar_2() {
		return pre_varchar_2;
	}

	public void setPre_varchar_2(String pre_varchar_2) {
		this.pre_varchar_2 = pre_varchar_2;
	}

	public String getPre_varchar_3() {
		return pre_varchar_3;
	}

	public void setPre_varchar_3(String pre_varchar_3) {
		this.pre_varchar_3 = pre_varchar_3;
	}

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

}