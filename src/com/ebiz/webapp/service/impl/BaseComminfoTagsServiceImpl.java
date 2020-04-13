package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.BaseComminfoTagsDao;
import com.ebiz.webapp.domain.BaseComminfoTags;
import com.ebiz.webapp.service.BaseComminfoTagsService;

/**
 * @author Wu,Yang
 * @version 2017-04-21 14:39
 */
@Service
public class BaseComminfoTagsServiceImpl implements BaseComminfoTagsService {

	@Resource
	private BaseComminfoTagsDao baseComminfoTagsDao;
	

	public Integer createBaseComminfoTags(BaseComminfoTags t) {
		return this.baseComminfoTagsDao.insertEntity(t);
	}

	public BaseComminfoTags getBaseComminfoTags(BaseComminfoTags t) {
		return this.baseComminfoTagsDao.selectEntity(t);
	}

	public Integer getBaseComminfoTagsCount(BaseComminfoTags t) {
		return this.baseComminfoTagsDao.selectEntityCount(t);
	}

	public List<BaseComminfoTags> getBaseComminfoTagsList(BaseComminfoTags t) {
		return this.baseComminfoTagsDao.selectEntityList(t);
	}

	public int modifyBaseComminfoTags(BaseComminfoTags t) {
		return this.baseComminfoTagsDao.updateEntity(t);
	}

	public int removeBaseComminfoTags(BaseComminfoTags t) {
		return this.baseComminfoTagsDao.deleteEntity(t);
	}

	public List<BaseComminfoTags> getBaseComminfoTagsPaginatedList(BaseComminfoTags t) {
		return this.baseComminfoTagsDao.selectEntityPaginatedList(t);
	}
	
	public List<BaseComminfoTags> getBaseComminfoTagsPaginatedListByPIndex(BaseComminfoTags t) {
		return this.baseComminfoTagsDao.selectBaseComminfoTagsPaginatedListByPIndex(t);
	}

}
