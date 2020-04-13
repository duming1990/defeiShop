package com.ebiz.webapp.dao.ibatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.UserRelationParDao;
import com.ebiz.webapp.domain.UserRelationPar;

/**
 * @author Wu,Yang
 * @version 2015-11-24 13:52
 */
@Service
public class UserRelationParDaoSqlMapImpl extends EntityDaoSqlMapImpl<UserRelationPar> implements UserRelationParDao {
	@SuppressWarnings("unchecked")
	public List<UserRelationPar> selectUserRelationParListWithScore(UserRelationPar t) {
		return super.getSqlMapClientTemplate().queryForList("selectUserRelationParListWithScore", t);
	}

	public String selectUserRelationParWithMaxLevel(UserRelationPar t) {
		return (String) super.getSqlMapClientTemplate().queryForObject("selectUserRelationParWithMaxLevel", t);
	}

	public Integer selectUserRelationParCountByBrotherLevel(UserRelationPar t) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectUserRelationParCountByBrotherLevel", t);
	}

	@SuppressWarnings("unchecked")
	public List<UserRelationPar> selectUserRelationParListForParam(UserRelationPar t) {
		return super.getSqlMapClientTemplate().queryForList("selectUserRelationParListForParam", t);
	}

	@SuppressWarnings("unchecked")
	public List<UserRelationPar> selectUserRelationParListWithAssessScore(UserRelationPar t) {
		return super.getSqlMapClientTemplate().queryForList("selectUserRelationParListWithAssessScore", t);
	}

	public String selectUserRelationParWithMaxLevelByUserParId(UserRelationPar t) {
		return (String) super.getSqlMapClientTemplate().queryForObject("selectUserRelationParWithMaxLevelByUserParId",
				t);
	}

	@SuppressWarnings("unchecked")
	public List<UserRelationPar> selectSpeciaPaginatedList(UserRelationPar t) {
		return super.getSqlMapClientTemplate().queryForList("selectSpeciaPaginatedList", t);
	}

	public Integer selectSpeciaCount(UserRelationPar t) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectSpeciaCount", t);
	}
}
