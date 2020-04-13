package com.ebiz.webapp.domain;

import java.io.Serializable;
import java.util.List;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2011-04-20 15:38
 */
public class ModPopedom extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private Integer mod_id;

	private Integer user_id;

	private Integer role_id;

	private String ppdm_code;

	List<ModPopedom> modPopedomList;

	public ModPopedom() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMod_id() {
		return mod_id;
	}

	public void setMod_id(Integer mod_id) {
		this.mod_id = mod_id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getRole_id() {
		return role_id;
	}

	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}

	public String getPpdm_code() {
		return ppdm_code;
	}

	public void setPpdm_code(String ppdmCode) {
		ppdm_code = ppdmCode;
	}

	public List<ModPopedom> getModPopedomList() {
		return modPopedomList;
	}

	public void setModPopedomList(List<ModPopedom> modPopedomList) {
		this.modPopedomList = modPopedomList;
	}

}