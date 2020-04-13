package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.OrderReturnAudit;

/**
 * @author Wu,Yang
 * @version 2016-07-13 15:19
 */
public interface OrderReturnAuditService {

	Integer createOrderReturnAudit(OrderReturnAudit t);

	int modifyOrderReturnAudit(OrderReturnAudit t);

	int removeOrderReturnAudit(OrderReturnAudit t);

	OrderReturnAudit getOrderReturnAudit(OrderReturnAudit t);

	List<OrderReturnAudit> getOrderReturnAuditList(OrderReturnAudit t);

	Integer getOrderReturnAuditCount(OrderReturnAudit t);

	List<OrderReturnAudit> getOrderReturnAuditPaginatedList(OrderReturnAudit t);

}