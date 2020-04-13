package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.OrderReturnInfo;

/**
 * @author Wu,Yang
 * @version 2016-07-13 15:19
 */
public interface OrderReturnInfoService {

	Integer createOrderReturnInfo(OrderReturnInfo t);

	int modifyOrderReturnInfo(OrderReturnInfo t);

	int removeOrderReturnInfo(OrderReturnInfo t);

	OrderReturnInfo getOrderReturnInfo(OrderReturnInfo t);

	List<OrderReturnInfo> getOrderReturnInfoList(OrderReturnInfo t);

	Integer getOrderReturnInfoCount(OrderReturnInfo t);

	List<OrderReturnInfo> getOrderReturnInfoPaginatedList(OrderReturnInfo t);

	OrderReturnInfo getRefund(OrderReturnInfo t);

}