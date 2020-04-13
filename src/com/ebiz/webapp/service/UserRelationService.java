package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.UserRelation;

/**
 * @author Wu,Yang
 * @version 2015-11-21 14:17
 */
public interface UserRelationService {

	Integer createUserRelation(UserRelation t);

	int modifyUserRelation(UserRelation t);

	int removeUserRelation(UserRelation t);

	UserRelation getUserRelation(UserRelation t);

	List<UserRelation> getUserRelationList(UserRelation t);

	Integer getUserRelationCount(UserRelation t);

	List<UserRelation> getUserRelationPaginatedList(UserRelation t);

	List<UserRelation> getUserRelationParentList(UserRelation t);

	List<UserRelation> getUserRelationSonList(UserRelation t);

}