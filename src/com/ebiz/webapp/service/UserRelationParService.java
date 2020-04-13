package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.UserRelationPar;

/**
 * @author Wu,Yang
 * @version 2015-11-24 13:52
 */
public interface UserRelationParService {

	Integer createUserRelationPar(UserRelationPar t);

	int modifyUserRelationPar(UserRelationPar t);

	int removeUserRelationPar(UserRelationPar t);

	UserRelationPar getUserRelationPar(UserRelationPar t);

	List<UserRelationPar> getUserRelationParList(UserRelationPar t);

	Integer getUserRelationParCount(UserRelationPar t);

	List<UserRelationPar> getUserRelationParPaginatedList(UserRelationPar t);

	List<UserRelationPar> getUserRelationParListWithScore(UserRelationPar t);

	List<UserRelationPar> getSpeciaPaginatedList(UserRelationPar t);

	Integer getSpeciaCount(UserRelationPar t);

}