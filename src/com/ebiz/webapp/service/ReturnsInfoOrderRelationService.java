package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.ReturnsInfoOrderRelation;

/**
 * @version 2014-06-19 10:12
 */
public interface ReturnsInfoOrderRelationService {

	Integer createReturnsInfoOrderRelation(ReturnsInfoOrderRelation t);

	int modifyReturnsInfoOrderRelation(ReturnsInfoOrderRelation t);

	int removeReturnsInfoOrderRelation(ReturnsInfoOrderRelation t);

	ReturnsInfoOrderRelation getReturnsInfoOrderRelation(ReturnsInfoOrderRelation t);

	List<ReturnsInfoOrderRelation> getReturnsInfoOrderRelationList(ReturnsInfoOrderRelation t);

	Integer getReturnsInfoOrderRelationCount(ReturnsInfoOrderRelation t);

	List<ReturnsInfoOrderRelation> getReturnsInfoOrderRelationPaginatedList(ReturnsInfoOrderRelation t);

	String getReturnsInfoOrderRelationMaxId(ReturnsInfoOrderRelation t);
}