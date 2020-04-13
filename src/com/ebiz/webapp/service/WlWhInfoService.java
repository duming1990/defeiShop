package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.WlWhInfo;

/**
 * @author Wu,Yang
 * @version 2014-10-20 10:42
 */
public interface WlWhInfoService {

	Integer createWlWhInfo(WlWhInfo t);

	int modifyWlWhInfo(WlWhInfo t);

	int removeWlWhInfo(WlWhInfo t);

	WlWhInfo getWlWhInfo(WlWhInfo t);

	List<WlWhInfo> getWlWhInfoList(WlWhInfo t);

	Integer getWlWhInfoCount(WlWhInfo t);

	List<WlWhInfo> getWlWhInfoPaginatedList(WlWhInfo t);

}