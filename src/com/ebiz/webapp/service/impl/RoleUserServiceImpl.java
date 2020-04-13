package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.RoleUserDao;
import com.ebiz.webapp.domain.RoleUser;
import com.ebiz.webapp.service.RoleUserService;

/**
 * @author Wu,Yang
 * @version 2011-04-20 02:13:04
 */
@Service
public class RoleUserServiceImpl implements RoleUserService {

	@Resource
	private RoleUserDao roleUserDao;

	public Integer createRoleUser(RoleUser t) {
		return this.roleUserDao.insertEntity(t);
	}

	public int modifyRoleUser(RoleUser t) {
		return this.roleUserDao.updateEntity(t);
	}

	public int removeRoleUser(RoleUser t) {
		return this.roleUserDao.deleteEntity(t);
	}

	public RoleUser getRoleUser(RoleUser t) {
		return this.roleUserDao.selectEntity(t);
	}

	public Integer getRoleUserCount(RoleUser t) {
		return this.roleUserDao.selectEntityCount(t);
	}

	public List<RoleUser> getRoleUserList(RoleUser t) {
		return this.roleUserDao.selectEntityList(t);
	}

	public List<RoleUser> getRoleUserPaginatedList(RoleUser t) {
		return this.roleUserDao.selectEntityPaginatedList(t);
	}

}
