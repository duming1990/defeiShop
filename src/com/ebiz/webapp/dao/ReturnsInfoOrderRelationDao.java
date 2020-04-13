package com.ebiz.webapp.dao;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.ReturnsInfoOrderRelation;

/**
 * @version 2014-06-19 10:12
 */
public interface ReturnsInfoOrderRelationDao extends EntityDao<ReturnsInfoOrderRelation> {
	public String selectReturnsInfoOrderRelationMaxId(ReturnsInfoOrderRelation t);
}
