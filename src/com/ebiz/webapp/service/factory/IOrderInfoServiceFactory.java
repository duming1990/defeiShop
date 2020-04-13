package com.ebiz.webapp.service.factory;

import com.ebiz.webapp.dao.BaseAuditRecordDao;
import com.ebiz.webapp.dao.BaseDataDao;
import com.ebiz.webapp.dao.BaseImgsDao;
import com.ebiz.webapp.dao.OrderInfoDao;
import com.ebiz.webapp.dao.OrderInfoDetailsDao;
import com.ebiz.webapp.dao.OrderReturnInfoDao;
import com.ebiz.webapp.dao.UserBiRecordDao;
import com.ebiz.webapp.dao.UserInfoDao;
import com.ebiz.webapp.domain.OrderReturnInfo;

/**
 * @author jiaoronggui
 * @version 2017-07-9 11:55
 */
public interface IOrderInfoServiceFactory {

	int DelayOrder(int id, OrderInfoDao dao);

	/**
	 * 退货申请
	 * 
	 * @param id 订单id
	 * @param dao
	 * @return
	 */
	int TuiHuoOrderDeclare(Integer order_id, OrderInfoDao orderInfoDao, OrderReturnInfoDao orderReturnInfoDao,
			OrderReturnInfo orderReturnInfo, String[] basefiles, BaseImgsDao baseImgsDao);

	/**
	 * 退货订单 1、建立退货订单；2、延迟的订单的确认时间；3、上传证据 <option value="1">退货退款</option> <option value="2">换货</option> <option
	 * value="3">仅退款</option>
	 */
	int TuiHuoOrderAudit(OrderReturnInfo orderInfo, OrderInfoDao orderInfoDao, OrderInfoDetailsDao orderInfoDetailsDao,
			UserInfoDao userInfoDao, BaseDataDao baseDataDao, UserBiRecordDao userBiRecordDao, int th_type,
			OrderReturnInfoDao orderReturnInfoDao, BaseAuditRecordDao baseAuditRecordDao);

}