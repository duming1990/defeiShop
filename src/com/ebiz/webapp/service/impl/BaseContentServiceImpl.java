package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.BaseContentDao;
import com.ebiz.webapp.domain.BaseContent;
import com.ebiz.webapp.service.BaseContentService;

/**
 * @author Wu,Yang
 * @version 2012-05-30 08:59
 */
@Service
public class BaseContentServiceImpl implements BaseContentService {

	@Resource
	private BaseContentDao baseContentDao;

	public Integer createBaseContent(BaseContent t) {
		return this.baseContentDao.insertEntity(t);
	}

	public BaseContent getBaseContent(BaseContent t) {
		return this.baseContentDao.selectEntity(t);
	}

	public Integer getBaseContentCount(BaseContent t) {
		return this.baseContentDao.selectEntityCount(t);
	}

	public List<BaseContent> getBaseContentList(BaseContent t) {
		return this.baseContentDao.selectEntityList(t);
	}

	public int modifyBaseContent(BaseContent t) {
		return this.baseContentDao.updateEntity(t);
	}

	public int removeBaseContent(BaseContent t) {
		return this.baseContentDao.deleteEntity(t);
	}

	public List<BaseContent> getBaseContentPaginatedList(BaseContent t) {
		return this.baseContentDao.selectEntityPaginatedList(t);
	}

}
