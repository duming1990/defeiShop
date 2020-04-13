package com.ebiz.webapp.dao;

import java.util.List;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.OrderInfoDetails;

/**
 * @author Wu,Yang
 * @version 2013-04-01 14:59
 */
public interface OrderInfoDetailsDao extends EntityDao<OrderInfoDetails> {

	Integer selectOrderInfoDetailsStatisticaCount(OrderInfoDetails t);

	Integer selectOrderInfoDetailsBuyHistoryCount(OrderInfoDetails t);

	Integer selectOrderInfoDetailsDisCount(OrderInfoDetails t);

	List<OrderInfoDetails> selectOrderInfoDetailsStatisticaEntpPaginatedList(OrderInfoDetails t);

	List<OrderInfoDetails> selectOrderInfoDetailsBuyHistoryPaginatedList(OrderInfoDetails t);

	List<OrderInfoDetails> selectOrderInfoDetailsStatisticaEntpClsList(OrderInfoDetails t);

	OrderInfoDetails selectOrderInfoDetailsStatisticaSum(OrderInfoDetails t);

	Integer selectOrderInfoDetailsByReportCount(OrderInfoDetails t);

	List<OrderInfoDetails> selectOrderInfoDetailsByReportPaginatedList(OrderInfoDetails t);

	Integer selectOrderInfoDetailsForTuiSum(OrderInfoDetails t);

	Integer selectOrderInfoDetailsEntpCommSaleCount(OrderInfoDetails t);

	List<OrderInfoDetails> selectOrderInfoDetailsEntpCommSalePaginatedList(OrderInfoDetails t);

	List<OrderInfoDetails> selectUserSaleRankingList(OrderInfoDetails t);

	List<OrderInfoDetails> selectOrderInfoDetailsEntpCommsSalePaginatedList(OrderInfoDetails t);

	List<OrderInfoDetails> selectOrderInfoDetailsLinkOrderInfoPaginatedList(OrderInfoDetails t);

	Integer selectOrderInfoDetailsLinkOrderInfoCount(OrderInfoDetails t);

	List<OrderInfoDetails> selectOrderInfoDetailsListByCommInfo(OrderInfoDetails t);

	List<OrderInfoDetails> selectPoorSalesRealtimeList(OrderInfoDetails t);

	List<OrderInfoDetails> selectPoorSalesRankingList(OrderInfoDetails t);

	Integer selectCommRankingCount(OrderInfoDetails t);

	List<OrderInfoDetails> selectCommRankingList(OrderInfoDetails t);

	List<OrderInfoDetails> selectOrderInfoDetailsListByOrder(OrderInfoDetails t);

}
