package com.ebiz.webapp.dao.ibatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.OrderReturnMsgDao;
import com.ebiz.webapp.domain.OrderReturnMsg;

/**
 * @author Wu,Yang
 * @version 2016-07-13 15:19
 */
@Service
public class OrderReturnMsgDaoSqlMapImpl extends EntityDaoSqlMapImpl<OrderReturnMsg> implements OrderReturnMsgDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<OrderReturnMsg> selectOrderReturnMsgLeftJoinOrderReturnInfoPaginatedList(OrderReturnMsg t) {
		return super.getSqlMapClientTemplate().queryForList("selectOrderReturnMsgLeftJoinOrderReturnInfoPaginatedList",
				t);
	}

}
