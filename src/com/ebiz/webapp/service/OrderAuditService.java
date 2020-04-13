package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.OrderAudit;

/**
 * @author Wu,Yang
 * @version 2017-06-19 14:29
 */
public interface OrderAuditService {

	Integer createOrderAudit(OrderAudit t);

	int modifyOrderAudit(OrderAudit t);

	int removeOrderAudit(OrderAudit t);

	OrderAudit getOrderAudit(OrderAudit t);

	List<OrderAudit> getOrderAuditList(OrderAudit t);

	Integer getOrderAuditCount(OrderAudit t);

	List<OrderAudit> getOrderAuditPaginatedList(OrderAudit t);

}