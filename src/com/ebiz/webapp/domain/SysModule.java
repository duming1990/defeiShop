package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.List;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2011-04-20 15:36
 */
public class SysModule extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Long mod_id;

	private Long par_id;

	private String mod_name;

	private String mod_desc;

	private String mod_url;

	private String ppdm_code;

	private Long order_value;

	private Integer is_public;

	private Integer is_lock;

	private Integer is_del;

	private Integer level;

	private Integer mod_level;

	private Integer is_leaf;

	private Integer mod_group;

	private List<BasePopedom> basePopedomList;

	private List<SysModule> sysModuleList;

	private String font_awesome;

	public SysModule() {

	}

	public Long getMod_id() {
		return mod_id;
	}

	public void setMod_id(Long mod_id) {
		this.mod_id = mod_id;
	}

	public Long getPar_id() {
		return par_id;
	}

	public void setPar_id(Long par_id) {
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

	public Long getOrder_value() {
		return order_value;
	}

	public void setOrder_value(Long order_value) {
		this.order_value = order_value;
	}

	public Integer getIs_public() {
		return is_public;
	}

	public void setIs_public(Integer is_public) {
		this.is_public = is_public;
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

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getPpdm_code() {
		return ppdm_code;
	}

	public void setPpdm_code(String ppdmCode) {
		ppdm_code = ppdmCode;
	}

	public List<BasePopedom> getBasePopedomList() {
		return basePopedomList;
	}

	public void setBasePopedomList(List<BasePopedom> basePopedomList) {
		this.basePopedomList = basePopedomList;
	}

	public Integer getMod_level() {
		return mod_level;
	}

	public void setMod_level(Integer mod_level) {
		this.mod_level = mod_level;
	}

	public Integer getIs_leaf() {
		return is_leaf;
	}

	public void setIs_leaf(Integer is_leaf) {
		this.is_leaf = is_leaf;
	}

	public Integer getMod_group() {
		return mod_group;
	}

	public void setMod_group(Integer mod_group) {
		this.mod_group = mod_group;
	}

	public String getFont_awesome() {
		return font_awesome;
	}

	public void setFont_awesome(String font_awesome) {
		this.font_awesome = font_awesome;
	}

	public List<SysModule> getSysModuleList() {
		return sysModuleList;
	}

	public void setSysModuleList(List<SysModule> sysModuleList) {
		this.sysModuleList = sysModuleList;
	}

}