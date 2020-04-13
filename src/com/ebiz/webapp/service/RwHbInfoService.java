package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.RwHbInfo;

/**
 * @author Wu,Yang
 * @version 2016-06-16 18:18
 */
public interface RwHbInfoService {

	Integer createRwHbInfo(RwHbInfo t);

	int modifyRwHbInfo(RwHbInfo t);

	int removeRwHbInfo(RwHbInfo t);

	RwHbInfo getRwHbInfo(RwHbInfo t);

	List<RwHbInfo> getRwHbInfoList(RwHbInfo t);

	Integer getRwHbInfoCount(RwHbInfo t);

	List<RwHbInfo> getRwHbInfoPaginatedList(RwHbInfo t);

}