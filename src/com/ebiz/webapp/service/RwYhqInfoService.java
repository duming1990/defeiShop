package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.RwYhqInfo;

/**
 * @author Wu,Yang
 * @version 2016-06-16 18:18
 */
public interface RwYhqInfoService {

	Integer createRwYhqInfo(RwYhqInfo t);

	int modifyRwYhqInfo(RwYhqInfo t);

	int removeRwYhqInfo(RwYhqInfo t);

	RwYhqInfo getRwYhqInfo(RwYhqInfo t);

	List<RwYhqInfo> getRwYhqInfoList(RwYhqInfo t);

	Integer getRwYhqInfoCount(RwYhqInfo t);

	List<RwYhqInfo> getRwYhqInfoPaginatedList(RwYhqInfo t);

	RwYhqInfo getRwYhqInfoStatisticaSum(RwYhqInfo t);

}