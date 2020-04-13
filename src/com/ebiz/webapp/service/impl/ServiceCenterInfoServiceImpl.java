package com.ebiz.webapp.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.dao.BaseAuditRecordDao;
import com.ebiz.webapp.dao.BaseDataDao;
import com.ebiz.webapp.dao.BaseImgsDao;
import com.ebiz.webapp.dao.BaseProvinceDao;
import com.ebiz.webapp.dao.EntpInfoDao;
import com.ebiz.webapp.dao.MsgDao;
import com.ebiz.webapp.dao.MsgReceiverDao;
import com.ebiz.webapp.dao.OrderInfoDao;
import com.ebiz.webapp.dao.OrderInfoDetailsDao;
import com.ebiz.webapp.dao.RoleUserDao;
import com.ebiz.webapp.dao.ServiceCenterInfoDao;
import com.ebiz.webapp.dao.SysOperLogDao;
import com.ebiz.webapp.dao.TongjiDao;
import com.ebiz.webapp.dao.UserBiRecordDao;
import com.ebiz.webapp.dao.UserInfoDao;
import com.ebiz.webapp.dao.UserJifenRecordDao;
import com.ebiz.webapp.dao.UserRelationDao;
import com.ebiz.webapp.dao.UserRelationParDao;
import com.ebiz.webapp.dao.UserScoreRecordDao;
import com.ebiz.webapp.domain.BaseAuditRecord;
import com.ebiz.webapp.domain.BaseImgs;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.SysOperLog;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.service.ServiceCenterInfoService;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.SMS;
import com.ebiz.webapp.web.util.DESPlus;
import com.ebiz.webapp.web.util.DateTools;
import com.ebiz.webapp.web.util.HttpUtils;

/**
 * @version 2015-12-06 14:47
 */
@Service
public class ServiceCenterInfoServiceImpl extends BaseImpl implements ServiceCenterInfoService {

	@Resource
	private ServiceCenterInfoDao serviceCenterInfoDao;

	@Resource
	private UserInfoDao userInfoDao;

	@Resource
	private RoleUserDao roleUserDao;

	@Resource
	private UserBiRecordDao userBiRecordDao;

	@Resource
	private BaseDataDao baseDataDao;

	@Resource
	private UserScoreRecordDao userScoreRecordDao;

	@Resource
	private UserRelationParDao userRelationParDao;

	@Resource
	private UserRelationDao userRelationDao;

	@Resource
	private BaseProvinceDao baseProvinceDao;

	@Resource
	private BaseAuditRecordDao baseAuditRecordDao;

	@Resource
	private MsgDao msgDao;

	@Resource
	private MsgReceiverDao msgReceiverDao;

	@Resource
	private SysOperLogDao sysOperLogDao;

	@Resource
	private OrderInfoDao orderInfoDao;

	@Resource
	private BaseImgsDao baseImgsDao;

	@Resource
	private OrderInfoDetailsDao orderInfoDetailsDao;

	@Resource
	private EntpInfoDao entpInfoDao;

	@Resource
	private UserJifenRecordDao userJifenRecordDao;

	@Resource
	private TongjiDao tongjiDao;

	public Integer createServiceCenterInfo(ServiceCenterInfo t) {
		return this.serviceCenterInfoDao.insertEntity(t);
	}

	public Integer createServiceCenterInfo(ServiceCenterInfo t, String[] files) {

		Integer id = this.serviceCenterInfoDao.insertEntity(t);
		if (id != null) {

			BaseImgs baseImg = new BaseImgs();
			baseImg.getMap().put("link_id", id);
			baseImg.getMap().put("img_type", Keys.BaseImgsType.Base_Imgs_TYPE_30.getIndex());
			this.baseImgsDao.deleteEntity(baseImg);

			for (int i = 0; i < files.length; i++) {
				if (StringUtils.isNotBlank(files[i])) {
					BaseImgs baseImgs = new BaseImgs();
					baseImgs.setLink_id(id);
					baseImgs.setImg_type(Keys.BaseImgsType.Base_Imgs_TYPE_30.getIndex());
					baseImgs.setFile_path(files[i]);
					this.baseImgsDao.insertEntity(baseImgs);
				}
			}
		}
		return id;
	}

	public ServiceCenterInfo getServiceCenterInfo(ServiceCenterInfo t) {
		return this.serviceCenterInfoDao.selectEntity(t);
	}

	public Integer getServiceCenterInfoCount(ServiceCenterInfo t) {
		return this.serviceCenterInfoDao.selectEntityCount(t);
	}

	public List<ServiceCenterInfo> getServiceCenterInfoList(ServiceCenterInfo t) {
		return this.serviceCenterInfoDao.selectEntityList(t);
	}

	public int modifyServiceCenterInfo(ServiceCenterInfo t) {
		Integer id = this.serviceCenterInfoDao.updateEntity(t);
		if (null != id) {
			logger.info("=================id==================" + t.getMap().get("user_id"));
			// 发送站内信
			if (null != t.getMap().get("user_id")) {
				logger.info("=================user_id==================");

				Integer user_id = (Integer) t.getMap().get("user_id");
				if (null != t.getAudit_date() && t.getAudit_state() == -1) {
					// 审核驳回发送站内信
					UserInfo uireal = super.getUserInfo(user_id, userInfoDao);
					String msg = StringUtils.replace(SMS.sms_12, "{0}", uireal.getUser_name());
					// msg = StringUtils.replace(msg, "{1}", t.getAudit_desc());
					super.sendMsg(1, user_id, "县域合伙人审核驳回", msg, msgDao, msgReceiverDao, userInfoDao);

				}
				if (null != t.getAudit_date() && t.getAudit_state() == 1) {
					logger.info("=================getAudit_date==================");
					// 审核通过发送站内信
					ServiceCenterInfo sci = new ServiceCenterInfo();
					sci.setId(t.getId());
					sci.setIs_virtual(t.getIs_virtual());
					sci = this.serviceCenterInfoDao.selectEntity(sci);
					if (null != sci && null != sci.getMap().get("full_name")) {
						String area = (String) sci.getMap().get("full_name");
						if (StringUtils.isNotBlank(area)) {
							area = StringUtils.replace(area, ",", "");
						}
						UserInfo uireal = super.getUserInfo(user_id, userInfoDao);
						String msg = StringUtils.replace(SMS.sms_11, "{0}", uireal.getUser_name());
						msg = StringUtils.replace(msg, "{1}", area);
						super.sendMsg(1, user_id, "县域合伙人审核通过", msg, msgDao, msgReceiverDao, userInfoDao);

						ServiceCenterInfo centerInfo = new ServiceCenterInfo();
						centerInfo.setId(sci.getId());
						centerInfo = serviceCenterInfoDao.selectEntity(centerInfo);
						if (null != centerInfo && StringUtils.isBlank(centerInfo.getService_qrcode_path())) {// 已经有的就不在生成了
							Map<String, String> map = new HashMap<String, String>();// 小程序二维码图片
							map.put("path", "pages/country/country");
							map.put("scene", sci.getId().toString());
							String sendGet = HttpUtils.sendGet(Keys.api_url + "/v1/mobile/weixin/getWeixinQrCode", map);
							JSONObject jsonObject = JSONObject.parseObject(sendGet);
							JSONObject url = JSONObject.parseObject(jsonObject.getString("data"));
							String qrcode_path = url.getString("url");
							if (StringUtils.isNotBlank(qrcode_path)) {
								ServiceCenterInfo centerInfoUpdate = new ServiceCenterInfo();
								centerInfoUpdate.setId(sci.getId());
								centerInfoUpdate.setService_qrcode_path(qrcode_path);
								serviceCenterInfoDao.updateEntity(centerInfoUpdate);
							}
						}

					}
				}
			}

			Integer user_id = null; // 审核通过的时候需要去创建用户
			if (null != t.getMap().get("need_create_user_info_by_serViceNo")) {

				UserInfo userInfo = new UserInfo();
				try {
					DESPlus des = new DESPlus();
					userInfo.setPassword(des.encrypt(Keys.INIT_PWD));
				} catch (Exception e) {
					e.printStackTrace();
				}

				ServiceCenterInfo sci = new ServiceCenterInfo();
				sci.setId(t.getId());
				sci.setIs_virtual(t.getIs_virtual());
				sci = this.serviceCenterInfoDao.selectEntity(sci);

				userInfo.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());
				userInfo.setUser_name(sci.getServicecenter_no());
				userInfo.setReal_name(sci.getServicecenter_linkman());
				userInfo.setMobile(sci.getServicecenter_linkman_tel());
				userInfo.setLogin_count(0);
				userInfo.setIs_del(0);
				userInfo.setIs_active(1);// 已激活
				userInfo.setAdd_date(new Date());
				userInfo.setIs_fuwu(1);
				UserInfo userInfoAdmin = super.getUserInfo(Keys.SYS_ADMIN_ID, userInfoDao);
				userInfo.setYmid(userInfoAdmin.getUser_name());
				user_id = this.userInfoDao.insertEntity(userInfo);

				// 这个地方需要调用插入上级用户，合伙人默认直接插入admin
				super.insertUserRelationAndPar(user_id, userInfo.getYmid(), userRelationDao, userInfoDao,
						userRelationParDao);

				t.setAdd_user_id(user_id);
				t.setAdd_user_name(userInfo.getUser_name());
				this.serviceCenterInfoDao.updateEntity(t);
			}

			if (null != t.getMap().get("update_link_user_info")) {
				if (null != t.getMap().get("user_id")) {
					user_id = (Integer) t.getMap().get("user_id");
					UserInfo userInfo = new UserInfo();
					userInfo.setId(user_id);
					if (null != t.getEffect_state() && t.getEffect_state() == 1) {
						userInfo.setIs_fuwu(1);
						userInfo.setIs_entp(0);
						userInfo.setIs_active(1);// 激活账号
						this.userInfoDao.updateEntity(userInfo);
					}
					if (null != t.getIs_del() && t.getIs_del() == 1) {
						userInfo.setIs_fuwu(0);
						userInfo.setIs_entp(0);
						userInfo.setIs_active(0);// 停用账号
						this.userInfoDao.updateEntity(userInfo);
					}
					UserInfo userInfoQuery = new UserInfo();
					userInfoQuery.setId(user_id);
					userInfoQuery = this.userInfoDao.selectEntity(userInfoQuery);
					if (null != userInfoQuery) {
						super.roleUserOpt(userInfoQuery, true, roleUserDao);
					}
				}
			}

			if (null != t.getMap().get("need_create_entp_info_by_serViceNo")) {
				ServiceCenterInfo serviceCenterInfo = new ServiceCenterInfo();
				serviceCenterInfo.setId(t.getId());
				serviceCenterInfo.setIs_virtual(t.getIs_virtual());
				serviceCenterInfo = this.serviceCenterInfoDao.selectEntity(serviceCenterInfo);

				EntpInfo entpInfo = new EntpInfo();
				// 主体信息
				entpInfo.setUuid(UUID.randomUUID().toString());
				entpInfo.setEntp_type(Keys.EntpType.ENTP_TYPE_20.getIndex());// 合伙人商家
				entpInfo.setEntp_linkman(serviceCenterInfo.getServicecenter_linkman());
				entpInfo.setEntp_tel(serviceCenterInfo.getServicecenter_linkman_tel());

				String entp_name = "";
				if (serviceCenterInfo.getIs_virtual().intValue() == 1) {// 虚拟合伙人
					entp_name = serviceCenterInfo.getServicecenter_name();
				} else if (null != serviceCenterInfo.getP_index()) {
					BaseProvince baseProvince = new BaseProvince();
					baseProvince.setP_index(serviceCenterInfo.getP_index().longValue());
					baseProvince = this.baseProvinceDao.selectEntity(baseProvince);
					entp_name = StringUtils.replace(baseProvince.getFull_name(), ",", "") + "九个挑夫运营中心";
				}

				entpInfo.setEntp_name(entp_name);
				entpInfo.setEntp_no(serviceCenterInfo.getServicecenter_no());
				entpInfo.setP_index(serviceCenterInfo.getP_index());
				entpInfo.setAdd_date(new Date());
				entpInfo.setAdd_user_id(user_id);
				entpInfo.setAdd_user_name(serviceCenterInfo.getServicecenter_no());
				entpInfo.setAudit_date(new Date());
				entpInfo.setAudit_user_id(user_id);
				entpInfo.setAudit_state(Keys.audit_state.audit_state_2.getIndex());
				// 附属信息
				entpInfo.setEntp_addr(serviceCenterInfo.getServicecenter_addr());
				entpInfo.setEntp_latlng(serviceCenterInfo.getPosition_latlng());
				entpInfo.setEntp_build_date(serviceCenterInfo.getServicecenter_build_date());
				entpInfo.setEntp_reg_money(serviceCenterInfo.getServicecenter_reg_money());
				entpInfo.setEntp_persons(null == serviceCenterInfo.getServicecenter_persons() ? null
						: serviceCenterInfo.getServicecenter_persons().intValue());
				int entp_id = this.entpInfoDao.insertEntity(entpInfo);
				if (null != user_id) {
					UserInfo userInfoQuery = new UserInfo();
					userInfoQuery.setId(user_id);
					userInfoQuery.setOwn_entp_id(entp_id);
					userInfoQuery.setOwn_entp_name(entpInfo.getEntp_name());
					this.userInfoDao.updateEntity(userInfoQuery);
				}

			}

			if (null != t.getMap().get("cancel_link_user_info_is_fuwu")) {// 取消合伙人，修改关联用户信息
				// ---操作日志----
				SysOperLog sysOperLog = new SysOperLog();
				sysOperLog.setOper_type(Keys.SysOperType.SysOperType_20.getIndex());
				sysOperLog.setOper_name(Keys.SysOperType.SysOperType_20.getName());
				sysOperLog.setOper_time(new Date());
				if (null != t.getMap().get("cancel_oper_uid")
						&& GenericValidator.isInt(t.getMap().get("cancel_oper_uid").toString())) {
					sysOperLog.setOper_uid(new Integer(t.getMap().get("cancel_oper_uid").toString()));
				}
				if (null != t.getMap().get("cancel_oper_uname")) {
					sysOperLog.setOper_uname(t.getMap().get("cancel_oper_uname").toString());
				}
				sysOperLog.setLink_id(t.getId());
				ServiceCenterInfo s = new ServiceCenterInfo();
				s.setId(t.getId());
				s.setIs_virtual(t.getIs_virtual());
				s = this.getServiceCenterInfo(s);
				if (null != s) {
					String oper_memo = "取消合伙人：" + s.getServicecenter_name();
					if (null != s.getEffect_date()) {
						oper_memo += ",生效时间：" + DateTools.getStringDate(s.getEffect_date());
					}
					if (null != s.getEffect_end_date()) {
						oper_memo += ",失效时间：" + DateTools.getStringDate(s.getEffect_end_date());
					}

					sysOperLog.setOper_memo(oper_memo);
				}
				this.sysOperLogDao.insertEntity(sysOperLog);
				// ---操作日志----
				user_id = (Integer) t.getMap().get("user_id");
				if (null != user_id) {
					UserInfo userInfo = new UserInfo();
					userInfo.setId(user_id);
					if (null != t.getAudit_state() && t.getAudit_state() == -1) {
						userInfo.setIs_fuwu(0);// 取消合伙人
						userInfo.setIs_entp(0);// 取消商家
						userInfo.setIs_active(0);// 停用账号
						this.userInfoDao.updateEntity(userInfo);

						// 更新BasePro表,更新区域表中是否是合伙人和合伙人数量
						if (null != t.getP_index()) {
							super.updateBasePro(t.getP_index(), -1, baseProvinceDao);
						}

					}
					BaseAuditRecord baseAuditRecord = new BaseAuditRecord();
					baseAuditRecord.setLink_id(t.getId());
					baseAuditRecord.setLink_table("ServiceCenterInfo");
					baseAuditRecord.setAudit_note(t.getAudit_desc());
					baseAuditRecord.setAudit_user_id(t.getAudit_user_id());
					baseAuditRecord.setAudit_state(t.getAudit_state());
					UserInfo ui = new UserInfo();
					ui.setId(t.getAudit_user_id());
					ui = this.userInfoDao.selectEntity(ui);

					baseAuditRecord.setOpt_type(Keys.OptType.OPT_TYPE_1.getIndex());
					baseAuditRecord.setAudit_user_name(ui.getUser_name());
					baseAuditRecordDao.insertEntity(baseAuditRecord);

					UserInfo userInfoQuery = new UserInfo();
					userInfoQuery.setId(user_id);
					userInfoQuery = this.userInfoDao.selectEntity(userInfoQuery);
					if (null != userInfoQuery) {
						super.roleUserOpt(userInfoQuery, true, roleUserDao);
					}

					if (null != userInfoQuery && null != userInfoQuery.getOwn_entp_id()) {// 更新合伙人对于商家的审核状态
						EntpInfo entpInfo = new EntpInfo();
						entpInfo.setId(userInfoQuery.getOwn_entp_id());
						entpInfo.setAudit_date(new Date());
						entpInfo.setAdd_user_id(t.getAudit_user_id());
						entpInfo.setAudit_state(-1);// 审核不通过
						this.entpInfoDao.updateEntity(entpInfo);
					}

				}
			}

			if (null != t.getMap().get("do_error_cancel_link_user_info_is_fuwu")) {// 误操作 取消合伙人

				user_id = (Integer) t.getMap().get("user_id");
				if (null != user_id) {
					UserInfo userInfo = new UserInfo();
					userInfo.setId(user_id);
					userInfo.setIs_fuwu(1);
					this.userInfoDao.updateEntity(userInfo);
					// 更新BasePro表,更新区域表中是否是合伙人和合伙人数量
					if (null != t.getP_index()) {
						super.updateBasePro(t.getP_index(), 1, baseProvinceDao);
					}

					UserInfo userInfoQuery = new UserInfo();
					userInfoQuery.setId(user_id);
					userInfoQuery = this.userInfoDao.selectEntity(userInfoQuery);
					if (null != userInfoQuery) {
						super.roleUserOpt(userInfoQuery, true, roleUserDao);
					}
				}
			}
			// if (null != t.getMap().get("only_update_link_basePro")) { // update BasePro 合伙人数量fwcount
			// 更新BasePro表
			// super.updateBasePro(t.getP_index(), 1, baseProvinceDao);
			// }
		}
		return id;
	}

	public int removeServiceCenterInfo(ServiceCenterInfo t) {
		return this.serviceCenterInfoDao.deleteEntity(t);
	}

	public List<ServiceCenterInfo> getServiceCenterInfoPaginatedList(ServiceCenterInfo t) {
		return this.serviceCenterInfoDao.selectEntityPaginatedList(t);
	}

	public List<ServiceCenterInfo> getServiceCenterInfoPaiMingList(ServiceCenterInfo t) {
		return this.serviceCenterInfoDao.selectServiceCenterInfoPaiMingList(t);
	}

	public List<ServiceCenterInfo> getMyServiceCenterInfoList(ServiceCenterInfo t) {
		return this.serviceCenterInfoDao.selectMyServiceCenterInfoList(t);
	}

	public List<HashMap> getServiceCenterInfoCountByPIndex(ServiceCenterInfo t) {
		return this.serviceCenterInfoDao.selectServiceCenterInfoCountByPIndex(t);
	}

}
