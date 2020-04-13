package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.Role;

/**
 * @author Wu,Yang
 * @version 2011-04-20 02:13:04
 */
public interface RoleService {

	Integer createRole(Role t);

	int modifyRole(Role t);

	int removeRole(Role t);

	Role getRole(Role t);

	List<Role> getRoleList(Role t);

	Integer getRoleCount(Role t);

	List<Role> getRolePaginatedList(Role t);

}
