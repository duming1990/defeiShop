package com.ebiz.webapp.service.factory.impl;

import org.slf4j.LoggerFactory;

import com.ebiz.webapp.dao.BaseDataDao;
import com.ebiz.webapp.dao.BaseImgsDao;
import com.ebiz.webapp.dao.OrderInfoDao;
import com.ebiz.webapp.dao.OrderInfoDetailsDao;
import com.ebiz.webapp.dao.OrderReturnInfoDao;
import com.ebiz.webapp.dao.UserBiRecordDao;
import com.ebiz.webapp.dao.UserInfoDao;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderReturnInfo;
import com.ebiz.webapp.service.factory.OrderTuiHuo.IOrderTuiHuo;
import com.ebiz.webapp.service.factory.impl.OrderTuiHuo.OrderTuiHuoReturnAll;
import com.ebiz.webapp.service.factory.impl.OrderTuiHuo.OrderTuiHuoReturnMoney;
import com.ebiz.webapp.service.factory.impl.OrderTuiHuo.OrderTuiHuoReturnMoneyFaHuo;
import com.ebiz.webapp.service.factory.impl.OrderTuiHuo.OrderTuiHuoSwap;
import com.ebiz.webapp.service.impl.BaseImpl;

/**
 * @author 仁义订单处理类，相关的订单处理逻辑在这里处理
 * @version 2017-07-09
 */

public class RenYiOrderInfoServiceImpl extends BaseImpl implements
		com.ebiz.webapp.service.factory.IMobaOrderInfoService {
	protected static final org.slf4j.Logger logger = LoggerFactory.getLogger(RenYiOrderInfoServiceImpl.class);

	/**
	 * 延迟订单，延迟订单7天 0表示失败 》=1表示成功
	 */
	@Override
	public int DelayOrder(Integer order_id, OrderInfoDao orderInfoDao) {
		if (order_id == null || orderInfoDao == null) {
			return 0;
		}
		OrderInfo entity = new OrderInfo();
		entity.setId(order_id);
		entity.setDelay_shouhuo(new Integer(1));
		entity.getMap().put("delay_shouhuo", true);// 取消订单，前一个状态0

		int row = orderInfoDao.updateEntity(entity);
		if (row == 0) {
			return 0;
		}
		if (row == -1) {
			return 0;
		}
		return row;
	}

	/**
	 * 退货订单 1、建立退货订单；2、延迟的订单的确认时间；3、上传证据 <option value="1">退货退款</option> <option value="2">换货</option> <option
	 * value="3">仅退款</option>
	 */
	@Override
	public int TuiHuoOrderDeclare(Integer order_id, OrderInfoDao orderInfoDao, OrderReturnInfoDao orderReturnInfoDao,
			OrderReturnInfo orderReturnInfo, String[] basefiles, BaseImgsDao baseImgsDao) {

		IOrderTuiHuo orderTh = new OrderTuiHuoSwap();

		if (orderReturnInfo != null && orderReturnInfo.getExpect_return_way() != null) {
			System.out.println("orderReturnInfo.getExpect_return_way():" + orderReturnInfo.getExpect_return_way());
			switch (orderReturnInfo.getExpect_return_way()) {
			case 1:
				orderTh = new OrderTuiHuoReturnAll();
				break;
			case 2:
				orderTh = new OrderTuiHuoSwap();
				break;
			case 3:
				orderTh = new OrderTuiHuoReturnMoney();
				break;
			}
		}
		return orderTh.TuiHuoOrderDeclare(order_id, orderInfoDao, orderReturnInfoDao, orderReturnInfo, basefiles,
				baseImgsDao);
	}

	@Override
	public int TuiHuoOrderAudit(Integer order_return_id, OrderReturnInfoDao orderReturnInfoDao,
			OrderInfoDao orderInfoDao, OrderInfoDetailsDao orderInfoDetailsDao, UserInfoDao userInfoDao,
			BaseDataDao baseDataDao, UserBiRecordDao userBiRecordDao, int th_type) {
		IOrderTuiHuo orderTh = new OrderTuiHuoSwap();

		System.out.println("===th_type:" + th_type);
		switch (th_type) {
		case 1:
			orderTh = new OrderTuiHuoReturnAll();// 退货退款
			break;
		case 2:
			orderTh = new OrderTuiHuoSwap();// 换货
			break;
		case 3:
			orderTh = new OrderTuiHuoReturnMoneyFaHuo();
			break;
		case 4:
			orderTh = new OrderTuiHuoReturnMoney();// 未发货退款
			break;
		}

		return orderTh.OrderTuiHuoAudit(order_return_id, orderReturnInfoDao, orderInfoDao, orderInfoDetailsDao,
				userInfoDao, baseDataDao, userBiRecordDao);
	}
}
