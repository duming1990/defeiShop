package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.EntpInfo;

/**
 * @author Wu,Yang
 * @version 2012-03-21 17:16
 */
public interface EntpInfoService {

	Integer createEntpInfo(EntpInfo t);

	int modifyEntpInfo(EntpInfo t);

	int removeEntpInfo(EntpInfo t);

	EntpInfo getEntpInfo(EntpInfo t);

	List<EntpInfo> getEntpInfoList(EntpInfo t);

	Integer getEntpInfoCount(EntpInfo t);

	List<EntpInfo> getEntpInfoPaginatedList(EntpInfo t);

	void modifyEntpInfoListForAutoEntpFanXianRule(EntpInfo t);

	void modifyEntpAndCommState(EntpInfo t, CommInfo c);

	Integer modifyEntpInfoForCancel(EntpInfo t);

	List<EntpInfo> getEntpInfoDistance(EntpInfo t);

}