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
import com.ebiz.webapp.domain.OrderReturnInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.service.factory.OrderTuiHuo.IOrderTuiHuo;
import com.ebiz.webapp.service.impl.BaseImpl;
import com.ebiz.webapp.web.Keys;

public class OrderTuiHuoReturnMoneyFaHuo extends BaseImpl implements IOrderTuiHuo {
	protected static final org.slf4j.Logger logger = LoggerFactory.getLogger(OrderTuiHuoReturnMoneyFaHuo.class);

	/**
	 * 退钱的话，需要填写返还的物流 1、退给消费者货款，如果订单失效了，扣除3%手续费； 2、退给商家物流费用； 3、 暂时不用此方法， 4、取消了这种退款方式
	 */
	@Override
	public int OrderTuiHuoAudit(Integer order_return_id, OrderReturnInfoDao orderReturnInfoDao,
			OrderInfoDao orderInfoDao, OrderInfoDetailsDao orderInfoDetailsDao, UserInfoDao userInfoDao,
			BaseDataDao baseDataDao, UserBiRecordDao userBiRecordDao) {
		OrderReturnInfo orderReturn = new OrderReturnInfo();
		orderReturn.setId(order_return_id);
		orderReturn.setIs_del(0);
		orderReturn = orderReturnInfoDao.selectEntity(orderReturn);

		OrderInfo orderInfoQueryUser = new OrderInfo();
		orderInfoQueryUser.setId(orderReturn.getOrder_id());
		orderInfoQueryUser = orderInfoDao.selectEntity(orderInfoQueryUser);
		if (null != orderInfoQueryUser) {
			UserInfo userInfo = new UserInfo();
			userInfo.setId(orderInfoQueryUser.getAdd_user_id());
			userInfo = userInfoDao.selectEntity(userInfo);
			if (null != userInfo) {
				// BigDecimal rmb_to_xiaoFeibi = super.BiToBi2(orderInfoQueryUser.getOrder_money(),
				// 订单金额-物流费用
				BigDecimal rmb_to_dianzibi = BiToBi2(
						orderInfoQueryUser.getOrder_money().subtract(orderInfoQueryUser.getMatflow_price()),
						Keys.BASE_DATA_ID.BASE_DATA_ID_904.getIndex(), baseDataDao);

				// 如果订单已经失效，退款时则扣除3%的手续费
				if (orderInfoQueryUser.getEnd_date().before(new Date())
						&& orderInfoQueryUser.getOrder_type().intValue() == Keys.OrderType.ORDER_TYPE_10.getIndex()) {
					// 消费商品订单超过失效时间才扣手续费，实物商品订单不扣了
					Double rate = Double.valueOf((100 - Keys.ORDER_TUIKUAN_RATE)) / 100;
					rmb_to_dianzibi = rmb_to_dianzibi.multiply(new BigDecimal(rate));
				}

				insertUserBiRecord(userInfo.getId(), null, 1, orderInfoQueryUser.getId(), orderReturn.getComm_id(),
						rmb_to_dianzibi, Keys.BiType.BI_TYPE_100.getIndex(), Keys.BiGetType.BI_GET_TYPE_160.getIndex(),
						null, userInfoDao, userBiRecordDao);

				// 1 更新用户的余额
				updateUserInfoBi(userInfo.getId(), rmb_to_dianzibi, "add_bi_dianzi", userInfoDao);

			}
			UserInfo entpUser = new UserInfo();
			entpUser.setOwn_entp_id(orderInfoQueryUser.getEntp_id());
			entpUser.setIs_del(0);
			entpUser = userInfoDao.selectEntity(entpUser);
			if (entpUser != null) {
				BigDecimal rmb_to_huokuanbi = BiToBi2(orderInfoQueryUser.getMatflow_price(),
						Keys.BASE_DATA_ID.BASE_DATA_ID_904.getIndex(), baseDataDao);
				insertUserBiRecord(entpUser.getId(), null, 1, orderInfoQueryUser.getId(), orderInfoQueryUser.getId(),
						rmb_to_huokuanbi, Keys.BiType.BI_TYPE_300.getIndex(),
						Keys.BiGetType.BI_GET_TYPE_160.getIndex(), null, userInfoDao, userBiRecordDao);
				updateUserInfoBi(userInfo.getId(), rmb_to_huokuanbi, "add_bi_huokuan", userInfoDao);
			}
		}
		return 1;
	}

	/**
	 * 退钱的话，增加1、申请订单 2、延迟订单7天 3、上传图片
	 */
	@Override
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
			entity.getMap().put("delay_shouhuo", true);// 取消订单，前一个状态0，将订单的自动确认时间延迟，延迟7天

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
