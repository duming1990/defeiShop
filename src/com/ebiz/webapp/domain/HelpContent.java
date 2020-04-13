package com.ebiz.webapp.domain;

import java.io.Serializable;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Jin,QingHua
 */
public class HelpContent extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private String content;

	public HelpContent() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}