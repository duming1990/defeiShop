package com.ebiz.webapp.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.BaseAuditRecordDao;
import com.ebiz.webapp.dao.BaseDataDao;
import com.ebiz.webapp.dao.BaseImgsDao;
import com.ebiz.webapp.dao.CommInfoDao;
import com.ebiz.webapp.dao.CommInfoPoorsDao;
import com.ebiz.webapp.dao.CommTczhPriceDao;
import com.ebiz.webapp.dao.EntpInfoDao;
import com.ebiz.webapp.dao.MsgDao;
import com.ebiz.webapp.dao.MsgReceiverDao;
import com.ebiz.webapp.dao.OrderInfoDao;
import com.ebiz.webapp.dao.OrderInfoDetailsDao;
import com.ebiz.webapp.dao.OrderReturnAuditDao;
import com.ebiz.webapp.dao.OrderReturnInfoDao;
import com.ebiz.webapp.dao.OrderReturnMoneyDao;
import com.ebiz.webapp.dao.ServiceCenterInfoDao;
import com.ebiz.webapp.dao.SysOperLogDao;
import com.ebiz.webapp.dao.TongjiDao;
import com.ebiz.webapp.dao.UserBiRecordDao;
import com.ebiz.webapp.dao.UserInfoDao;
import com.ebiz.webapp.dao.UserJifenRecordDao;
import com.ebiz.webapp.dao.UserRelationParDao;
import com.ebiz.webapp.dao.UserScoreRecordDao;
import com.ebiz.webapp.dao.VillageInfoDao;
import com.ebiz.webapp.domain.BaseAuditRecord;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.OrderReturnAudit;
import com.ebiz.webapp.domain.OrderReturnInfo;
import com.ebiz.webapp.domain.OrderReturnMoney;
import com.ebiz.webapp.domain.UserBiRecord;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.service.OrderReturnInfoService;
import com.ebiz.webapp.service.factory.IOrderInfoServiceFactory;
import com.ebiz.webapp.service.factory.impl.OrderInfoServiceFactoryImpl;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.struts.alipay.AlipayUtils;
import com.ebiz.webapp.web.struts.allinpay.SybPayService;
import com.ebiz.webapp.web.struts.weixin.WeixinUtils;

/**
 * @author Wu,Yang
 * @version 2016-07-13 15:19
 */
@Service
public class OrderReturnInfoServiceImpl extends BaseImpl implements OrderReturnInfoService {

	@Resource
	private OrderReturnInfoDao orderReturnInfoDao;

	@Resource
	private OrderReturnMoneyDao orderReturnMoneyDao;

	@Resource
	private OrderInfoDao orderInfoDao;

	@Resource
	private OrderReturnAuditDao orderReturnAuditDao;

	@Resource
	private UserInfoDao userInfoDao;

	@Resource
	private BaseDataDao baseDataDao;

	@Resource
	private UserBiRecordDao userBiRecordDao;

	@Resource
	private OrderInfoDetailsDao orderInfoDetailsDao;

	@Resource
	private BaseImgsDao baseImgsDao;

	@Resource
	private CommInfoDao commInfoDao;

	@Resource
	private EntpInfoDao entpInfoDao;

	@Resource
	private BaseAuditRecordDao baseAuditRecordDao;

	@Resource
	private CommTczhPriceDao commTczhPriceDao;

	@Resource
	private UserScoreRecordDao userScoreRecordDao;

	@Resource
	private UserRelationParDao userRelationParDao;

	@Resource
	private UserJifenRecordDao userJifenRecordDao;

	@Resource
	private ServiceCenterInfoDao serviceCenterInfoDao;

	@Resource
	private TongjiDao tongjiDao;

	@Resource
	private CommInfoPoorsDao commInfoPoorsDao;

	@Resource
	private VillageInfoDao villageInfoDao;

	@Resource
	private MsgDao msgDao;

	@Resource
	private MsgReceiverDao msgReceiverDao;

	@Resource
	private SysOperLogDao sysOperLogDao;

	public Integer createOrderReturnInfo(OrderReturnInfo t) {

		String[] files = null;
		try {
			files = (String[]) t.getMap().get("basefiles");
		} catch (Exception e) {

		}

		OrderInfo orderInfo = getOrder(t.getOrder_id(), orderInfoDao);

		IOrderInfoServiceFactory service = new OrderInfoServiceFactoryImpl("liren");
		int id = service.TuiHuoOrderDeclare(t.getOrder_id(), orderInfoDao, orderReturnInfoDao, t, files, baseImgsDao);

		insertOrderReturnAuditRecord(orderInfo, id, baseAuditRecordDao);

		return id;
		// return this.orderReturnInfoDao.insertEntity(t);
	}

	public OrderReturnInfo getOrderReturnInfo(OrderReturnInfo t) {
		return this.orderReturnInfoDao.selectEntity(t);
	}

	public Integer getOrderReturnInfoCount(OrderReturnInfo t) {
		return this.orderReturnInfoDao.selectEntityCount(t);
	}

	public List<OrderReturnInfo> getOrderReturnInfoList(OrderReturnInfo t) {
		return this.orderReturnInfoDao.selectEntityList(t);
	}

	public int modifyOrderReturnInfo(OrderReturnInfo t) {

		OrderReturnInfo orderReturnInfo = getOrderReturn(t.getId(), orderReturnInfoDao);

		OrderInfo orderInfo = getOrder(t.getOrder_id(), orderInfoDao);

		UserInfo userInfo = new UserInfo();
		userInfo.setId(orderInfo.getAdd_user_id());
		userInfo = userInfoDao.selectEntity(userInfo);

		OrderReturnAudit orderReturnAudit = new OrderReturnAudit();
		orderReturnAudit.setOrder_return_id(t.getId());
		if (null != t.getMap().get("remark")) {
			orderReturnAudit.setRemark(String.valueOf(t.getMap().get("remark")));
		}
		orderReturnAudit.setUser_id(t.getUser_id());
		orderReturnAudit.setUser_name(t.getUser_name());
		orderReturnAudit.setAdd_date(new Date());
		orderReturnAudit.setAudit_state(t.getAudit_state());
		orderReturnAudit.setOrder_state(orderInfo.getOrder_state());
		orderReturnAuditDao.insertEntity(orderReturnAudit);

		if (t.getAudit_state().intValue() == Keys.audit_state.audit_state_1.getIndex()) {// 平台核通过
			// 换货
			if (orderReturnInfo.getExpect_return_way().intValue() == Keys.expectReturnWay.expectReturnWay_2
					.getIndex()) {
				int index = OrderTuiHuoSwap(orderReturnInfo, orderInfo, userInfo);
				if (index == -1) {
					return index;
				}
			}
			// 退款
			if (orderReturnInfo.getExpect_return_way().intValue() == Keys.expectReturnWay.expectReturnWay_1.getIndex()
					|| orderReturnInfo.getExpect_return_way().intValue() == Keys.expectReturnWay.expectReturnWay_4
							.getIndex()) {
				int index = TuiHuoOrderAudit(orderReturnInfo, orderInfo, userInfo);
				if (index == -1) {
					return index;
				}

			}

			// 创建订单退款记录表
			this.insertOrderReturnMoney(t);

			if (orderReturnInfo.getExpect_return_way().intValue() != Keys.expectReturnWay.expectReturnWay_2
					.getIndex()) {
				// 删除待返余额
				UserBiRecord updateUserBiRecord = new UserBiRecord();
				updateUserBiRecord.setIs_del(0);
				updateUserBiRecord.getMap().put("bi_chu_or_ru", Keys.BI_CHU_OR_RU.BASE_DATA_ID_1.getIndex());
				updateUserBiRecord.getMap().put("order_id", orderInfo.getId());
				updateUserBiRecord.getMap().put("bi_type_in", Keys.BiType.BI_TYPE_200.getIndex() + ","
						+ Keys.BiType.BI_TYPE_400.getIndex() + "," + Keys.BiType.BI_TYPE_600.getIndex());
				List<UserBiRecord> userBiRecordList = userBiRecordDao.selectEntityList(updateUserBiRecord);
				if (null != userBiRecordList && userBiRecordList.size() > 0) {
					for (UserBiRecord ubr : userBiRecordList) {
						if (ubr.getBi_type().intValue() == Keys.BiType.BI_TYPE_200.getIndex()) {// 待返余额
							// 更新待返余额
							UserInfo update_user = new UserInfo();
							update_user.setId(ubr.getAdd_user_id());
							update_user.getMap().put("sub_bi_dianzi_lock", ubr.getBi_no());
							userInfoDao.updateEntity(update_user);
						} else if (ubr.getBi_type().intValue() == Keys.BiType.BI_TYPE_400.getIndex()) {// 待返货款
							// 更新待返货款
							UserInfo update_user = new UserInfo();
							update_user.setId(ubr.getAdd_user_id());
							update_user.getMap().put("sub_bi_huokuan_lock", ubr.getBi_no());
							userInfoDao.updateEntity(update_user);
						} else if (ubr.getBi_type().intValue() == Keys.BiType.BI_TYPE_600.getIndex()) {// 待返扶贫金
							// 更新待返扶贫金
							UserInfo update_user = new UserInfo();
							update_user.setId(ubr.getAdd_user_id());
							update_user.getMap().put("sub_bi_aid_lock", ubr.getBi_no());
							userInfoDao.updateEntity(update_user);
						}
					}
				}
				updateUserBiRecord.setIs_del(1);
				userBiRecordDao.updateEntity(updateUserBiRecord);

				// 重新计算待返余额
				super.reckonRebateAndAid(orderInfo.getId(), orderInfoDao, orderInfoDetailsDao, userInfoDao,
						userScoreRecordDao, userRelationParDao, userBiRecordDao, entpInfoDao, serviceCenterInfoDao,
						new Date(), baseDataDao, userJifenRecordDao, tongjiDao, commInfoDao, commInfoPoorsDao,
						villageInfoDao);
			}

			t.setIs_confirm(2);

			// 改库存
			if (orderReturnInfo.getExpect_return_way().intValue() == Keys.expectReturnWay.expectReturnWay_1.getIndex()
					|| orderReturnInfo.getExpect_return_way().intValue() == Keys.expectReturnWay.expectReturnWay_4
							.getIndex()) {
				orderReturnUpdateCommInfoCommSaleCountAndInventory(1, orderReturnInfo, orderReturnInfoDao,
						commTczhPriceDao, commInfoDao, entpInfoDao, orderInfoDetailsDao);
			}
		}

		// 退款，商家审核成功，修改退款订单，审核状态
		super.modifyTuiKuanAuditState(orderReturnInfo.getId(), t.getAudit_state(), orderInfo,
				orderReturnInfo.getOrder_detail_id(), orderReturnInfoDao, baseAuditRecordDao);

		if (t.getAudit_state().intValue() == Keys.audit_state.audit_state_fu_1.getIndex()
				|| t.getAudit_state().intValue() == Keys.audit_state.audit_state_fu_2.getIndex()) {// 审核不通过
			BaseAuditRecord baseAudit = new BaseAuditRecord();
			baseAudit.setOpt_type(Keys.OptType.OPT_TYPE_11.getIndex());
			baseAudit.setLink_id(orderReturnInfo.getId());
			baseAudit.setLink_table("OrderReturnInfo");
			baseAudit = baseAuditRecordDao.selectEntity(baseAudit);
			if (null != baseAudit) {
				baseAudit.setAudit_state(t.getAudit_state().intValue());
				baseAudit.setAudit_user_id(orderInfo.getEntp_id());
				baseAudit.setAudit_user_name(orderInfo.getEntp_name());
				baseAudit.setAudit_date(new Date());
				baseAuditRecordDao.updateEntity(baseAudit);

			}
			if (null != orderReturnInfo.getOrder_state()) {
				orderInfo.setOrder_state(orderReturnInfo.getOrder_state());
				orderInfo.setUpdate_date(new Date());
				orderInfo.setUpdate_user_id(orderReturnInfo.getAudit_user_id());
				orderInfoDao.updateEntity(orderInfo);
			}
		}

		return this.orderReturnInfoDao.updateEntity(t);
	}

	public int OrderTuiHuoSwap(OrderReturnInfo orderReturnInfo, OrderInfo orderInfo, UserInfo userInfo) {
		int id = 0;

		OrderInfoDetails ods = getOrderInfoDetailsInfo(orderInfoDetailsDao, orderReturnInfo.getOrder_detail_id());

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
		newOrder.setReal_pay_money(newOrder.getOrder_money());
		newOrder.setNo_dis_money(new BigDecimal(0));
		newOrder.setYhq_tip_desc("");

		id = orderInfoDao.insertEntity(newOrder);

		OrderInfoDetails newOds = new OrderInfoDetails();
		newOds = ods;
		newOds.setId(null);
		newOds.setOrder_id(id);
		newOds.setAdd_date(new Date());
		orderInfoDetailsDao.insertEntity(newOds);

		int i = orderInfoDao.updateEntity(orderInfo);

		OrderInfoDetails updateOds = new OrderInfoDetails();
		updateOds.setId(orderReturnInfo.getOrder_detail_id());
		updateOds.setIs_tuihuo(1);
		orderInfoDetailsDao.updateEntity(updateOds);

		return id;
	}

	public int TuiHuoOrderAudit(OrderReturnInfo orderReturnInfo, OrderInfo orderInfo, UserInfo userInfo) {
		int i = 0;

		BigDecimal order_welfare = orderInfo.getWelfare_pay_money();// 订单中使用的福利金
		BigDecimal this_return_welfare = new BigDecimal(0);// 本次退款福利金

		Integer new_order_state = Keys.OrderState.ORDER_STATE_X20.getIndex();

		OrderInfo updateOrder = new OrderInfo();
		updateOrder.setId(orderInfo.getId());

		// 订单金额-物流费用
		BigDecimal return_money = new BigDecimal(0);

		// 全退
		if (null == orderReturnInfo.getOrder_detail_id()) {

			return_money = new BigDecimal(0);
			OrderInfoDetails ods = new OrderInfoDetails();
			ods.setOrder_id(orderInfo.getId());
			List<OrderInfoDetails> odsList = orderInfoDetailsDao.selectEntityList(ods);
			if (null != odsList && odsList.size() > 0) {
				for (OrderInfoDetails item : odsList) {

					if (orderReturnInfo.getExpect_return_way().intValue() == Keys.expectReturnWay.expectReturnWay_4
							.getIndex()) {
						// 未发货退款
						// 退款金额 = 实际支付金额 + 物流费用
						return_money = return_money.add(item.getActual_money().add(item.getMatflow_price()));
					} else if (orderReturnInfo.getExpect_return_way()
							.intValue() == Keys.expectReturnWay.expectReturnWay_1.getIndex()) {
						// 退货退款
						// 退款金额 = 实际支付金额 - 物流费用
						return_money = return_money.add(item.getActual_money().subtract(item.getMatflow_price()));
					}
				}
			}
			ods.setIs_tuihuo(1);
			orderInfoDetailsDao.updateEntity(ods);
		}

		if (null != orderReturnInfo.getOrder_detail_id()) {
			// 退一个
			OrderInfoDetails ods = new OrderInfoDetails();
			ods.setId(orderReturnInfo.getOrder_detail_id());
			ods = orderInfoDetailsDao.selectEntity(ods);

			if (orderReturnInfo.getExpect_return_way().intValue() == Keys.expectReturnWay.expectReturnWay_4
					.getIndex()) {
				// 未发货退款
				// 退款金额 = 实际支付金额 + 物流费用
				return_money = super.BiToBi2(ods.getActual_money().add(ods.getMatflow_price()),
						Keys.BASE_DATA_ID.BASE_DATA_ID_904.getIndex(), baseDataDao);

				new_order_state = Keys.OrderState.ORDER_STATE_10.getIndex();
			} else if (orderReturnInfo.getExpect_return_way().intValue() == Keys.expectReturnWay.expectReturnWay_1
					.getIndex()) {
				// 退货退款
				// 退款金额 = 实际支付金额
				return_money = return_money.add(ods.getActual_money());

				new_order_state = Keys.OrderState.ORDER_STATE_20.getIndex();
			}

			ods.setIs_tuihuo(1);
			orderInfoDetailsDao.updateEntity(ods);

			BigDecimal tiaofulijian = ods.getGood_sum_price().subtract(ods.getActual_money());

			// 修改订单金额
			orderInfo.setNo_dis_money(orderInfo.getNo_dis_money().subtract(return_money.add(tiaofulijian)));
		}

		this_return_welfare = return_money;

		BigDecimal return_user_bi = new BigDecimal(0);// 给会员的电子币
		BigDecimal return_real_pay_money = new BigDecimal(0);// 第三方回退金额
		BigDecimal new_money_bi = orderInfo.getMoney_bi();// 订单剩余电子币
		BigDecimal new_order_money = orderInfo.getOrder_money();// 订单剩余金额

		int return_money_type = Keys.return_money_type.return_money_type_0.getIndex();

		// 1.全电子币 原【电子币支付金额】 >= 【实际支付金额】
		if (orderInfo.getMoney_bi().compareTo(return_money) >= 0) {
			return_user_bi = return_money;

			// 【第三方回退金额】= 【退款金额】 - 【余额支付金额】
			return_real_pay_money = return_money.subtract(return_user_bi);

			// 新的订单金额
			new_order_money = orderInfo.getOrder_money().subtract(return_real_pay_money);

			new_money_bi = orderInfo.getMoney_bi().subtract(return_user_bi);

			return_money_type = Keys.return_money_type.return_money_type_0.getIndex();
		} else {
			// 3.部分电子币 部分第三方 原【电子币支付金额】 < 【实际支付金额】
			return_user_bi = orderInfo.getMoney_bi();

			// 【第三方回退金额】= 【退款金额】 - 【余额支付金额】
			return_real_pay_money = return_money.subtract(return_user_bi);

			// 新的订单金额
			new_order_money = orderInfo.getOrder_money().subtract(return_real_pay_money);

			new_money_bi = new BigDecimal(0);

			return_money_type = Keys.return_money_type.return_money_type_2.getIndex();

			// 如果【反回电子币】是0
			if (return_user_bi.compareTo(new BigDecimal(0)) == 0) {
				return_money_type = Keys.return_money_type.return_money_type_1.getIndex();
			}
		}

		// 有第三方回退金额
		if (return_real_pay_money.compareTo(new BigDecimal(0)) == 1) {
			// 微信支付
			if (orderInfo.getPay_type().intValue() == Keys.PayType.PAY_TYPE_3.getIndex()) {
				int ret = 0;

				if (null != orderInfo.getReal_pay_money()) {
					if (null != orderInfo.getPay_platform()
							&& orderInfo.getPay_platform().intValue() == Keys.PayPlatform.MIN.getIndex()) {// 小程序端支付
						ret = WeixinUtils.TuiKuanForWeiXinMp(orderReturnInfo.getReturn_no(), orderInfo.getTrade_no(),
								orderInfo.getReal_pay_money(), return_real_pay_money, Keys.ctxService, sysOperLogDao);
					} else {
						ret = WeixinUtils.TuiKuan(orderReturnInfo.getReturn_no(), orderInfo.getTrade_no(),
								orderInfo.getReal_pay_money(), return_real_pay_money, Keys.ctxService, sysOperLogDao);
					}

					if (ret == 0) {
						return -1;
					}
				}

				// 微信回头失败
				if (ret == 0) {
					return_user_bi = return_user_bi.add(return_real_pay_money);
					return_real_pay_money = new BigDecimal(0);
					return_money_type = Keys.return_money_type.return_money_type_0.getIndex();
				}
			} else if (orderInfo.getPay_type().intValue() == Keys.PayType.PAY_TYPE_1.getIndex()) {
				// 支付宝支付
				int ret = 0;

				if (null != orderInfo.getReal_pay_money()) {
					ret = AlipayUtils.TuiKuan(orderInfo.getTrade_index(), orderInfo.getTrade_no(),
							orderReturnInfo.getReturn_no(), return_real_pay_money, Keys.ctxService, sysOperLogDao);
					if (ret == 0) {
						return -1;
					}
				}

				if (ret == 0) {
					return_user_bi = return_user_bi.add(return_real_pay_money);
					return_real_pay_money = new BigDecimal(0);
					return_money_type = Keys.return_money_type.return_money_type_0.getIndex();
				}
			} else if (orderInfo.getPay_type().intValue() == Keys.PayType.PAY_TYPE_4.getIndex()) {
				// 联通支付
				int ret = 0;
				if (null != orderInfo.getReal_pay_money()) {

					ret = SybPayService.refund(return_real_pay_money.toString(), orderReturnInfo.getReturn_no(),
							orderInfo.getTrade_no(), orderInfo.getTrade_merger_index(), sysOperLogDao);
					if (ret == 0) {
						return -1;
					}
				}

				if (ret == 0) {
					return_user_bi = return_user_bi.add(return_real_pay_money);
					return_real_pay_money = new BigDecimal(0);
					return_money_type = Keys.return_money_type.return_money_type_0.getIndex();
				}
			} else {
				// 余额
				return_user_bi = return_user_bi.add(return_real_pay_money);
				return_real_pay_money = new BigDecimal(0);
				return_money_type = Keys.return_money_type.return_money_type_0.getIndex();
			}
		}
		// 买了100元 2个商品 一个60 一个40 只有30福利金 支付30福利金 70余额 退一个40的
		// 退40元 40退到余额

		// 买了100元 2个商品 一个60 一个40 只有100福利金 支付100福利金 0余额 退一个40的
		// 退40元 40退到福利金

		// 买了100元 2个商品 一个60 一个40 只有80福利金 支付100福利金 20余额 退一个40的
		// 退40元 20退余额 20退福利金
		if (order_welfare.compareTo(new BigDecimal(0)) > 0) {
			this_return_welfare = return_user_bi;
		}

		// 订单福利金 < 当前退款福利金
		if (order_welfare.compareTo(this_return_welfare) < 0) {
			this_return_welfare = order_welfare;
		}

		OrderReturnInfo updateOrderReturn = new OrderReturnInfo();
		updateOrderReturn.setId(orderReturnInfo.getId());
		updateOrderReturn.setReturn_real_money(return_real_pay_money);
		updateOrderReturn.setReturn_bi_dianzi(return_user_bi);
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

		if (return_user_bi.compareTo(new BigDecimal(0)) == 1) {

			if (super.compareTo(orderInfo.getWelfare_pay_money(), new BigDecimal(0)) > 0) {
				// 使用了福利金，插入福利金操作记录
				super.insertUserWelfareBuyBiRecord(userInfo.getId(), null, 1, orderInfo.getId(),
						orderReturnInfo.getComm_id(), return_user_bi, Keys.BiType.BI_TYPE_100.getIndex(),
						Keys.BiGetType.BI_GET_TYPE_160.getIndex(), null, userInfoDao, userBiRecordDao,
						this_return_welfare);
				updateOrder.getMap().put("sub_welfare_pay_money", this_return_welfare);
			} else {
				// 未使用福利金，正常操作记录
				super.insertUserBiRecord(userInfo.getId(), null, 1, orderInfo.getId(), orderReturnInfo.getComm_id(),
						return_user_bi, Keys.BiType.BI_TYPE_100.getIndex(), Keys.BiGetType.BI_GET_TYPE_160.getIndex(),
						null, userInfoDao, userBiRecordDao);
			}

			// 1 更新用户的余额
			super.updateUserInfoBi(userInfo.getId(), return_user_bi, "add_bi_dianzi", userInfoDao);

			if (super.compareTo(orderInfo.getWelfare_pay_money(), new BigDecimal(0)) > 0) {
				// 使用了福利金，插入福利金操作记录
				super.updateUserInfoBi(userInfo.getId(), return_user_bi, "add_bi_welfare", userInfoDao);
			}

		}

		// 更新订单状态
		updateOrder.setMoney_bi(new_money_bi);
		updateOrder.setOrder_money(new_order_money);
		updateOrder.setOrder_state(new_order_state);
		updateOrder.setUpdate_date(new Date());
		updateOrder.setUpdate_user_id(orderReturnInfo.getAudit_user_id());
		orderInfoDao.updateEntity(updateOrder);

		String pay_type_name = "";
		if (orderInfo.getPay_type().intValue() == Keys.PayType.PAY_TYPE_3.getIndex()) {
			pay_type_name = "微信";
		}
		if (orderInfo.getPay_type().intValue() == Keys.PayType.PAY_TYPE_1.getIndex()) {
			pay_type_name = "支付宝";
		}

		String msg = "您有一笔退款订单【" + orderReturnInfo.getReturn_no() + "】已处理，";
		if (return_money_type == Keys.return_money_type.return_money_type_0.getIndex()) {
			msg += "余额退款:" + return_user_bi.doubleValue();
		} else if (return_money_type == Keys.return_money_type.return_money_type_1.getIndex()) {
			msg += pay_type_name + "退款:" + return_real_pay_money.doubleValue();
		} else {
			msg += pay_type_name + "退款:" + return_real_pay_money.doubleValue() + ",余额退款:"
					+ return_user_bi.doubleValue();
		}

		msg = msg + "。请及时查看账号金额！";

		super.sendMsg(1, userInfo.getId(), "退款提醒", msg, msgDao, msgReceiverDao, userInfoDao);

		return i;
	}

	public void insertOrderReturnMoney(OrderReturnInfo t) {
		OrderReturnMoney orderReturnMoney = new OrderReturnMoney();
		orderReturnMoney.setOrder_return_id(t.getId());
		orderReturnMoney.setUser_id(t.getUser_id());
		orderReturnMoney.setUser_name(t.getUser_name());
		orderReturnMoney.setAdd_date(new Date());
		if (null != t.getComm_id()) {
			orderReturnMoney.setComm_id(Long.valueOf(t.getComm_id()));
		}
		orderReturnMoney.setPrice(t.getPrice());
		orderReturnMoney.setNum(t.getNum());
		if (null != t.getComm_name()) {
			orderReturnMoney.setComm_name(t.getComm_name());
		}
		orderReturnMoney.setTk_money(t.getTk_money());
		if (null != t.getOrder_detail_id()) {
			orderReturnMoney.setOrder_detail_id(Long.valueOf(t.getOrder_detail_id()));
		}
		// orderReturnMoney.setTk_remark();
		orderReturnMoney.setTk_type(t.getReturn_way());
		this.orderReturnMoneyDao.insertEntity(orderReturnMoney);
	}

	public int removeOrderReturnInfo(OrderReturnInfo t) {
		return this.orderReturnInfoDao.deleteEntity(t);
	}

	public List<OrderReturnInfo> getOrderReturnInfoPaginatedList(OrderReturnInfo t) {
		return this.orderReturnInfoDao.selectEntityPaginatedList(t);
	}

	@Override
	public OrderReturnInfo getRefund(OrderReturnInfo t) {
		return this.orderReturnInfoDao.selectOrderReturnInfoRefund(t);
	}

}
