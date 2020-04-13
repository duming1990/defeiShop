package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.OrderInfoDao;
import com.ebiz.webapp.dao.OrderMergerInfoDao;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderMergerInfo;
import com.ebiz.webapp.service.OrderMergerInfoService;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:55
 */
@Service
public class OrderMergerInfoServiceImpl implements OrderMergerInfoService {

	@Resource
	private OrderInfoDao orderInfoDao;

	@Resource
	private OrderMergerInfoDao orderMergerInfoDao;

	@Override
	public Integer createOrderMergerInfo(OrderMergerInfo t) {
		// 向OrderInfo表中添加，合并的订单号(trade_merger_index)
		String updateOrderInfo = (String) t.getMap().get("updateOrderInfo");
		if (StringUtils.isNotBlank(updateOrderInfo)) {
			String trade_merger_index = "";
			OrderMergerInfo omi = new OrderMergerInfo();
			omi.setId(t.getPar_id());
			omi = this.orderMergerInfoDao.selectEntity(omi);
			if (null != omi) {
				trade_merger_index = omi.getOut_trade_no();
				if (StringUtils.isNotBlank(trade_merger_index)) {
					OrderInfo orderInfo = new OrderInfo();
					orderInfo.setTrade_index(t.getTrade_index());
					orderInfo = this.orderInfoDao.selectEntity(orderInfo);
					if (null != orderInfo) {
						orderInfo.setTrade_merger_index(trade_merger_index);
						this.orderInfoDao.updateEntity(orderInfo);
					}
				}
			}
		}
		return this.orderMergerInfoDao.insertEntity(t);
	}

	@Override
	public OrderMergerInfo getOrderMergerInfo(OrderMergerInfo t) {
		return this.orderMergerInfoDao.selectEntity(t);
	}

	@Override
	public Integer getOrderMergerInfoCount(OrderMergerInfo t) {
		return this.orderMergerInfoDao.selectEntityCount(t);
	}

	@Override
	public List<OrderMergerInfo> getOrderMergerInfoList(OrderMergerInfo t) {
		return this.orderMergerInfoDao.selectEntityList(t);
	}

	@Override
	public int modifyOrderMergerInfo(OrderMergerInfo t) {
		return this.orderMergerInfoDao.updateEntity(t);
	}

	@Override
	public int removeOrderMergerInfo(OrderMergerInfo t) {
		return this.orderMergerInfoDao.deleteEntity(t);
	}

	@Override
	public List<OrderMergerInfo> getOrderMergerInfoPaginatedList(OrderMergerInfo t) {
		return this.orderMergerInfoDao.selectEntityPaginatedList(t);
	}

}
