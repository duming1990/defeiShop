package com.ebiz.webapp.service.factory.impl.OrderTuiHuo;

import java.math.BigDecimal;
import java.util.Date;

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
import com.ebiz.webapp.service.factory.OrderTuiHuo.IOrderTuiHuo;
import com.ebiz.webapp.service.impl.BaseImpl;
import com.ebiz.webapp.web.Keys;

/**
 * 换货
 * 
 * @author jiaorg
 */
public class OrderTuiHuoSwap extends BaseImpl implements IOrderTuiHuo {
	protected static final org.slf4j.Logger logger = LoggerFactory.getLogger(OrderTuiHuoSwap.class);

	/**
	 * 换货的话，修改订单的状态为待发货状态，需要客户重新发货，然后重新计算发货时间
	 */
	@Override
	public int OrderTuiHuoAudit(Integer order_return_id, OrderReturnInfoDao orderReturnInfoDao,
			OrderInfoDao orderInfoDao, OrderInfoDetailsDao orderInfoDetailsDao, UserInfoDao userInfoDao,
			BaseDataDao baseDataDao, UserBiRecordDao userBiRecordDao) {

		OrderReturnInfo orderReturn = getOrderReturn(order_return_id, orderReturnInfoDao);

		OrderInfo orderInfo = getOrder(orderReturn.getOrder_id(), orderInfoDao);

		OrderInfoDetails ods = getOrderInfoDetailsInfo(orderInfoDetailsDao, orderReturn.getOrder_detail_id());

		BigDecimal newOrderMoney = ods.getActual_money().add(ods.getMatflow_price());

		OrderInfo newOrder = new OrderInfo();
		newOrder = orderInfo;
		newOrder.setId(null);
		newOrder.setTrade_index(super.creatTradeIndex());
		newOrder.setOrder_num(ods.getGood_count());
		newOrder.setAdd_date(new Date());
		newOrder.setPay_date(new Date());
		newOrder.setOrder_date(new Date());
		newOrder.setOrder_state(Keys.OrderState.ORDER_STATE_10.getIndex());
		newOrder.setMatflow_price(ods.getMatflow_price());
		newOrder.setMoney_bi(new BigDecimal(0));
		newOrder.setOrder_money(new BigDecimal(0));
		newOrder.setNo_dis_money(new BigDecimal(0));
		newOrder.setYhq_tip_desc("");

		int id = orderInfoDao.insertEntity(newOrder);

		OrderInfoDetails newOds = new OrderInfoDetails();
		newOds = ods;
		newOds.setId(null);
		newOds.setOrder_id(id);
		newOds.setAdd_date(new Date());
		orderInfoDetailsDao.insertEntity(newOds);

		int i = orderInfoDao.updateEntity(orderInfo);

		OrderInfoDetails updateOds = new OrderInfoDetails();
		updateOds.setId(orderReturn.getOrder_detail_id());
		updateOds.setIs_tuihuo(1);
		orderInfoDetailsDao.updateEntity(updateOds);

		return id;
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
			entity.getMap().put("delay_shouhuo15", true);// 取消订单，前一个状态0，将订单的自动确认时间延迟延迟15天

			// entity.setOrder_state(Keys.OrderState.ORDER_STATE_15.getIndex());// 更改订单的状态

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
