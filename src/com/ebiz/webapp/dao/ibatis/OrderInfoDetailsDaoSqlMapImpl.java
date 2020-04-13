package com.ebiz.webapp.dao.ibatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.OrderInfoDetailsDao;
import com.ebiz.webapp.domain.OrderInfoDetails;

/**
 * @author Wu,Yang
 * @version 2013-04-01 14:59
 */
@Service
public class OrderInfoDetailsDaoSqlMapImpl extends EntityDaoSqlMapImpl<OrderInfoDetails>
		implements OrderInfoDetailsDao {

	public Integer selectOrderInfoDetailsStatisticaCount(OrderInfoDetails t) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectOrderInfoDetailsStatisticaCount", t);
	}

	public Integer selectOrderInfoDetailsBuyHistoryCount(OrderInfoDetails t) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectOrderInfoDetailsBuyHistoryCount", t);
	}

	public Integer selectOrderInfoDetailsDisCount(OrderInfoDetails t) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectOrderInfoDetailsDisCount", t);
	}

	@SuppressWarnings("unchecked")
	public List<OrderInfoDetails> selectOrderInfoDetailsStatisticaEntpPaginatedList(OrderInfoDetails t) {
		return super.getSqlMapClientTemplate().queryForList("selectOrderInfoDetailsStatisticaEntpPaginatedList", t);
	}

	@SuppressWarnings("unchecked")
	public List<OrderInfoDetails> selectOrderInfoDetailsBuyHistoryPaginatedList(OrderInfoDetails t) {
		return super.getSqlMapClientTemplate().queryForList("selectOrderInfoDetailsBuyHistoryPaginatedList", t);
	}

	@SuppressWarnings("unchecked")
	public List<OrderInfoDetails> selectOrderInfoDetailsStatisticaEntpClsList(OrderInfoDetails t) {
		return super.getSqlMapClientTemplate().queryForList("selectOrderInfoDetailsStatisticaEntpClsList", t);
	}

	@Override
	public OrderInfoDetails selectOrderInfoDetailsStatisticaSum(OrderInfoDetails t) {
		return (OrderInfoDetails) super.getSqlMapClientTemplate().queryForObject("selectOrderInfoDetailsStatisticaSum",
				t);
	}

	public Integer selectOrderInfoDetailsByReportCount(OrderInfoDetails t) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectOrderInfoDetailsByReportCount", t);
	}

	@SuppressWarnings("unchecked")
	public List<OrderInfoDetails> selectOrderInfoDetailsByReportPaginatedList(OrderInfoDetails t) {
		return super.getSqlMapClientTemplate().queryForList("selectOrderInfoDetailsByReportPaginatedList", t);
	}

	public Integer selectOrderInfoDetailsForTuiSum(OrderInfoDetails t) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectOrderInfoDetailsForTuiSum", t);
	}

	public Integer selectOrderInfoDetailsEntpCommSaleCount(OrderInfoDetails t) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectOrderInfoDetailsEntpCommSaleCount", t);
	}

	@SuppressWarnings("unchecked")
	public List<OrderInfoDetails> selectOrderInfoDetailsEntpCommSalePaginatedList(OrderInfoDetails t) {
		return super.getSqlMapClientTemplate().queryForList("selectOrderInfoDetailsEntpCommSalePaginatedList", t);
	}

	@SuppressWarnings("unchecked")
	public List<OrderInfoDetails> selectUserSaleRankingList(OrderInfoDetails t) {
		return super.getSqlMapClientTemplate().queryForList("selectUserSaleRankingList", t);
	}

	@SuppressWarnings("unchecked")
	public List<OrderInfoDetails> selectOrderInfoDetailsEntpCommsSalePaginatedList(OrderInfoDetails t) {
		return super.getSqlMapClientTemplate().queryForList("selectOrderInfoDetailsEntpCommsSalePaginatedList", t);
	}

	@SuppressWarnings("unchecked")
	public List<OrderInfoDetails> selectOrderInfoDetailsLinkOrderInfoPaginatedList(OrderInfoDetails t) {
		return super.getSqlMapClientTemplate().queryForList("selectOrderInfoDetailsLinkOrderInfoPaginatedList", t);
	}

	public Integer selectOrderInfoDetailsLinkOrderInfoCount(OrderInfoDetails t) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectOrderInfoDetailsLinkOrderInfoCount", t);
	}

	@SuppressWarnings("unchecked")
	public List<OrderInfoDetails> selectOrderInfoDetailsListByCommInfo(OrderInfoDetails t) {
		return super.getSqlMapClientTemplate().queryForList("selectOrderInfoDetailsListByCommInfo", t);
	}

	@SuppressWarnings("unchecked")
	public List<OrderInfoDetails> selectPoorSalesRankingList(OrderInfoDetails t) {
		return super.getSqlMapClientTemplate().queryForList("selectPoorSalesRankingList", t);
	}

	@SuppressWarnings("unchecked")
	public List<OrderInfoDetails> selectPoorSalesRealtimeList(OrderInfoDetails t) {
		return super.getSqlMapClientTemplate().queryForList("selectPoorSalesRealtimeList", t);
	}

	public Integer selectCommRankingCount(OrderInfoDetails t) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectCommRankingCount", t);
	}

	@SuppressWarnings("unchecked")
	public List<OrderInfoDetails> selectCommRankingList(OrderInfoDetails t) {
		return super.getSqlMapClientTemplate().queryForList("selectCommRankingList", t);
	}

	@SuppressWarnings("unchecked")
	public List<OrderInfoDetails> selectOrderInfoDetailsListByOrder(OrderInfoDetails t) {
		return super.getSqlMapClientTemplate().queryForList("selectOrderInfoDetailsListByOrder", t);
	}

}
