package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.OrderReturnAuditDao;
import com.ebiz.webapp.domain.OrderReturnAudit;
import com.ebiz.webapp.service.OrderReturnAuditService;

/**
 * @author Wu,Yang
 * @version 2016-07-13 15:19
 */
@Service
public class OrderReturnAuditServiceImpl implements OrderReturnAuditService {

	@Resource
	private OrderReturnAuditDao orderReturnAuditDao;
	

	public Integer createOrderReturnAudit(OrderReturnAudit t) {
		return this.orderReturnAuditDao.insertEntity(t);
	}

	public OrderReturnAudit getOrderReturnAudit(OrderReturnAudit t) {
		List<OrderReturnAudit> list= this.orderReturnAuditDao.selectEntityList(t);
		if(list != null &&list.size()>0){
			return list.get(list.size()-1);
		}
		return null;
	}

	public Integer getOrderReturnAuditCount(OrderReturnAudit t) {
		return this.orderReturnAuditDao.selectEntityCount(t);
	}

	public List<OrderReturnAudit> getOrderReturnAuditList(OrderReturnAudit t) {
		return this.orderReturnAuditDao.selectEntityList(t);
	}

	public int modifyOrderReturnAudit(OrderReturnAudit t) {
		return this.orderReturnAuditDao.updateEntity(t);
	}

	public int removeOrderReturnAudit(OrderReturnAudit t) {
		return this.orderReturnAuditDao.deleteEntity(t);
	}

	public List<OrderReturnAudit> getOrderReturnAuditPaginatedList(OrderReturnAudit t) {
		return this.orderReturnAuditDao.selectEntityPaginatedList(t);
	}

}
