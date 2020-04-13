package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.PdContentDao;
import com.ebiz.webapp.domain.PdContent;
import com.ebiz.webapp.service.PdContentService;

/**
 * @author Wu,Yang
 * @version 2012-03-21 17:16
 */
@Service
public class PdContentServiceImpl implements PdContentService {

	@Resource
	private PdContentDao pdContentDao;

	public Integer createPdContent(PdContent t) {
		return this.pdContentDao.insertEntity(t);
	}

	public PdContent getPdContent(PdContent t) {
		return this.pdContentDao.selectEntity(t);
	}

	public Integer getPdContentCount(PdContent t) {
		return this.pdContentDao.selectEntityCount(t);
	}

	public List<PdContent> getPdContentList(PdContent t) {
		return this.pdContentDao.selectEntityList(t);
	}

	public int modifyPdContent(PdContent t) {
		return this.pdContentDao.updateEntity(t);
	}

	public int removePdContent(PdContent t) {
		return this.pdContentDao.deleteEntity(t);
	}

	public List<PdContent> getPdContentPaginatedList(PdContent t) {
		return this.pdContentDao.selectEntityPaginatedList(t);
	}

}
