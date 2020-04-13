package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.RoleDao;
import com.ebiz.webapp.domain.Role;
import com.ebiz.webapp.service.RoleService;

/**
 * @author Wu,Yang
 * @version 2011-04-20 02:13:04
 */
@Service
public class RoleServiceImpl implements RoleService {

	@Resource
	private RoleDao roleDao;

	public Integer createRole(Role t) {
		return this.roleDao.insertEntity(t);
	}

	public int modifyRole(Role t) {
		return this.roleDao.updateEntity(t);
	}

	public int removeRole(Role t) {
		return this.roleDao.deleteEntity(t);
	}

	public Role getRole(Role t) {
		return this.roleDao.selectEntity(t);
	}

	public Integer getRoleCount(Role t) {
		return this.roleDao.selectEntityCount(t);
	}

	public List<Role> getRoleList(Role t) {
		return this.roleDao.selectEntityList(t);
	}

	public List<Role> getRolePaginatedList(Role t) {
		return this.roleDao.selectEntityPaginatedList(t);
	}

}
