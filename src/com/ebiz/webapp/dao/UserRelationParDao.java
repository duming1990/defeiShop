package com.ebiz.webapp.dao;

import java.util.List;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.UserRelationPar;

/**
 * @author Wu,Yang
 * @version 2015-11-24 13:52
 */
public interface UserRelationParDao extends EntityDao<UserRelationPar> {
	public List<UserRelationPar> selectUserRelationParListWithScore(UserRelationPar t);

	public String selectUserRelationParWithMaxLevel(UserRelationPar t);

	public Integer selectUserRelationParCountByBrotherLevel(UserRelationPar t);

	public List<UserRelationPar> selectUserRelationParListForParam(UserRelationPar t);

	public List<UserRelationPar> selectUserRelationParListWithAssessScore(UserRelationPar t);

	public String selectUserRelationParWithMaxLevelByUserParId(UserRelationPar t);

	public List<UserRelationPar> selectSpeciaPaginatedList(UserRelationPar t);

	public Integer selectSpeciaCount(UserRelationPar t);
}
