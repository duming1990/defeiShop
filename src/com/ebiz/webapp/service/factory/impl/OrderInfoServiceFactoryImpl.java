package com.ebiz.webapp.service.factory.impl;

import com.ebiz.webapp.dao.BaseAuditRecordDao;
import com.ebiz.webapp.dao.BaseDataDao;
import com.ebiz.webapp.dao.BaseImgsDao;
import com.ebiz.webapp.dao.OrderInfoDao;
import com.ebiz.webapp.dao.OrderInfoDetailsDao;
import com.ebiz.webapp.dao.OrderReturnInfoDao;
import com.ebiz.webapp.dao.UserBiRecordDao;
import com.ebiz.webapp.dao.UserInfoDao;
import com.ebiz.webapp.domain.OrderReturnInfo;
import com.ebiz.webapp.service.factory.IMobaOrderInfoService;

/**
 * @author Wu,Yang
 * @version 2012-05-18 14:18
 */

public class OrderInfoServiceFactoryImpl implements com.ebiz.webapp.service.factory.IOrderInfoServiceFactory {

	String _orderString;

	IMobaOrderInfoService service = null;

	/**
	 * 订单处理工厂构造函数 如果传输liren，则执行的是RenYiOrderInfoServiceImpl 如果传输zhonghui，则执行的是ZhongHuiOrderInfoServiceImpl
	 * 如果还有其他逻辑，在这里增加就可以了
	 * 
	 * @param order_string liren/zhonghui
	 */
	public OrderInfoServiceFactoryImpl() {
		service = new RenYiOrderInfoServiceImpl();
	}

	public OrderInfoServiceFactoryImpl(String order_string) {
		_orderString = order_string;

		if (_orderString.equals("liren")) {
			service = new RenYiOrderInfoServiceImpl();

		}
		if (service == null) {
			service = new RenYiOrderInfoServiceImpl();
		}
	}

	@Override
	public int DelayOrder(int id, OrderInfoDao dao) {

		return service.DelayOrder(id, dao);
	}

	@Override
	public int TuiHuoOrderDeclare(Integer order_id, OrderInfoDao orderInfoDao, OrderReturnInfoDao orderReturnInfoDao,
			OrderReturnInfo orderReturnInfo, String[] basefiles, BaseImgsDao baseImgsDao) {
		return service.TuiHuoOrderDeclare(order_id, orderInfoDao, orderReturnInfoDao, orderReturnInfo, basefiles,
				baseImgsDao);
	}

	@Override
	public int TuiHuoOrderAudit(OrderReturnInfo orderReturnInfo, OrderInfoDao orderInfoDao,
			OrderInfoDetailsDao orderInfoDetailsDao, UserInfoDao userInfoDao, BaseDataDao baseDataDao,
			UserBiRecordDao userBiRecordDao, int th_type, OrderReturnInfoDao orderReturnInfoDao,
			BaseAuditRecordDao baseAuditRecordDao) {

		return service.TuiHuoOrderAudit(orderReturnInfo.getId(), orderReturnInfoDao, orderInfoDao, orderInfoDetailsDao,
				userInfoDao, baseDataDao, userBiRecordDao, th_type);
	}
}
