package com.ebiz.webapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.UserRelationDao;
import com.ebiz.webapp.domain.UserRelation;
import com.ebiz.webapp.service.UserRelationService;

/**
 * @author Wu,Yang
 * @version 2015-11-21 14:17
 */
@Service
public class UserRelationServiceImpl implements UserRelationService {

	@Resource
	private UserRelationDao userRelationDao;

	public Integer createUserRelation(UserRelation t) {
		return this.userRelationDao.insertEntity(t);
	}

	public UserRelation getUserRelation(UserRelation t) {
		return this.userRelationDao.selectEntity(t);
	}

	public Integer getUserRelationCount(UserRelation t) {
		return this.userRelationDao.selectEntityCount(t);
	}

	public List<UserRelation> getUserRelationList(UserRelation t) {
		return this.userRelationDao.selectEntityList(t);
	}

	public int modifyUserRelation(UserRelation t) {
		return this.userRelationDao.updateEntity(t);
	}

	public int removeUserRelation(UserRelation t) {
		return this.userRelationDao.deleteEntity(t);
	}

	public List<UserRelation> getUserRelationPaginatedList(UserRelation t) {
		return this.userRelationDao.selectEntityPaginatedList(t);
	}

	/**
	 * @author Wu,Yang
	 * @desc 取父节点list
	 */
	public List<UserRelation> getUserRelationParentList(UserRelation t) {
		// 子查父
		List<UserRelation> parentList = new ArrayList<UserRelation>();
		List<UserRelation> getUserRelationParentList = this.getParentList(t.getUser_par_id(), parentList);
		return getUserRelationParentList;
	}

	/**
	 * @author Wu,Yang
	 * @desc 取子节点list
	 */
	public List<UserRelation> getUserRelationSonList(UserRelation t) {

		// 父查子
		List<UserRelation> sonList = new ArrayList<UserRelation>();
		List<UserRelation> getUserRelationSonList = this.getSonList(t.getUser_id(), sonList);
		return getUserRelationSonList;
	}

	public List<UserRelation> getParentList(Integer par_id, List<UserRelation> parentList) {

		UserRelation userRelation = new UserRelation();
		userRelation.setUser_id(par_id);
		userRelation = this.userRelationDao.selectEntity(userRelation);
		if (null != userRelation) {
			parentList.add(userRelation);
			this.getParentList(userRelation.getUser_par_id(), parentList);
		}
		return parentList;
	}

	public List<UserRelation> getSonList(Integer par_id, List<UserRelation> sonList) {
		UserRelation UserRelation = new UserRelation();
		UserRelation.setUser_par_id(par_id);
		List<UserRelation> UserRelationList = this.userRelationDao.selectEntityList(UserRelation);
		if (null != UserRelationList && UserRelationList.size() > 0) {
			for (UserRelation temp : UserRelationList) {
				sonList.add(temp);
				this.getSonList(temp.getUser_id(), sonList);
			}
		}
		return sonList;
	}
}
