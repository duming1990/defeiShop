package com.ebiz.webapp.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:55
 */
public interface OrderInfoService {

	Integer createOrderInfo(OrderInfo t);

	Integer createShuaDanOrderInfo(OrderInfo t);

	int modifyOrderInfo(OrderInfo t);

	int removeOrderInfo(OrderInfo t);

	OrderInfo getOrderInfo(OrderInfo t);

	List<OrderInfo> getOrderInfoList(OrderInfo t);

	Integer getOrderInfoCount(OrderInfo t);

	Integer getBigShowOrderInfoCount(OrderInfo t);

	List<OrderInfo> getOrderInfoPaginatedList(OrderInfo t);

	BigDecimal genOrderInfoTradeIndex(OrderInfo t);

	List<OrderInfo> getOrderInfoWithRealNamePaginatedList(OrderInfo t);

	List<OrderInfo> getOrderInfoWithRealNameList(OrderInfo t);

	int modifyOrderInfoInventory(OrderInfo t);

	Integer getOrderInfoStatisticaCount(OrderInfo t);

	List<OrderInfo> getOrderInfoStatisticaPaginatedList(OrderInfo t);

	OrderInfo getOrderInfoStatisticaSum(OrderInfo t);

	List<OrderInfo> getRangPriceSum(OrderInfo t);

	int modifyOrderInfoForChongZhi(OrderInfo t);

	OrderInfo getOrderInfoByDetailId(Map map);

	void modifyOrderInfoForCancel(OrderInfo t);

	int getselectCheckCount(OrderInfo t);

	List<OrderInfo> getselectCheckList(OrderInfo t);

	JSONObject createSupermarketOrderInfo(OrderInfo t);

	List<OrderInfo> getOrderInfoStatisticaListSum(OrderInfo t);

	List<OrderInfo> getVillageInviteUserSum(OrderInfo t);

	Integer getMembershipFeeCount(OrderInfo t);

	List<OrderInfo> getMembershipFeePaginatedList(OrderInfo t);

	List<OrderInfo> getGroupLeaderOrderInfo(OrderInfo t);

	boolean modifyOrderInfoAndDetails(List<OrderInfoDetails> orderInfoDetailsList, List<OrderInfo> orderInfoList);

	List<OrderInfo> getOrderInfoListNew(OrderInfo orderInfo);

	BigDecimal getOrderInfoListNewSumMoney(OrderInfo orderInfo);

	Integer createUserUpLevelOrderInfo(OrderInfo t);

}