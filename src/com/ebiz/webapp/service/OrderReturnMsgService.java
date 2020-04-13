package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.OrderReturnMsg;

/**
 * @author Wu,Yang
 * @version 2016-07-13 15:19
 */
public interface OrderReturnMsgService {

	Integer createOrderReturnMsg(OrderReturnMsg t);

	int modifyOrderReturnMsg(OrderReturnMsg t);

	int removeOrderReturnMsg(OrderReturnMsg t);

	OrderReturnMsg getOrderReturnMsg(OrderReturnMsg t);

	List<OrderReturnMsg> getOrderReturnMsgList(OrderReturnMsg t);

	Integer getOrderReturnMsgCount(OrderReturnMsg t);

	List<OrderReturnMsg> getOrderReturnMsgPaginatedList(OrderReturnMsg t);

	List<OrderReturnMsg> getOrderReturnMsgLeftJoinOrderReturnInfoPaginatedList(OrderReturnMsg t);

}