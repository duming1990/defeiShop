package com.ebiz.webapp.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.OrderInfo;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:55
 */
public interface OrderInfoDao extends EntityDao<OrderInfo> {

	BigDecimal generateOrderInfoTradeIndex(OrderInfo t);

	List<OrderInfo> selectOrderInfoWithRealNamePaginatedList(OrderInfo t);

	List<OrderInfo> selectOrderInfoWithRealNameList(OrderInfo t);

	Integer selectOrderInfoStatisticaCount(OrderInfo t);

	List<OrderInfo> selectOrderInfoStatisticaPaginatedList(OrderInfo t);

	OrderInfo selectOrderInfoStatisticaSum(OrderInfo t);

	OrderInfo selectOrderInfoByDetailId(Map map);

	OrderInfo selectOrderInfoForSumBalance(OrderInfo t);

	Integer getselectCheckCount(OrderInfo t);

	Integer selectBigShowOrderInfoCount(OrderInfo t);

	List<OrderInfo> getselectCheckList(OrderInfo t);

	List<OrderInfo> selectRangPriceSum(OrderInfo t);

	List<OrderInfo> selectOrderInfoStatisticaListSum(OrderInfo t);

	List<OrderInfo> selectVillageInviteUserSum(OrderInfo t);

	Integer selectMembershipFeeCount(OrderInfo t);

	List<OrderInfo> selectMembershipFeePaginatedList(OrderInfo t);

	List<OrderInfo> selectGroupLeaderOrderInfo(OrderInfo t);

	List<OrderInfo> selectOrderInfoListNew(OrderInfo t);

	BigDecimal selectOrderInfoListNewSumMoney(OrderInfo t);

}
