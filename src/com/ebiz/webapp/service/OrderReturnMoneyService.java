package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.OrderReturnMoney;

/**
 * @author Wu,Yang
 * @version 2016-07-13 15:19
 */
public interface OrderReturnMoneyService {

	Integer createOrderReturnMoney(OrderReturnMoney t);

	int modifyOrderReturnMoney(OrderReturnMoney t);

	int removeOrderReturnMoney(OrderReturnMoney t);

	OrderReturnMoney getOrderReturnMoney(OrderReturnMoney t);

	List<OrderReturnMoney> getOrderReturnMoneyList(OrderReturnMoney t);

	Integer getOrderReturnMoneyCount(OrderReturnMoney t);

	List<OrderReturnMoney> getOrderReturnMoneyPaginatedList(OrderReturnMoney t);

}