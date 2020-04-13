package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.OrderLinkInfoDao;
import com.ebiz.webapp.domain.OrderLinkInfo;
import com.ebiz.webapp.service.OrderLinkInfoService;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:55
 */
@Service
public class OrderLinkInfoServiceImpl implements OrderLinkInfoService {

	@Resource
	private OrderLinkInfoDao orderLinkInfoDao;

	public Integer createOrderLinkInfo(OrderLinkInfo t) {
		return this.orderLinkInfoDao.insertEntity(t);
	}

	public OrderLinkInfo getOrderLinkInfo(OrderLinkInfo t) {
		return this.orderLinkInfoDao.selectEntity(t);
	}

	public Integer getOrderLinkInfoCount(OrderLinkInfo t) {
		return this.orderLinkInfoDao.selectEntityCount(t);
	}

	public List<OrderLinkInfo> getOrderLinkInfoList(OrderLinkInfo t) {
		return this.orderLinkInfoDao.selectEntityList(t);
	}

	public int modifyOrderLinkInfo(OrderLinkInfo t) {
		return this.orderLinkInfoDao.updateEntity(t);
	}

	public int removeOrderLinkInfo(OrderLinkInfo t) {
		return this.orderLinkInfoDao.deleteEntity(t);
	}

	public List<OrderLinkInfo> getOrderLinkInfoPaginatedList(OrderLinkInfo t) {
		return this.orderLinkInfoDao.selectEntityPaginatedList(t);
	}

}
