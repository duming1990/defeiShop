package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.OrderReturnAuditDao;
import com.ebiz.webapp.domain.OrderReturnAudit;
import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;

/**
 * @author Wu,Yang
 * @version 2016-07-13 15:19
 */
@Service
public class OrderReturnAuditDaoSqlMapImpl extends EntityDaoSqlMapImpl<OrderReturnAudit> implements OrderReturnAuditDao {

}
