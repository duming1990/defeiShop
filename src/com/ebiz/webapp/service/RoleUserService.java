package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.RoleUser;

/**
 * @author Wu,Yang
 * @version 2011-04-20 02:13:04
 */
public interface RoleUserService {

	Integer createRoleUser(RoleUser t);

	int modifyRoleUser(RoleUser t);

	int removeRoleUser(RoleUser t);

	RoleUser getRoleUser(RoleUser t);

	List<RoleUser> getRoleUserList(RoleUser t);

	Integer getRoleUserCount(RoleUser t);

	List<RoleUser> getRoleUserPaginatedList(RoleUser t);

}
