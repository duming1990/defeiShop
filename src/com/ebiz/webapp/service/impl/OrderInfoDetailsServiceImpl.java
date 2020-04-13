package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.OrderInfoDetailsDao;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.service.OrderInfoDetailsService;

/**
 * @author Wu,Yang
 * @version 2013-03-25 15:17
 */
@Service
public class OrderInfoDetailsServiceImpl implements OrderInfoDetailsService {

	@Resource
	private OrderInfoDetailsDao orderInfoDetailsDao;

	public Integer createOrderInfoDetails(OrderInfoDetails t) {
		return this.orderInfoDetailsDao.insertEntity(t);
	}

	public OrderInfoDetails getOrderInfoDetails(OrderInfoDetails t) {
		return this.orderInfoDetailsDao.selectEntity(t);
	}

	public Integer getOrderInfoDetailsCount(OrderInfoDetails t) {
		return this.orderInfoDetailsDao.selectEntityCount(t);
	}

	public Integer getOrderInfoDetailsDisCount(OrderInfoDetails t) {
		return this.orderInfoDetailsDao.selectOrderInfoDetailsDisCount(t);
	}

	public List<OrderInfoDetails> getOrderInfoDetailsList(OrderInfoDetails t) {
		return this.orderInfoDetailsDao.selectEntityList(t);
	}

	public int modifyOrderInfoDetails(OrderInfoDetails t) {
		return this.orderInfoDetailsDao.updateEntity(t);
	}

	public int removeOrderInfoDetails(OrderInfoDetails t) {
		return this.orderInfoDetailsDao.deleteEntity(t);
	}

	public List<OrderInfoDetails> getOrderInfoDetailsPaginatedList(OrderInfoDetails t) {
		return this.orderInfoDetailsDao.selectEntityPaginatedList(t);
	}

	// 订单统计
	public Integer getOrderInfoDetailsStatisticaCount(OrderInfoDetails t) {
		return this.orderInfoDetailsDao.selectOrderInfoDetailsStatisticaCount(t);
	}

	public List<OrderInfoDetails> getOrderInfoDetailsStatisticaEntpPaginatedList(OrderInfoDetails t) {
		return this.orderInfoDetailsDao.selectOrderInfoDetailsStatisticaEntpPaginatedList(t);
	}

	public Integer getOrderInfoDetailsBuyHistoryCount(OrderInfoDetails t) {
		return this.orderInfoDetailsDao.selectOrderInfoDetailsBuyHistoryCount(t);
	}

	public List<OrderInfoDetails> getOrderInfoDetailsBuyHistoryPaginatedList(OrderInfoDetails t) {
		return this.orderInfoDetailsDao.selectOrderInfoDetailsBuyHistoryPaginatedList(t);
	}

	public List<OrderInfoDetails> getOrderInfoDetailsStatisticaEntpClsList(OrderInfoDetails t) {
		return this.orderInfoDetailsDao.selectOrderInfoDetailsStatisticaEntpClsList(t);
	}

	@Override
	public OrderInfoDetails getOrderInfoDetailsStatisticaSum(OrderInfoDetails t) {
		return this.orderInfoDetailsDao.selectOrderInfoDetailsStatisticaSum(t);
	}

	public Integer getOrderInfoDetailsByReportCount(OrderInfoDetails t) {
		return this.orderInfoDetailsDao.selectOrderInfoDetailsByReportCount(t);
	}

	public List<OrderInfoDetails> getOrderInfoDetailsByReportPaginatedList(OrderInfoDetails t) {
		return this.orderInfoDetailsDao.selectOrderInfoDetailsByReportPaginatedList(t);
	}

	public Integer getOrderInfoDetailsForTuiSum(OrderInfoDetails t) {
		return this.orderInfoDetailsDao.selectOrderInfoDetailsForTuiSum(t);
	}

	public Integer getOrderInfoDetailsEntpCommSaleCount(OrderInfoDetails t) {
		return this.orderInfoDetailsDao.selectOrderInfoDetailsEntpCommSaleCount(t);
	}

	public List<OrderInfoDetails> getOrderInfoDetailsEntpCommSalePaginatedList(OrderInfoDetails t) {
		return this.orderInfoDetailsDao.selectOrderInfoDetailsEntpCommSalePaginatedList(t);
	}

	public List<OrderInfoDetails> getUserSaleRankingList(OrderInfoDetails t) {
		return this.orderInfoDetailsDao.selectUserSaleRankingList(t);
	}

	@Override
	public List<OrderInfoDetails> getOrderInfoDetailsEntpCommsSalePaginatedList(OrderInfoDetails t) {
		return this.orderInfoDetailsDao.selectOrderInfoDetailsEntpCommsSalePaginatedList(t);
	}

	@Override
	public List<OrderInfoDetails> getOrderInfoDetailsLinkOrderInfoPaginatedList(OrderInfoDetails t) {
		return this.orderInfoDetailsDao.selectOrderInfoDetailsLinkOrderInfoPaginatedList(t);
	}

	@Override
	public Integer getOrderInfoDetailsLinkOrderInfoCount(OrderInfoDetails t) {
		return this.orderInfoDetailsDao.selectOrderInfoDetailsLinkOrderInfoCount(t);
	}

	@Override
	public List<OrderInfoDetails> getPoorSalesRankingList(OrderInfoDetails t) {
		return this.orderInfoDetailsDao.selectPoorSalesRankingList(t);
	}

	@Override
	public List<OrderInfoDetails> getPoorSalesRealtimeList(OrderInfoDetails t) {
		return this.orderInfoDetailsDao.selectPoorSalesRealtimeList(t);
	}

	@Override
	public Integer getCommRankingCount(OrderInfoDetails t) {
		return this.orderInfoDetailsDao.selectCommRankingCount(t);
	}

	@Override
	public List<OrderInfoDetails> getCommRankingList(OrderInfoDetails t) {
		return this.orderInfoDetailsDao.selectCommRankingList(t);
	}

	@Override
	public List<OrderInfoDetails> getOrderInfoDetailsListByOrder(OrderInfoDetails t) {
		return this.orderInfoDetailsDao.selectOrderInfoDetailsListByOrder(t);
	}

}
