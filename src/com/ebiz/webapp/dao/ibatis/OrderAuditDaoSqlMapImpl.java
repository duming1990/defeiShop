package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.OrderAuditDao;
import com.ebiz.webapp.domain.OrderAudit;
import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;

/**
 * @author Wu,Yang
 * @version 2017-06-19 14:29
 */
@Service
public class OrderAuditDaoSqlMapImpl extends EntityDaoSqlMapImpl<OrderAudit> implements OrderAuditDao {

}
