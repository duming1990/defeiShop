package com.ebiz.webapp.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.CommInfoDao;
import com.ebiz.webapp.dao.EntpContentDao;
import com.ebiz.webapp.dao.EntpInfoDao;
import com.ebiz.webapp.dao.MsgDao;
import com.ebiz.webapp.dao.MsgReceiverDao;
import com.ebiz.webapp.dao.RoleUserDao;
import com.ebiz.webapp.dao.UserBiRecordDao;
import com.ebiz.webapp.dao.UserInfoDao;
import com.ebiz.webapp.dao.UserRelationDao;
import com.ebiz.webapp.dao.UserRelationParDao;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.EntpContent;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.service.EntpInfoService;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.SMS;
import com.ebiz.webapp.web.util.DESPlus;

/**
 * @author Wu,Yang
 * @version 2012-03-21 17:16
 */
@Service
public class EntpInfoServiceImpl extends BaseImpl implements EntpInfoService {

	@Resource
	private EntpInfoDao entpInfoDao;

	@Resource
	private UserInfoDao userInfoDao;

	@Resource
	private RoleUserDao roleUserDao;

	@Resource
	private CommInfoDao commInfoDao;

	@Resource
	private UserRelationParDao userRelationParDao;

	@Resource
	private UserBiRecordDao userBiRecordDao;

	@Resource
	private MsgDao msgDao;

	@Resource
	private MsgReceiverDao msgReceiverDao;

	@Resource
	private EntpContentDao entpContentDao;

	@Resource
	private UserRelationDao userRelationDao;

	@Override
	public Integer createEntpInfo(EntpInfo t) {
		Integer id = this.entpInfoDao.insertEntity(t);
		String password = (String) t.getMap().get("password");
		if (null != t.getMap().get("Supermarket") && null != password && id > 0) {// 无人超市添加店铺人
			UserInfo entity = new UserInfo();
			entity.setUser_name(t.getEntp_no());
			entity.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());
			try {
				DESPlus des = new DESPlus();
				entity.setPassword(des.encrypt(password));
			} catch (Exception e) {
				e.printStackTrace();
			}
			entity.setAdd_date(new Date());
			entity.setAdd_user_id(new Integer(1));
			entity.setIs_active(1);
			entity.setIs_has_update_pass(0);

			UserInfo user_ymid = new UserInfo();
			user_ymid.getMap().put("ym_id", "admin");
			user_ymid.setIs_del(0);
			user_ymid = this.userInfoDao.selectEntity(user_ymid);
			entity.setYmid(user_ymid.getUser_name());// 全部保存用户名

			entity.setIs_entp(1);
			entity.setOwn_entp_id(id);
			entity.setOwn_entp_name(t.getEntp_name());
			Integer user_id = this.userInfoDao.insertEntity(entity);

			UserInfo uinfo = new UserInfo();
			uinfo.setId(user_id);
			uinfo = userInfoDao.selectEntity(uinfo);

			super.roleUserOpt(uinfo, false, roleUserDao);

			super.insertUserRelationAndPar(user_id, entity.getYmid(), userRelationDao, userInfoDao, userRelationParDao);

			// 修改企业的添加人ID为新创建的用户id
			EntpInfo ei = new EntpInfo();
			ei.setId(id);
			ei.setAdd_user_id(uinfo.getId());
			ei.setAdd_user_name(uinfo.getUser_name());
			this.entpInfoDao.updateEntity(ei);
		}

		// 插入商家详细信息
		String entp_content = (null == t.getMap().get("entp_content")) ? "" : t.getMap().get("entp_content").toString();
		EntpContent entpContent = new EntpContent();
		entpContent.setType(0);
		entpContent.setEntp_id(id);
		entpContent.setContent(entp_content);
		this.entpContentDao.insertEntity(entpContent);

		return id;
	}

	@Override
	public EntpInfo getEntpInfo(EntpInfo t) {
		return this.entpInfoDao.selectEntity(t);
	}

	@Override
	public Integer getEntpInfoCount(EntpInfo t) {
		return this.entpInfoDao.selectEntityCount(t);
	}

	@Override
	public List<EntpInfo> getEntpInfoList(EntpInfo t) {
		return this.entpInfoDao.selectEntityList(t);
	}

	@Override
	public int modifyEntpInfo(EntpInfo t) {
		int id = this.entpInfoDao.updateEntity(t);
		if (null != t.getId() && null != t.getMap().get("update_entp_content")) {
			String entp_content = (null == t.getMap().get("entp_content")) ? ""
					: t.getMap().get("entp_content").toString();
			EntpContent entpContent = new EntpContent();
			entpContent.setType(0);
			entpContent.setEntp_id(t.getId());
			entpContent = this.entpContentDao.selectEntity(entpContent);

			if (null != entpContent) {
				entpContent.setContent(entp_content);
				this.entpContentDao.updateEntity(entpContent);
			}
		}

		// 审核通过的时候，需要站内信通知合伙人
		// 如果发现店铺有对应的用户，需要修改用户权限以及其他信息
		if (null != t.getMap().get("update_user_info")) {
			Integer user_id = (Integer) t.getMap().get("user_id");
			UserInfo ui = new UserInfo();
			ui.setId(user_id);
			if (null != t.getAudit_state() && t.getAudit_state() == 2) {
				ui.setIs_entp(1);
				ui.setOwn_entp_id(t.getId());
				ui.setOwn_entp_name(t.getEntp_name());

				this.userInfoDao.updateEntity(ui);
				// 审核通过发送站内信
				UserInfo uireal = super.getUserInfo(user_id, userInfoDao);
				String msg = StringUtils.replace(SMS.sms_07, "{0}", uireal.getUser_name());
				super.sendMsg(1, user_id, "商家申请审核通过", msg, msgDao, msgReceiverDao, userInfoDao);

			} else {// 商家审核不通过
				// 审核不通过发送站内信
				if (null != t.getAudit_state() && (t.getAudit_state() == -2 || t.getAudit_state() == -1)
						|| t.getAudit_state() == 0) {

					ui.setIs_entp(0);
					ui.setOwn_entp_id(t.getId());
					ui.setOwn_entp_name(t.getEntp_name());
					this.userInfoDao.updateEntity(ui);

					if (t.getAudit_state() == -2) {
						UserInfo uireal = super.getUserInfo(user_id, userInfoDao);
						String msg = StringUtils.replace(SMS.sms_08, "{0}", uireal.getUser_name());
						msg = StringUtils.replace(msg, "{1}", t.getAudit_desc_two());
						super.sendMsg(Keys.MSG_TYPE.MSG_TYPE_30.getIndex(), 1, user_id, "商家申请审核驳回", msg, msgDao,
								msgReceiverDao, userInfoDao);
					}
				}
				// 审核不通过商家所属商品下架
				if (null != t.getId() && null != t.getMap().get("update_entp_comm")) {
					// CommInfo commInfo = new CommInfo();
					// commInfo.setOwn_entp_id(t.getId());
					// List<CommInfo> commList = this.commInfoDao.selectEntityList(commInfo);
					// if (null != commList && commList.size() > 0) {
					//
					//
					// for (CommInfo temp : commList) {
					// temp.setUpdate_date(new Date());
					// temp.setIs_sell(0);
					// this.commInfoDao.selectEntityList(temp);
					// }
					// }
				}
			}

			// 如果企业被删除需要找到对应的用户更新用户信息
			if (null != t.getIs_del() && t.getIs_del() == 1) {
				UserInfo userInfoByEntpId = new UserInfo();
				userInfoByEntpId.setOwn_entp_id(t.getId());
				userInfoByEntpId = userInfoDao.selectEntity(userInfoByEntpId);
				if (null != userInfoByEntpId) {
					ui.setId(userInfoByEntpId.getId());
					ui.setIs_entp(0);
					this.userInfoDao.updateEntity(ui);
				}
			}

			UserInfo userInfoQuery = new UserInfo();
			userInfoQuery.setId(user_id);
			userInfoQuery = this.userInfoDao.selectEntity(userInfoQuery);
			if (null != userInfoQuery) {
				super.roleUserOpt(userInfoQuery, true, roleUserDao);
			}
		}
		return id;
	}

	public Integer modifyEntpInfoForCancel(EntpInfo t) {
		int id = this.entpInfoDao.updateEntity(t);

		Integer user_id = (Integer) t.getMap().get("user_id");
		UserInfo ui = new UserInfo();
		ui.setId(user_id);
		ui.setIs_entp(0);
		this.userInfoDao.updateEntity(ui);

		UserInfo userInfoQuery = new UserInfo();
		userInfoQuery.setId(user_id);
		userInfoQuery = this.userInfoDao.selectEntity(userInfoQuery);
		if (null != userInfoQuery) {
			super.roleUserOpt(userInfoQuery, true, roleUserDao);
		}
		return id;
	}

	@Override
	public int removeEntpInfo(EntpInfo t) {
		int flag = this.entpInfoDao.updateEntity(t);
		if (flag > 0) {
			if (null != t.getMap().get("update_link_userInfo")) {
				if (null != t.getMap().get("userInfo")) {
					UserInfo userInfo = (UserInfo) t.getMap().get("userInfo");
					userInfo.getMap().put("own_entp_id", t.getId());
					userInfo.setIs_del(1);
					this.userInfoDao.updateEntity(userInfo);
				}
			}
		}

		return flag;
	}

	@Override
	public List<EntpInfo> getEntpInfoPaginatedList(EntpInfo t) {
		return this.entpInfoDao.selectEntityPaginatedList(t);
	}

	@Override
	public List<EntpInfo> getEntpInfoDistance(EntpInfo t) {
		return this.entpInfoDao.selectEntpInfoDistance(t);
	}

	@Override
	public void modifyEntpInfoListForAutoEntpFanXianRule(EntpInfo t) {
		// 第一步：查询所有符合条件的企业列表（当前时间>新返现规则生效时间，修改后返现规则不为空，返现规则不等于修改后返现规则）
		// List<EntpInfo> entpInfoList = this.entpInfoDao.selectEntityList(t);
		// if (null != entpInfoList && entpInfoList.size() > 0) {
		// for (EntpInfo e : entpInfoList) {
		// EntpInfo temp = new EntpInfo();
		// temp.setId(e.getId());
		// temp.setFanxian_rule(e.getFanxian_rule_new());// 更新返现规则
		// this.entpInfoDao.updateEntity(temp);
		// }
		// }
	}

	@Override
	public void modifyEntpAndCommState(EntpInfo t, CommInfo c) {
		// List<EntpInfo> entpInfoList = this.entpInfoDao.selectEntityList(t);
		// List<CommInfo> commInfoList = this.commInfoDao.selectEntityList(c);
		// if (null != entpInfoList && entpInfoList.size() > 0) {
		// for (EntpInfo e : entpInfoList) {
		// EntpInfo temp = new EntpInfo();
		// temp.setId(e.getId());
		// temp.setIs_closed(Keys.IsClosed.IS_CLOSED_1.getIndex());// 关店
		// this.entpInfoDao.updateEntity(temp);
		// }
		// }
		// if (null != commInfoList && commInfoList.size() > 0) {
		// for (CommInfo ci : commInfoList) {
		// CommInfo temp = new CommInfo();
		// temp.setId(ci.getId());
		// temp.setIs_sell(Keys.IsSell.IS_SELL_0.getIndex());// 下架
		// this.commInfoDao.updateEntity(temp);
		// }
		// }
	}

}
