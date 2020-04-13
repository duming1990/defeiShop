package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.AppInfoDao;
import com.ebiz.webapp.domain.AppInfo;
import com.ebiz.webapp.service.AppInfoService;

/**
 * @author Wu,Yang
 * @version 2018-03-26 10:36
 */
@Service
public class AppInfoServiceImpl implements AppInfoService {

	@Resource
	private AppInfoDao appInfoDao;
	

	public Integer createAppInfo(AppInfo t) {
		return this.appInfoDao.insertEntity(t);
	}

	public AppInfo getAppInfo(AppInfo t) {
		return this.appInfoDao.selectEntity(t);
	}

	public Integer getAppInfoCount(AppInfo t) {
		return this.appInfoDao.selectEntityCount(t);
	}

	public List<AppInfo> getAppInfoList(AppInfo t) {
		return this.appInfoDao.selectEntityList(t);
	}

	public int modifyAppInfo(AppInfo t) {
		return this.appInfoDao.updateEntity(t);
	}

	public int removeAppInfo(AppInfo t) {
		return this.appInfoDao.deleteEntity(t);
	}

	public List<AppInfo> getAppInfoPaginatedList(AppInfo t) {
		return this.appInfoDao.selectEntityPaginatedList(t);
	}

}
