package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.ReturnsInfoOrderRelationDao;
import com.ebiz.webapp.domain.ReturnsInfoOrderRelation;

/**
 * @version 2014-06-19 10:12
 */
@Service
public class ReturnsInfoOrderRelationDaoSqlMapImpl extends EntityDaoSqlMapImpl<ReturnsInfoOrderRelation> implements
		ReturnsInfoOrderRelationDao {
	@Override
	public String selectReturnsInfoOrderRelationMaxId(ReturnsInfoOrderRelation t) {
		return (String) super.getSqlMapClientTemplate().queryForObject("selectReturnsInfoOrderRelationMaxId", t);

	}

}
