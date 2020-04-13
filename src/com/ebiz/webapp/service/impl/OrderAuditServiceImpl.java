package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.OrderAuditDao;
import com.ebiz.webapp.dao.OrderInfoDao;
import com.ebiz.webapp.dao.OrderInfoDetailsDao;
import com.ebiz.webapp.domain.OrderAudit;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.service.OrderAuditService;

/**
 * @author Wu,Yang
 * @version 2017-06-19 14:29
 */
@Service
public class OrderAuditServiceImpl extends BaseImpl implements OrderAuditService {

	@Resource
	private OrderAuditDao orderAuditDao;

	@Resource
	private OrderInfoDao orderInfoDao;

	@Resource
	private OrderInfoDetailsDao orderInfoDetailsDao;

	public Integer createOrderAudit(OrderAudit t) {
		return this.orderAuditDao.insertEntity(t);
	}

	public OrderAudit getOrderAudit(OrderAudit t) {
		return this.orderAuditDao.selectEntity(t);
	}

	public Integer getOrderAuditCount(OrderAudit t) {
		return this.orderAuditDao.selectEntityCount(t);
	}

	public List<OrderAudit> getOrderAuditList(OrderAudit t) {
		return this.orderAuditDao.selectEntityList(t);
	}

	public int modifyOrderAudit(OrderAudit t) {
		return this.orderAuditDao.updateEntity(t);
	}

	public int removeOrderAudit(OrderAudit t) {

		if (null != t.getMap().get("remove_orderinfo_and_orderinfodetail")) {
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setId(t.getOrder_id());
			this.orderInfoDao.deleteEntity(orderInfo);
			OrderInfoDetails oDetails = new OrderInfoDetails();
			oDetails.setOrder_id(t.getOrder_id());
			List<OrderInfoDetails> list = this.orderInfoDetailsDao.selectEntityList(oDetails);
			if (list != null && list.size() > 0) {
				for (OrderInfoDetails temp : list) {
					this.orderInfoDetailsDao.deleteEntity(temp);
				}
			}
		}
		return this.orderAuditDao.deleteEntity(t);

	}

	public List<OrderAudit> getOrderAuditPaginatedList(OrderAudit t) {
		return this.orderAuditDao.selectEntityPaginatedList(t);
	}

}
