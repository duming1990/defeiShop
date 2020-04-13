package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.OrderInfoDetails;

/**
 * @author Wu,Yang
 * @version 2013-03-25 15:17
 */
public interface OrderInfoDetailsService {

	Integer createOrderInfoDetails(OrderInfoDetails t);

	int modifyOrderInfoDetails(OrderInfoDetails t);

	int removeOrderInfoDetails(OrderInfoDetails t);

	OrderInfoDetails getOrderInfoDetails(OrderInfoDetails t);

	List<OrderInfoDetails> getOrderInfoDetailsList(OrderInfoDetails t);

	Integer getOrderInfoDetailsCount(OrderInfoDetails t);

	Integer getOrderInfoDetailsDisCount(OrderInfoDetails t);

	List<OrderInfoDetails> getOrderInfoDetailsPaginatedList(OrderInfoDetails t);

	Integer getOrderInfoDetailsStatisticaCount(OrderInfoDetails t);

	List<OrderInfoDetails> getOrderInfoDetailsStatisticaEntpPaginatedList(OrderInfoDetails t);

	Integer getOrderInfoDetailsBuyHistoryCount(OrderInfoDetails t);

	List<OrderInfoDetails> getOrderInfoDetailsBuyHistoryPaginatedList(OrderInfoDetails t);

	List<OrderInfoDetails> getOrderInfoDetailsStatisticaEntpClsList(OrderInfoDetails t);

	OrderInfoDetails getOrderInfoDetailsStatisticaSum(OrderInfoDetails t);

	Integer getOrderInfoDetailsByReportCount(OrderInfoDetails t);

	List<OrderInfoDetails> getOrderInfoDetailsByReportPaginatedList(OrderInfoDetails t);

	Integer getOrderInfoDetailsForTuiSum(OrderInfoDetails t);

	Integer getOrderInfoDetailsEntpCommSaleCount(OrderInfoDetails t);

	List<OrderInfoDetails> getOrderInfoDetailsEntpCommSalePaginatedList(OrderInfoDetails t);

	List<OrderInfoDetails> getOrderInfoDetailsEntpCommsSalePaginatedList(OrderInfoDetails t);

	List<OrderInfoDetails> getUserSaleRankingList(OrderInfoDetails t);

	List<OrderInfoDetails> getOrderInfoDetailsLinkOrderInfoPaginatedList(OrderInfoDetails t);

	Integer getOrderInfoDetailsLinkOrderInfoCount(OrderInfoDetails t);

	List<OrderInfoDetails> getPoorSalesRankingList(OrderInfoDetails t);

	List<OrderInfoDetails> getPoorSalesRealtimeList(OrderInfoDetails t);

	Integer getCommRankingCount(OrderInfoDetails t);

	List<OrderInfoDetails> getCommRankingList(OrderInfoDetails t);

	List<OrderInfoDetails> getOrderInfoDetailsListByOrder(OrderInfoDetails t);

}