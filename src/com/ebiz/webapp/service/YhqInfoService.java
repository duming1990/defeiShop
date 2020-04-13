package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.YhqInfo;

/**
 * @author Wu,Yang
 * @version 2017-04-19 17:50
 */
public interface YhqInfoService {

	Integer createYhqInfo(YhqInfo t);

	int modifyYhqInfo(YhqInfo t);

	int removeYhqInfo(YhqInfo t);

	YhqInfo getYhqInfo(YhqInfo t);

	List<YhqInfo> getYhqInfoList(YhqInfo t);

	Integer getYhqInfoCount(YhqInfo t);

	List<YhqInfo> getYhqInfoPaginatedList(YhqInfo t);

}