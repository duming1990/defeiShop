package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.AppTokensDao;
import com.ebiz.webapp.domain.AppTokens;
import com.ebiz.webapp.service.AppTokensService;

/**
 * @author Wu,Yang
 * @version 2018-03-26 10:36
 */
@Service
public class AppTokensServiceImpl implements AppTokensService {

	@Resource
	private AppTokensDao appTokensDao;
	

	public Integer createAppTokens(AppTokens t) {
		return this.appTokensDao.insertEntity(t);
	}

	public AppTokens getAppTokens(AppTokens t) {
		return this.appTokensDao.selectEntity(t);
	}

	public Integer getAppTokensCount(AppTokens t) {
		return this.appTokensDao.selectEntityCount(t);
	}

	public List<AppTokens> getAppTokensList(AppTokens t) {
		return this.appTokensDao.selectEntityList(t);
	}

	public int modifyAppTokens(AppTokens t) {
		return this.appTokensDao.updateEntity(t);
	}

	public int removeAppTokens(AppTokens t) {
		return this.appTokensDao.deleteEntity(t);
	}

	public List<AppTokens> getAppTokensPaginatedList(AppTokens t) {
		return this.appTokensDao.selectEntityPaginatedList(t);
	}

}
