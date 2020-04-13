package com.ebiz.webapp.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ebiz.webapp.dao.BaseDataDao;
import com.ebiz.webapp.dao.BaseImgsDao;
import com.ebiz.webapp.dao.OrderInfoDao;
import com.ebiz.webapp.dao.OrderInfoDetailsDao;
import com.ebiz.webapp.dao.RoleUserDao;
import com.ebiz.webapp.dao.ServiceCenterInfoDao;
import com.ebiz.webapp.dao.SysSettingDao;
import com.ebiz.webapp.dao.TongjiDao;
import com.ebiz.webapp.dao.UserBiRecordDao;
import com.ebiz.webapp.dao.UserInfoDao;
import com.ebiz.webapp.dao.UserJifenRecordDao;
import com.ebiz.webapp.dao.UserRelationDao;
import com.ebiz.webapp.dao.UserRelationParDao;
import com.ebiz.webapp.dao.UserScoreRecordDao;
import com.ebiz.webapp.dao.UserSecurityDao;
import com.ebiz.webapp.domain.BaseImgs;
import com.ebiz.webapp.domain.SysSetting;
import com.ebiz.webapp.domain.Tongji;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.UserRelation;
import com.ebiz.webapp.domain.UserRelationPar;
import com.ebiz.webapp.domain.UserSecurity;
import com.ebiz.webapp.service.UserInfoService;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 * @version 2011-04-20 02:13:04
 */
@Service
public class UserInfoServiceImpl extends BaseImpl implements UserInfoService {

	@Resource
	private UserInfoDao userInfoDao;

	@Resource
	private UserBiRecordDao userBiRecordDao;

	@Resource
	private BaseDataDao baseDataDao;

	@Resource
	private UserScoreRecordDao userScoreRecordDao;

	@Resource
	private UserRelationDao userRelationDao;

	@Resource
	private UserRelationParDao userRelationParDao;

	@Resource
	private RoleUserDao roleUserDao;

	@Resource
	private UserSecurityDao userSecurityDao;

	@Resource
	private TongjiDao tongjiDao;

	@Resource
	private BaseImgsDao baseImgsDao;

	@Resource
	private OrderInfoDao orderInfoDao;

	@Resource
	private OrderInfoDetailsDao orderInfoDetailsDao;

	@Resource
	private ServiceCenterInfoDao serviceCenterInfoDao;

	@Resource
	private SysSettingDao sysSettingDao;

	@Resource
	private UserJifenRecordDao userJifenRecordDao;

	@Override
	public Integer createUserInfo(UserInfo t) {
		Integer user_id = this.userInfoDao.insertEntity(t);

		// 用户注册积分 已废弃
		// super.updateUserInfoUserLevel(user_id, t.getCur_score(), baseDataDao, userInfoDao, userScoreRecordDao);

		int user_type = t.getUser_type().intValue();
		if (user_type != Keys.UserType.USER_TYPE_100.getIndex()) {// 后台管理用户不算注册的用户数
			// 注册成功后，用户注册统计数量加一.!数据库已增加了记录，此处只更新了
			Tongji tongJi = new Tongji();
			tongJi.getMap().put("tongji_type", Keys.TongjiType.TONGJITYPE_10.getIndex());
			tongJi.getMap().put("add_tongji_num1", 1);
			tongJi.setModify_date(new Date());
			tongJi.setModify_uid(user_id);
			tongjiDao.updateEntity(tongJi);
		}

		String update_user_name = (String) t.getMap().get("update_user_name");

		if (StringUtils.isNotBlank(update_user_name)) {
			UserInfo uiupdate = new UserInfo();
			uiupdate.setId(user_id);
			uiupdate.setUser_name("USER" + user_id);// 这里更新下用户名
			userInfoDao.updateEntity(uiupdate);
		}
		String update_user_name_and_real_name = (String) t.getMap().get("update_user_name_and_real_name");

		if (StringUtils.isNotBlank(update_user_name_and_real_name)) {
			UserInfo uiupdate = new UserInfo();
			uiupdate.setId(user_id);
			uiupdate.setUser_name("USER" + user_id);// 这里更新下用户名
			uiupdate.setReal_name(uiupdate.getUser_name());
			userInfoDao.updateEntity(uiupdate);
		}

		if (null != t.getMap().get("insert_user_realtion")) {
			super.insertUserRelationAndPar(user_id, t.getYmid(), userRelationDao, userInfoDao, userRelationParDao);
		}

		// 注册成功后，赠送20// TAG 20160107
		// if (null != t.getMap().get("xiaofeibi")) {
		// BigDecimal bi_no = new BigDecimal(t.getMap().get("xiaofeibi").toString());
		// super.insertUserBiRecord(user_id, 1, null, bi_no, Keys.BiType.BI_TYPE_200.getIndex(),
		// Keys.BiGetType.BI_GET_TYPE_802.getIndex(), userInfoDao, userBiRecordDao);
		// }

		UserInfo uinfo = new UserInfo();
		uinfo.setId(user_id);
		uinfo = userInfoDao.selectEntity(uinfo);
		super.roleUserOpt(uinfo, false, roleUserDao);// 用户角色操作，主要为自动授权用

		return user_id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int modifyUserInfo(UserInfo t) {

		// 修改用户扶贫金
		if (null != t.getMap().get("update_bi_aid")) {
			UserInfo selectUser = super.getUserInfo(t.getId(), userInfoDao);
			if (null == selectUser) {
				return -1;
			}
			BigDecimal bi_aid = new BigDecimal(t.getMap().get("update_bi_aid").toString());

			return super.insertUserBiAid(selectUser.getPoor_id(), Keys.BiType.BI_TYPE_500.getIndex(), null, null,
					bi_aid, userInfoDao, userBiRecordDao);

		}

		if (null != t.getMap().get("basefiles")) {
			if (null != t.getId()) {
				BaseImgs img = new BaseImgs();
				img.getMap().put("link_id", t.getId());
				img.getMap().put("img_type", Keys.BaseImgsType.Base_Imgs_TYPE_10.getIndex());
				baseImgsDao.deleteEntity(img);

				String[] bfs = (String[]) t.getMap().get("basefiles");
				for (int i = 0; i < bfs.length; i++) {
					if (StringUtils.isNotBlank(bfs[i])) {
						BaseImgs baseImgs = new BaseImgs();
						baseImgs.setLink_id(t.getId());
						baseImgs.setImg_type(Keys.BaseImgsType.Base_Imgs_TYPE_10.getIndex());
						baseImgs.setFile_path(bfs[i]);
						baseImgsDao.insertEntity(baseImgs);
					} else {
						continue;
					}
				}
			}
		}
		if (null != t.getMap().get("inserUserSecurityList")) {
			List<UserSecurity> inserUserSecurityList = (List<UserSecurity>) t.getMap().get("inserUserSecurityList");
			if ((null != inserUserSecurityList) && (inserUserSecurityList.size() > 0)) {
				// 先删后增
				UserSecurity tempDelete = new UserSecurity();
				tempDelete.getMap().put("user_id", t.getId());
				userSecurityDao.deleteEntity(tempDelete);

				for (UserSecurity temp : inserUserSecurityList) {
					userSecurityDao.insertEntity(temp);
				}
			}
		}
		// 更新用户名 然后更新关联的ym_id
		if (null != t.getMap().get("update_user_name_update_ym_id")) {
			// 先去查询哪些人关联的该用户
			UserRelation userRelation = new UserRelation();
			userRelation.setUser_par_id(t.getId());
			List<UserRelation> userRelationList = this.userRelationDao.selectEntityList(userRelation);
			if (null != userRelationList && userRelationList.size() > 0) {
				for (UserRelation temp : userRelationList) {// update userInfo ym_id
					if (null != temp.getUser_id()) {
						UserInfo userInfo = super.getUserInfo(temp.getUser_id(), userInfoDao);
						if (null != userInfo) {
							UserInfo userInfoUpdate = new UserInfo();
							userInfoUpdate.setId(userInfo.getId());
							userInfoUpdate.setYmid(t.getUser_name());
							this.userInfoDao.updateEntity(userInfoUpdate);
						}
					}
				}
			}
		}

		if (null != t.getMap().get("addUserScore")) {// 添加积分并升级，给直接上级添加积分并升级

			addUserScore(t, true, userScoreRecordDao, userInfoDao, userRelationParDao, baseDataDao);
		}
		// 变更用户关系
		if (null != t.getMap().get("update_user_relation")) {
			this.modifyUserInfoRelation(t);
		}

		if (null != t.getMap().get("pay_success_create_orderInfo")) {

			UserInfo userInfo = new UserInfo();
			userInfo.setId(t.getId());
			userInfo = this.userInfoDao.selectEntity(userInfo);
			if (null != userInfo) {
				String trade_index = t.getMap().get("out_trade_no").toString();
				String trade_no = t.getMap().get("trade_no").toString();
				String pay_type = t.getMap().get("pay_type").toString();
				BigDecimal order_money = new BigDecimal(t.getMap().get("total_fee").toString());

				createOrderInfoPublic(order_money, userInfo, trade_index, Keys.OrderType.ORDER_TYPE_20.getName(),
						Integer.valueOf(pay_type), Keys.OrderType.ORDER_TYPE_20.getIndex(), orderInfoDao,
						orderInfoDetailsDao, trade_no);
			}
		}

		int row = this.userInfoDao.updateEntity(t);

		if (null != t.getMap().get("update_role_user") && t.getId().intValue() != 1) {// 不是管理员才修改用户角色后权限管理也需要修改
			UserInfo uinfo = new UserInfo();
			uinfo.setId(t.getId());
			uinfo = userInfoDao.selectEntity(uinfo);
			super.roleUserOpt(uinfo, true, roleUserDao);
		}

		// 在线升级
		if (null != t.getMap().get("set_user_level")) {
			// 生成对应订单
			UserInfo userInfo = new UserInfo();
			userInfo.setId(t.getId());
			userInfo = this.userInfoDao.selectEntity(userInfo);
			if (null != userInfo) {
				SysSetting sysSetting = new SysSetting();
				sysSetting.setTitle(Keys.upLevelNeedPayMoney);
				sysSetting = this.sysSettingDao.selectEntity(sysSetting);
				createOrderInfoPublic(new BigDecimal(sysSetting.getContent()), userInfo, super.creatTradeIndex(),
						Keys.OrderType.ORDER_TYPE_20.getName(), Keys.PayType.PAY_TYPE_0.getIndex(),
						Keys.OrderType.ORDER_TYPE_20.getIndex(), orderInfoDao, orderInfoDetailsDao, null);
			}

		}

		if (row == 0) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return 0;// 操作失败，比如减少余额或者减少货款失败了
		}

		return row;
	}

	@Override
	public int removeUserInfo(UserInfo t) {
		return this.userInfoDao.deleteEntity(t);
	}

	@Override
	public UserInfo getUserInfo(UserInfo t) {
		return this.userInfoDao.selectEntity(t);
	}

	@Override
	public Integer getUserInfoCount(UserInfo t) {
		return this.userInfoDao.selectEntityCount(t);
	}

	@Override
	public List<UserInfo> getUserInfoList(UserInfo t) {
		return this.userInfoDao.selectEntityList(t);
	}

	@Override
	public List<UserInfo> getUserInfoPaginatedList(UserInfo t) {
		return this.userInfoDao.selectEntityPaginatedList(t);
	}

	@Override
	public UserInfo getUserInfoWithSum(UserInfo t) {
		return this.userInfoDao.selectUserInfoWithSum(t);
	}

	public void updateUserInfoYmIdAndInsertUserRelation(UserInfo t) {
		super.insertUserRelationAndPar(t.getId(), t.getYmid(), userRelationDao, userInfoDao, userRelationParDao);
	}

	@Override
	public void modifyUserInfoRelation(UserInfo t) {
		if (null != t.getId()) {
			// 1、 先删除掉自己的所有的userRelation 和 userRelationPar表中相关的数据 然后在重新插入
			removeUserInfoRelationAndInsertUserInfoRelation(t);

			// 2、查询自己是否有子元素 然后修复子元素
			List<UserRelation> sonList = new ArrayList<UserRelation>();
			List<UserRelation> getUserRelationSonList = super.getUserRelationSonList(t.getId(), sonList,
					userRelationDao);

			if (null != getUserRelationSonList && getUserRelationSonList.size() > 0) {
				for (UserRelation temp : getUserRelationSonList) {
					UserInfo userInfoSon = new UserInfo();
					userInfoSon.setId(temp.getUser_id());
					userInfoSon = this.userInfoDao.selectEntity(userInfoSon);
					if (null != userInfoSon) {
						removeUserInfoRelationAndInsertUserInfoRelation(userInfoSon);
					}
				}
			}

		}
	}

	public void removeUserInfoRelationAndInsertUserInfoRelation(UserInfo t) {

		// 删除
		UserRelation userRelation = new UserRelation();
		userRelation.getMap().put("user_id", t.getId());
		this.userRelationDao.deleteEntity(userRelation);
		// 删除
		UserRelationPar userRelationPar = new UserRelationPar();
		userRelationPar.getMap().put("user_id", t.getId());
		this.userRelationParDao.deleteEntity(userRelationPar);
		// 增加
		super.insertUserRelationAndPar(t.getId(), t.getYmid(), userRelationDao, userInfoDao, userRelationParDao);
	}

	@Override
	public Integer getSpokesmanRankingListCount(UserInfo t) {
		return this.userInfoDao.selectSpokesmanRankingListCount(t);
	}

	@Override
	public List<UserInfo> getSpokesmanRankingList(UserInfo t) {
		return this.userInfoDao.selectSpokesmanRankingList(t);
	}

	@Override
	public List<UserInfo> getUserSpeciaList(UserInfo t) {
		return this.userInfoDao.selectUserSpeciaList(t);
	}

	@Override
	public Integer getUserSpeciaCount(UserInfo t) {
		return this.userInfoDao.selectUserSpeciaCount(t);
	}

}
