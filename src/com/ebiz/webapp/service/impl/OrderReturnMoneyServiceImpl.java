package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.OrderReturnMoneyDao;
import com.ebiz.webapp.domain.OrderReturnMoney;
import com.ebiz.webapp.service.OrderReturnMoneyService;

/**
 * @author Wu,Yang
 * @version 2016-07-13 15:19
 */
@Service
public class OrderReturnMoneyServiceImpl implements OrderReturnMoneyService {

	@Resource
	private OrderReturnMoneyDao orderReturnMoneyDao;
	

	public Integer createOrderReturnMoney(OrderReturnMoney t) {
		return this.orderReturnMoneyDao.insertEntity(t);
	}

	public OrderReturnMoney getOrderReturnMoney(OrderReturnMoney t) {
		return this.orderReturnMoneyDao.selectEntity(t);
	}

	public Integer getOrderReturnMoneyCount(OrderReturnMoney t) {
		return this.orderReturnMoneyDao.selectEntityCount(t);
	}

	public List<OrderReturnMoney> getOrderReturnMoneyList(OrderReturnMoney t) {
		return this.orderReturnMoneyDao.selectEntityList(t);
	}

	public int modifyOrderReturnMoney(OrderReturnMoney t) {
		return this.orderReturnMoneyDao.updateEntity(t);
	}

	public int removeOrderReturnMoney(OrderReturnMoney t) {
		return this.orderReturnMoneyDao.deleteEntity(t);
	}

	public List<OrderReturnMoney> getOrderReturnMoneyPaginatedList(OrderReturnMoney t) {
		return this.orderReturnMoneyDao.selectEntityPaginatedList(t);
	}

}
