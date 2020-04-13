package com.ebiz.webapp.service.factory.impl.OrderTuiHuo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import com.ebiz.webapp.dao.BaseDataDao;
import com.ebiz.webapp.dao.BaseImgsDao;
import com.ebiz.webapp.dao.OrderInfoDao;
import com.ebiz.webapp.dao.OrderInfoDetailsDao;
import com.ebiz.webapp.dao.OrderReturnInfoDao;
import com.ebiz.webapp.dao.UserBiRecordDao;
import com.ebiz.webapp.dao.UserInfoDao;
import com.ebiz.webapp.domain.BaseImgs;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.OrderReturnInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.service.factory.OrderTuiHuo.IOrderTuiHuo;
import com.ebiz.webapp.service.impl.BaseImpl;
import com.ebiz.webapp.web.Keys;

public class OrderTuiHuoReturnAll extends BaseImpl implements IOrderTuiHuo {
	protected static final org.slf4j.Logger logger = LoggerFactory.getLogger(OrderTuiHuoReturnAll.class);

	@Override
	/**
	 * 退货退款
	 */
	public int OrderTuiHuoAudit(Integer order_return_id, OrderReturnInfoDao orderReturnInfoDao,
			OrderInfoDao orderInfoDao, OrderInfoDetailsDao orderInfoDetailsDao, UserInfoDao userInfoDao,
			BaseDataDao baseDataDao, UserBiRecordDao userBiRecordDao) {
		Integer new_order_state = Keys.OrderState.ORDER_STATE_X20.getIndex();

		OrderReturnInfo orderReturn = new OrderReturnInfo();
		orderReturn.setId(order_return_id);
		orderReturn.setIs_del(0);
		orderReturn = orderReturnInfoDao.selectEntity(orderReturn);

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(orderReturn.getOrder_id());
		orderInfo = orderInfoDao.selectEntity(orderInfo);

		UserInfo userInfo = new UserInfo();
		userInfo.setId(orderInfo.getAdd_user_id());
		userInfo.setIs_del(0);
		userInfo = userInfoDao.selectEntity(userInfo);
		if (null == userInfo) {
			return -1;

		}

		OrderInfo updateOrder = new OrderInfo();
		updateOrder.setId(orderReturn.getOrder_id());

		BigDecimal return_user_bi = new BigDecimal(0);
		BigDecimal return_real_pay_money = new BigDecimal(0);
		BigDecimal new_money_bi = orderInfo.getMoney_bi();
		BigDecimal new_order_money = orderInfo.getOrder_money();
		int return_money_type = Keys.return_money_type.return_money_type_0.getIndex();

		// 订单金额-物流费用
		BigDecimal rmb_to_dianzibi = BiToBi2(orderInfo.getOrder_money().subtract(orderInfo.getMatflow_price()),
				Keys.BASE_DATA_ID.BASE_DATA_ID_904.getIndex(), baseDataDao);

		// 全退
		if (null == orderReturn.getOrder_detail_id()) {
			rmb_to_dianzibi = new BigDecimal(0);
			OrderInfoDetails ods = new OrderInfoDetails();
			ods.setOrder_id(orderInfo.getId());
			List<OrderInfoDetails> odslist = orderInfoDetailsDao.selectEntityList(ods);
			if (null != odslist && odslist.size() > 0) {
				for (OrderInfoDetails item : odslist) {
					rmb_to_dianzibi = rmb_to_dianzibi.add(item.getActual_money());
				}
			}
			ods.setIs_tuihuo(1);
			orderInfoDetailsDao.updateEntity(ods);

		}

		// 退一个
		if (null != orderReturn.getOrder_detail_id()) {

			OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
			orderInfoDetails.setId(orderReturn.getOrder_detail_id());
			orderInfoDetails = orderInfoDetailsDao.selectEntity(orderInfoDetails);

			// 实际支付金额
			rmb_to_dianzibi = super.BiToBi2(orderInfoDetails.getActual_money(),
					Keys.BASE_DATA_ID.BASE_DATA_ID_904.getIndex(), baseDataDao);

			orderInfoDetails.setIs_tuihuo(1);
			orderInfoDetailsDao.updateEntity(orderInfoDetails);

			new_order_state = Keys.OrderState.ORDER_STATE_20.getIndex();

			BigDecimal tiaofulijian = orderInfoDetails.getGood_sum_price().subtract(orderInfoDetails.getActual_money());

			// 修改订单金额
			updateOrder.setNo_dis_money(orderInfo.getNo_dis_money().subtract(rmb_to_dianzibi.add(tiaofulijian)));

		}

		// 原【电子币支付金额】 < 【实际支付金额】
		if (orderInfo.getMoney_bi().compareTo(new BigDecimal(0)) != 0
				&& orderInfo.getMoney_bi().compareTo(rmb_to_dianzibi) < 0) {
			return_user_bi = orderInfo.getMoney_bi();
			new_money_bi = new BigDecimal(0);

			return_money_type = Keys.return_money_type.return_money_type_2.getIndex();
		}

		// 原【电子币支付金额】 >= 【实际支付金额】
		if (orderInfo.getMoney_bi().compareTo(rmb_to_dianzibi) >= 0) {
			return_user_bi = rmb_to_dianzibi;
			new_money_bi = orderInfo.getMoney_bi().subtract(rmb_to_dianzibi);
		}

		// 【第三方回退金额】= 【退款金额】 - 【余额支付金额】
		return_real_pay_money = rmb_to_dianzibi.subtract(orderInfo.getMoney_bi());

		if (orderInfo.getMoney_bi().compareTo(new BigDecimal(0)) == 0) {
			return_money_type = Keys.return_money_type.return_money_type_1.getIndex();
		}

		// 新的订单金额
		new_order_money = orderInfo.getOrder_money().subtract(return_real_pay_money);

		logger.info("return_user_bi:" + return_user_bi);
		// 微信支付
		if (orderInfo.getPay_type().intValue() == Keys.PayType.PAY_TYPE_3.getIndex()) {
			// int ret = WeixinUtils.TuiKuan(orderReturn.getReturn_no(), orderInfo.getTrade_no(),
			// orderInfo.getReal_pay_money(), return_real_pay_money, Keys.ctxService);
			// if (ret == 0) {
			// logger.info("====ret:" + ret + ".退回余额");
			// return_user_bi = return_user_bi.add(return_real_pay_money);
			// return_money_type = Keys.return_money_type.return_money_type_0.getIndex();
			// logger.info("return_user_bi:" + return_user_bi);
			// }

		}
		OrderReturnInfo updateOrderReturn = new OrderReturnInfo();
		updateOrderReturn.setId(orderReturn.getId());
		updateOrderReturn.setReturn_money_type(return_money_type);
		orderReturnInfoDao.updateEntity(updateOrderReturn);

		OrderInfoDetails ods = new OrderInfoDetails();
		ods.setOrder_id(orderInfo.getId());
		ods.setIs_tuihuo(0);
		int count = orderInfoDetailsDao.selectEntityCount(ods);
		if (count == 0) {
			// 全部退了
			new_order_state = Keys.OrderState.ORDER_STATE_X20.getIndex();
		}

		insertUserBiRecord(userInfo.getId(), null, 1, orderInfo.getId(), orderReturn.getComm_id(), return_user_bi,
				Keys.BiType.BI_TYPE_100.getIndex(), Keys.BiGetType.BI_GET_TYPE_160.getIndex(), null, userInfoDao,
				userBiRecordDao);

		// 1 更新用户的余额
		updateUserInfoBi(userInfo.getId(), return_user_bi, "add_bi_dianzi", userInfoDao);

		// 更新订单状态
		updateOrder.setMoney_bi(new_money_bi);
		updateOrder.setOrder_money(new_order_money);
		updateOrder.setOrder_state(new_order_state);
		updateOrder.setUpdate_date(new Date());
		updateOrder.setUpdate_user_id(orderReturn.getAudit_user_id());
		orderInfoDao.updateEntity(updateOrder);

		return 1;
	}

	@Override
	/**
	 * 退钱的话，增加1、申请订单
	 * 				2、延迟订单15天
	 * 				3、上传图片
	 */
	public int TuiHuoOrderDeclare(Integer order_id, OrderInfoDao orderInfoDao, OrderReturnInfoDao orderReturnInfoDao,
			OrderReturnInfo orderReturnInfo, String[] basefiles, BaseImgsDao baseImgsDao) {
		if (orderInfoDao == null) {
			logger.debug("orderInfoDao为空");
			return 0;
		}
		if (orderReturnInfoDao == null) {
			logger.debug("orderReturnInfoDao为空");
			return 0;
		}

		if (orderReturnInfoDao == null) {
			logger.debug("orderReturnInfoDao为空");
			return 0;
		}
		if (orderReturnInfo == null) {
			logger.debug("orderReturnInfo为空");
			return 0;
		}

		if (baseImgsDao == null) {
			logger.debug("baseImgsDao为空");
			return 0;
		}

		int i = 0;
		// 添加退回申请信息
		i = orderReturnInfoDao.insertEntity(orderReturnInfo);

		if (i > 0) {

			OrderInfo entity = new OrderInfo();
			entity.setId(order_id);
			entity.setDelay_shouhuo(new Integer(1));
			entity.getMap().put("delay_shouhuo15", true);// 取消订单，前一个状态0，将订单的自动确认时间延迟7天

			entity.setOrder_state(Keys.OrderState.ORDER_STATE_15.getIndex());// 更改订单的状态
			int row = orderInfoDao.updateEntity(entity);

			// 延迟订单收货时间7天，默认就是7天，防止系统自动默认了

			if (ArrayUtils.isNotEmpty(basefiles)) {
				for (String file_path_lbt : basefiles) {
					if (StringUtils.isNotBlank(file_path_lbt)) {
						BaseImgs baseImgs = new BaseImgs();
						baseImgs.setImg_type(Keys.BaseImgsType.Base_Imgs_TYPE_20.getIndex());
						baseImgs.setFile_path(file_path_lbt);
						baseImgs.setLink_id(Integer.valueOf(i));
						baseImgsDao.insertEntity(baseImgs);
					}
				}
			}
		}

		return i;
	}

}
