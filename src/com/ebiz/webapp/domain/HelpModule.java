package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.List;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Jin,QingHua
 */
public class HelpModule extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	/**
	 * 规则：00,00,00,00 8位数字，两位一组，分四组，从左到右分别为1、2、3、4级目录，最多支持四级目录，每级目录序号0-99递增。
	 */
	private Integer h_mod_id;

	private Integer par_id;

	private String mod_name;

	private String mod_desc;

	private String mod_url;

	private Integer order_value;

	/**
	 * 0：未锁定 1：已锁定，不能删除，即不能将IS_DEL设置为1
	 */
	private Integer is_lock;

	private Integer del_mark;

	private List<HelpModule> helpModuleList;

	public HelpModule() {

	}

	public HelpModule(Integer h_mod_id) {
		this.h_mod_id = h_mod_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getH_mod_id() {
		return h_mod_id;
	}

	public void setH_mod_id(Integer h_mod_id) {
		this.h_mod_id = h_mod_id;
	}

	public Integer getPar_id() {
		return par_id;
	}

	public void setPar_id(Integer par_id) {
		this.par_id = par_id;
	}

	public String getMod_name() {
		return mod_name;
	}

	public void setMod_name(String mod_name) {
		this.mod_name = mod_name;
	}

	public String getMod_desc() {
		return mod_desc;
	}

	public void setMod_desc(String mod_desc) {
		this.mod_desc = mod_desc;
	}

	public String getMod_url() {
		return mod_url;
	}

	public void setMod_url(String mod_url) {
		this.mod_url = mod_url;
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

	public Integer getDel_mark() {
		return del_mark;
	}

	public void setDel_mark(Integer del_mark) {
		this.del_mark = del_mark;
	}

	public List<HelpModule> getHelpModuleList() {
		return helpModuleList;
	}

	public void setHelpModuleList(List<HelpModule> helpModuleList) {
		this.helpModuleList = helpModuleList;
	}

}