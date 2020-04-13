package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.AppInfo;

/**
 * @author Wu,Yang
 * @version 2018-03-26 10:36
 */
public interface AppInfoService {

	Integer createAppInfo(AppInfo t);

	int modifyAppInfo(AppInfo t);

	int removeAppInfo(AppInfo t);

	AppInfo getAppInfo(AppInfo t);

	List<AppInfo> getAppInfoList(AppInfo t);

	Integer getAppInfoCount(AppInfo t);

	List<AppInfo> getAppInfoPaginatedList(AppInfo t);

}