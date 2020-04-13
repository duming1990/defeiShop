package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.OrderReturnMsgDao;
import com.ebiz.webapp.domain.OrderReturnMsg;
import com.ebiz.webapp.service.OrderReturnMsgService;

/**
 * @author Wu,Yang
 * @version 2016-07-13 15:19
 */
@Service
public class OrderReturnMsgServiceImpl implements OrderReturnMsgService {

	@Resource
	private OrderReturnMsgDao orderReturnMsgDao;

	public Integer createOrderReturnMsg(OrderReturnMsg t) {
		return this.orderReturnMsgDao.insertEntity(t);
	}

	public OrderReturnMsg getOrderReturnMsg(OrderReturnMsg t) {
		return this.orderReturnMsgDao.selectEntity(t);
	}

	public Integer getOrderReturnMsgCount(OrderReturnMsg t) {
		return this.orderReturnMsgDao.selectEntityCount(t);
	}

	public List<OrderReturnMsg> getOrderReturnMsgList(OrderReturnMsg t) {
		return this.orderReturnMsgDao.selectEntityList(t);
	}

	public int modifyOrderReturnMsg(OrderReturnMsg t) {
		return this.orderReturnMsgDao.updateEntity(t);
	}

	public int removeOrderReturnMsg(OrderReturnMsg t) {
		return this.orderReturnMsgDao.deleteEntity(t);
	}

	public List<OrderReturnMsg> getOrderReturnMsgPaginatedList(OrderReturnMsg t) {
		return this.orderReturnMsgDao.selectEntityPaginatedList(t);
	}

	@Override
	public List<OrderReturnMsg> getOrderReturnMsgLeftJoinOrderReturnInfoPaginatedList(OrderReturnMsg t) {
		return this.orderReturnMsgDao.selectOrderReturnMsgLeftJoinOrderReturnInfoPaginatedList(t);
	}

}
