package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.UserSecurityDao;
import com.ebiz.webapp.domain.UserSecurity;
import com.ebiz.webapp.service.UserSecurityService;

/**
 * @author Wu,Yang
 * @version 2015-12-06 15:07
 */
@Service
public class UserSecurityServiceImpl implements UserSecurityService {

	@Resource
	private UserSecurityDao userSecurityDao;
	

	public Integer createUserSecurity(UserSecurity t) {
		return this.userSecurityDao.insertEntity(t);
	}

	public UserSecurity getUserSecurity(UserSecurity t) {
		return this.userSecurityDao.selectEntity(t);
	}

	public Integer getUserSecurityCount(UserSecurity t) {
		return this.userSecurityDao.selectEntityCount(t);
	}

	public List<UserSecurity> getUserSecurityList(UserSecurity t) {
		return this.userSecurityDao.selectEntityList(t);
	}

	public int modifyUserSecurity(UserSecurity t) {
		return this.userSecurityDao.updateEntity(t);
	}

	public int removeUserSecurity(UserSecurity t) {
		return this.userSecurityDao.deleteEntity(t);
	}

	public List<UserSecurity> getUserSecurityPaginatedList(UserSecurity t) {
		return this.userSecurityDao.selectEntityPaginatedList(t);
	}

}
