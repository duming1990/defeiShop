package com.ebiz.webapp.dao;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.OrderReturnInfo;

/**
 * @author Wu,Yang
 * @version 2016-07-13 15:19
 */
public interface OrderReturnInfoDao extends EntityDao<OrderReturnInfo> {

	OrderReturnInfo selectOrderReturnInfoRefund(OrderReturnInfo t);

}
