package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.OrderLinkInfo;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:55
 */
public interface OrderLinkInfoService {

	Integer createOrderLinkInfo(OrderLinkInfo t);

	int modifyOrderLinkInfo(OrderLinkInfo t);

	int removeOrderLinkInfo(OrderLinkInfo t);

	OrderLinkInfo getOrderLinkInfo(OrderLinkInfo t);

	List<OrderLinkInfo> getOrderLinkInfoList(OrderLinkInfo t);

	Integer getOrderLinkInfoCount(OrderLinkInfo t);

	List<OrderLinkInfo> getOrderLinkInfoPaginatedList(OrderLinkInfo t);

}