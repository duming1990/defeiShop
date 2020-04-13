package com.ebiz.webapp.dao;

import java.util.List;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.OrderReturnMsg;

/**
 * @author Wu,Yang
 * @version 2016-07-13 15:19
 */
public interface OrderReturnMsgDao extends EntityDao<OrderReturnMsg> {

	List<OrderReturnMsg> selectOrderReturnMsgLeftJoinOrderReturnInfoPaginatedList(OrderReturnMsg t);

}
