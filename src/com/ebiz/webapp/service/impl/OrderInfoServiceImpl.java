package com.ebiz.webapp.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.dao.BaseAuditRecordDao;
import com.ebiz.webapp.dao.BaseClassDao;
import com.ebiz.webapp.dao.BaseDataDao;
import com.ebiz.webapp.dao.CardInfoDao;
import com.ebiz.webapp.dao.CartInfoDao;
import com.ebiz.webapp.dao.CommInfoDao;
import com.ebiz.webapp.dao.CommInfoPoorsDao;
import com.ebiz.webapp.dao.CommTczhPriceDao;
import com.ebiz.webapp.dao.EntpInfoDao;
import com.ebiz.webapp.dao.MsgDao;
import com.ebiz.webapp.dao.MsgReceiverDao;
import com.ebiz.webapp.dao.OrderAuditDao;
import com.ebiz.webapp.dao.OrderInfoDao;
import com.ebiz.webapp.dao.OrderInfoDetailsDao;
import com.ebiz.webapp.dao.OrderReturnInfoDao;
import com.ebiz.webapp.dao.PoorCuoShiDao;
import com.ebiz.webapp.dao.PoorInfoDao;
import com.ebiz.webapp.dao.ServiceCenterInfoDao;
import com.ebiz.webapp.dao.SysOperLogDao;
import com.ebiz.webapp.dao.SysSettingDao;
import com.ebiz.webapp.dao.TongjiDao;
import com.ebiz.webapp.dao.UserBiRecordDao;
import com.ebiz.webapp.dao.UserInfoDao;
import com.ebiz.webapp.dao.UserJifenRecordDao;
import com.ebiz.webapp.dao.UserRelationParDao;
import com.ebiz.webapp.dao.UserScoreRecordDao;
import com.ebiz.webapp.dao.VillageInfoDao;
import com.ebiz.webapp.dao.WlOrderInfoDao;
import com.ebiz.webapp.dao.YhqInfoDao;
import com.ebiz.webapp.dao.YhqInfoSonDao;
import com.ebiz.webapp.domain.CardInfo;
import com.ebiz.webapp.domain.CartInfo;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.OrderReturnInfo;
import com.ebiz.webapp.domain.SysSetting;
import com.ebiz.webapp.domain.UserBiRecord;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.WlOrderInfo;
import com.ebiz.webapp.domain.YhqInfo;
import com.ebiz.webapp.domain.YhqInfoSon;
import com.ebiz.webapp.service.OrderInfoService;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.SMS;
import com.ebiz.webapp.web.util.dysms.DySmsUtils;
import com.ebiz.webapp.web.util.dysms.SmsTemplate;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:55
 */
@Service
public class OrderInfoServiceImpl extends BaseImpl implements OrderInfoService {

	@Resource
	private CartInfoDao cartInfoDao;

	@Resource
	private CardInfoDao cardInfoDao;

	@Resource
	private OrderInfoDao orderInfoDao;

	@Resource
	private CommInfoDao commInfoDao;

	@Resource
	private CommTczhPriceDao commTczhPriceDao;

	@Resource
	private OrderInfoDetailsDao orderInfoDetailsDao;

	@Resource
	private UserInfoDao userInfoDao;

	@Resource
	private BaseDataDao baseDataDao;

	@Resource
	private UserScoreRecordDao userScoreRecordDao;

	@Resource
	private UserBiRecordDao userBiRecordDao;

	@Resource
	private UserRelationParDao userRelationParDao;

	@Resource
	private EntpInfoDao entpInfoDao;

	@Resource
	private WlOrderInfoDao wlOrderInfoDao;

	@Resource
	private MsgDao msgDao;

	@Resource
	private MsgReceiverDao msgReceiverDao;

	@Resource
	private ServiceCenterInfoDao serviceCenterInfoDao;

	@Resource
	private UserJifenRecordDao userJifenRecordDao;

	@Resource
	private TongjiDao tongjiDao;

	@Resource
	private OrderReturnInfoDao orderReturnInfoDao;

	@Resource
	private BaseAuditRecordDao baseAuditRecordDao;

	@Resource
	private OrderAuditDao orderAuditDao;

	@Resource
	private SysOperLogDao sysOperLogDao;

	@Resource
	private BaseClassDao baseClassDao;

	@Resource
	private CommInfoPoorsDao commInfoPoorsDao;

	@Resource
	private VillageInfoDao villageInfoDao;

	@Resource
	private PoorCuoShiDao poorCuoShiDao;

	@Resource
	private YhqInfoSonDao yhqInfoSonDao;

	@Resource
	private YhqInfoDao yhqInfoDao;

	@Resource
	private SysSettingDao sysSettingDao;

	@Resource
	private PoorInfoDao poorInfoDao;

	@Override
	public Integer createOrderInfo(OrderInfo t) {
		if (null != t.getMap().get("payOrder")) {// 购物车下订单，每个商家一个订单
			// Order_Info订单表 Order_Info_Details订单详细表
			List<OrderInfo> orderInfoList = t.getOrderInfoList();
			int insert_flag = 0;

			if (null != orderInfoList && orderInfoList.size() > 0) {
				for (OrderInfo ci : orderInfoList) {
					Integer order_id = this.orderInfoDao.insertEntity(ci);
					String isLeader = (String) ci.getMap().get("isLeader");

					// 是拼团主订单，将主订单id设置到leader_order_id
					if (StringUtils.isNotBlank(isLeader) && "1".equals(isLeader)) {
						OrderInfo order = new OrderInfo();
						order.setId(order_id);
						order.setLeader_order_id(order_id);
						this.orderInfoDao.updateEntity(order);
					}
					if (StringUtils.isNotBlank(isLeader) && "0".equals(isLeader)) {
						OrderInfo updateLeaderOrder = new OrderInfo();
						updateLeaderOrder.setId(ci.getLeader_order_id());
						updateLeaderOrder.getMap().put("updata_group_number", true);
						updateLeaderOrder.setGroup_number(1);
						updateLeaderOrder.getMap().put("comm_group_count", ci.getMap().get("comm_group_count"));

						int updateCount = this.orderInfoDao.updateEntity(updateLeaderOrder);
						if (updateCount == 0) {
							// 拼团人数已满，回滚操作
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
							return -3;
						}
					}
					insert_flag = order_id;
					// Order_Info_Details订单详细表
					List<OrderInfoDetails> orderInfoDetailsList = ci.getOrderInfoDetailsList();
					if (null != orderInfoDetailsList && orderInfoDetailsList.size() > 0) {
						for (OrderInfoDetails cid : orderInfoDetailsList) {
							cid.setOrder_id(order_id);
							cid.setAdd_date(new Date());
							this.orderInfoDetailsDao.insertEntity(cid);
						}
					}

					if (ci.getOrder_type().intValue() == Keys.OrderType.ORDER_TYPE_30.getIndex()) {
						try {
							String createJdOrderFlag = super.syncUploadJdOrder(order_id, orderInfoDao,
									orderInfoDetailsDao);
							if (createJdOrderFlag.equals("0")) {
								insert_flag = -2;
								TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
								return insert_flag;
							}
						} catch (IOException e) {
							e.printStackTrace();
							insert_flag = -1;
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
							return insert_flag;
						}
					}

					if (null != t.getMap().get("update_comm_info_saleCountAndInventory")) {
						int saleCountFlag = super.modifyOrderInfoCommSaleCountAndInventory(order_id, 0,
								orderInfoDetailsDao, commTczhPriceDao, commInfoDao, entpInfoDao);
						if (saleCountFlag == 1) { // 库存不足
							insert_flag = -1;
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
							return insert_flag;
						}
					}
				}
				// 清空购物车
				CartInfo cartInfo_temp = new CartInfo();
				cartInfo_temp.setUser_id(t.getAdd_user_id());
				cartInfo_temp.getMap().put("cart_ids", t.getMap().get("cart_ids"));
				List<CartInfo> cart_info_list = this.cartInfoDao.selectEntityList(cartInfo_temp);
				for (CartInfo temp : cart_info_list) {
					this.cartInfoDao.deleteEntity(temp);
				}
			}
			return insert_flag;
		} else {// 普通订单

			int order_id = this.orderInfoDao.insertEntity(t);
			// Order_Info_Details订单详细表
			List<OrderInfoDetails> orderInfoDetailsList = t.getOrderInfoDetailsList();
			if (null != orderInfoDetailsList && orderInfoDetailsList.size() > 0) {
				for (OrderInfoDetails cid : orderInfoDetailsList) {
					cid.setOrder_id(order_id);
					cid.setAdd_date(new Date());
					this.orderInfoDetailsDao.insertEntity(cid);
				}
			}

			// 扫描店铺二维码订单
			if (null != t.getMap().get("m_pay_scan_update_link_table_type_0_4")) {
				return mPayScan(order_id);
			}
			// 扫描店铺二维码订单
			if (null != t.getMap().get("update_comm_info_saleCountAndInventory")) {
				int i = super.modifyOrderInfoCommSaleCountAndInventory(order_id, 0, orderInfoDetailsDao,
						commTczhPriceDao, commInfoDao, entpInfoDao);
				if (i < 0) {
					return i;
				}
			}
			return order_id;
		}
	}

	/**
	 * 无人超市扶贫
	 */
	@Override
	public JSONObject createSupermarketOrderInfo(OrderInfo t) {

		int order_id = this.orderInfoDao.insertEntity(t);
		// Order_Info_Details订单详细表
		List<OrderInfoDetails> orderInfoDetailsList = t.getOrderInfoDetailsList();
		if (null != orderInfoDetailsList && orderInfoDetailsList.size() > 0) {
			for (OrderInfoDetails cid : orderInfoDetailsList) {
				cid.setOrder_id(order_id);
				cid.setAdd_date(new Date());
				this.orderInfoDetailsDao.insertEntity(cid);
			}
		}
		JSONObject jsonObject = superMarketHelpPoor(order_id, orderInfoDao, orderInfoDetailsDao, userInfoDao,
				userRelationParDao, userBiRecordDao, entpInfoDao, baseDataDao, userJifenRecordDao, commInfoDao,
				commInfoPoorsDao, poorInfoDao);
		return jsonObject;
	}

	/**
	 * 刷单
	 * 
	 * @param OrderInfo
	 * @return
	 */
	@Override
	public Integer createShuaDanOrderInfo(OrderInfo t) {
		int insert_flag = 0;

		if (null != t.getMap().get("payOrder")) {// 购物车下订单，每个商家一个订单
			// Order_Info订单表 Order_Info_Details订单详细表
			List<OrderInfo> orderInfoList = t.getOrderInfoList();

			if (null != orderInfoList && orderInfoList.size() > 0) {
				for (OrderInfo ci : orderInfoList) {
					Integer order_id = this.orderInfoDao.insertEntity(ci);
					insert_flag = order_id;
					// Order_Info_Details订单详细表
					List<OrderInfoDetails> orderInfoDetailsList = ci.getOrderInfoDetailsList();
					if (null != orderInfoDetailsList && orderInfoDetailsList.size() > 0) {
						for (OrderInfoDetails cid : orderInfoDetailsList) {
							cid.setOrder_id(order_id);
							cid.setAdd_date(new Date());
							this.orderInfoDetailsDao.insertEntity(cid);
						}
					}
					// 发放扶贫金
					if (null != t.getMap().get("is_send_fp")) {
						super.reckonAidMoney(order_id, orderInfoDao, orderInfoDetailsDao, userInfoDao,
								userScoreRecordDao, userRelationParDao, userBiRecordDao, entpInfoDao,
								serviceCenterInfoDao, new Date(), baseDataDao, userJifenRecordDao, tongjiDao,
								commInfoDao, commInfoPoorsDao, villageInfoDao);

						super.grantBiAidLockToUser(order_id, orderInfoDao, orderInfoDetailsDao, userInfoDao,
								userBiRecordDao, entpInfoDao, baseDataDao, userJifenRecordDao, poorCuoShiDao);

						OrderInfo oInfo = new OrderInfo();
						oInfo.setId(order_id);
						oInfo.setOrder_state(Keys.OrderState.ORDER_STATE_50.getIndex());
						this.orderInfoDao.updateEntity(oInfo);
					}
				}
			}
		}
		return insert_flag;
	}

	/**
	 * 减订单中商品库存
	 * 
	 * @param OrderInfo
	 * @return
	 */
	@Override
	public int modifyOrderInfoInventory(OrderInfo t) {
		int i = 0;
		OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
		orderInfoDetails.setOrder_id(t.getId());
		List<OrderInfoDetails> orderInfoDetailsList = this.orderInfoDetailsDao.selectEntityList(orderInfoDetails);
		if (orderInfoDetailsList != null && orderInfoDetailsList.size() > 0) {
			OrderInfoDetails d = new OrderInfoDetails();
			for (int z = 0; z < orderInfoDetailsList.size(); z++) {
				i++;
				d = orderInfoDetailsList.get(z);
				if (d.getComm_id() != null) {
					if (d.getComm_tczh_id() != null) {
						CommTczhPrice commTczhPrice = new CommTczhPrice();
						commTczhPrice.setId(d.getComm_tczh_id());
						commTczhPrice = this.commTczhPriceDao.selectEntity(commTczhPrice);
						if (commTczhPrice != null) {
							commTczhPrice.setInventory(d.getGood_count());
							this.commTczhPriceDao.updateCommTczhPriceInventory(commTczhPrice);
						}
					} else {
						CommInfo commInfo = new CommInfo();
						commInfo.setId(d.getComm_id());
						commInfo = this.commInfoDao.selectEntity(commInfo);
						if (commInfo != null) {
							commInfo.setInventory(d.getGood_count());
							this.commInfoDao.updateCommInfoInventory(commInfo);
						}
					}

					// 更新企业销售量
					if (null != d.getEntp_id() && null != d.getGood_count()) {
						EntpInfo ei = new EntpInfo();
						ei.setId(d.getEntp_id());
						ei.getMap().put("add_sale_count", d.getGood_count());
						this.entpInfoDao.updateEntity(ei);
					}

				}
			}
		}
		return i;
	}

	/**
	 * 更新企业销售额
	 * 
	 * @param OrderInfo
	 * @param addOrLose 增加或者减少 0 增加销售额 1减少销售额
	 * @return
	 */
	public int modifyOrderInfoEntpSaleSumMoney(Integer order_id, Integer addOrLose) {
		int i = 0;

		OrderInfo orderInfoQuery = new OrderInfo();
		orderInfoQuery.setId(order_id);
		orderInfoQuery = this.orderInfoDao.selectEntity(orderInfoQuery);
		if (null != orderInfoQuery && null != orderInfoQuery.getEntp_id()) {
			if (addOrLose == 0) {
				// 增加企业销售量
				EntpInfo ei = new EntpInfo();
				ei.setId(orderInfoQuery.getEntp_id());
				ei.getMap().put("add_sum_sale_money", orderInfoQuery.getOrder_money());
				this.entpInfoDao.updateEntity(ei);
			}
			if (addOrLose == 1) {
				// 减少企业销售量
				EntpInfo ei = new EntpInfo();
				ei.setId(orderInfoQuery.getEntp_id());
				ei.getMap().put("sub_sum_sale_money", orderInfoQuery.getOrder_money());
				this.entpInfoDao.updateEntity(ei);
			}
		}

		return i;
	}

	/**
	 * 仅仅更新企业销量和销售额
	 * 
	 * @param OrderInfo
	 * @param addOrLose 增加或者减少 0 增加销量1减少销量
	 * @return
	 */
	public int modifyOrderInfoEntpSaleCount(Integer order_id, Integer addOrLose) {
		OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
		orderInfoDetails.setOrder_id(order_id);
		List<OrderInfoDetails> orderInfoDetailsList = this.orderInfoDetailsDao.selectEntityList(orderInfoDetails);
		int i = 0;
		if (orderInfoDetailsList != null && orderInfoDetailsList.size() > 0) {
			OrderInfoDetails d = new OrderInfoDetails();
			for (int z = 0; z < orderInfoDetailsList.size(); z++) {
				d = orderInfoDetailsList.get(z);

				if (addOrLose == 0) {
					// 增加企业销售量
					if (null != d.getEntp_id() && null != d.getGood_count()) {
						EntpInfo ei = new EntpInfo();
						ei.setId(d.getEntp_id());
						ei.getMap().put("add_sale_count", d.getGood_count());
						ei.getMap().put("add_sum_sale_money", d.getGood_sum_price());
						this.entpInfoDao.updateEntity(ei);
					}
				}
				if (addOrLose == 1) {
					// 减少企业销售量
					if (null != d.getEntp_id() && null != d.getGood_count()) {
						EntpInfo ei = new EntpInfo();
						ei.setId(d.getEntp_id());
						ei.getMap().put("sub_sale_count", d.getGood_count());
						ei.getMap().put("sub_sum_sale_money", d.getGood_sum_price());
						this.entpInfoDao.updateEntity(ei);
					}
				}
			}
		}
		return i;
	}

	@Override
	public OrderInfo getOrderInfo(OrderInfo t) {
		return this.orderInfoDao.selectEntity(t);
	}

	@Override
	public Integer getOrderInfoCount(OrderInfo t) {
		return this.orderInfoDao.selectEntityCount(t);
	}

	@Override
	public List<OrderInfo> getOrderInfoList(OrderInfo t) {
		return this.orderInfoDao.selectEntityList(t);
	}

	@Override
	public int modifyOrderInfo(OrderInfo t) {

		if (null != t.getMap().get("activity_update_pay_type") && t.getPay_type() != null) {// 线下活动支付
			OrderInfo thisOrder = super.getOrder(t.getId(), orderInfoDao);

			if (t.getPay_type().intValue() == Keys.PayType.PAY_TYPE_0.getIndex()) {
				// t.setMoney_bi(thisOrder.getOrder_money());
				// t.setOrder_money(new BigDecimal(0));
			}
		}

		if (null != t.getMap().get("activity_pay_sus_seng_entp_msg")) {// 发送企业信息，线下活动
			OrderInfo order = super.getOrder(t.getId(), orderInfoDao);
			if (null != order) {
				UserInfo u = new UserInfo();
				u.setOwn_entp_id(order.getEntp_id());
				u.setIs_entp(1);
				u = userInfoDao.selectEntity(u);
				if (null != u) {
					// 给商家发送站内信
					if (null != order.getEntp_id()) {
						String msg = StringUtils.replace(SMS.sms_24, "{0}", order.getEntp_name());
						super.sendMsg(1, u.getId(), "新订单提醒", msg, msgDao, msgReceiverDao, userInfoDao);
					}
				}

			}

		}

		// 插入退款记录
		if (null != t.getMap().get("insert_order_return_info")) {
			insertOrderReturnInfo(t);
		}

		// 更新拼团主订单group_number,子订单取消时
		if (null != t.getMap().get("updata_group_number")
				&& t.getOrder_state() == Keys.OrderState.ORDER_STATE_X10.getIndex()) {
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setId(t.getId());
			orderInfo = this.orderInfoDao.selectEntity(orderInfo);
			OrderInfo updateLeaderOrder = new OrderInfo();
			updateLeaderOrder.setId(orderInfo.getLeader_order_id());
			updateLeaderOrder.getMap().put("updata_group_number", true);
			updateLeaderOrder.setGroup_number(-1);
			this.orderInfoDao.updateEntity(updateLeaderOrder);
		}

		int row = this.orderInfoDao.updateEntity(t);
		if (row == 0) {
			return row;
		}

		// 支付成功
		if (null != t.getMap().get("pay_success_update_link_table")) {
			orderSuccessUpdateLinkUserInfo(t.getId());
		}
		// 消费成功 以及确认收货成功相关操作
		if (null != t.getMap().get("xiaofei_success_update_link_table")) { // 0
			// 增加企业销售额
			modifyOrderInfoEntpSaleSumMoney(t.getId(), 0);
		}

		// 更新库存以及销量
		if (null != t.getMap().get("update_comm_info_saleCountAndInventory")) {
			super.modifyOrderInfoCommSaleCountAndInventory(t.getId(), 0, orderInfoDetailsDao, commTczhPriceDao,
					commInfoDao, entpInfoDao);
		}
		if (null != t.getMap().get("update_yhq")) {
			update_yhq(t);

		}
		// 取消京东订单
		if (null != t.getMap().get("cancel_jd_order_update_info")) {
			String cancelJdOrder = super.syncCancelJdOrder(t.getId(), orderInfoDao);
			if (cancelJdOrder.equals("0")) {
				row = -2;
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return row;
			}
		}

		// 退款相关操作
		if (null != t.getMap().get("tuikuan_update_link_table")) {
			// 退款申请成功，修改相关状态
			super.modifyTuiKuanAuditState(t, null, orderReturnInfoDao, baseAuditRecordDao);
			super.modifyOrderInfoForTk(t.getId(), orderInfoDao, userInfoDao, baseDataDao, userBiRecordDao);
			super.modifyOrderInfoCommSaleCountAndInventory(t.getId(), 1, orderInfoDetailsDao, commTczhPriceDao,
					commInfoDao, entpInfoDao);
		}
		// 添加物流信息
		if (null != t.getMap().get("add_wlOrderInfo_info")) {
			WlOrderInfo wlOrderInfo = (WlOrderInfo) t.getMap().get("add_wlOrderInfo_info");
			this.wlOrderInfoDao.insertEntity(wlOrderInfo);
		}
		// 更新物流信息
		if (null != t.getMap().get("update_wlOrderInfo_info")) {
			WlOrderInfo wlOrderInfo = (WlOrderInfo) t.getMap().get("update_wlOrderInfo_info");
			this.wlOrderInfoDao.updateEntity(wlOrderInfo);
		}
		// 扫码支付
		if (null != t.getMap().get("user_scan_code_order")) {
			userScanCodeOrder(t.getId());
		}
		if (null != t.getMap().get("delect_aid_money")) {
			UserBiRecord userBiRecord = new UserBiRecord();
			userBiRecord.setOrder_id(t.getId());
			userBiRecord.setBi_type(Keys.BiType.BI_TYPE_500.getIndex());
			userBiRecord.setBi_get_type(Keys.BiGetType.BI_GET_TYPE_500.getIndex());
			userBiRecord.setBi_chu_or_ru(Keys.BI_CHU_OR_RU.BASE_DATA_ID_1.getIndex());
			List<UserBiRecord> selectEntityList = userBiRecordDao.selectEntityList(userBiRecord);
			for (UserBiRecord temp : selectEntityList) {// 找出所有得到扶贫金的人
				super.insertUserBiRecord(temp.getAdd_user_id(), null, -1, temp.getOrder_id(), temp.getLink_id(),
						temp.getBi_no(), Keys.BiType.BI_TYPE_500.getIndex(), Keys.BiGetType.BI_GET_TYPE_3002.getIndex(),
						null, userInfoDao, userBiRecordDao);
				// 更新会员扶贫金
				UserInfo update_user = new UserInfo();
				update_user.setId(temp.getAdd_user_id());
				update_user.getMap().put("sub_bi_aid", temp.getBi_no());
				userInfoDao.updateEntity(update_user);
			}
		}

		// 线下扫码活动支付成功增加商家货款币
		if (null != t.getMap().get("update_entp_huokuan_bi")) {
			OrderInfo orderInfo = super.getOrder(t.getId(), orderInfoDao);

			OrderInfo updateOrder = new OrderInfo();
			updateOrder.setId(orderInfo.getId());
			BigDecimal entp_huokuan_bi = orderInfo.getNo_dis_money().subtract(orderInfo.getXiadan_user_sum());

			if (entp_huokuan_bi.compareTo(new BigDecimal(0)) == -1) {
				entp_huokuan_bi = new BigDecimal(0);
			}
			updateOrder.setEntp_huokuan_bi(entp_huokuan_bi);
			orderInfoDao.updateEntity(updateOrder);

			// 待返商家货款
			EntpInfo ei = new EntpInfo();
			ei.setId(orderInfo.getEntp_id());
			ei.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
			ei = entpInfoDao.selectEntity(ei);
			if (null != ei) {// 查询商家
				insertUserBiHuokuanRecord(ei.getAdd_user_id(), Keys.BiGetType.BI_GET_TYPE_4100.getIndex(),
						orderInfo.getId(), orderInfo.getId(), entp_huokuan_bi, userInfoDao, userBiRecordDao);
			}
		}

		// 订单状态更新回来
		OrderInfo updateOpt = new OrderInfo();
		updateOpt.setId(t.getId());
		updateOpt.setIs_opt(0);
		this.orderInfoDao.updateEntity(updateOpt);
		return row;
	}

	public void update_yhq(OrderInfo t) {
		OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
		orderInfoDetails.setOrder_id(t.getId());
		List<OrderInfoDetails> orderInfoDetailsList = orderInfoDetailsDao.selectEntityList(orderInfoDetails);
		if (orderInfoDetailsList != null && orderInfoDetailsList.size() > 0) {
			OrderInfoDetails d = new OrderInfoDetails();
			for (OrderInfoDetails item : orderInfoDetailsList) {
				if (null != item.getYhq_son_id()) {
					YhqInfoSon yhqson = new YhqInfoSon();
					yhqson.setId(item.getYhq_son_id());
					yhqson = this.yhqInfoSonDao.selectEntity(yhqson);
					if (null != yhqson) {
						YhqInfo yhq = new YhqInfo();
						yhq.setId(yhqson.getLink_id());
						yhq.setIs_del(0);
						yhq = this.yhqInfoDao.selectEntity(yhq);
						if (null != yhq) {
							// 优惠券主表还在，进行修改
							YhqInfoSon update = new YhqInfoSon();
							update.setId(yhqson.getId());
							update.setYhq_state(Keys.YhqState.YHQ_STATE_10.getIndex());
							update.setIs_used(0);
							this.yhqInfoSonDao.updateEntity(update);
						}
					}

				}

			}
		}
	}

	/**
	 * @desc 插入退款记录
	 * @param t
	 */
	public void insertOrderReturnInfo(OrderInfo t) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(t.getId());
		orderInfo = this.orderInfoDao.selectEntity(orderInfo);
		if (null != orderInfo && orderInfo.getOrder_state() != Keys.OrderState.ORDER_STATE_15.getIndex()) {// 防止出现多次提交退款申请
			UserInfo userInfo = super.getUserInfo(orderInfo.getAdd_user_id(), userInfoDao);
			EntpInfo entpInfo = new EntpInfo();
			entpInfo.setId(orderInfo.getEntp_id());
			entpInfo = this.entpInfoDao.selectEntity(entpInfo);

			BigDecimal price = new BigDecimal(0);
			OrderInfoDetails ods = new OrderInfoDetails();
			ods.setOrder_id(orderInfo.getId());
			List<OrderInfoDetails> list = this.orderInfoDetailsDao.selectEntityList(ods);
			if (null != list && list.size() > 0) {
				for (OrderInfoDetails item : list) {
					price = price.add(item.getActual_money().add(item.getMatflow_price()));
				}
			}

			// ods = list.get(0);
			OrderReturnInfo returnInfo = new OrderReturnInfo();
			returnInfo.setReturn_no(getReturnNo());
			returnInfo.setOrder_id(orderInfo.getId());
			returnInfo.setAudit_state(Keys.audit_state.audit_state_0.getIndex());
			returnInfo.setOrder_state(orderInfo.getOrder_state());
			returnInfo.setUser_id(userInfo.getId());
			returnInfo.setUser_name(userInfo.getUser_name());
			returnInfo.setReturn_type(Keys.return_why.return_why_11630.getIndex());
			returnInfo.setExpect_return_way(Keys.return_type.return_type_4_tuikuan.getIndex());
			returnInfo.setPrice(price);
			returnInfo.setNum(orderInfo.getOrder_num());
			returnInfo.setTk_status(0);
			returnInfo.setIs_del(0);
			returnInfo.setAdd_date(new Date());
			returnInfo.setEntp_id(orderInfo.getEntp_id());
			// returnInfo.setComm_name(ods.getComm_name());
			int id = orderReturnInfoDao.insertEntity(returnInfo);

			if (id > 0) {
				insertOrderReturnAuditRecord(orderInfo, id, baseAuditRecordDao);
				SimpleDateFormat sdFormat_ymdhms_china = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
				// 1、创蓝短信
				// String message = StringUtils.replace(SMS.sms_tk, "{0}", userInfo.getUser_name());
				// message = StringUtils.replace(message, "{1}", orderInfo.getTrade_index());
				// message = StringUtils.replace(message, "{2}", sdFormat_ymdhms_china.format(new Date()));
				// SmsUtils.sendMessage(message, userInfo.getMobile());
				// 2、阿里云短信

				// 提醒用户
				StringBuffer message = new StringBuffer("{\"user\":\"" + userInfo.getUser_name() + "\",");
				message.append("\"order\":\"" + orderInfo.getTrade_index() + "\",");
				message.append("\"time\":\"" + sdFormat_ymdhms_china.format(new Date()) + "\"");
				message.append("}");
				DySmsUtils.sendMessage(message.toString(), userInfo.getMobile(), SmsTemplate.sms_tk);

				// 提醒商家
				StringBuffer message1 = new StringBuffer("{\"user\":\"" + userInfo.getUser_name() + "\",");
				message1.append("\"entp\":\"" + entpInfo.getEntp_name() + "\",");
				message1.append("\"order\":\"" + orderInfo.getTrade_index() + "\",");
				message1.append("\"time\":\"" + sdFormat_ymdhms_china.format(new Date()) + "\"");
				message1.append("}");
				DySmsUtils.sendMessage(message1.toString(), entpInfo.getEntp_tel(), SmsTemplate.sms_tk_01);

				// 提醒系统管理员
				SysSetting sysSetting = new SysSetting();
				sysSetting.setTitle("adminMobile");
				sysSetting = this.sysSettingDao.selectEntity(sysSetting);
				DySmsUtils.sendMessage(message1.toString(), sysSetting.getContent(), SmsTemplate.sms_tk_02);
			}
		}
	}

	@Override
	public int removeOrderInfo(OrderInfo t) {
		return this.orderInfoDao.deleteEntity(t);
	}

	@Override
	public List<OrderInfo> getOrderInfoPaginatedList(OrderInfo t) {
		return this.orderInfoDao.selectEntityPaginatedList(t);
	}

	@Override
	public BigDecimal genOrderInfoTradeIndex(OrderInfo t) {
		return this.orderInfoDao.generateOrderInfoTradeIndex(t);
	}

	@Override
	public List<OrderInfo> getOrderInfoWithRealNamePaginatedList(OrderInfo t) {
		return this.orderInfoDao.selectOrderInfoWithRealNamePaginatedList(t);
	}

	@Override
	public List<OrderInfo> getOrderInfoWithRealNameList(OrderInfo t) {
		return this.orderInfoDao.selectOrderInfoWithRealNameList(t);
	}

	@Override
	public Integer getOrderInfoStatisticaCount(OrderInfo t) {
		return this.orderInfoDao.selectOrderInfoStatisticaCount(t);
	}

	@Override
	public List<OrderInfo> getOrderInfoStatisticaPaginatedList(OrderInfo t) {
		return this.orderInfoDao.selectOrderInfoStatisticaPaginatedList(t);
	}

	@Override
	public OrderInfo getOrderInfoStatisticaSum(OrderInfo t) {
		return this.orderInfoDao.selectOrderInfoStatisticaSum(t);
	}

	@Override
	public List<OrderInfo> getOrderInfoStatisticaListSum(OrderInfo t) {
		return this.orderInfoDao.selectOrderInfoStatisticaListSum(t);
	}

	@Override
	public List<OrderInfo> getRangPriceSum(OrderInfo t) {
		return this.orderInfoDao.selectRangPriceSum(t);
	}

	@Override
	public List<OrderInfo> getVillageInviteUserSum(OrderInfo t) {
		return this.orderInfoDao.selectVillageInviteUserSum(t);
	}

	@Override
	public OrderInfo getOrderInfoByDetailId(Map map) {
		return this.orderInfoDao.selectOrderInfoByDetailId(map);
	}

	@Override
	public void modifyOrderInfoForCancel(OrderInfo order) {

		// 取消订单
		OrderInfo temp = new OrderInfo();
		temp.setId(order.getId());
		temp.setOrder_state(Keys.OrderState.ORDER_STATE_X10.getIndex());// 已取消
		this.orderInfoDao.updateEntity(temp);
		if (order.getOrder_type().intValue() == Keys.OrderType.ORDER_TYPE_100.getIndex()
				&& order.getIs_leader().intValue() == 0) {// 取消拼团子订单，要修改其主订单的group_number数量为group_number-1
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setId(order.getLeader_order_id());
			orderInfo.getMap().put("updata_group_number", true);
			orderInfo.setGroup_number(-1);
			this.orderInfoDao.updateEntity(orderInfo);
		}
		super.modifyOrderInfoCommSaleCountAndInventory(order.getId(), 1, orderInfoDetailsDao, commTczhPriceDao,
				commInfoDao, entpInfoDao);
	}

	public void orderSuccessUpdateLinkUserInfo(Integer order_id) {

		OrderInfo orderInfoQuery = new OrderInfo();
		orderInfoQuery.setId(order_id);
		orderInfoQuery = this.orderInfoDao.selectEntity(orderInfoQuery);
		if (null != orderInfoQuery) {

			if (orderInfoQuery.getOrder_type().intValue() == Keys.OrderType.ORDER_TYPE_7.getIndex()
					|| orderInfoQuery.getOrder_type().intValue() == Keys.OrderType.ORDER_TYPE_10.getIndex()
					|| orderInfoQuery.getOrder_type().intValue() == Keys.OrderType.ORDER_TYPE_30.getIndex()
					|| orderInfoQuery.getOrder_type().intValue() == Keys.OrderType.ORDER_TYPE_70.getIndex()
					|| orderInfoQuery.getOrder_type().intValue() == Keys.OrderType.ORDER_TYPE_80.getIndex()
					|| orderInfoQuery.getOrder_type().intValue() == Keys.OrderType.ORDER_TYPE_90.getIndex()
					|| orderInfoQuery.getOrder_type().intValue() == Keys.OrderType.ORDER_TYPE_100.getIndex()) {
				super.reckonRebateAndAid(order_id, orderInfoDao, orderInfoDetailsDao, userInfoDao, userScoreRecordDao,
						userRelationParDao, userBiRecordDao, entpInfoDao, serviceCenterInfoDao, new Date(), baseDataDao,
						userJifenRecordDao, tongjiDao, commInfoDao, commInfoPoorsDao, villageInfoDao);
			}

			if (orderInfoQuery.getMoney_bi().compareTo(new BigDecimal(0)) >= 0) {// 包含余额支付
				UserInfo userInfo = new UserInfo();
				userInfo.setId(orderInfoQuery.getAdd_user_id());
				userInfo = this.userInfoDao.selectEntity(userInfo);
				if (null != userInfo) {
					// 如果有福利金，则减去
					BigDecimal sub_bi_welfare = new BigDecimal(0);

					if (null != userInfo.getBi_welfare() && userInfo.getBi_welfare().compareTo(new BigDecimal(0)) > 0) {// 用户福利金大于0
						if (userInfo.getBi_welfare().compareTo(orderInfoQuery.getMoney_bi()) >= 0) {
							// 福利金金额大于等于订单金额
							sub_bi_welfare = orderInfoQuery.getMoney_bi();
						} else {
							sub_bi_welfare = userInfo.getBi_welfare();
						}
					}

					BigDecimal rmb_to_dianzibi = super.BiToBi2(orderInfoQuery.getMoney_bi(),
							Keys.BASE_DATA_ID.BASE_DATA_ID_904.getIndex(), baseDataDao);

					super.insertUserWelfareBuyBiRecord(userInfo.getId(), null, -1, orderInfoQuery.getId(),
							orderInfoQuery.getId(), rmb_to_dianzibi, Keys.BiType.BI_TYPE_100.getIndex(),
							Keys.BiGetType.BI_OUT_TYPE_10.getIndex(), null, userInfoDao, userBiRecordDao,
							sub_bi_welfare);

					super.updateUserInfoBi(userInfo.getId(), rmb_to_dianzibi, "sub_bi_dianzi", userInfoDao);// 扣除余额

					super.updateUserInfoBi(userInfo.getId(), sub_bi_welfare, "sub_bi_welfare", userInfoDao);// 扣除福利金

					// 讲福利金抵扣金额存入order_info
					OrderInfo oInfo = new OrderInfo();
					oInfo.setId(order_id);
					oInfo.setWelfare_pay_money(sub_bi_welfare);
					this.orderInfoDao.updateEntity(oInfo);

					if (orderInfoQuery.getCard_pay_money().compareTo(new BigDecimal(0)) > 0) {// 包含福利卡抵扣

						// 插入福利卡消费记录
						super.insertUserBiRecord(orderInfoQuery.getAdd_user_id(), null, -1, orderInfoQuery.getId(),
								orderInfoQuery.getCard_id(), orderInfoQuery.getCard_pay_money(),
								Keys.BiType.BI_TYPE_800.getIndex(), Keys.BiGetType.BI_OUT_TYPE_801.getIndex(), null,
								userInfoDao, userBiRecordDao);

						// 扣除福利卡金额
						CardInfo card = new CardInfo();
						card.setId(orderInfoQuery.getCard_id());
						// 若福利卡全部用完，更新状态为已全部使用
						CardInfo cardInfo = this.cardInfoDao.selectEntity(card);
						if (cardInfo.getCard_cash().compareTo(orderInfoQuery.getCard_pay_money()) == 0) {
							card.setCard_state(Keys.CARD_STATE.CARD_STATE_2.getIndex());
						}
						card.getMap().put("sub_card_cash", orderInfoQuery.getCard_pay_money());
						this.cardInfoDao.updateEntity(card);
					}

				}
			}
			if (orderInfoQuery.getOrder_type().intValue() == Keys.OrderType.ORDER_TYPE_30.getIndex()) {
				try {
					super.syncConfirmJdOrder(order_id, orderInfoDao);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	}

	@Override
	public int modifyOrderInfoForChongZhi(OrderInfo t) {

		int num = this.orderInfoDao.updateEntity(t);
		if (num > 0) {
			if (null != t.getMap().get("chongZhi_successs_update_link_tables")) {
				Integer user_id = (Integer) t.getMap().get("user_id");

				BigDecimal rmb_to_dianzibi = super.BiToBi2(t.getOrder_money(),
						Keys.BASE_DATA_ID.BASE_DATA_ID_904.getIndex(), baseDataDao);

				super.insertUserBiRecord(user_id, null, 1, t.getId(), t.getId(), rmb_to_dianzibi,
						Keys.BiType.BI_TYPE_100.getIndex(), Keys.BiGetType.BI_GET_TYPE_140.getIndex(), null,
						userInfoDao, userBiRecordDao);

				super.updateUserInfoBi(user_id, rmb_to_dianzibi, "add_bi_dianzi", userInfoDao);

			}
		}
		return num;
	}

	// 扫描店铺二维码订单 这是余额和货款比支付
	public int mPayScan(Integer order_id) {
		OrderInfo orderInfoQuery = new OrderInfo();
		orderInfoQuery.setId(order_id);
		orderInfoQuery = this.orderInfoDao.selectEntity(orderInfoQuery);
		if (null != orderInfoQuery) {

			// 这个需要增加企业的销量
			this.modifyOrderInfoEntpSaleCount(order_id, 0);

			UserInfo userInfo = new UserInfo();
			userInfo.setId(orderInfoQuery.getAdd_user_id());
			userInfo = this.userInfoDao.selectEntity(userInfo);
			if (null != userInfo) {
				if (orderInfoQuery.getPay_type().intValue() == Keys.PayType.PAY_TYPE_0.getIndex()) {// 余额充值

					BigDecimal rmb_to_dianzibi = super.BiToBi2(orderInfoQuery.getOrder_money(),
							Keys.BASE_DATA_ID.BASE_DATA_ID_904.getIndex(), baseDataDao);

					super.insertUserBiRecord(userInfo.getId(), null, -1, order_id, order_id, rmb_to_dianzibi,
							Keys.BiType.BI_TYPE_100.getIndex(), Keys.BiGetType.BI_OUT_TYPE_150.getIndex(), null,
							userInfoDao, userBiRecordDao);

					super.updateUserInfoBi(userInfo.getId(), rmb_to_dianzibi, "sub_bi_dianzi", userInfoDao);

				}

				OrderInfo oi = new OrderInfo();
				oi.setId(order_id);
				oi.setOrder_state(Keys.OrderState.ORDER_STATE_50.getIndex());
				oi.setPay_date(new Date());
				oi.setQrsh_date(new Date());

				if (orderInfoQuery.getOrder_state().intValue() != Keys.OrderState.ORDER_STATE_50.getIndex()) {
					int row = this.orderInfoDao.updateEntity(oi);

					userScanCodeOrder(orderInfoQuery.getId());
					if (row > 0) {
						// 给商家发送站内信
						if (null != orderInfoQuery.getEntp_id()) {
							String msg = StringUtils.replace(SMS.sms_09, "{0}", orderInfoQuery.getEntp_name());
							super.sendMsg(1, userInfo.getId(), "新订单提醒", msg, msgDao, msgReceiverDao, userInfoDao);
						}
					}
				}
			}
		}
		return new Integer(1);
	}

	// 扫码支付订单
	public void userScanCodeOrder(Integer order_id) {
		OrderInfo orderInfoQuery = new OrderInfo();
		orderInfoQuery.setId(order_id);
		orderInfoQuery = this.orderInfoDao.selectEntity(orderInfoQuery);
		if (null != orderInfoQuery) {
			// 这个需要增加企业的销量
			this.modifyOrderInfoEntpSaleCount(order_id, 0);
		}
	}

	@Override
	public int getselectCheckCount(OrderInfo t) {
		return this.orderInfoDao.getselectCheckCount(t);
	}

	@Override
	public List<OrderInfo> getselectCheckList(OrderInfo t) {
		return this.orderInfoDao.getselectCheckList(t);
	}

	@Override
	public Integer getMembershipFeeCount(OrderInfo t) {
		return this.orderInfoDao.selectMembershipFeeCount(t);
	}

	@Override
	public List<OrderInfo> getMembershipFeePaginatedList(OrderInfo t) {
		return this.orderInfoDao.selectMembershipFeePaginatedList(t);
	}

	/**
	 * 修改订单金额，订单详情金额
	 */
	@Override
	public boolean modifyOrderInfoAndDetails(List<OrderInfoDetails> orderInfoDetailsList,
			List<OrderInfo> orderInfoList) {
		boolean updateflag = true;
		for (OrderInfoDetails oid : orderInfoDetailsList) {
			this.orderInfoDetailsDao.updateEntity(oid);
		}

		for (OrderInfo oi2 : orderInfoList) {
			OrderInfoDetails orderInfoDetails_ = new OrderInfoDetails();
			orderInfoDetails_.setOrder_id(oi2.getId());
			List<OrderInfoDetails> orderInfoDetailsList2 = this.orderInfoDetailsDao.selectEntityList(orderInfoDetails_);
			// 重新计算订单价格
			BigDecimal order_money = new BigDecimal("0");
			for (OrderInfoDetails orderInfoDetails : orderInfoDetailsList2) {
				order_money = order_money
						.add(orderInfoDetails.getGood_sum_price().add(orderInfoDetails.getMatflow_price()));
			}
			oi2.setOrder_money(order_money);
			oi2.setReal_pay_money(oi2.getOrder_money());
			oi2.setNo_dis_money(oi2.getOrder_money());
			oi2.setMoney_bi(new BigDecimal(0));
			oi2.getMap().put("opt_order_state", Keys.OrderState.ORDER_STATE_0.getIndex());
			int count = this.modifyOrderInfo(oi2);

			if (count == 0) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 订单已支付，修改失败，回滚
				updateflag = false;
				return updateflag;
			}
		}
		return updateflag;
	}

	@Override
	public List<OrderInfo> getGroupLeaderOrderInfo(OrderInfo t) {
		return this.orderInfoDao.selectGroupLeaderOrderInfo(t);
	}

	@Override
	public BigDecimal getOrderInfoListNewSumMoney(OrderInfo t) {
		return this.orderInfoDao.selectOrderInfoListNewSumMoney(t);
	}

	@Override
	public List<OrderInfo> getOrderInfoListNew(OrderInfo t) {
		return this.orderInfoDao.selectOrderInfoListNew(t);
	}

	/**
	 * 创建未支付的会员升级订单
	 * 
	 * @param OrderInfo
	 * @return
	 */
	@Override
	public Integer createUserUpLevelOrderInfo(OrderInfo t) {
		UserInfo userInfo = new UserInfo();
		userInfo.setId(t.getAdd_user_id());
		userInfo = this.userInfoDao.selectEntity(userInfo);
		if (null == userInfo) {
			return 0;
		}
		int order_id = createOrderInfoPublic(t.getOrder_money(), userInfo, t.getTrade_index(),
				Keys.OrderType.ORDER_TYPE_20.getName(), 0, Keys.OrderType.ORDER_TYPE_20.getIndex(), orderInfoDao,
				orderInfoDetailsDao, null, Keys.OrderState.ORDER_STATE_0.getIndex(),
				Keys.PayPlatform.APP_ANDROID.getIndex());

		return order_id;
	}

	@Override
	public Integer getBigShowOrderInfoCount(OrderInfo t) {
		return this.orderInfoDao.selectBigShowOrderInfoCount(t);

	}

}
