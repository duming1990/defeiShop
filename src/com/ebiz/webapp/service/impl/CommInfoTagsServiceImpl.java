package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.CommInfoTagsDao;
import com.ebiz.webapp.domain.CommInfoTags;
import com.ebiz.webapp.service.CommInfoTagsService;

/**
 * @author Wu,Yang
 * @version 2017-04-21 14:39
 */
@Service
public class CommInfoTagsServiceImpl implements CommInfoTagsService {

	@Resource
	private CommInfoTagsDao commInfoTagsDao;

	public Integer createCommInfoTags(CommInfoTags t) {
		return this.commInfoTagsDao.insertEntity(t);
	}

	public CommInfoTags getCommInfoTags(CommInfoTags t) {
		return this.commInfoTagsDao.selectEntity(t);
	}

	public Integer getCommInfoTagsCount(CommInfoTags t) {
		return this.commInfoTagsDao.selectEntityCount(t);
	}

	public Integer getCommInfoTagsJiaZaiCount(CommInfoTags t) {
		return this.commInfoTagsDao.selectCommInfoTagsJiaZaiCount(t);
	}

	public List<CommInfoTags> getCommInfoTagsList(CommInfoTags t) {
		return this.commInfoTagsDao.selectEntityList(t);
	}

	public int modifyCommInfoTags(CommInfoTags t) {
		return this.commInfoTagsDao.updateEntity(t);
	}

	public int removeCommInfoTags(CommInfoTags t) {
		return this.commInfoTagsDao.deleteEntity(t);
	}

	public List<CommInfoTags> getCommInfoTagsPaginatedList(CommInfoTags t) {
		return this.commInfoTagsDao.selectEntityPaginatedList(t);
	}

	public List<CommInfoTags> getCommInfoTagsJiaZaiPaginatedList(CommInfoTags t) {
		return this.commInfoTagsDao.selectCommInfoTagsJiaZaiPaginatedList(t);
	}
}
