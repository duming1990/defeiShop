package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.ReturnsInfoOrderRelationDao;
import com.ebiz.webapp.domain.ReturnsInfoOrderRelation;
import com.ebiz.webapp.service.ReturnsInfoOrderRelationService;

/**
 * @version 2014-06-19 10:12
 */
@Service
public class ReturnsInfoOrderRelationServiceImpl implements ReturnsInfoOrderRelationService {

	@Resource
	private ReturnsInfoOrderRelationDao returnsInfoOrderRelationDao;

	public Integer createReturnsInfoOrderRelation(ReturnsInfoOrderRelation t) {
		return this.returnsInfoOrderRelationDao.insertEntity(t);
	}

	public ReturnsInfoOrderRelation getReturnsInfoOrderRelation(ReturnsInfoOrderRelation t) {
		return this.returnsInfoOrderRelationDao.selectEntity(t);
	}

	public Integer getReturnsInfoOrderRelationCount(ReturnsInfoOrderRelation t) {
		return this.returnsInfoOrderRelationDao.selectEntityCount(t);
	}

	public List<ReturnsInfoOrderRelation> getReturnsInfoOrderRelationList(ReturnsInfoOrderRelation t) {
		return this.returnsInfoOrderRelationDao.selectEntityList(t);
	}

	public int modifyReturnsInfoOrderRelation(ReturnsInfoOrderRelation t) {
		return this.returnsInfoOrderRelationDao.updateEntity(t);
	}

	public int removeReturnsInfoOrderRelation(ReturnsInfoOrderRelation t) {
		return this.returnsInfoOrderRelationDao.deleteEntity(t);
	}

	public List<ReturnsInfoOrderRelation> getReturnsInfoOrderRelationPaginatedList(ReturnsInfoOrderRelation t) {
		return this.returnsInfoOrderRelationDao.selectEntityPaginatedList(t);
	}

	public String getReturnsInfoOrderRelationMaxId(ReturnsInfoOrderRelation t) {
		return this.returnsInfoOrderRelationDao.selectReturnsInfoOrderRelationMaxId(t);
	}

}
