package com.ebiz.webapp.service.factory.OrderTuiHuo;

import com.ebiz.webapp.dao.BaseDataDao;
import com.ebiz.webapp.dao.BaseImgsDao;
import com.ebiz.webapp.dao.OrderInfoDao;
import com.ebiz.webapp.dao.OrderInfoDetailsDao;
import com.ebiz.webapp.dao.OrderReturnInfoDao;
import com.ebiz.webapp.dao.UserBiRecordDao;
import com.ebiz.webapp.dao.UserInfoDao;
import com.ebiz.webapp.domain.OrderReturnInfo;

public interface IOrderTuiHuo {

	int OrderTuiHuoAudit(Integer order_id, OrderReturnInfoDao orderReturnInfoDao, OrderInfoDao orderInfoDao,
			OrderInfoDetailsDao orderInfoDetailsDao, UserInfoDao userInfoDao, BaseDataDao baseDataDao,
			UserBiRecordDao userBiRecordDao);

	/**
	 * 退货申请
	 * 
	 * @param id 订单id
	 * @param dao
	 * @return
	 */
	int TuiHuoOrderDeclare(Integer order_id, OrderInfoDao orderInfoDao, OrderReturnInfoDao orderReturnInfoDao,
			OrderReturnInfo orderReturnInfo, String[] basefiles, BaseImgsDao baseImgsDao);
}
