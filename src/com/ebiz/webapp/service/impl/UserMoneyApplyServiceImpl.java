package com.ebiz.webapp.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.BaseDataDao;
import com.ebiz.webapp.dao.MsgDao;
import com.ebiz.webapp.dao.MsgReceiverDao;
import com.ebiz.webapp.dao.OrderInfoDao;
import com.ebiz.webapp.dao.SysSettingDao;
import com.ebiz.webapp.dao.TongjiDao;
import com.ebiz.webapp.dao.UserBiRecordDao;
import com.ebiz.webapp.dao.UserInfoDao;
import com.ebiz.webapp.dao.UserMoneyApplyDao;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.SysSetting;
import com.ebiz.webapp.domain.Tongji;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.UserMoneyApply;
import com.ebiz.webapp.service.UserMoneyApplyService;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.Keys.IsDel;
import com.ebiz.webapp.web.SMS;
import com.ebiz.webapp.web.util.dysms.DySmsUtils;
import com.ebiz.webapp.web.util.dysms.SmsTemplate;

/**
 * @author Wu,Yang
 * @version 2015-11-21 14:17
 */
@Service
public class UserMoneyApplyServiceImpl extends BaseImpl implements UserMoneyApplyService {

	public static DecimalFormat dfn = new DecimalFormat("0.00");

	@Resource
	private UserMoneyApplyDao userMoneyApplyDao;

	@Resource
	private UserInfoDao userInfoDao;

	@Resource
	private TongjiDao tongjiDao;

	@Resource
	private UserBiRecordDao userBiRecordDao;

	@Resource
	private MsgDao msgDao;

	@Resource
	private MsgReceiverDao msgReceiverDao;

	@Resource
	private BaseDataDao baseDataDao;

	@Resource
	private OrderInfoDao orderInfoDao;

	@Resource
	private SysSettingDao sysSettingDao;

	public Integer createUserMoneyApply(UserMoneyApply t) {
		int id = this.userMoneyApplyDao.insertEntity(t);

		UserInfo u = new UserInfo();
		u.setId(t.getUser_id());
		u = this.userInfoDao.selectEntity(u);
		if (t.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_10.getIndex()) {// 余额提现

			BigDecimal rmb_to_dianzibi = super.BiToBi2(t.getCash_count(),
					Keys.BASE_DATA_ID.BASE_DATA_ID_904.getIndex(), baseDataDao);
			// 插入消费记录
			super.insertUserBiRecord(u.getId(), null, -1, id, null, rmb_to_dianzibi,
					Keys.BiType.BI_TYPE_100.getIndex(), Keys.BiGetType.BI_OUT_TYPE_50.getIndex(), null, userInfoDao,
					userBiRecordDao);

			UserInfo user = new UserInfo();
			user.setId(t.getUser_id());
			user.getMap().put("sub_bi_dianzi", rmb_to_dianzibi);
			this.userInfoDao.updateEntity(user);

		} else if (t.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_20.getIndex()) {// 发放扶贫金
			// 插入消费记录
			super.insertUserBiRecord(u.getId(), null, -1, id, id, t.getCash_count(),
					Keys.BiType.BI_TYPE_500.getIndex(), Keys.BiGetType.BI_OUT_TYPE_51.getIndex(), null, userInfoDao,
					userBiRecordDao);

			UserInfo user = new UserInfo();
			user.setId(t.getUser_id());
			user.getMap().put("sub_bi_aid", t.getCash_count());
			user.getMap().put("add_bi_aid_sended", t.getCash_count());
			this.userInfoDao.updateEntity(user);
		} else if (t.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_30.getIndex()) {// 货款提现
			// 插入消费记录
			super.insertUserBiRecord(u.getId(), null, -1, id, null, t.getCash_count(),
					Keys.BiType.BI_TYPE_300.getIndex(), Keys.BiGetType.BI_OUT_TYPE_60.getIndex(), null, userInfoDao,
					userBiRecordDao);

			UserInfo user = new UserInfo();
			user.setId(t.getUser_id());
			user.getMap().put("sub_bi_huokuan", t.getCash_count());
			this.userInfoDao.updateEntity(user);
		} else if (t.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_40.getIndex()) {// 福利金提现
			// 插入提现记录
			BigDecimal rmb_to_dianzibi = super.BiToBi2(t.getCash_count(),
					Keys.BASE_DATA_ID.BASE_DATA_ID_904.getIndex(), baseDataDao);

			super.insertUserWelfareBuyBiRecord(u.getId(), null, -1, null, id, rmb_to_dianzibi,
					Keys.BiType.BI_TYPE_100.getIndex(), Keys.BiGetType.BI_OUT_TYPE_52.getIndex(), null, userInfoDao,
					userBiRecordDao, rmb_to_dianzibi);

			UserInfo user = new UserInfo();
			user.setId(t.getUser_id());
			user.getMap().put("sub_bi_dianzi", rmb_to_dianzibi);
			user.getMap().put("sub_bi_welfare", rmb_to_dianzibi);
			this.userInfoDao.updateEntity(user);
		}

		return id;
	}

	public UserMoneyApply getUserMoneyApply(UserMoneyApply t) {
		return this.userMoneyApplyDao.selectEntity(t);
	}

	public Integer getUserMoneyApplyCount(UserMoneyApply t) {
		return this.userMoneyApplyDao.selectEntityCount(t);
	}

	public UserMoneyApply getselectUserMoneyApplyForMoneyTongJi(UserMoneyApply t) {
		return this.userMoneyApplyDao.selectEntityForMoneyTongJi(t);
	}

	public List<UserMoneyApply> getUserMoneyApplyList(UserMoneyApply t) {
		return this.userMoneyApplyDao.selectEntityList(t);
	}

	public void updateMoneyApplyForAuditNot(UserMoneyApply t) {
		// 删除审核不通过时，删除提现订单，防止记账明细统计有问题
		if (null != t.getId()) {
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.getMap().put("trade_merger_index", "tixian_" + t.getId());
			orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_90.getIndex());
			this.orderInfoDao.updateEntity(orderInfo);
		}

		if (t.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_10.getIndex()) {
			// 插入 提现未成功余额退回 记录
			super.insertUserBiRecord(t.getUser_id(), null, 1, t.getId(), null, t.getCash_count(),
					Keys.BiType.BI_TYPE_100.getIndex(), Keys.BiGetType.BI_GET_TYPE_111.getIndex(), null, userInfoDao,
					userBiRecordDao);

			UserInfo userInfo = new UserInfo();
			userInfo.setId(t.getUser_id());
			userInfo.getMap().put("add_bi_dianzi", t.getCash_count());
			this.userInfoDao.updateEntity(userInfo);

		}
		if (t.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_20.getIndex()) {
			// 插入 提现未成功扶贫金退回 记录
			super.insertUserBiRecord(t.getUser_id(), null, 1, t.getId(), null, t.getCash_count(),
					Keys.BiType.BI_TYPE_500.getIndex(), Keys.BiGetType.BI_GET_TYPE_113.getIndex(), null, userInfoDao,
					userBiRecordDao);

			UserInfo userInfo = new UserInfo();
			userInfo.setId(t.getUser_id());
			userInfo.getMap().put("add_bi_aid", t.getCash_count());
			this.userInfoDao.updateEntity(userInfo);

		}
		if (t.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_30.getIndex()) {
			// 插入 提现未成功货款退回 记录
			super.insertUserBiRecord(t.getUser_id(), null, 1, t.getId(), null, t.getCash_count(),
					Keys.BiType.BI_TYPE_300.getIndex(), Keys.BiGetType.BI_GET_TYPE_112.getIndex(), null, userInfoDao,
					userBiRecordDao);

			UserInfo userInfo = new UserInfo();
			userInfo.setId(t.getUser_id());
			userInfo.getMap().put("add_bi_huokuan", t.getCash_count());
			this.userInfoDao.updateEntity(userInfo);

		}

		if (t.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_40.getIndex()) {
			// 插入 提现未成功余额退回 记录
			super.insertUserWelfareBuyBiRecord(t.getUser_id(), null, 1, null, t.getId(), t.getCash_count(),
					Keys.BiType.BI_TYPE_100.getIndex(), Keys.BiGetType.BI_GET_TYPE_114.getIndex(), null, userInfoDao,
					userBiRecordDao, t.getCash_count());

			UserInfo userInfo = new UserInfo();
			userInfo.setId(t.getUser_id());
			userInfo.getMap().put("add_bi_dianzi", t.getCash_count());
			userInfo.getMap().put("add_bi_welfare", t.getCash_count());
			this.userInfoDao.updateEntity(userInfo);

		}
	}

	public int modifyUserMoneyApply(UserMoneyApply t) {
		List<UserMoneyApply> userMoneyApplyList = new ArrayList<UserMoneyApply>();
		if (null != t.getMap().get("getCashcount")) {
			if (t.getInfo_state() == Keys.INFO_STATE.INFO_STATE_Not.getIndex()
					|| t.getInfo_state().intValue() == Keys.INFO_STATE.INFO_STATE_X2.getIndex()) {
				// 审核不通过时，将余额和货款，还给用户
				if (null == t.getMap().get("pks")) {// 单个操作
					updateMoneyApplyForAuditNot(t);
				} else {
					String[] pks = (String[]) t.getMap().get("pks");

					for (int i = 0; i < pks.length; i++) {
						UserMoneyApply userMoneyApply = new UserMoneyApply();
						userMoneyApply.setId(Integer.valueOf(pks[i]));
						userMoneyApply.setIs_del(IsDel.IS_DEL_0.getIndex());
						userMoneyApply = userMoneyApplyDao.selectEntity(userMoneyApply);
						userMoneyApplyList.add(userMoneyApply);
						updateMoneyApplyForAuditNot(userMoneyApply);
					}
				}
			}
		}

		if (t.getInfo_state() == Keys.INFO_STATE.INFO_STATE_Not.getIndex()) {
			Integer user_id = t.getUser_id();
			String audit_memo = t.getAudit_memo();
			// 批量提现驳回站内信通知
			if (userMoneyApplyList.size() >= 1 && null != t.getMap().get("pks")) {
				for (UserMoneyApply temp : userMoneyApplyList) {
					user_id = temp.getUser_id();

					UserInfo uireal = super.getUserInfo(user_id, userInfoDao);
					if (temp.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_10.getIndex()) {
						sendMsgForPublic(SMS.sms_18, uireal.getUser_name(), audit_memo, user_id, "余额提现驳回通知");

					} else if (temp.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_30.getIndex()) {
						sendMsgForPublic(SMS.sms_18, uireal.getUser_name(), audit_memo, user_id, "货款提现驳回通知");
					} else if (temp.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_20.getIndex()) {
						sendMsgForPublic(SMS.sms_18, uireal.getUser_name(), audit_memo, user_id, "扶贫金提现驳回通知");
					} else if (temp.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_40.getIndex()) {
						sendMsgForPublic(SMS.sms_18, uireal.getUser_name(), audit_memo, user_id, "福利金提现驳回通知");
					}
				}
			} else {
				// 单个提现驳回站内信通知
				UserInfo uireal = super.getUserInfo(t.getUser_id(), userInfoDao);
				if (t.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_10.getIndex()) {
					sendMsgForPublic(SMS.sms_18, uireal.getUser_name(), audit_memo, user_id, "余额提现驳回通知");
				} else if (t.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_30.getIndex()) {
					sendMsgForPublic(SMS.sms_18, uireal.getUser_name(), audit_memo, user_id, "货款提现驳回通知");
				} else if (t.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_20.getIndex()) {
					sendMsgForPublic(SMS.sms_18, uireal.getUser_name(), audit_memo, user_id, "扶贫金提现驳回通知");
				} else if (t.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_40.getIndex()) {
					sendMsgForPublic(SMS.sms_18, uireal.getUser_name(), audit_memo, user_id, "福利金提现驳回通知");
				}
			}
		}
		if (t.getInfo_state() == Keys.INFO_STATE.INFO_STATE_1.getIndex()) {
			logger.info("==================info_state======================");
			String audit_memo = t.getAudit_memo();
			// 批量提现成功站内信通知和短信通知
			if (null != t.getMap().get("pks")) {
				String[] pks = (String[]) t.getMap().get("pks");
				for (int i = 0; i < pks.length; i++) {
					UserMoneyApply userMoneyApply = new UserMoneyApply();
					userMoneyApply.setId(Integer.valueOf(pks[i]));
					userMoneyApply.setIs_del(IsDel.IS_DEL_0.getIndex());
					userMoneyApply = userMoneyApplyDao.selectEntity(userMoneyApply);

					// 2、阿里云短信 给财务
					SysSetting sysSetting = new SysSetting();
					sysSetting.setTitle(Keys.financialMobile);
					sysSetting = sysSettingDao.selectEntity(sysSetting);
					logger.info("===========sysSetting=============" + sysSetting.getContent());
					if (userMoneyApply.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_10.getIndex()) {
						if (null != sysSetting) {
							StringBuffer message1 = new StringBuffer("{\"name\":\"" + "余额提现" + "\",");
							message1.append("}");
							DySmsUtils.sendMessage(message1.toString(), sysSetting.getContent(), SmsTemplate.sms_23);
						}
					} else if (userMoneyApply.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_20.getIndex()) {
						if (null != sysSetting) {
							StringBuffer message1 = new StringBuffer("{\"name\":\"" + "扶贫金提现" + "\",");
							message1.append("}");
							DySmsUtils.sendMessage(message1.toString(), sysSetting.getContent(), SmsTemplate.sms_23);
						}
					} else if (userMoneyApply.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_40.getIndex()) {
						if (null != sysSetting) {
							StringBuffer message1 = new StringBuffer("{\"name\":\"" + "福利金提现" + "\",");
							message1.append("}");
							DySmsUtils.sendMessage(message1.toString(), sysSetting.getContent(), SmsTemplate.sms_23);
						}
					}
				}
			} else {// 单个提现成功站内信通知
				UserMoneyApply uma = new UserMoneyApply();
				uma.setId(t.getId());
				uma.setIs_del(IsDel.IS_DEL_0.getIndex());
				uma = userMoneyApplyDao.selectEntity(uma);
				// 2、阿里云短信 给财务
				SysSetting sysSetting = new SysSetting();
				sysSetting.setTitle(Keys.financialMobile);
				sysSetting = sysSettingDao.selectEntity(sysSetting);
				logger.info("===========sysSetting=============" + sysSetting.getContent());
				if (uma.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_10.getIndex()) {
					if (null != sysSetting) {
						StringBuffer message1 = new StringBuffer("{\"name\":\"" + "余额提现" + "\",");
						message1.append("}");
						DySmsUtils.sendMessage(message1.toString(), sysSetting.getContent(), SmsTemplate.sms_23);
					}
				} else if (uma.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_20.getIndex()) {
					if (null != sysSetting) {
						StringBuffer message1 = new StringBuffer("{\"name\":\"" + "扶贫金提现" + "\",");
						message1.append("}");
						DySmsUtils.sendMessage(message1.toString(), sysSetting.getContent(), SmsTemplate.sms_23);
					}
				} else if (uma.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_40.getIndex()) {
					if (null != sysSetting) {
						StringBuffer message1 = new StringBuffer("{\"name\":\"" + "福利金提现" + "\",");
						message1.append("}");
						DySmsUtils.sendMessage(message1.toString(), sysSetting.getContent(), SmsTemplate.sms_23);
					}
				}
			}
		}

		if (t.getInfo_state() == Keys.INFO_STATE.INFO_STATE_2.getIndex()) {
			String audit_memo = t.getAudit_memo();
			// 批量提现成功站内信通知和短信通知
			if (null != t.getMap().get("pks")) {
				String[] pks = (String[]) t.getMap().get("pks");
				for (int i = 0; i < pks.length; i++) {
					UserMoneyApply userMoneyApply = new UserMoneyApply();
					userMoneyApply.setId(Integer.valueOf(pks[i]));
					userMoneyApply.setIs_del(IsDel.IS_DEL_0.getIndex());
					userMoneyApply = userMoneyApplyDao.selectEntity(userMoneyApply);
					UserInfo uireal = super.getUserInfo(userMoneyApply.getUser_id(), userInfoDao);
					if (userMoneyApply.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_10.getIndex()) {
						String msg = StringUtils.replace(SMS.sms_19, "{0}", uireal.getUser_name());
						msg = StringUtils.replace(msg, "{1}", dfn.format(userMoneyApply.getCash_count()));
						super.sendMsg(1, uireal.getId(), "余额提现成功通知", msg, msgDao, msgReceiverDao, userInfoDao);
						// 1、创蓝短信
						// SmsUtils.sendMessage(msg, uireal.getMobile());
						// 2、阿里云短信
						StringBuffer message = new StringBuffer("{\"user\":\"" + uireal.getUser_name() + "\",");
						message.append("\"money\":\"" + dfn.format(userMoneyApply.getCash_count()) + "\"");
						message.append("}");
						DySmsUtils.sendMessage(message.toString(), uireal.getMobile(), SmsTemplate.sms_19);
					} else if (userMoneyApply.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_30.getIndex()) {
						String msg = StringUtils.replace(SMS.sms_19, "{0}", uireal.getUser_name());
						msg = StringUtils.replace(msg, "{1}", dfn.format(userMoneyApply.getCash_count()));
						super.sendMsg(1, uireal.getId(), "货款提现成功通知", msg, msgDao, msgReceiverDao, userInfoDao);
						// 1、创蓝短信
						// SmsUtils.sendMessage(msg, uireal.getMobile());

						// 2、阿里云短信
						StringBuffer message = new StringBuffer("{\"user\":\"" + uireal.getUser_name() + "\",");
						message.append("\"money\":\"" + dfn.format(userMoneyApply.getCash_count()) + "\"");
						message.append("}");
						DySmsUtils.sendMessage(message.toString(), uireal.getMobile(), SmsTemplate.sms_19);
					} else if (userMoneyApply.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_20.getIndex()) {
						String msg = StringUtils.replace(SMS.sms_19, "{0}", uireal.getUser_name());
						msg = StringUtils.replace(msg, "{1}", dfn.format(userMoneyApply.getCash_count()));
						super.sendMsg(1, uireal.getId(), "扶贫金提现成功通知", msg, msgDao, msgReceiverDao, userInfoDao);
						// 1、创蓝短信
						// SmsUtils.sendMessage(msg, uireal.getMobile());

						// 2、阿里云短信
						StringBuffer message = new StringBuffer("{\"user\":\"" + uireal.getUser_name() + "\",");
						message.append("\"money\":\"" + dfn.format(userMoneyApply.getCash_count()) + "\"");
						message.append("}");
						DySmsUtils.sendMessage(message.toString(), uireal.getMobile(), SmsTemplate.sms_19);
					} else if (userMoneyApply.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_40.getIndex()) {
						String msg = StringUtils.replace(SMS.sms_19, "{0}", uireal.getUser_name());
						msg = StringUtils.replace(msg, "{1}", dfn.format(userMoneyApply.getCash_count()));
						super.sendMsg(1, uireal.getId(), "福利金提现成功通知", msg, msgDao, msgReceiverDao, userInfoDao);
						// 1、创蓝短信
						// SmsUtils.sendMessage(msg, uireal.getMobile());

						// 2、阿里云短信
						StringBuffer message = new StringBuffer("{\"user\":\"" + uireal.getUser_name() + "\",");
						message.append("\"money\":\"" + dfn.format(userMoneyApply.getCash_count()) + "\"");
						message.append("}");
						DySmsUtils.sendMessage(message.toString(), uireal.getMobile(), SmsTemplate.sms_19);
					}
				}
			} else {// 单个提现成功站内信通知

				UserMoneyApply uma = new UserMoneyApply();
				uma.setId(t.getId());
				uma.setIs_del(IsDel.IS_DEL_0.getIndex());
				uma = userMoneyApplyDao.selectEntity(uma);

				UserInfo uireal = super.getUserInfo(uma.getUser_id(), userInfoDao);

				if (uma.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_10.getIndex()) {
					String msg = StringUtils.replace(SMS.sms_19, "{0}", uireal.getUser_name());
					msg = StringUtils.replace(msg, "{1}", dfn.format(uma.getCash_count()));
					super.sendMsg(1, uma.getUser_id(), "余额提现成功通知", msg, msgDao, msgReceiverDao, userInfoDao);
					// 1、创蓝短信
					// SmsUtils.sendMessage(msg, uireal.getMobile());

					// 2、阿里云短信
					StringBuffer message = new StringBuffer("{\"user\":\"" + uireal.getUser_name() + "\",");
					message.append("\"money\":\"" + dfn.format(uma.getCash_count()) + "\"");
					message.append("}");
					DySmsUtils.sendMessage(message.toString(), uireal.getMobile(), SmsTemplate.sms_19);
				} else if (uma.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_30.getIndex()) {
					String msg = StringUtils.replace(SMS.sms_19, "{0}", uireal.getUser_name());
					msg = StringUtils.replace(msg, "{1}", dfn.format(uma.getCash_count()));
					super.sendMsg(1, uma.getUser_id(), "货款提现成功通知", msg, msgDao, msgReceiverDao, userInfoDao);
					// 1、创蓝短信
					// SmsUtils.sendMessage(msg, uireal.getMobile());

					// 2、阿里云短信
					StringBuffer message = new StringBuffer("{\"user\":\"" + uireal.getUser_name() + "\",");
					message.append("\"money\":\"" + dfn.format(uma.getCash_count()) + "\"");
					message.append("}");
					DySmsUtils.sendMessage(message.toString(), uireal.getMobile(), SmsTemplate.sms_19);
				} else if (uma.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_20.getIndex()) {
					String msg = StringUtils.replace(SMS.sms_19, "{0}", uireal.getUser_name());
					msg = StringUtils.replace(msg, "{1}", dfn.format(uma.getCash_count()));
					super.sendMsg(1, uma.getUser_id(), "扶贫金提现成功通知", msg, msgDao, msgReceiverDao, userInfoDao);
					// 发送短信
					// SmsUtils.sendMessage(msg, uireal.getMobile());

					// 2、阿里云短信
					StringBuffer message = new StringBuffer("{\"user\":\"" + uireal.getUser_name() + "\",");
					message.append("\"money\":\"" + dfn.format(uma.getCash_count()) + "\"");
					message.append("}");
					DySmsUtils.sendMessage(message.toString(), uireal.getMobile(), SmsTemplate.sms_19);
				} else if (uma.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_40.getIndex()) {
					String msg = StringUtils.replace(SMS.sms_19, "{0}", uireal.getUser_name());
					msg = StringUtils.replace(msg, "{1}", dfn.format(uma.getCash_count()));
					super.sendMsg(1, uma.getUser_id(), "福利金提现成功通知", msg, msgDao, msgReceiverDao, userInfoDao);
					// 发送短信
					// SmsUtils.sendMessage(msg, uireal.getMobile());

					// 2、阿里云短信
					StringBuffer message = new StringBuffer("{\"user\":\"" + uireal.getUser_name() + "\",");
					message.append("\"money\":\"" + dfn.format(uma.getCash_count()) + "\"");
					message.append("}");
					DySmsUtils.sendMessage(message.toString(), uireal.getMobile(), SmsTemplate.sms_19);
				}
			}

		}

		if (null != t.getMap().get("update_tixian_tongji_num")) {// 单个付款

			UserMoneyApply userMoneyApply = new UserMoneyApply();
			userMoneyApply.setId(t.getId());
			userMoneyApply.setIs_del(IsDel.IS_DEL_0.getIndex());
			userMoneyApply = userMoneyApplyDao.selectEntity(userMoneyApply);

			// 体现申请审核通过后，付款后更新统计表,数据库中已有记录，此处只更新！
			Tongji tongji = new Tongji();
			tongji = new Tongji();
			tongji.getMap().put("tongji_type", Keys.TongjiType.TONGJITYPE_40.getIndex());
			tongji.setModify_date(new Date());
			tongji.setModify_uid(t.getAdd_uid());
			tongji.getMap().put("add_tongji_num1", userMoneyApply.getCash_pay());
			tongjiDao.updateEntity(tongji);
		}

		if (null != t.getMap().get("update_tixian_tongji_num_pks")) { // 批量付款
			String[] pks = (String[]) t.getMap().get("pks");
			BigDecimal cash_pay_total = new BigDecimal(0);

			for (int i = 0; i < pks.length; i++) {
				UserMoneyApply userMoneyApply = new UserMoneyApply();
				userMoneyApply.setId(Integer.valueOf(pks[i]));
				userMoneyApply.setIs_del(IsDel.IS_DEL_0.getIndex());
				userMoneyApply = userMoneyApplyDao.selectEntity(userMoneyApply);
				cash_pay_total = cash_pay_total.add(userMoneyApply.getCash_pay());
			}

			// 体现申请审核通过后，付款后更新统计表,数据库中已有记录，此处只更新！
			Tongji tongji = new Tongji();
			tongji = new Tongji();
			tongji.getMap().put("tongji_type", Keys.TongjiType.TONGJITYPE_40.getIndex());
			tongji.setModify_date(new Date());
			tongji.setModify_uid((Integer) t.getMap().get("modify_id"));
			tongji.getMap().put("add_tongji_num1", cash_pay_total);
			tongjiDao.updateEntity(tongji);
		}
		int a = this.userMoneyApplyDao.updateEntity(t);
		return a;
	}

	public int removeUserMoneyApply(UserMoneyApply t) {
		return this.userMoneyApplyDao.deleteEntity(t);
	}

	public List<UserMoneyApply> getUserMoneyApplyPaginatedList(UserMoneyApply t) {
		return this.userMoneyApplyDao.selectEntityPaginatedList(t);
	}

	@Override
	public UserMoneyApply getUserMoneyApplyForMoneyTongJi(UserMoneyApply t) {
		return this.userMoneyApplyDao.selectEntityForMoneyTongJi(t);
	}

	public void sendMsgForPublic(String msgType, String real_name, String audit_memo, Integer user_id, String notice) {
		String msg = StringUtils.replace(msgType, "{0}", real_name);
		// if (StringUtils.isNotBlank(audit_memo)) {
		// msg = StringUtils.replace(msg, "{1}", audit_memo);
		// }
		super.sendMsg(1, user_id, notice, msg, msgDao, msgReceiverDao, userInfoDao);
	}

}
