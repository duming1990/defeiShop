package com.ebiz.webapp.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.BaseDataDao;
import com.ebiz.webapp.dao.CommInfoDao;
import com.ebiz.webapp.dao.CommInfoPoorsDao;
import com.ebiz.webapp.dao.EntpInfoDao;
import com.ebiz.webapp.dao.OrderInfoDao;
import com.ebiz.webapp.dao.OrderInfoDetailsDao;
import com.ebiz.webapp.dao.PoorCuoShiDao;
import com.ebiz.webapp.dao.ServiceCenterInfoDao;
import com.ebiz.webapp.dao.TongjiDao;
import com.ebiz.webapp.dao.UserBiRecordDao;
import com.ebiz.webapp.dao.UserInfoDao;
import com.ebiz.webapp.dao.UserJifenRecordDao;
import com.ebiz.webapp.dao.UserRelationParDao;
import com.ebiz.webapp.dao.UserScoreRecordDao;
import com.ebiz.webapp.dao.VillageInfoDao;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommInfoPoors;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.PoorCuoShi;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.UserBiRecord;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.UserRelationPar;
import com.ebiz.webapp.domain.VillageInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.WeiXinSendMessageUtils;

/**
 * @author 九个挑夫基础公共方法类
 * @version 2015-12-01
 */
@Service
public class BaseImplForTiaoFu extends BaseImpl {

	/**
	 * 计算返现奖励和扶贫金，转入用户待返金额中
	 * 
	 * @param order_id 订单ID
	 * @param orderInfoDao
	 * @param orderInfoDetailsDao
	 * @param userInfoDao
	 * @param userScoreRecordDao
	 * @param userRelationParDao
	 * @param userBiRecordDao
	 * @param entpInfoDao
	 * @param serviceCenterInfoDao
	 * @param day
	 * @param baseDataDao
	 * @param userJifenRecordDao
	 * @param tongjiDao
	 * @param commInfoDao
	 * @param commInfoPoorsDao
	 * @param villageInfoDao
	 */
	public void reckonRebateAndAid(Integer order_id, OrderInfoDao orderInfoDao,
			OrderInfoDetailsDao orderInfoDetailsDao, UserInfoDao userInfoDao, UserScoreRecordDao userScoreRecordDao,
			UserRelationParDao userRelationParDao, UserBiRecordDao userBiRecordDao, EntpInfoDao entpInfoDao,
			ServiceCenterInfoDao serviceCenterInfoDao, Date day, BaseDataDao baseDataDao,
			UserJifenRecordDao userJifenRecordDao, TongjiDao tongjiDao, CommInfoDao commInfoDao,
			CommInfoPoorsDao commInfoPoorsDao, VillageInfoDao villageInfoDao) {
		// TODO Date day 在完成测试后删除
		// 1、计算商家货款并转入待转账户
		// 2、计算会员消费奖励
		// 3、计算直接邀请奖励
		// 4、计算驿站奖励
		// 5、计算县域合伙人奖励
		// 6、计算扶贫金
		OrderInfo orderInfo = new OrderInfo();
		// orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_10.getIndex());// 订单支付成功
		orderInfo.setId(order_id);
		orderInfo = orderInfoDao.selectEntity(orderInfo);
		if (null != orderInfo) {
			// 将消费金额增加到会员累计消费额度
			super.recordTongjiWithTotal(orderInfo.getAdd_user_id(), orderInfo.getOrder_money(), tongjiDao);

			// 更新会员累计消费金额
			UserInfo u_leiji = new UserInfo();
			u_leiji.setId(orderInfo.getAdd_user_id());
			u_leiji.getMap().put("add_leiji_money_user", orderInfo.getOrder_money());
			userInfoDao.updateEntity(u_leiji);

			OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
			orderInfoDetails.setOrder_id(order_id);
			orderInfoDetails.setIs_tuihuo(0);// 未退货
			List<OrderInfoDetails> orderInfoDetailsList = orderInfoDetailsDao.selectEntityList(orderInfoDetails);

			if (Keys.OrderType.ORDER_TYPE_7.getIndex() == orderInfo.getOrder_type().intValue()) {
				// 村民商品流程
				// 计算并更新订单货款、返现金额、扶贫金额
				OrderInfo oi = new OrderInfo();
				oi.setId(orderInfo.getId());
				BigDecimal entp_huokuan_bi = orderInfo.getNo_dis_money();
				if (entp_huokuan_bi.compareTo(new BigDecimal(0)) == -1) {
					entp_huokuan_bi = new BigDecimal(0);
				}
				oi.setEntp_huokuan_bi(entp_huokuan_bi);
				orderInfoDao.updateEntity(oi);

				if (null != orderInfo.getPublish_user_id()) {
					// 待返个人商品销售款
					insertUserBiDianziLock(orderInfo.getPublish_user_id(), Keys.BiGetType.BI_GET_TYPE_2001.getIndex(),
							orderInfo.getId(), orderInfo.getId(), entp_huokuan_bi, userInfoDao, userBiRecordDao);
				}
			} else {
				// 普通商品走正常返利流程
				BigDecimal sum_rebate_money = new BigDecimal(0);// 订单总返利金额
				BigDecimal sum_aid_money = new BigDecimal(0);// 订单总扶贫金额
				if (null != orderInfoDetailsList && orderInfoDetailsList.size() > 0) {
					for (OrderInfoDetails ods : orderInfoDetailsList) {
						CommInfo commInfo = new CommInfo();
						commInfo.setId(ods.getComm_id());
						commInfo = commInfoDao.selectEntity(commInfo);

						BigDecimal rebate_money = new BigDecimal(0);// 订单明细的返利金额
						BigDecimal aid_money = new BigDecimal(0);// 订单明细的扶贫金额
						if (null != commInfo && 1 == commInfo.getIs_rebate()) {// 返利商品
							rebate_money = ods.getGood_sum_price().multiply(commInfo.getRebate_scale())
									.divide(new BigDecimal(100));// 商品返利金额
						}
						if (null != commInfo && 1 == commInfo.getIs_aid()) {
							aid_money = ods.getGood_sum_price().multiply(commInfo.getAid_scale())
									.divide(new BigDecimal(100));// 商品扶贫金额
						}
						if (rebate_money.compareTo(new BigDecimal(0)) == -1) {
							rebate_money = new BigDecimal(0);
						}
						if (aid_money.compareTo(new BigDecimal(0)) == -1) {
							aid_money = new BigDecimal(0);
						}

						// 更新订单明细
						OrderInfoDetails od = new OrderInfoDetails();
						od.setId(ods.getId());
						od.setSum_rebate_money(rebate_money);
						od.setSum_aid_money(aid_money);
						orderInfoDetailsDao.updateEntity(od);

						sum_rebate_money = sum_rebate_money.add(rebate_money);// 订单明细的返利金额累计到订单总返利金额
						sum_aid_money = sum_aid_money.add(aid_money);// 订单明细的扶贫金额累计到订单明细的扶贫金额

						// TODO 更新商品信息
						CommInfo ci = new CommInfo();
						ci.setId(ods.getComm_id());
						ci.getMap().put("add_sum_rebate_money", rebate_money);
						ci.getMap().put("add_sum_aid_money", aid_money);
						commInfoDao.updateEntity(ci);

						if (aid_money.compareTo(new BigDecimal(0)) == 1) {// 扶贫金额大于0
							// 一、计算待发扶贫金额
							if (null != ods.getPoor_id()) {// 从贫困户页面加入购物车，扶贫金额全部发放给该贫困户
								insertUserBiAidLock(ods.getPoor_id(), Keys.BiGetType.BI_GET_TYPE_500.getIndex(),
										orderInfo.getId(), ods.getId(), aid_money, userInfoDao, userBiRecordDao);
							} else {// 由平台商城加入购物车，扶贫金额平分
								CommInfoPoors commInfoPoors = new CommInfoPoors();
								commInfoPoors.setComm_id(ods.getComm_id());
								List<CommInfoPoors> commInfoPoorsList = commInfoPoorsDao
										.selectEntityList(commInfoPoors);
								if (null != commInfoPoorsList && commInfoPoorsList.size() > 0) {
									for (CommInfoPoors p : commInfoPoorsList) {
										insertUserBiAidLock(p.getPoor_id(), Keys.BiGetType.BI_GET_TYPE_500.getIndex(),
												orderInfo.getId(), ods.getId(), aid_money.divide(new BigDecimal(
														commInfoPoorsList.size()), 2, BigDecimal.ROUND_DOWN),
												userInfoDao, userBiRecordDao);
									}
								}
							}
						}

					}
				}

				// 计算并更新订单货款、返现金额、扶贫金额
				OrderInfo oi = new OrderInfo();
				oi.setId(orderInfo.getId());
				BigDecimal entp_huokuan_bi = orderInfo.getNo_dis_money().subtract(sum_rebate_money)
						.subtract(sum_aid_money);
				if (entp_huokuan_bi.compareTo(new BigDecimal(0)) == -1) {
					entp_huokuan_bi = new BigDecimal(0);
				}
				oi.setEntp_huokuan_bi(entp_huokuan_bi);
				oi.setXiadan_user_sum(sum_rebate_money.add(sum_aid_money));// 返现金额+扶贫金]\
				oi.setRes_balance(sum_rebate_money);// 返现预留金额

				// TODO
				// 除了线下订单才会有待返
				if (orderInfo.getOrder_type().intValue() != Keys.OrderType.ORDER_TYPE_90.getIndex()) {
					// 待返商家货款
					EntpInfo ei = new EntpInfo();
					ei.setId(orderInfo.getEntp_id());
					ei.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
					ei = entpInfoDao.selectEntity(ei);
					if (null != ei) {// 查询商家
						insertUserBiHuokuanLock(ei.getAdd_user_id(), Keys.BiGetType.BI_GET_TYPE_150.getIndex(),
								orderInfo.getId(), orderInfo.getId(), entp_huokuan_bi, userInfoDao, userBiRecordDao);
					}
				}

				// 二、待返奖励发放规则
				// 1、消费返现奖励（发放给消费者本人）
				// 2018-3-13改为会员购物立减，不再订单收货后7天返现
				// 获取消费返现奖励比例
				// BaseData baseData1001 =
				// super.getBaseData(Keys.REBATE_BASE_DATA_ID.REBATE_BASE_DATA_ID_1001.getIndex(),
				// baseDataDao);
				// insertUserBiDianziLock(
				// orderInfo.getAdd_user_id(),
				// Keys.BiGetType.BI_GET_TYPE_1001.getIndex(),
				// orderInfo.getId(),
				// orderInfo.getId(),
				// sum_rebate_money.multiply(new BigDecimal(baseData1001.getPre_number2())).divide(
				// new BigDecimal(baseData1001.getPre_number()), 2, BigDecimal.ROUND_DOWN), userInfoDao,
				// userBiRecordDao);

				BigDecimal reward_money = new BigDecimal("0");
				// 2、直邀返现奖励（发放给消费者的直接邀请人）
				BaseData baseData1002 = super.getBaseData(Keys.REBATE_BASE_DATA_ID.REBATE_BASE_DATA_ID_1002.getIndex(),
						baseDataDao);
				BaseData baseData1003 = super.getBaseData(Keys.REBATE_BASE_DATA_ID.REBATE_BASE_DATA_ID_1003.getIndex(),
						baseDataDao);
				UserInfo ui = new UserInfo();
				ui.setId(orderInfo.getAdd_user_id());
				ui = userInfoDao.selectEntity(ui);

				if (null != ui) {

					if (1 == ui.getIs_village()) {// 消费者是驿站
						UserInfo v_ui = new UserInfo();
						v_ui.setIs_village(1);// 驿站用户
						v_ui.setUser_type(Keys.UserType.USER_TYPE_4.getIndex());
						v_ui.setOwn_village_id(ui.getOwn_village_id());
						v_ui.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());// 未删除
						v_ui.setIs_active(1);// 已激活
						v_ui = userInfoDao.selectEntity(v_ui);
						if (null != v_ui) {
							// 2、直邀返现奖励（发放给驿站）
							insertUserBiDianziLock(
									v_ui.getId(),
									Keys.BiGetType.BI_GET_TYPE_1002.getIndex(),
									orderInfo.getId(),
									orderInfo.getId(),
									sum_rebate_money.multiply(new BigDecimal(baseData1002.getPre_number2())).divide(
											new BigDecimal(baseData1002.getPre_number()), 2, BigDecimal.ROUND_DOWN),
									userInfoDao, userBiRecordDao);
							reward_money = reward_money.add(sum_rebate_money.multiply(
									new BigDecimal(baseData1002.getPre_number2())).divide(
									new BigDecimal(baseData1002.getPre_number()), 2, BigDecimal.ROUND_DOWN));
							// 3、驿站返现奖励
							insertUserBiDianziLock(
									v_ui.getId(),
									Keys.BiGetType.BI_GET_TYPE_1003.getIndex(),
									orderInfo.getId(),
									orderInfo.getId(),
									sum_rebate_money.multiply(new BigDecimal(baseData1003.getPre_number2())).divide(
											new BigDecimal(baseData1003.getPre_number()), 2, BigDecimal.ROUND_DOWN),
									userInfoDao, userBiRecordDao);
							reward_money = reward_money.add(sum_rebate_money.multiply(
									new BigDecimal(baseData1003.getPre_number2())).divide(
									new BigDecimal(baseData1003.getPre_number()), 2, BigDecimal.ROUND_DOWN));

							// 4、县域合伙人返现奖励（发放给驿站的县域合伙人）
							VillageInfo villageInfo = new VillageInfo();
							villageInfo.setId(v_ui.getOwn_village_id());
							villageInfo = villageInfoDao.selectEntity(villageInfo);
							if (null != villageInfo && null != villageInfo.getP_index()) {
								ServiceCenterInfo serviceCenterInfo = new ServiceCenterInfo();
								serviceCenterInfo.setP_index(Integer.valueOf(villageInfo.getP_index().toString()
										.substring(0, 6)));// 驿站所在县域
								serviceCenterInfo.setAudit_state(1);// 审核通过
								serviceCenterInfo.setEffect_state(1);// 已生效
								serviceCenterInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
								List<ServiceCenterInfo> serviceCenterInfoList = serviceCenterInfoDao
										.selectEntityList(serviceCenterInfo);
								if (null != serviceCenterInfoList && serviceCenterInfoList.size() == 1) {
									// 4、县域合伙人返现奖励
									if (serviceCenterInfoList.get(0).getServicecenter_level().intValue() == Keys.ServiceCenterLevel.SERVICE_CENTER_LEVEL_1
											.getIndex()) {// 普通县域合伙人
										// 普通县域合伙人奖励比例
										BaseData baseData1004 = super.getBaseData(
												Keys.REBATE_BASE_DATA_ID.REBATE_BASE_DATA_ID_1004.getIndex(),
												baseDataDao);
										insertUserBiDianziLock(
												serviceCenterInfoList.get(0).getAdd_user_id(),
												Keys.BiGetType.BI_GET_TYPE_1004.getIndex(),
												orderInfo.getId(),
												orderInfo.getId(),
												sum_rebate_money
														.multiply(new BigDecimal(baseData1004.getPre_number2()))
														.divide(new BigDecimal(baseData1004.getPre_number()), 2,
																BigDecimal.ROUND_DOWN), userInfoDao, userBiRecordDao);

										reward_money = reward_money
												.add(sum_rebate_money.multiply(
														new BigDecimal(baseData1004.getPre_number2())).divide(
														new BigDecimal(baseData1004.getPre_number()), 2,
														BigDecimal.ROUND_DOWN));
									} else if (serviceCenterInfoList.get(0).getServicecenter_level().intValue() == Keys.ServiceCenterLevel.SERVICE_CENTER_LEVEL_2
											.getIndex()) {
										// 股份县域合伙人奖励比例
										BaseData baseData1005 = super.getBaseData(
												Keys.REBATE_BASE_DATA_ID.REBATE_BASE_DATA_ID_1005.getIndex(),
												baseDataDao);
										insertUserBiDianziLock(
												serviceCenterInfoList.get(0).getAdd_user_id(),
												Keys.BiGetType.BI_GET_TYPE_1005.getIndex(),
												orderInfo.getId(),
												orderInfo.getId(),
												sum_rebate_money
														.multiply(new BigDecimal(baseData1005.getPre_number2()))
														.divide(new BigDecimal(baseData1005.getPre_number()), 2,
																BigDecimal.ROUND_DOWN), userInfoDao, userBiRecordDao);
										reward_money = reward_money
												.add(sum_rebate_money.multiply(
														new BigDecimal(baseData1005.getPre_number2())).divide(
														new BigDecimal(baseData1005.getPre_number()), 2,
														BigDecimal.ROUND_DOWN));
									}

								}

							}

						}
					} else {// 消费者是普通会员
							// 2、直邀返现奖励（发放给消费者的直接邀请人）
						// FIXME 这个地方有问题
						UserRelationPar userRelationParFirst = super.getUserRelationParFirst(ui.getId(),
								userRelationParDao);
						insertUserBiDianziLock(
								userRelationParFirst.getUser_par_id(),
								Keys.BiGetType.BI_GET_TYPE_1002.getIndex(),
								orderInfo.getId(),
								orderInfo.getId(),
								sum_rebate_money.multiply(new BigDecimal(baseData1002.getPre_number2())).divide(
										new BigDecimal(baseData1002.getPre_number()), 2, BigDecimal.ROUND_DOWN),
								userInfoDao, userBiRecordDao);
						reward_money = reward_money.add(sum_rebate_money.multiply(
								new BigDecimal(baseData1002.getPre_number2())).divide(
								new BigDecimal(baseData1002.getPre_number()), 2, BigDecimal.ROUND_DOWN));
						// 查找上级驿站
						UserRelationPar userRelationPar = new UserRelationPar();
						userRelationPar.setUser_id(orderInfo.getAdd_user_id());
						userRelationPar.getMap().put("isVillage", "true");
						userRelationPar.getMap().put("orderByUserParLevleAsc", "true");
						List<UserRelationPar> userRelationParListByVillage = userRelationParDao
								.selectEntityList(userRelationPar);
						if (null != userRelationParListByVillage && userRelationParListByVillage.size() > 0) {
							// 3、驿站返现奖励
							insertUserBiDianziLock(
									userRelationParListByVillage.get(0).getUser_par_id(),
									Keys.BiGetType.BI_GET_TYPE_1003.getIndex(),
									orderInfo.getId(),
									orderInfo.getId(),
									sum_rebate_money.multiply(new BigDecimal(baseData1003.getPre_number2())).divide(
											new BigDecimal(baseData1003.getPre_number()), 2, BigDecimal.ROUND_DOWN),
									userInfoDao, userBiRecordDao);
							reward_money = reward_money.add(sum_rebate_money.multiply(
									new BigDecimal(baseData1003.getPre_number2())).divide(
									new BigDecimal(baseData1003.getPre_number()), 2, BigDecimal.ROUND_DOWN));

							UserInfo v_ui = new UserInfo();
							v_ui.setId(userRelationParListByVillage.get(0).getUser_par_id());
							v_ui = userInfoDao.selectEntity(v_ui);

							if (null != v_ui && null != v_ui.getOwn_village_id()) {
								// 4、县域合伙人返现奖励（发放给驿站的县域合伙人）
								VillageInfo villageInfo = new VillageInfo();
								villageInfo.setId(v_ui.getOwn_village_id());
								villageInfo = villageInfoDao.selectEntity(villageInfo);
								if (null != villageInfo && null != villageInfo.getP_index()) {
									ServiceCenterInfo serviceCenterInfo = new ServiceCenterInfo();
									serviceCenterInfo.setP_index(Integer.valueOf(villageInfo.getP_index().toString()
											.substring(0, 6)));// 驿站所在县域
									serviceCenterInfo.setAudit_state(1);// 审核通过
									serviceCenterInfo.setEffect_state(1);// 已生效
									serviceCenterInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
									List<ServiceCenterInfo> serviceCenterInfoList = serviceCenterInfoDao
											.selectEntityList(serviceCenterInfo);
									if (null != serviceCenterInfoList && serviceCenterInfoList.size() == 1) {
										// 4、县域合伙人返现奖励
										if (serviceCenterInfoList.get(0).getServicecenter_level().intValue() == Keys.ServiceCenterLevel.SERVICE_CENTER_LEVEL_1
												.getIndex()) {// 普通县域合伙人
											// 普通县域合伙人奖励比例
											BaseData baseData1004 = super.getBaseData(
													Keys.REBATE_BASE_DATA_ID.REBATE_BASE_DATA_ID_1004.getIndex(),
													baseDataDao);
											insertUserBiDianziLock(
													serviceCenterInfoList.get(0).getAdd_user_id(),
													Keys.BiGetType.BI_GET_TYPE_1004.getIndex(),
													orderInfo.getId(),
													orderInfo.getId(),
													sum_rebate_money.multiply(
															new BigDecimal(baseData1004.getPre_number2())).divide(
															new BigDecimal(baseData1004.getPre_number()), 2,
															BigDecimal.ROUND_DOWN), userInfoDao, userBiRecordDao);
											reward_money = reward_money.add(sum_rebate_money.multiply(
													new BigDecimal(baseData1004.getPre_number2())).divide(
													new BigDecimal(baseData1004.getPre_number()), 2,
													BigDecimal.ROUND_DOWN));
										} else if (serviceCenterInfoList.get(0).getServicecenter_level().intValue() == Keys.ServiceCenterLevel.SERVICE_CENTER_LEVEL_2
												.getIndex()) {
											// 股份县域合伙人奖励比例
											BaseData baseData1005 = super.getBaseData(
													Keys.REBATE_BASE_DATA_ID.REBATE_BASE_DATA_ID_1005.getIndex(),
													baseDataDao);
											insertUserBiDianziLock(
													serviceCenterInfoList.get(0).getAdd_user_id(),
													Keys.BiGetType.BI_GET_TYPE_1005.getIndex(),
													orderInfo.getId(),
													orderInfo.getId(),
													sum_rebate_money.multiply(
															new BigDecimal(baseData1005.getPre_number2())).divide(
															new BigDecimal(baseData1005.getPre_number()), 2,
															BigDecimal.ROUND_DOWN), userInfoDao, userBiRecordDao);
											reward_money = reward_money.add(sum_rebate_money.multiply(
													new BigDecimal(baseData1005.getPre_number2())).divide(
													new BigDecimal(baseData1005.getPre_number()), 2,
													BigDecimal.ROUND_DOWN));
										}

									}

								}
							}
						}
					}
				}
				oi.setReward_money(reward_money);// 发放奖励金额
				orderInfoDao.updateEntity(oi);
			}
		}
	}

	public void grantBiLockToUser(Integer order_id, OrderInfoDao orderInfoDao, OrderInfoDetailsDao orderInfoDetailsDao,
			UserInfoDao userInfoDao, UserBiRecordDao userBiRecordDao, EntpInfoDao entpInfoDao, BaseDataDao baseDataDao,
			UserJifenRecordDao userJifenRecordDao, PoorCuoShiDao poorCuoShiDao) {
		// 1、发放商家货款
		UserBiRecord userBiRecord = new UserBiRecord();
		userBiRecord.setOrder_id(order_id);
		userBiRecord.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		userBiRecord.setBi_type(Keys.BiType.BI_TYPE_400.getIndex());// 待返商家货款
		userBiRecord.setBi_chu_or_ru(1);// 入
		List<UserBiRecord> userBiRecordList = userBiRecordDao.selectEntityList(userBiRecord);
		if (null != userBiRecordList && userBiRecordList.size() > 0) {
			for (UserBiRecord t : userBiRecordList) {
				// 待返货款：出
				this.insertUserBiRecord(t.getAdd_user_id(), null, -1, order_id, t.getLink_id(), t.getBi_no(),
						Keys.BiType.BI_TYPE_400.getIndex(), t.getBi_get_type(), null, userInfoDao, userBiRecordDao);
				// 货款：入
				this.insertUserBiRecord(t.getAdd_user_id(), null, 1, order_id, t.getLink_id(), t.getBi_no(),
						Keys.BiType.BI_TYPE_300.getIndex(), t.getBi_get_type(), null, userInfoDao, userBiRecordDao);

				// 更新商家货款和待返货款
				UserInfo update_user = new UserInfo();
				update_user.setId(t.getAdd_user_id());
				update_user.getMap().put("add_bi_huokuan", t.getBi_no());
				update_user.getMap().put("sub_bi_huokuan_lock", t.getBi_no());
				userInfoDao.updateEntity(update_user);
			}
		}

		// 2、发放扶贫金额
		userBiRecord = new UserBiRecord();
		userBiRecord.setOrder_id(order_id);
		userBiRecord.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		userBiRecord.setBi_type(Keys.BiType.BI_TYPE_600.getIndex());// 待返扶贫金
		userBiRecord.setBi_chu_or_ru(1);// 入
		List<UserBiRecord> userBiRecordListByAid = userBiRecordDao.selectEntityList(userBiRecord);
		if (null != userBiRecordListByAid && userBiRecordListByAid.size() > 0) {
			for (UserBiRecord t : userBiRecordListByAid) {
				// 待返扶贫金：出
				this.insertUserBiRecord(t.getAdd_user_id(), null, -1, order_id, t.getLink_id(), t.getBi_no(),
						Keys.BiType.BI_TYPE_600.getIndex(), t.getBi_get_type(), null, userInfoDao, userBiRecordDao);
				// 扶贫金：入
				this.insertUserBiRecord(t.getAdd_user_id(), null, 1, order_id, t.getLink_id(), t.getBi_no(),
						Keys.BiType.BI_TYPE_500.getIndex(), t.getBi_get_type(), null, userInfoDao, userBiRecordDao);

				// 更新会员扶贫金
				UserInfo update_user = new UserInfo();
				update_user.setId(t.getAdd_user_id());
				update_user.getMap().put("add_bi_aid", t.getBi_no());
				update_user.getMap().put("sub_bi_aid_lock", t.getBi_no());
				userInfoDao.updateEntity(update_user);

				// 添加扶贫记录到扶贫措施中
				UserInfo ui = new UserInfo();
				ui.setId(t.getAdd_user_id());
				ui = userInfoDao.selectEntity(ui);
				if (null != ui && null != ui.getPoor_id()) {
					OrderInfo oi = new OrderInfo();
					oi.setId(order_id);
					oi = orderInfoDao.selectEntity(oi);
					PoorCuoShi poorCuoShi = new PoorCuoShi();
					poorCuoShi.setLink_id(ui.getPoor_id());
					poorCuoShi.setDan_wei_name(oi.getEntp_name());
					String xf_user_name = "******";
					if (oi.getAdd_user_name().length() >= 11) {
						xf_user_name = oi.getAdd_user_name().substring(0, 3) + "***"
								+ oi.getAdd_user_name().substring(7);
					} else if (oi.getAdd_user_name().length() > 3) {
						xf_user_name = oi.getAdd_user_name().substring(0, 3) + "***";
					} else if (oi.getAdd_user_name().length() > 0) {
						xf_user_name = oi.getAdd_user_name().substring(0, 1) + "***";
					}
					poorCuoShi.setContent("会员：" + xf_user_name + "在平台购物，贫困户获取扶贫金额："
							+ t.getBi_no().setScale(2, BigDecimal.ROUND_HALF_UP) + "元");
					poorCuoShi.setHelp_date(new Date());
					poorCuoShi.setAdd_date(new Date());
					poorCuoShi.setAdd_user_id(1);
					poorCuoShiDao.insertEntity(poorCuoShi);
				}
			}
		}

		// 3、发放返现奖励
		userBiRecord = new UserBiRecord();
		userBiRecord.setOrder_id(order_id);
		userBiRecord.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		userBiRecord.setBi_type(Keys.BiType.BI_TYPE_200.getIndex());// 待返返现奖励
		userBiRecord.setBi_chu_or_ru(1);// 入
		List<UserBiRecord> userBiRecordListByDianzi = userBiRecordDao.selectEntityList(userBiRecord);
		if (null != userBiRecordListByAid && userBiRecordListByDianzi.size() > 0) {
			for (UserBiRecord t : userBiRecordListByDianzi) {
				// 待返余额：出
				this.insertUserBiRecord(t.getAdd_user_id(), null, -1, order_id, t.getLink_id(), t.getBi_no(),
						Keys.BiType.BI_TYPE_200.getIndex(), t.getBi_get_type(), null, userInfoDao, userBiRecordDao);
				// 余额：入
				this.insertUserBiRecord(t.getAdd_user_id(), null, 1, order_id, t.getLink_id(), t.getBi_no(),
						Keys.BiType.BI_TYPE_100.getIndex(), t.getBi_get_type(), null, userInfoDao, userBiRecordDao);

				// 更新余额和待返余额
				UserInfo update_user = new UserInfo();
				update_user.setId(t.getAdd_user_id());
				update_user.getMap().put("add_bi_dianzi", t.getBi_no());
				update_user.getMap().put("sub_bi_dianzi_lock", t.getBi_no());
				userInfoDao.updateEntity(update_user);

				OrderInfo oi = new OrderInfo();
				oi.setId(order_id);
				oi = orderInfoDao.selectEntity(oi);
				UserInfo ui = new UserInfo();
				ui.setId(t.getAdd_user_id());
				ui = userInfoDao.selectEntity(ui);
				// 微信公众号消息推送
				if (t.getBi_get_type().intValue() == Keys.BiGetType.BI_GET_TYPE_1002.getIndex()) {
					WeiXinSendMessageUtils.spokesMan(oi, ui, t);
				}
			}
		}

	}

	/**
	 * 插入待返扶贫金记录公共方法
	 * 
	 * @param poor_id 贫困户ID
	 * @param bi_get_type 获取来源
	 * @param order_id 订单id
	 * @param link_id 关联id
	 * @param bi_dianzi 金额
	 * @param userInfoDao
	 * @param userBiRecordDao
	 */
	public void insertUserBiAidLock(Integer poor_id, Integer bi_get_type, Integer order_id, Integer link_id,
			BigDecimal bi_dianzi, UserInfoDao userInfoDao, UserBiRecordDao userBiRecordDao) {
		UserInfo ui = new UserInfo();
		ui.setIs_poor(1);
		ui.setPoor_id(poor_id);
		ui.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		ui = userInfoDao.selectEntity(ui);
		if (null != ui && null != ui.getId()) {
			this.insertUserBiRecord(ui.getId(), null, 1, order_id, link_id, bi_dianzi,
					Keys.BiType.BI_TYPE_600.getIndex(), bi_get_type, null, userInfoDao, userBiRecordDao);

			// 一、获取待返余额记录
			UserInfo update_user = new UserInfo();
			update_user.setId(ui.getId());
			update_user.getMap().put("add_bi_aid_lock", bi_dianzi);
			userInfoDao.updateEntity(update_user);
		}
	}

	/**
	 * 插入待返返利金额记录公共方法
	 * 
	 * @param user_id 会议ID
	 * @param bi_get_type 获取来源
	 * @param order_id 订单id
	 * @param link_id 关联id
	 * @param bi_dianzi 金额
	 * @param userInfoDao
	 * @param userBiRecordDao
	 */
	public void insertUserBiDianziLock(Integer user_id, Integer bi_get_type, Integer order_id, Integer link_id,
			BigDecimal bi_dianzi, UserInfoDao userInfoDao, UserBiRecordDao userBiRecordDao) {
		this.insertUserBiRecord(user_id, null, 1, order_id, link_id, bi_dianzi, Keys.BiType.BI_TYPE_200.getIndex(),
				bi_get_type, null, userInfoDao, userBiRecordDao);

		// 一、获取待返余额记录
		UserInfo update_user = new UserInfo();
		update_user.setId(user_id);
		update_user.getMap().put("add_bi_dianzi_lock", bi_dianzi);
		userInfoDao.updateEntity(update_user);
	}

	/**
	 * 插入待返商家货款记录公共方法
	 * 
	 * @param user_id 会议ID
	 * @param bi_get_type 获取来源
	 * @param order_id 订单id
	 * @param link_id 关联id
	 * @param bi_dianzi 金额
	 * @param userInfoDao
	 * @param userBiRecordDao
	 */
	public void insertUserBiHuokuanLock(Integer user_id, Integer bi_get_type, Integer order_id, Integer link_id,
			BigDecimal bi_dianzi, UserInfoDao userInfoDao, UserBiRecordDao userBiRecordDao) {
		this.insertUserBiRecord(user_id, null, 1, order_id, link_id, bi_dianzi, Keys.BiType.BI_TYPE_400.getIndex(),
				bi_get_type, null, userInfoDao, userBiRecordDao);

		// 一、获取待返余额记录
		UserInfo update_user = new UserInfo();
		update_user.setId(user_id);
		update_user.getMap().put("add_bi_huokuan_lock", bi_dianzi);
		userInfoDao.updateEntity(update_user);
	}
}
