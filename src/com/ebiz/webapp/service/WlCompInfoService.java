package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.WlCompInfo;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:54
 */
public interface WlCompInfoService {

	Integer createWlCompInfo(WlCompInfo t);

	int modifyWlCompInfo(WlCompInfo t);

	int removeWlCompInfo(WlCompInfo t);

	WlCompInfo getWlCompInfo(WlCompInfo t);

	List<WlCompInfo> getWlCompInfoList(WlCompInfo t);

	Integer getWlCompInfoCount(WlCompInfo t);

	List<WlCompInfo> getWlCompInfoPaginatedList(WlCompInfo t);

	List<WlCompInfo> getWlCompInfoGroupByPalpha(WlCompInfo t);

}