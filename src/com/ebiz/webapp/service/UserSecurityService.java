package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.UserSecurity;

/**
 * @author Wu,Yang
 * @version 2015-12-06 15:07
 */
public interface UserSecurityService {

	Integer createUserSecurity(UserSecurity t);

	int modifyUserSecurity(UserSecurity t);

	int removeUserSecurity(UserSecurity t);

	UserSecurity getUserSecurity(UserSecurity t);

	List<UserSecurity> getUserSecurityList(UserSecurity t);

	Integer getUserSecurityCount(UserSecurity t);

	List<UserSecurity> getUserSecurityPaginatedList(UserSecurity t);

}