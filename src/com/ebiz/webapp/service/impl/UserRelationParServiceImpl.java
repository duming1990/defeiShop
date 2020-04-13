package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.UserRelationParDao;
import com.ebiz.webapp.domain.UserRelationPar;
import com.ebiz.webapp.service.UserRelationParService;

/**
 * @author Wu,Yang
 * @version 2015-11-24 13:52
 */
@Service
public class UserRelationParServiceImpl implements UserRelationParService {

	@Resource
	private UserRelationParDao userRelationParDao;

	public Integer createUserRelationPar(UserRelationPar t) {
		return this.userRelationParDao.insertEntity(t);
	}

	public UserRelationPar getUserRelationPar(UserRelationPar t) {
		return this.userRelationParDao.selectEntity(t);
	}

	public Integer getUserRelationParCount(UserRelationPar t) {
		return this.userRelationParDao.selectEntityCount(t);
	}

	public List<UserRelationPar> getUserRelationParList(UserRelationPar t) {
		return this.userRelationParDao.selectEntityList(t);
	}

	public List<UserRelationPar> getUserRelationParListWithScore(UserRelationPar t) {
		return this.userRelationParDao.selectUserRelationParListWithScore(t);
	}

	public int modifyUserRelationPar(UserRelationPar t) {
		return this.userRelationParDao.updateEntity(t);
	}

	public int removeUserRelationPar(UserRelationPar t) {
		return this.userRelationParDao.deleteEntity(t);
	}

	public List<UserRelationPar> getUserRelationParPaginatedList(UserRelationPar t) {
		return this.userRelationParDao.selectEntityPaginatedList(t);
	}

	@Override
	public List<UserRelationPar> getSpeciaPaginatedList(UserRelationPar t) {
		return this.userRelationParDao.selectSpeciaPaginatedList(t);
	}

	@Override
	public Integer getSpeciaCount(UserRelationPar t) {
		return this.userRelationParDao.selectSpeciaCount(t);
	}

}
