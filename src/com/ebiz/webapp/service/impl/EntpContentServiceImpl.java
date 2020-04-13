package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.EntpContentDao;
import com.ebiz.webapp.domain.EntpContent;
import com.ebiz.webapp.service.EntpContentService;

/**
 * @author Wu,Yang
 * @version 2016-03-28 15:00
 */
@Service
public class EntpContentServiceImpl implements EntpContentService {

	@Resource
	private EntpContentDao entpContentDao;
	

	public Integer createEntpContent(EntpContent t) {
		return this.entpContentDao.insertEntity(t);
	}

	public EntpContent getEntpContent(EntpContent t) {
		return this.entpContentDao.selectEntity(t);
	}

	public Integer getEntpContentCount(EntpContent t) {
		return this.entpContentDao.selectEntityCount(t);
	}

	public List<EntpContent> getEntpContentList(EntpContent t) {
		return this.entpContentDao.selectEntityList(t);
	}

	public int modifyEntpContent(EntpContent t) {
		return this.entpContentDao.updateEntity(t);
	}

	public int removeEntpContent(EntpContent t) {
		return this.entpContentDao.deleteEntity(t);
	}

	public List<EntpContent> getEntpContentPaginatedList(EntpContent t) {
		return this.entpContentDao.selectEntityPaginatedList(t);
	}

}
