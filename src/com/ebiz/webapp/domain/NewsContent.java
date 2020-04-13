package com.ebiz.webapp.domain;

import java.io.Serializable;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Wu,Yang
 * @version 2011-11-22 16:51
 */
public class NewsContent extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;

	private String content;

	public NewsContent() {

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