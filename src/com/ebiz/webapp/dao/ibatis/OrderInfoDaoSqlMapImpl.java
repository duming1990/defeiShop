package com.ebiz.webapp.dao.ibatis;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.OrderInfoDao;
import com.ebiz.webapp.domain.OrderInfo;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:55
 */
@Service
public class OrderInfoDaoSqlMapImpl extends EntityDaoSqlMapImpl<OrderInfo> implements OrderInfoDao {
	/**
	 * @author Wu,Yang
	 * @version 2011-12-20
	 * @desc 生成订单号
	 */
	@Override
	public BigDecimal generateOrderInfoTradeIndex(OrderInfo t) {
		return (BigDecimal) super.getSqlMapClientTemplate().queryForObject("generateOrderInfoTradeIndex", t);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<OrderInfo> selectOrderInfoWithRealNamePaginatedList(OrderInfo t) {
		return super.getSqlMapClientTemplate().queryForList("selectOrderInfoWithRealNamePaginatedList", t);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<OrderInfo> selectOrderInfoWithRealNameList(OrderInfo t) {
		return super.getSqlMapClientTemplate().queryForList("selectOrderInfoWithRealNameList", t);
	}

	@Override
	public Integer selectOrderInfoStatisticaCount(OrderInfo t) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectOrderInfoStatisticaCount", t);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<OrderInfo> selectOrderInfoStatisticaPaginatedList(OrderInfo t) {
		return super.getSqlMapClientTemplate().queryForList("selectOrderInfoStatisticaPaginatedList", t);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<OrderInfo> selectOrderInfoStatisticaListSum(OrderInfo t) {
		return super.getSqlMapClientTemplate().queryForList("selectOrderInfoStatisticaListSum", t);
	}

	@Override
	public OrderInfo selectOrderInfoStatisticaSum(OrderInfo t) {
		return (OrderInfo) super.getSqlMapClientTemplate().queryForObject("selectOrderInfoStatisticaSum", t);
	}

	@Override
	public List<OrderInfo> selectRangPriceSum(OrderInfo t) {
		return super.getSqlMapClientTemplate().queryForList("selectRangPriceSum", t);
	}

	@Override
	public List<OrderInfo> selectVillageInviteUserSum(OrderInfo t) {
		return super.getSqlMapClientTemplate().queryForList("selectVillageInviteUserSum", t);
	}

	@Override
	public OrderInfo selectOrderInfoByDetailId(Map map) {
		return (OrderInfo) super.getSqlMapClientTemplate().queryForObject("selectOrderInfoByDetailId", map);

	}

	@Override
	public OrderInfo selectOrderInfoForSumBalance(OrderInfo t) {
		return (OrderInfo) super.getSqlMapClientTemplate().queryForObject("selectOrderInfoForSumBalance", t);
	}

	@Override
	public Integer getselectCheckCount(OrderInfo t) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectCheckCount", t);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<OrderInfo> getselectCheckList(OrderInfo t) {
		return super.getSqlMapClientTemplate().queryForList("selectCheckList", t);
	}

	@Override
	public Integer selectMembershipFeeCount(OrderInfo t) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectMembershipFeeCount", t);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<OrderInfo> selectMembershipFeePaginatedList(OrderInfo t) {
		return super.getSqlMapClientTemplate().queryForList("selectMembershipFeePaginatedList", t);
	}

	@Override
	public List<OrderInfo> selectGroupLeaderOrderInfo(OrderInfo t) {
		return super.getSqlMapClientTemplate().queryForList("selectGroupLeaderOrderInfo", t);
	}

	@Override
	public List<OrderInfo> selectOrderInfoListNew(OrderInfo t) {
		return super.getSqlMapClientTemplate().queryForList("selectOrderInfoListNew", t);
	}

	@Override
	public BigDecimal selectOrderInfoListNewSumMoney(OrderInfo t) {
		return (BigDecimal) super.getSqlMapClientTemplate().queryForObject("selectOrderInfoListNewSumMoney", t);
	}

	@Override
	public Integer selectBigShowOrderInfoCount(OrderInfo t) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectBigShowOrderInfoCount", t);

	}
}
