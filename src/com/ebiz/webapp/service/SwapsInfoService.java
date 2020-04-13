package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.SwapsInfo;

/**
 * @version 2014-06-18 16:13
 */
public interface SwapsInfoService {

	Integer createSwapsInfo(SwapsInfo t);

	int modifySwapsInfo(SwapsInfo t);

	int removeSwapsInfo(SwapsInfo t);

	SwapsInfo getSwapsInfo(SwapsInfo t);

	List<SwapsInfo> getSwapsInfoList(SwapsInfo t);

	Integer getSwapsInfoCount(SwapsInfo t);

	List<SwapsInfo> getSwapsInfoPaginatedList(SwapsInfo t);

	public void sendGoods(SwapsInfo entity);

}