package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.OrderMergerInfo;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:55
 */
public interface OrderMergerInfoService {

	Integer createOrderMergerInfo(OrderMergerInfo t);

	int modifyOrderMergerInfo(OrderMergerInfo t);

	int removeOrderMergerInfo(OrderMergerInfo t);

	OrderMergerInfo getOrderMergerInfo(OrderMergerInfo t);

	List<OrderMergerInfo> getOrderMergerInfoList(OrderMergerInfo t);

	Integer getOrderMergerInfoCount(OrderMergerInfo t);

	List<OrderMergerInfo> getOrderMergerInfoPaginatedList(OrderMergerInfo t);

}