package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.OrderReturnInfoDao;
import com.ebiz.webapp.domain.OrderReturnInfo;

/**
 * @author Wu,Yang
 * @version 2016-07-13 15:19
 */
@Service
public class OrderReturnInfoDaoSqlMapImpl extends EntityDaoSqlMapImpl<OrderReturnInfo> implements OrderReturnInfoDao {
	@Override
	public OrderReturnInfo selectOrderReturnInfoRefund(OrderReturnInfo t) {
		return (OrderReturnInfo) super.getSqlMapClientTemplate().queryForObject("selectOrderReturnInfoRefund", t);
	}

}
