package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.WlOrderInfo;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:54
 */
public interface WlOrderInfoService {

	Integer createWlOrderInfo(WlOrderInfo t);

	int modifyWlOrderInfo(WlOrderInfo t);

	int removeWlOrderInfo(WlOrderInfo t);

	WlOrderInfo getWlOrderInfo(WlOrderInfo t);

	List<WlOrderInfo> getWlOrderInfoList(WlOrderInfo t);

	Integer getWlOrderInfoCount(WlOrderInfo t);

	List<WlOrderInfo> getWlOrderInfoPaginatedList(WlOrderInfo t);

}