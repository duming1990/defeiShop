package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.OrderInfoDao;
import com.ebiz.webapp.dao.WlOrderInfoDao;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.WlCompInfo;
import com.ebiz.webapp.domain.WlOrderInfo;
import com.ebiz.webapp.service.WlOrderInfoService;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:54
 */
@Service
public class WlOrderInfoServiceImpl implements WlOrderInfoService {

	@Resource
	private WlOrderInfoDao wlOrderInfoDao;

	@Resource
	private OrderInfoDao orderInfoDao;

	public Integer createWlOrderInfo(WlOrderInfo t) {
		return this.wlOrderInfoDao.insertEntity(t);
	}

	public WlOrderInfo getWlOrderInfo(WlOrderInfo t) {

		List<WlOrderInfo> list = this.wlOrderInfoDao.selectEntityList(t);
		if(list.size()>0){
			return list.get(list.size()-1);
		}else{
			return null;
		}
	}

	public Integer getWlOrderInfoCount(WlOrderInfo t) {
		return this.wlOrderInfoDao.selectEntityCount(t);
	}

	public List<WlOrderInfo> getWlOrderInfoList(WlOrderInfo t) {
		return this.wlOrderInfoDao.selectEntityList(t);
	}

	public int modifyWlOrderInfo(WlOrderInfo t) {
		int count = this.wlOrderInfoDao.updateEntity(t);

		if (count > 0) {
			if (null != t.getMap().get("update_order_info")) {
				OrderInfo orderInfoUpdate = (OrderInfo) t.getMap().get("update_order_info");
				this.orderInfoDao.updateEntity(orderInfoUpdate);
			}
		}

		return count;
	}

	public int removeWlOrderInfo(WlOrderInfo t) {
		return this.wlOrderInfoDao.deleteEntity(t);
	}

	public List<WlOrderInfo> getWlOrderInfoPaginatedList(WlOrderInfo t) {
		return this.wlOrderInfoDao.selectEntityPaginatedList(t);
	}

}
