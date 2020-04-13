package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.OrderReturnMoneyDao;
import com.ebiz.webapp.domain.OrderReturnMoney;
import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;

/**
 * @author Wu,Yang
 * @version 2016-07-13 15:19
 */
@Service
public class OrderReturnMoneyDaoSqlMapImpl extends EntityDaoSqlMapImpl<OrderReturnMoney> implements OrderReturnMoneyDao {

}
