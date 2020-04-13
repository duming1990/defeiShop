package com.ebiz.webapp.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.dao.BaseAuditRecordDao;
import com.ebiz.webapp.dao.BaseClassDao;
import com.ebiz.webapp.dao.BaseDataDao;
import com.ebiz.webapp.dao.BaseProvinceDao;
import com.ebiz.webapp.dao.CardInfoDao;
import com.ebiz.webapp.dao.CommInfoDao;
import com.ebiz.webapp.dao.CommInfoPoorsDao;
import com.ebiz.webapp.dao.CommTczhPriceDao;
import com.ebiz.webapp.dao.EntpInfoDao;
import com.ebiz.webapp.dao.JdAreasDao;
import com.ebiz.webapp.dao.MsgDao;
import com.ebiz.webapp.dao.MsgReceiverDao;
import com.ebiz.webapp.dao.OrderInfoDao;
import com.ebiz.webapp.dao.OrderInfoDetailsDao;
import com.ebiz.webapp.dao.OrderReturnInfoDao;
import com.ebiz.webapp.dao.PoorCuoShiDao;
import com.ebiz.webapp.dao.PoorInfoDao;
import com.ebiz.webapp.dao.RoleUserDao;
import com.ebiz.webapp.dao.ServiceCenterInfoDao;
import com.ebiz.webapp.dao.ShippingAddressDao;
import com.ebiz.webapp.dao.SysOperLogDao;
import com.ebiz.webapp.dao.TongjiDao;
import com.ebiz.webapp.dao.UserBiRecordDao;
import com.ebiz.webapp.dao.UserInfoDao;
import com.ebiz.webapp.dao.UserJifenRecordDao;
import com.ebiz.webapp.dao.UserRelationDao;
import com.ebiz.webapp.dao.UserRelationParDao;
import com.ebiz.webapp.dao.UserScoreRecordDao;
import com.ebiz.webapp.dao.VillageDynamicRecordDao;
import com.ebiz.webapp.dao.VillageInfoDao;
import com.ebiz.webapp.dao.WlCompInfoDao;
import com.ebiz.webapp.dao.WlOrderInfoDao;
import com.ebiz.webapp.domain.BaseAuditRecord;
import com.ebiz.webapp.domain.BaseClass;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.CardInfo;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommInfoPoors;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.Msg;
import com.ebiz.webapp.domain.MsgReceiver;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.OrderReturnInfo;
import com.ebiz.webapp.domain.PoorCuoShi;
import com.ebiz.webapp.domain.PoorInfo;
import com.ebiz.webapp.domain.RoleUser;
import com.ebiz.webapp.domain.ShippingAddress;
import com.ebiz.webapp.domain.SysOperLog;
import com.ebiz.webapp.domain.Tongji;
import com.ebiz.webapp.domain.UserBiRecord;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.UserJifenRecord;
import com.ebiz.webapp.domain.UserRelation;
import com.ebiz.webapp.domain.UserRelationPar;
import com.ebiz.webapp.domain.UserScoreRecord;
import com.ebiz.webapp.domain.VillageDynamicRecord;
import com.ebiz.webapp.domain.VillageInfo;
import com.ebiz.webapp.domain.WlCompInfo;
import com.ebiz.webapp.domain.WlOrderInfo;
import com.ebiz.webapp.util.JdApiUtil;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.Keys.BiGetType;
import com.ebiz.webapp.web.Keys.SysOperType;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

/**
 * @author 基础公共方法类
 * @version 2015-12-01
 */
@Service
public class BaseImpl {
	protected static final Logger logger = LoggerFactory.getLogger(BaseImpl.class);

	@Resource
	private BaseDataDao baseDataDao;

	@Resource
	private JdAreasDao jdAreasDao;

	@Resource
	private ShippingAddressDao shippingAddressDao;

	@Resource
	private BaseProvinceDao baseProvinceDao;

	@Resource
	private CommInfoDao commInfoDao;

	@Resource
	private WlCompInfoDao wlCompInfoDao;

	@Resource
	private WlOrderInfoDao wlOrderInfoDao;

	@Resource
	private BaseImplForTiaoFu baseImplForTiaoFu;

	@Resource
	private VillageInfoDao villageInfoDao;

	@Resource
	private SysOperLogDao sysOperLogDao;

	@Resource
	private CardInfoDao cardInfoDao;

	/**
	 * 发送站内信BaseImpl版本
	 */
	public void sendMsg(Integer src_uid, Integer dest_uid, String msg_title, String msg_content, MsgDao msgDao,
			MsgReceiverDao msgReceiverDao, UserInfoDao userInfoDao) {
		try {
			String dest_uname = "";
			UserInfo desc_ui = this.getUserInfo(dest_uid, userInfoDao);
			if (null == desc_ui) {
				return;
			}
			dest_uname = desc_ui.getUser_name() + "(" + desc_ui.getMobile() + ")";

			String src_uname = "站内通知";
			if (src_uid.intValue() != 1) {// 不是系统管理员
				UserInfo src_ui = this.getUserInfo(src_uid, userInfoDao);
				if (null != src_ui) {
					src_uname = src_ui.getUser_name();
				}
			}
			Msg msg = new Msg();
			msg.setMsg_type(Keys.MSG_TYPE.MSG_TYPE_0.getIndex());
			msg.setMsg_title(msg_title);
			msg.setMsg_content(msg_content);
			msg.setUser_id(src_uid);
			msg.setUser_name(src_uname);
			msg.setSend_time(new Date());
			msg.setInfo_state(1);
			int msg_id = msgDao.insertEntity(msg);

			MsgReceiver mr = new MsgReceiver();
			mr.setMsg_id(msg_id);
			mr.setUser_id(src_uid);
			mr.setReceiver_user_id(dest_uid);
			mr.setReceiver_user_mobile(dest_uname);
			mr.setIs_del(0);
			mr.setIs_read(0);
			mr.setIs_reply(0);
			msgReceiverDao.insertEntity(mr);
		} catch (Exception e) {
			logger.info("==SendMsg imp Error:{}", e.getMessage());
		}
	}

	/**
	 * 发送站内信BaseImpl版本
	 */
	public void sendMsg(Integer type, Integer src_uid, Integer dest_uid, String msg_title, String msg_content,
			MsgDao msgDao, MsgReceiverDao msgReceiverDao, UserInfoDao userInfoDao) {
		try {
			String dest_uname = "";
			UserInfo desc_ui = this.getUserInfo(dest_uid, userInfoDao);
			if (null == desc_ui) {
				return;
			}
			dest_uname = desc_ui.getUser_name() + "(" + desc_ui.getMobile() + ")";

			String src_uname = "站内通知";
			if (src_uid.intValue() != 1) {// 不是系统管理员
				UserInfo src_ui = this.getUserInfo(src_uid, userInfoDao);
				if (null != src_ui) {
					src_uname = src_ui.getUser_name();
				}
			}
			Msg msg = new Msg();
			msg.setMsg_type(type);
			msg.setSend_user_id(dest_uid);
			msg.setMsg_title(msg_title);
			msg.setMsg_content(msg_content);
			msg.setUser_id(src_uid);
			msg.setUser_name(src_uname);
			msg.setSend_time(new Date());
			msg.setInfo_state(1);
			int msg_id = msgDao.insertEntity(msg);

			MsgReceiver mr = new MsgReceiver();
			mr.setMsg_id(msg_id);
			mr.setUser_id(src_uid);
			mr.setReceiver_user_id(dest_uid);
			mr.setReceiver_user_mobile(dest_uname);
			mr.setIs_del(0);
			mr.setIs_read(0);
			mr.setIs_reply(0);
			msgReceiverDao.insertEntity(mr);
		} catch (Exception e) {
			logger.info("==SendMsg imp Error:{}", e.getMessage());
		}
	}

	public void insertUserRelationAndPar(Integer user_id, String ym_id, UserRelationDao userRelationDao,
			UserInfoDao userInfoDao, UserRelationParDao userRelationParDao) {

		UserInfo userInfoPar = new UserInfo();
		userInfoPar.setIs_del(0);
		userInfoPar.getMap().put("ym_id", ym_id);
		userInfoPar = userInfoDao.selectEntity(userInfoPar);

		// 查询 父关系表 并且插入自己关系表 UserRelation
		UserRelation entityPar = new UserRelation();
		entityPar.setUser_id(userInfoPar.getId());
		entityPar = userRelationDao.selectEntity(entityPar);
		if (null != entityPar) {
			UserRelation entityInsert = new UserRelation();
			entityInsert.setUser_id(user_id);
			entityInsert.setAdd_user_id(user_id);
			entityInsert.setAdd_date(new Date());
			entityInsert.setUser_par_id(entityPar.getUser_id());
			entityInsert.setUser_root_id(entityPar.getUser_par_id());
			int insertFlag = userRelationDao.insertEntity(entityInsert);
			if (insertFlag > 0) { // 如果插入成 则再插入UserRelationPar
				List<UserRelation> parentList = new ArrayList<UserRelation>();
				List<UserRelation> getParentList = this.getUserRelationParentList(entityPar.getUser_id(), parentList,
						userRelationDao);
				if (null != getParentList && getParentList.size() > 0) {
					int i = 1;
					for (UserRelation temp : getParentList) {
						UserRelationPar userRelationParInsert = new UserRelationPar();
						userRelationParInsert.setAdd_date(new Date());
						userRelationParInsert.setUser_id(user_id);
						userRelationParInsert.setUser_par_id(temp.getUser_id());
						userRelationParInsert.setUser_par_levle(i);
						userRelationParDao.insertEntity(userRelationParInsert);
						i++;
					}
				}
			}
		}
	}

	/**
	 * 查询所有的父节点
	 * 
	 * @return List<UserRelation>
	 */
	public List<UserRelation> getUserRelationParentList(Integer user_par_id, List<UserRelation> parentList,
			UserRelationDao userRelationDao) {

		UserRelation entity = new UserRelation();
		entity.setUser_id(user_par_id);
		entity = userRelationDao.selectEntity(entity);
		if (null != entity) {
			parentList.add(entity);
			this.getUserRelationParentList(entity.getUser_par_id(), parentList, userRelationDao);
		}
		return parentList;
	}

	/**
	 * 查询所有的子节点
	 * 
	 * @return List<UserRelation>
	 */
	public List<UserRelation> getUserRelationSonList(Integer user_id, List<UserRelation> sonList,
			UserRelationDao userRelationDao) {

		UserRelation entity = new UserRelation();
		entity.setUser_par_id(user_id);
		List<UserRelation> entityList = userRelationDao.selectEntityList(entity);
		if (null != entityList && entityList.size() > 0) {
			for (UserRelation temp : entityList) {
				sonList.add(temp);
				this.getUserRelationSonList(temp.getUser_id(), sonList, userRelationDao);
			}
		}
		return sonList;
	}

	/**
	 * 查询直接上级
	 * 
	 * @return List<UserRelation>
	 */
	public UserRelationPar getUserRelationParFirst(Integer user_id, UserRelationParDao userRelationParDao) {
		UserRelationPar userRelationPar_entp = new UserRelationPar();
		userRelationPar_entp.setUser_id(user_id);
		userRelationPar_entp.setUser_par_levle(1);
		// userRelationPar_entp.getRow().setCount(1);
		userRelationPar_entp = userRelationParDao.selectEntity(userRelationPar_entp);
		return userRelationPar_entp;
	}

	/**
	 * 查询上级 查询几个上级
	 * 
	 * @return List<UserRelation>
	 */
	public List<UserRelationPar> getUserRelationParList(Integer user_id, Integer par_level,
			UserRelationParDao userRelationParDao) {
		List<UserRelationPar> userRelationParList = new ArrayList<UserRelationPar>();
		for (int i = 1; i <= par_level; i++) {
			UserRelationPar userRelationPar = new UserRelationPar();
			userRelationPar.setUser_id(user_id);
			userRelationPar.setUser_par_levle(i);
			// userRelationPar.getRow().setCount(1);
			userRelationPar = userRelationParDao.selectEntity(userRelationPar);
			if (null != userRelationPar)
				userRelationParList.add(userRelationPar);
		}
		return userRelationParList;
	}

	/**
	 * 查询直接下级
	 * 
	 * @return List<UserRelation>
	 */
	public UserRelationPar getUserRelationSonFirst(Integer user_id, UserRelationParDao userRelationParDao) {
		UserRelationPar userRelationPar_entp = new UserRelationPar();
		userRelationPar_entp.setUser_par_id(user_id);
		userRelationPar_entp.setUser_par_levle(1);
		// userRelationPar_entp.getRow().setCount(1);
		userRelationPar_entp = userRelationParDao.selectEntity(userRelationPar_entp);
		return userRelationPar_entp;
	}

	/**
	 * 用户注册升级,已废弃
	 * 
	 * @return List<UserRelation>
	 */
	// public void updateUserInfoUserLevel(Integer user_id, Integer cur_score,
	// BaseDataDao baseDataDao,
	// UserInfoDao userInfoDao, UserScoreRecordDao userScoreRecordDao) {
	//
	// BaseData baseData = new BaseData();
	// baseData.setIs_del(0);
	// baseData.setType(6610);
	// baseData = baseDataDao.selectEntity(baseData);
	// if (null != baseData) {
	//
	// this.insertUserScoreRecord(user_id,
	// Keys.ScoreType.SCORE_TYPE_2.getIndex(), baseData.getPre_number(),
	// userInfoDao, userScoreRecordDao);
	//
	// Integer get_jf_count = cur_score + baseData.getPre_number();
	// UserInfo userInfo = new UserInfo();
	// userInfo.setId(user_id);
	// userInfo.setCur_score(get_jf_count);
	//
	// BaseData baseData2 = new BaseData();
	// baseData2.setType(200);
	// baseData2.setIs_del(0);
	// baseData2.getMap().put("order_by_pre_number", true);
	// List<BaseData> baseDataList = baseDataDao.selectEntityList(baseData);
	// for (BaseData bd : baseDataList) {// 判断用户是不是需要升级
	// if (get_jf_count >= bd.getPre_number()) {
	// userInfo.setUser_level(bd.getId());
	// }
	// }
	// userInfoDao.updateEntity(userInfo);
	// }
	// }

	public void roleUserOpt(UserInfo t, boolean is_update, RoleUserDao roleUserDao) {
		if (t.getId().intValue() != 1) {
			if (is_update) {// 先删除原来的角色用户表数据
				RoleUser ru1 = new RoleUser();
				ru1.getMap().put("user_id", t.getId());
				roleUserDao.deleteEntity(ru1);
			}
			// roleUser 插入数据
			if (null != t.getUser_type()) {
				RoleUser ru = new RoleUser();
				ru.setUser_id(t.getId());
				int user_type = t.getUser_type().intValue();
				if (user_type == Keys.UserType.USER_TYPE_100.getIndex()) {
					return;
				}
				ru.setRole_id(user_type);
				roleUserDao.insertEntity(ru);

				if (t.getIs_entp().intValue() == 1) {
					ru.setId(null);
					ru.setRole_id(Keys.RoleType.ROLE_TYPE_3.getIndex());// 商家
					roleUserDao.insertEntity(ru);
				}
				if (t.getIs_village().intValue() == 1) {
					ru.setId(null);
					ru.setRole_id(Keys.RoleType.ROLE_TYPE_4.getIndex());// 驿站
					roleUserDao.insertEntity(ru);
				}
				if (t.getIs_fuwu().intValue() == 1) {
					ru.setId(null);
					ru.setRole_id(Keys.RoleType.ROLE_TYPE_5.getIndex());// 区域合伙人
					roleUserDao.insertEntity(ru);
				}
			}
		}
	}

	/**
	 * 取单条baseData
	 */
	public BaseData getBaseData(Integer id, BaseDataDao baseDataDao) {
		BaseData baseData = new BaseData();
		baseData.setId(id);
		baseData.setIs_del(0);
		baseData = baseDataDao.selectEntity(baseData);
		return baseData;
	}

	public List<BaseData> getBaseDataListByType(Integer type, BaseDataDao baseDataDao, String order_by_info) {
		BaseData baseData = new BaseData();
		baseData.setType(type);
		baseData.setIs_del(0);
		baseData.getMap().put("order_by_info", order_by_info);
		List<BaseData> baseDataList = baseDataDao.selectEntityList(baseData);
		return baseDataList;
	}

	public UserInfo getUserInfo(Integer id, UserInfoDao userInfoDao) {
		UserInfo userInfo = new UserInfo();
		userInfo.setId(id);
		userInfo = userInfoDao.selectEntity(userInfo);
		return userInfo;
	}

	/**
	 * 更新用户的金额相关信息
	 */
	public int updateUserInfoBi(Integer user_id, BigDecimal money, String chu_or_ru, UserInfoDao userInfoDao) {
		UserInfo userInfo = new UserInfo();
		userInfo.setId(user_id);
		userInfo.getMap().put(chu_or_ru, money);
		return userInfoDao.updateEntity(userInfo);
	}

	/**
	 * 插入用户币记录UserInfo ui_dest,
	 */
	public void insertUserBiRecord(Integer add_user_id, UserInfo ui_dest, Integer chu_or_ru, Integer order_id,
			Integer link_id, BigDecimal bi_no, Integer bi_type, Integer get_tpye, BigDecimal get_rate,
			UserInfoDao userInfoDao, UserBiRecordDao userBiRecordDao) {
		UserInfo ui = this.getUserInfo(add_user_id, userInfoDao);

		UserBiRecord userBiRecord = new UserBiRecord();
		userBiRecord.setAdd_user_id(add_user_id);
		userBiRecord.setAdd_uname(ui.getUser_name());
		userBiRecord.setAdd_date(new Date());
		userBiRecord.setBi_chu_or_ru(chu_or_ru);
		if (null != get_rate) {
			userBiRecord.setGet_rate(get_rate);
		}
		if (null != link_id) {
			userBiRecord.setLink_id(link_id);
		}
		if (null != order_id) {
			userBiRecord.setOrder_id(order_id);
		}

		if (bi_type.intValue() == Keys.BiType.BI_TYPE_100.getIndex()) {
			userBiRecord.setBi_no_before(ui.getBi_dianzi());
			userBiRecord.setBi_no(bi_no);
			if (chu_or_ru == 1) {// 入，增加
				userBiRecord.setBi_no_after(ui.getBi_dianzi().add(bi_no));
			} else {// 出，减少
				userBiRecord.setBi_no_after(ui.getBi_dianzi().subtract(bi_no));
			}
		}

		if (bi_type.intValue() == Keys.BiType.BI_TYPE_200.getIndex()) {
			userBiRecord.setBi_no_before(ui.getBi_dianzi_lock());
			userBiRecord.setBi_no(bi_no);
			if (chu_or_ru == 1) {// 入，增加
				userBiRecord.setBi_no_after(ui.getBi_dianzi_lock().add(bi_no));
			} else {// 出，减少
				userBiRecord.setBi_no_after(ui.getBi_dianzi_lock().subtract(bi_no));
			}
		}

		if (bi_type.intValue() == Keys.BiType.BI_TYPE_300.getIndex()) {
			userBiRecord.setBi_no_before(ui.getBi_huokuan());
			userBiRecord.setBi_no(bi_no);
			if (chu_or_ru == 1) {// 入，增加
				userBiRecord.setBi_no_after(ui.getBi_huokuan().add(bi_no));
			} else {// 出，减少
				userBiRecord.setBi_no_after(ui.getBi_huokuan().subtract(bi_no));
			}
		}

		if (bi_type.intValue() == Keys.BiType.BI_TYPE_400.getIndex()) {
			userBiRecord.setBi_no_before(ui.getBi_huokuan_lock());
			userBiRecord.setBi_no(bi_no);
			if (chu_or_ru == 1) {// 入，增加
				userBiRecord.setBi_no_after(ui.getBi_huokuan_lock().add(bi_no));
			} else {// 出，减少
				userBiRecord.setBi_no_after(ui.getBi_huokuan_lock().subtract(bi_no));
			}
		}

		if (bi_type.intValue() == Keys.BiType.BI_TYPE_500.getIndex()) {
			userBiRecord.setBi_no_before(ui.getBi_aid());
			userBiRecord.setBi_no(bi_no);
			if (chu_or_ru == 1) {// 入，增加
				userBiRecord.setBi_no_after(ui.getBi_aid().add(bi_no));
			} else {// 出，减少
				userBiRecord.setBi_no_after(ui.getBi_aid().subtract(bi_no));
			}
		}

		if (bi_type.intValue() == Keys.BiType.BI_TYPE_600.getIndex()) {
			userBiRecord.setBi_no_before(ui.getBi_aid_lock());
			userBiRecord.setBi_no(bi_no);
			if (chu_or_ru == 1) {// 入，增加
				userBiRecord.setBi_no_after(ui.getBi_aid_lock().add(bi_no));
			} else {// 出，减少
				userBiRecord.setBi_no_after(ui.getBi_aid_lock().subtract(bi_no));
			}
		}
		if (bi_type.intValue() == Keys.BiType.BI_TYPE_700.getIndex()) {
			userBiRecord.setBi_no_before(ui.getBi_welfare());
			userBiRecord.setBi_no(bi_no);
			if (chu_or_ru == 1) {// 入，增加
				userBiRecord.setBi_no_after(ui.getBi_welfare().add(bi_no));
			} else {// 出，减少
				userBiRecord.setBi_no_after(ui.getBi_welfare().subtract(bi_no));
			}
		}

		if (bi_type.intValue() == Keys.BiType.BI_TYPE_800.getIndex()) {
			CardInfo card = new CardInfo();
			card.setId(link_id);
			card = this.cardInfoDao.selectEntity(card);

			userBiRecord.setBi_no_before(card.getCard_cash());
			userBiRecord.setBi_no(bi_no);
			if (chu_or_ru == 1) {// 入，增加
				userBiRecord.setBi_no_after(card.getCard_cash().add(bi_no));
			} else {// 出，减少
				userBiRecord.setBi_no_after(card.getCard_cash().subtract(bi_no));
			}
		}

		userBiRecord.setBi_type(bi_type);
		String bi_get_type_name = "";
		for (BiGetType temp : Keys.BiGetType.values()) {
			if (get_tpye == temp.getIndex()) {
				bi_get_type_name = temp.getName();
				break;
			}
		}
		userBiRecord.setBi_get_type(get_tpye);
		userBiRecord.setBi_get_memo(bi_get_type_name);

		if (null != ui_dest) {// 余额转让记录
			Double bi_rate = (Double) ui_dest.getMap().get("bi_rate");
			userBiRecord.setDest_uid(ui_dest.getId());
			userBiRecord.setDest_uname(ui_dest.getUser_name());
			userBiRecord.setBi_rate(new BigDecimal(bi_rate));
		}
		userBiRecordDao.insertEntity(userBiRecord);
	}

	/**
	 * 插入用户币记录UserInfo ui_dest,
	 */
	public void insertUserBiRecordAndLock(Integer add_user_id, UserInfo ui_dest, Integer chu_or_ru, Integer link_id,
			BigDecimal bi_no, BigDecimal tianfan_no, Integer bi_type, Integer get_tpye, BigDecimal get_rate,
			UserInfoDao userInfoDao, UserBiRecordDao userBiRecordDao) {
		UserInfo ui = this.getUserInfo(add_user_id, userInfoDao);

		UserBiRecord userBiRecord = new UserBiRecord();
		userBiRecord.setAdd_user_id(add_user_id);
		userBiRecord.setAdd_uname(ui.getUser_name());
		userBiRecord.setAdd_date(new Date());
		userBiRecord.setBi_chu_or_ru(chu_or_ru);
		userBiRecord.setGet_rate(get_rate);
		if (null != link_id) {
			userBiRecord.setLink_id(link_id);
		}

		if (bi_type.intValue() == Keys.BiType.BI_TYPE_100.getIndex()) {
			userBiRecord.setBi_no_before(ui.getBi_dianzi());
			userBiRecord.setBi_no(bi_no);
			userBiRecord.setTianfan_no_before(ui.getBi_dianzi_lock());
			userBiRecord.setTianfan_no(tianfan_no);
			if (chu_or_ru == 1) {// 入，增加
				userBiRecord.setBi_no_after(ui.getBi_dianzi().add(bi_no));
				userBiRecord.setTianfan_no_after(ui.getBi_dianzi_lock().subtract(tianfan_no));
			} else {// 出，减少
				userBiRecord.setBi_no_after(ui.getBi_dianzi().subtract(bi_no));
			}
		}

		userBiRecord.setBi_type(bi_type);
		String bi_get_type_name = "";
		for (BiGetType temp : Keys.BiGetType.values()) {
			if (get_tpye == temp.getIndex()) {
				bi_get_type_name = temp.getName();
				break;
			}
		}
		userBiRecord.setBi_get_type(get_tpye);
		userBiRecord.setBi_get_memo(bi_get_type_name);

		userBiRecordDao.insertEntity(userBiRecord);

	}

	/**
	 * 插入分红记录 余额，复销券,
	 */
	public void insertFuXiaoUserBiRecord(Integer add_user_id, UserInfo ui_dest, Integer chu_or_ru, Integer link_id,
			BigDecimal bi_no, BigDecimal tianfan_no, Integer bi_type, Integer get_tpye, BigDecimal get_rate,
			UserInfoDao userInfoDao, UserBiRecordDao userBiRecordDao, BigDecimal fuxiao_no, BigDecimal bi_rate) {
		UserInfo ui = this.getUserInfo(add_user_id, userInfoDao);

		UserBiRecord userBiRecord = new UserBiRecord();
		userBiRecord.setAdd_user_id(add_user_id);
		userBiRecord.setAdd_uname(ui.getUser_name());
		userBiRecord.setAdd_date(new Date());
		userBiRecord.setBi_chu_or_ru(chu_or_ru);
		userBiRecord.setGet_rate(get_rate);
		if (null != link_id) {
			userBiRecord.setLink_id(link_id);
		}

		if (bi_type.intValue() == Keys.BiType.BI_TYPE_100.getIndex()) {
			userBiRecord.setBi_no_before(ui.getBi_dianzi());
			userBiRecord.setBi_no(bi_no);

			userBiRecord.setTianfan_no_before(ui.getBi_dianzi_lock());
			userBiRecord.setTianfan_no(tianfan_no);

			userBiRecord.setFuxiao_no_before(ui.getBi_fuxiao());
			userBiRecord.setFuxiao_no(fuxiao_no);
			if (chu_or_ru == 1) {// 入，增加
				userBiRecord.setBi_no_after(ui.getBi_dianzi().add(bi_no));
				userBiRecord.setTianfan_no_after(ui.getBi_dianzi_lock().subtract(tianfan_no));
				userBiRecord.setFuxiao_no_after(ui.getBi_fuxiao().add(fuxiao_no));

				userBiRecord.setBi_rate(bi_rate);
			} else {// 出，减少
				userBiRecord.setBi_no_after(ui.getBi_dianzi().subtract(bi_no));
				userBiRecord.setFuxiao_no_after(ui.getBi_fuxiao().subtract(fuxiao_no));
			}
		}

		userBiRecord.setBi_type(bi_type);
		String bi_get_type_name = "";
		for (BiGetType temp : Keys.BiGetType.values()) {
			if (get_tpye == temp.getIndex()) {
				bi_get_type_name = temp.getName();
				break;
			}
		}
		userBiRecord.setBi_get_type(get_tpye);
		userBiRecord.setBi_get_memo(bi_get_type_name);

		userBiRecordDao.insertEntity(userBiRecord);

	}

	/**
	 * 插入用户积分记录
	 */
	public void insertUserScoreRecord(int user_id, boolean is_union, Integer score_type, BigDecimal hd_score,
			Integer link_id, UserInfoDao userInfoDao, UserScoreRecordDao userScoreRecordDao) {
		UserInfo ui = this.getUserInfo(user_id, userInfoDao);

		UserScoreRecord userScoreRecord = new UserScoreRecord();

		if (null != link_id) {
			userScoreRecord.setLink_id(link_id);
		}
		userScoreRecord.setScore_type(score_type);

		if (is_union) {// 更新联盟积分
			userScoreRecord.setHd_score_before(ui.getUser_score_union());
			userScoreRecord.setHd_score(hd_score);
			userScoreRecord.setHd_score_after(hd_score.add(ui.getUser_score_union()));
		} else {// 更新个人积分
			userScoreRecord.setHd_score_before(ui.getCur_score());
			userScoreRecord.setHd_score(hd_score);
			userScoreRecord.setHd_score_after(hd_score.add(ui.getCur_score()));
		}

		userScoreRecord.setAdd_user_id(ui.getId());
		userScoreRecord.setAdd_date(new Date());
		userScoreRecordDao.insertEntity(userScoreRecord);

	}

	public void insertUserScoreRecordForCard(UserInfo ui, boolean is_union, Integer score_type, BigDecimal hd_score,
			Integer link_id, UserInfoDao userInfoDao, UserScoreRecordDao userScoreRecordDao) {

		UserScoreRecord userScoreRecord = new UserScoreRecord();

		if (null != link_id) {
			userScoreRecord.setLink_id(link_id);
		}
		userScoreRecord.setScore_type(score_type);

		if (is_union) {// 更新联盟积分
			userScoreRecord.setHd_score_before(ui.getUser_score_union());
			userScoreRecord.setHd_score(hd_score);
			userScoreRecord.setHd_score_after(hd_score.add(ui.getUser_score_union()));
		} else {// 更新个人积分
			userScoreRecord.setHd_score_before(ui.getCur_score());
			userScoreRecord.setHd_score(hd_score);
			userScoreRecord.setHd_score_after(hd_score.add(ui.getCur_score()));
		}

		userScoreRecord.setAdd_user_id(ui.getId());
		userScoreRecord.setAdd_date(new Date());
		userScoreRecordDao.insertEntity(userScoreRecord);

	}

	/**
	 * 用户添加积分以及升级
	 */
	public void addUserScore(UserInfo t, Boolean is_up, UserScoreRecordDao userScoreRecordDao, UserInfoDao userInfoDao,
			UserRelationParDao userRelationParDao, BaseDataDao baseDataDao) {

		Integer score = Integer.valueOf(String.valueOf(t.getMap().get("score")));
		Integer userLevel = null;

		// 添加一条用户积分记录
		this.insertUserScoreRecord(t.getId(), false, Keys.ScoreType.SCORE_TYPE_2.getIndex(), new BigDecimal(score),
				null, userInfoDao, userScoreRecordDao);

		// 更新用户当前积分
		UserInfo userInfo1 = new UserInfo();
		userInfo1.setIs_del(0);
		userInfo1.setId(t.getId());
		userInfo1 = userInfoDao.selectEntity(t);

		UserInfo userInfo = new UserInfo();
		userInfo.setId(t.getId());
		userInfo.setUpdate_date(new Date());
		userInfo.setUpdate_user_id(t.getId());

		// 升级自己
		if (is_up) {
			userLevel = this.belongLevel(userInfo1.getCur_score().add(new BigDecimal(score)), baseDataDao);
			userInfo.setUser_level(userLevel);
		}

		userInfo.getMap().put("add_cur_score", score);
		userInfo.setScore_update_date(new Date());// 更新积分修改时间
		userInfoDao.updateEntity(userInfo);
	}

	// 判断个人积分是否升级
	public Integer belongLevel(BigDecimal curScore, BaseDataDao baseDataDao) {
		Integer userLevel = 0;

		BaseData baseData = new BaseData();
		baseData.setType(200);
		baseData.setIs_del(0);
		baseData.getMap().put("order_by_info", "pre_number asc,");
		List<BaseData> baseDataList = baseDataDao.selectEntityList(baseData);
		for (BaseData bd : baseDataList) {// 判断用户是不是需要升级
			if (curScore.compareTo(new BigDecimal(bd.getPre_number())) >= 0) {
				userLevel = bd.getId();
			}
		}
		return userLevel;
	}

	public BigDecimal BiToBi(BigDecimal bi_old, Integer baseDataId, BaseDataDao baseDataDao) {
		BaseData baseData = this.getBaseData(baseDataId, baseDataDao);

		BigDecimal new_bi = bi_old
				.multiply(new BigDecimal(baseData.getPre_number()).divide(new BigDecimal(baseData.getPre_number2())));
		return new_bi;

	}

	// 反过来计算的 和BiToBi相反
	public BigDecimal BiToBi2(BigDecimal bi_old, Integer baseDataId, BaseDataDao baseDataDao) {
		BaseData baseData = this.getBaseData(baseDataId, baseDataDao);
		BigDecimal new_bi = bi_old
				.multiply(new BigDecimal(baseData.getPre_number2()).divide(new BigDecimal(baseData.getPre_number())));
		return new_bi;
	}

	// 反过来计算的 和BiToBi相反
	public BigDecimal BiToBi_DianZi(BigDecimal bi_old, Integer baseDataId, BaseDataDao baseDataDao) {
		BaseData baseData = this.getBaseData(baseDataId, baseDataDao);
		BigDecimal new_bi = bi_old
				.multiply(new BigDecimal(baseData.getPre_number()).multiply(new BigDecimal(baseData.getPre_number2())));
		return new_bi;
	}

	/**
	 * 插入 收支明细
	 */
	public void insertUserJifenRecord(UserInfo uicur, Integer card_id, String card_no, BigDecimal add_dianzibi,
			Integer jifen_type, Integer add_score, UserBiRecordDao userBiRecordDao, UserInfoDao userInfoDao,
			UserRelationParDao userRelationParDao, BaseDataDao baseDataDao, UserJifenRecordDao userJifenRecordDao) {
		// 取出当前人积分start
		UserJifenRecord userJifenRecord = new UserJifenRecord();
		userJifenRecord.setJifen_type(jifen_type);

		userJifenRecord.setUser_id(uicur.getId());
		userJifenRecord.setUser_name(uicur.getUser_name());
		userJifenRecord.setUser_level(uicur.getUser_level());
		// BaseData bd = getBaseData(uicur.getUser_level(), baseDataDao);
		// if (null != bd) {
		// 固化
		userJifenRecord.setUser_level_name(this.getBaseDataFor200WithName(uicur.getUser_level()));
		// }

		userJifenRecord.setCard_id(card_id);
		userJifenRecord.setCard_no(card_no);

		UserRelationPar userRelationPar_entp = getUserRelationParFirst(uicur.getId(), userRelationParDao);
		if (null != userRelationPar_entp) {
			userJifenRecord.setUser_par_id(userRelationPar_entp.getUser_par_id());
			UserInfo userPar = getUserInfo(userRelationPar_entp.getUser_par_id(), userInfoDao);
			String user_par_name = userPar.getUser_name();
			userJifenRecord.setUser_par_name(user_par_name);
			userJifenRecord.setUser_par_level(userPar.getUser_level());
			// bd = getBaseData(userPar.getUser_level(), baseDataDao);
			// if (null != bd) {
			// userJifenRecord.setUser_par_level_name(bd.getType_name());
			userJifenRecord.setUser_par_level_name(this.getBaseDataFor200WithName(userPar.getUser_level()));
			// }
		} else {
			userJifenRecord.setUser_par_name("-");
			userJifenRecord.setUser_par_level_name("-");
			userJifenRecord.setRemark("顶级会员，没有上级");
		}

		BigDecimal cur_score = uicur.getCur_score();
		userJifenRecord.setOpt_b_score(cur_score);
		userJifenRecord.setOpt_c_score(new BigDecimal(add_score));
		userJifenRecord.setOpt_a_score(cur_score.add(new BigDecimal(add_score)));

		BigDecimal dianzibi = uicur.getBi_dianzi();
		userJifenRecord.setOpt_b_dianzibi(dianzibi);
		userJifenRecord.setOpt_c_dianzibi(add_dianzibi);
		userJifenRecord.setOpt_a_dianzibi(dianzibi.add(add_dianzibi));
		userJifenRecord.setAdd_date(new Date());
		// 取出当前人积分end

		if (jifen_type.intValue() == Keys.JifenType.JifenType_20.getIndex()
				|| jifen_type.intValue() == Keys.JifenType.JifenType_30.getIndex()) {
			UserJifenRecord uj = new UserJifenRecord();
			uj.setCard_id(card_id);
			uj.setUser_id(uicur.getId());
			uj.getMap().put("jifen_type_in", "11,12");// TODO
														// JifenType_11("返现积分(个人)",
														// 11),
														// JifenType_12("返现积分(联盟)",
														// 12)
			uj.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
			List<UserJifenRecord> ujList = userJifenRecordDao.selectEntityList(uj);
			if (null != ujList && ujList.size() == 1) {
				userJifenRecord = new UserJifenRecord();
				userJifenRecord.setId(ujList.get(0).getId());
				userJifenRecord.setJifen_type(jifen_type);
				userJifenRecord.setOpt_b_dianzibi(dianzibi);
				userJifenRecord.setOpt_c_dianzibi(add_dianzibi);
				userJifenRecord.setOpt_a_dianzibi(dianzibi.add(add_dianzibi));
				userJifenRecordDao.updateEntity(userJifenRecord);
			}
		} else {
			userJifenRecordDao.insertEntity(userJifenRecord);
		}

	}

	// 更新关联BasePro信息 add_or_plus 1增加 -1 减少

	public void updateBasePro(Integer p_index, Integer add_or_plus, BaseProvinceDao baseProvinceDao) {

		BaseProvince entity = this.getBaseProvinceInfo(p_index, baseProvinceDao);
		if (null != entity) {
			BaseProvince entityUpdate = new BaseProvince();
			entityUpdate.setP_index(entity.getP_index());
			if (add_or_plus == 1) {
				entityUpdate.setIs_fuwu(1);
			} else {
				entityUpdate.setIs_fuwu(0);
			}
			baseProvinceDao.updateEntity(entityUpdate);
		}

		List<BaseProvince> parentList = new ArrayList<BaseProvince>();
		List<BaseProvince> baseProParList = this.getBaseProvinceParList(parentList, p_index, baseProvinceDao);
		if (null != baseProParList && baseProParList.size() > 0) {
			for (BaseProvince temp : baseProParList) {
				BaseProvince tempUpdate = new BaseProvince();
				tempUpdate.setP_index(temp.getP_index());
				if (add_or_plus == 1) {
					tempUpdate.getMap().put("add_fuwu_count", 1);
				} else {
					tempUpdate.getMap().put("sub_fuwu_count", 1);
				}
				baseProvinceDao.updateEntity(tempUpdate);
			}
		}
	}

	public BaseProvince getBaseProvinceInfo(Integer p_index, BaseProvinceDao baseProvinceDao) {
		BaseProvince entity = new BaseProvince();
		entity.setP_index(p_index.longValue());
		entity.setIs_del(0);
		entity = baseProvinceDao.selectEntity(entity);
		return entity;
	}

	public List<BaseProvince> getBaseProvinceParList(List<BaseProvince> parentList, Integer par_index,
			BaseProvinceDao baseProvinceDao) {
		BaseProvince entity = new BaseProvince();
		entity.setP_index(par_index.longValue());
		entity = baseProvinceDao.selectEntity(entity);
		if (null != entity) {
			parentList.add(entity);
			this.getBaseProvinceParList(parentList, Integer.valueOf(entity.getPar_index().toString()), baseProvinceDao);
		}
		return parentList;
	}

	/**
	 * @author Wu,Yang
	 * @date 2011-09-06 生成订单交易流水号,长度：22位，（前17位：4年2月2日2时2分2秒3毫秒） + （后三位：3SEQ）
	 * @desc 后三位根据SEQ_ORDER_INFO_TRADE_INDEX生成，最大999循环生成
	 */
	public String creatTradeIndex() {
		SimpleDateFormat sdFormatymdhmsS = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		String trade_no = sdFormatymdhmsS.format(new Date());

		return trade_no;
	}

	// 会员星级
	public BigDecimal getBaseDataFor200(Integer id) {
		BigDecimal rate = new BigDecimal(1.2);
		for (BaseData bd : Keys.keysBaseData200List) {
			if (bd.getId().intValue() == id) {
				BigDecimal b1 = new BigDecimal(bd.getPre_number2());
				BigDecimal b2 = new BigDecimal(10);
				rate = b1.divide(b2, 2, BigDecimal.ROUND_HALF_DOWN);
				break;
			}
		}

		return rate;
	}

	public String getBaseDataFor200WithName(Integer id) {
		String name = "";
		for (BaseData bd : Keys.keysBaseData200List) {
			if (bd.getId().intValue() == id) {
				name = bd.getType_name();
				break;
			}
		}

		return name;
	}

	public double getBaseDataFor1000(Integer id) {
		double rate = 5;
		switch (id) {
		case 1011:
			rate = 0.1;
			break;
		// case 1021:
		// rate = Keys.service_center_rate_p;// 省级区域合伙人0.1
		// break;
		// case 1022:
		// rate = Keys.service_center_rate_c;// 市级区域合伙人0.3
		// break;
		// case 1023:
		// rate = Keys.service_center_rate_t;// 县级区域合伙人0.6
		// break;
		case 1030:
			rate = 0.2;
			break;
		case 1040:
			rate = 200;
			break;
		case 1050:
			rate = 0.3;
			break;
		case 2010:
			rate = 1;
			break;
		case 2020:
			rate = 2;
			break;
		case 2030:
			rate = 3;
			break;
		}
		return rate;
	}

	public void updateOrderInfoBi(Integer order_id, OrderInfoDao orderInfoDao, BigDecimal money_bi,
			BigDecimal money_cash, BigDecimal money_bi_lock) {
		OrderInfo orderInfoUpdate = new OrderInfo();
		orderInfoUpdate.setId(order_id);
		if (null != money_bi) {
			orderInfoUpdate.setMoney_bi(money_bi);
		}
		if (null != money_cash) {
			orderInfoUpdate.setMoney_cash(money_cash);
		}
		if (null != money_bi_lock) {
			orderInfoUpdate.setMoney_bi_lock(money_bi_lock);
		}
		orderInfoDao.updateEntity(orderInfoUpdate);
	}

	/**
	 * 获取自身所在区域信息
	 */
	public BaseProvince getBaseProvinceByUserId(Integer user_id, UserInfoDao userInfoDao,
			BaseProvinceDao baseProvinceDao) {

		UserInfo ui = new UserInfo();
		ui.setId(user_id);
		ui = userInfoDao.selectEntity(ui);
		if (null != ui && null != ui.getP_index()) {
			if (!"00".equals(ui.getP_index().toString().substring(4, 6))) {// 会员所在区域到区县
				BaseProvince baseProvince = new BaseProvince();
				baseProvince.setP_index(ui.getP_index().longValue());
				baseProvince = baseProvinceDao.selectEntity(baseProvince);
				if (null != baseProvince) {
					return baseProvince;
				}
			}
		}

		return null;
	}

	/**
	 * 插入订单积分记录
	 */
	public void insertUserScoreRecordForOrder(UserInfo ui, boolean is_union, Integer score_type, BigDecimal hd_score,
			Integer link_id, UserInfoDao userInfoDao, UserScoreRecordDao userScoreRecordDao) {

		UserScoreRecord userScoreRecord = new UserScoreRecord();

		if (null != link_id) {
			userScoreRecord.setLink_id(link_id);
		}
		userScoreRecord.setScore_type(score_type);

		if (is_union) {// 更新联盟积分
			userScoreRecord.setHd_score_before(ui.getUser_score_union());
			userScoreRecord.setHd_score(hd_score);
			userScoreRecord.setHd_score_after(hd_score.add(ui.getUser_score_union()));
		} else {// 更新个人积分
			userScoreRecord.setHd_score_before(ui.getCur_score());
			userScoreRecord.setHd_score(hd_score);
			userScoreRecord.setHd_score_after(hd_score.add(ui.getCur_score()));
		}

		userScoreRecord.setAdd_user_id(ui.getId());
		userScoreRecord.setAdd_date(new Date());
		userScoreRecordDao.insertEntity(userScoreRecord);

	}

	/**
	 * 获取余额
	 * 
	 * @desc user_id：用户，bi_dianzi：获取余额额度，card_id：关联返现卡，bi_get_type：余额获取方式，get_rate ：余额获取比率
	 */
	public void getBiDianzi(Integer user_id, BigDecimal bi_dianzi, Integer order_id, String trade_index,
			Integer bi_get_type, BigDecimal get_rate, UserInfoDao userInfoDao, UserBiRecordDao userBiRecordDao) {
		UserInfo userInfo = new UserInfo();
		userInfo.setId(user_id);
		userInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());// 未删除
		userInfo = userInfoDao.selectEntity(userInfo);
		if (null != userInfo) {
			this.insertUserBiRecord(userInfo.getId(), null, 1, order_id, order_id, bi_dianzi,
					Keys.BiType.BI_TYPE_100.getIndex(), bi_get_type, get_rate, userInfoDao, userBiRecordDao);

			// 一、获取余额记录
			UserInfo ui = new UserInfo();
			ui.setId(userInfo.getId());
			// ui.setBi_dianzi(userInfo.getBi_dianzi().add(bi_dianzi));
			ui.getMap().put("add_bi_dianzi", bi_dianzi);
			userInfoDao.updateEntity(ui);

		}
	}

	// 返现比例：4.9至5.5之间(万分之几的比例)
	// [a,b]
	public BigDecimal getTianFanForScale(double a, double b) {

		Random r = new Random();
		double y = r.nextDouble() * (b - a) + a;
		BigDecimal rate = new BigDecimal(y).setScale(3, BigDecimal.ROUND_HALF_UP);

		return rate.divide(new BigDecimal(10000));
	}

	/**
	 * 退货退款插入用户电子币操作记录 返还余额return_money，link_id（order_return_info_id）,出或入chu_or_ru
	 * ，币类型bi_type，币支出方式bi_get_type，是否返还is_fanhuan
	 */
	public void insertOrderReturnUserBiRecord(BigDecimal return_money, Integer link_id, Integer chu_or_ru,
			Integer bi_type, Integer bi_get_type, Integer is_fanhuan, Integer add_user_id, Integer dest_uid,
			UserInfoDao userInfoDao, UserBiRecordDao userBiRecordDao, OrderReturnInfoDao orderReturnInfoDao) {

		OrderReturnInfo orderReturnInfo = new OrderReturnInfo();
		orderReturnInfo.setId(link_id);
		orderReturnInfo = orderReturnInfoDao.selectEntity(orderReturnInfo);

		UserInfo ui = this.getUserInfo(orderReturnInfo.getUser_id(), userInfoDao);
		UserInfo entp_ui = this.getUserInfo(orderReturnInfo.getEntp_id(), userInfoDao);

		UserBiRecord userBiRecord = new UserBiRecord();

		userBiRecord.setAdd_user_id(add_user_id);
		userBiRecord.setDest_uid(dest_uid);

		userBiRecord.setLink_id(orderReturnInfo.getId());// 退款申请记录表ID
		userBiRecord.setBi_chu_or_ru(chu_or_ru);
		userBiRecord.setBi_type(bi_type);
		userBiRecord.setBi_no_before(ui.getBi_dianzi());// 操作前用户余额
		userBiRecord.setBi_no(return_money);// 返还金额
		// ui.setBi_dianzi(ui.getBi_dianzi().add(return_money));//
		// 给用户余额加上返还的退款余额
		// userBiRecord.setBi_get_type(Keys.BiGetType.BI_GET_TYPE_300.getIndex());
		userBiRecord.setBi_get_memo(orderReturnInfo.getReturn_desc());

		userBiRecord.setAdd_date(new Date());
		userBiRecord.setBi_state(Keys.BiState.BI_STATE_0.getIndex());
		userBiRecord.setIs_del(0);
		userBiRecord.setIs_fanhuan(1);
		userBiRecord.setDest_uid(orderReturnInfo.getUser_id());
		userBiRecord.setAdd_uname(ui.getUser_name());

		if (bi_type.intValue() == Keys.BiType.BI_TYPE_100.getIndex()) {
			userBiRecord.setBi_no_before(ui.getBi_dianzi());
			userBiRecord.setBi_no(return_money);
			if (chu_or_ru == 1) {// 入，增加
				userBiRecord.setBi_no_after(ui.getBi_dianzi().add(return_money));
			} else {// 出，减少
				userBiRecord.setBi_no_after(ui.getBi_dianzi().subtract(return_money));
			}
		}
		if (bi_type.intValue() == Keys.BiType.BI_TYPE_300.getIndex()) {
			userBiRecord.setBi_no_before(entp_ui.getBi_huokuan());
			userBiRecord.setBi_no(return_money);
			if (chu_or_ru == 1) {// 入，增加
				userBiRecord.setBi_no_after(entp_ui.getBi_huokuan().add(return_money));
			} else {// 出，减少
				userBiRecord.setBi_no_after(entp_ui.getBi_huokuan().subtract(return_money));
			}
		}

		userBiRecord.setBi_type(bi_type);
		String bi_get_type_name = "";
		for (BiGetType temp : Keys.BiGetType.values()) {
			if (bi_get_type == temp.getIndex()) {
				bi_get_type_name = temp.getName();
				break;
			}
		}
		userBiRecord.setBi_get_type(bi_get_type);
		userBiRecord.setBi_get_memo(bi_get_type_name);

		userBiRecordDao.insertEntity(userBiRecord);

	}

	/**
	 * 插入订单各类奖励、订单分红、订单待返积分记录 jifen_type=记录类型，user_id=所属人ID，user_par_id=直接邀请人ID，dianzibi
	 * =操作余额，tianfan=操作分红积分，order_id=订单ID ，trade_index=订单编码，order_type=订单类型，rest_tianfan=剩余待返
	 */
	public void insertUserJifenRecord(Integer jifen_type, Integer user_id, Integer user_par_id, BigDecimal dianzibi,
			BigDecimal tianfan, BigDecimal rest_tianfan, Integer order_id, String trade_index, Integer order_type,
			OrderInfo orderInfo, UserInfoDao userInfoDao, BaseDataDao baseDataDao,
			UserJifenRecordDao userJifenRecordDao) {
		UserJifenRecord userJifenRecord = new UserJifenRecord();
		userJifenRecord.setJifen_type(jifen_type);
		userJifenRecord.setUser_id(user_id);
		UserInfo ui = new UserInfo();
		ui.setId(user_id);
		ui = userInfoDao.selectEntity(ui);
		if (null != ui) {
			userJifenRecord.setUser_name(ui.getUser_name());
			userJifenRecord.setUser_level(ui.getUser_level());
			BaseData baseData = this.getBaseData(ui.getUser_level(), baseDataDao);
			if (null != baseData) {
				userJifenRecord.setUser_level_name(baseData.getType_name());
			}
			// 余额
			userJifenRecord.setOpt_b_dianzibi(ui.getBi_dianzi());
			userJifenRecord.setOpt_c_dianzibi(dianzibi);
			userJifenRecord.setOpt_a_dianzibi(ui.getBi_dianzi().add(dianzibi));
			// 已返积分
			userJifenRecord.setOpt_b_tianfan(ui.getBi_xiaofei());
			userJifenRecord.setOpt_c_tianfan(tianfan);
			userJifenRecord.setOpt_a_tianfan(ui.getBi_xiaofei().add(tianfan));
		}
		if (null != user_par_id) {
			ui = new UserInfo();
			ui.setId(user_par_id);
			ui = userInfoDao.selectEntity(ui);
			userJifenRecord.setUser_par_id(user_par_id);
			if (null != ui) {
				userJifenRecord.setUser_par_name(ui.getUser_name());
				userJifenRecord.setUser_par_level(ui.getUser_level());
				BaseData baseData = this.getBaseData(ui.getUser_level(), baseDataDao);
				if (null != baseData) {
					userJifenRecord.setUser_par_level_name(baseData.getType_name());
				}
			}
		} else {
			userJifenRecord.setUser_par_name("-");
			userJifenRecord.setUser_par_level_name("-");
			userJifenRecord.setRemark("顶级会员，没有上级");
		}

		userJifenRecord.setOrder_id(order_id);
		userJifenRecord.setTrade_index(trade_index);
		userJifenRecord.setOrder_type(order_type);
		userJifenRecord.setRest_tianfan(rest_tianfan);
		userJifenRecord.setXiadan_uname(orderInfo.getAdd_user_name());
		userJifenRecord.setXiadan_uid(orderInfo.getAdd_user_id());
		userJifenRecord.setAdd_date(new Date());
		userJifenRecordDao.insertEntity(userJifenRecord);
	}

	public int createOrderInfoPublic(BigDecimal order_money, UserInfo userInfo, String trade_index, String comm_name,
			Integer pay_type, int orderType, OrderInfoDao orderInfoDao, OrderInfoDetailsDao orderInfoDetailsDao,
			String trade_no) {
		// 需要插入订单信息 以及直接插入

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setTrade_index(trade_index);
		orderInfo.setTrade_no(trade_no);
		orderInfo.setOrder_num(1);
		orderInfo.setOrder_money(order_money);
		orderInfo.setReal_pay_money(orderInfo.getOrder_money());
		orderInfo.setMatflow_price(new BigDecimal("0"));
		orderInfo.setOrder_weight(new BigDecimal("0"));
		orderInfo.setPay_type(pay_type);
		orderInfo.setPay_date(new Date());

		orderInfo.setOrder_date(new Date());
		orderInfo.setAdd_date(new Date());
		orderInfo.setAdd_user_id(userInfo.getId());
		orderInfo.setAdd_user_name(userInfo.getUser_name());
		orderInfo.setFinish_date(new Date());

		if (null != userInfo.getMobile()) {
			orderInfo.setRel_phone(userInfo.getMobile());
		}

		// 保存下管理ID到trade_merger_index，方便删除
		if (null != userInfo.getMap().get("tixian_id")) {
			orderInfo.setTrade_merger_index("tixian_" + String.valueOf(userInfo.getMap().get("tixian_id")));
		}
		orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_50.getIndex());
		orderInfo.setRel_name(userInfo.getUser_name());
		orderInfo.setPay_platform(Keys.PayPlatform.PC.getIndex());

		orderInfo.setOrder_type(orderType);
		int order_id = orderInfoDao.insertEntity(orderInfo);
		if (order_id > 0) {
			OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
			orderInfoDetails.setOrder_id(order_id);
			orderInfoDetails.setComm_name(comm_name);
			orderInfoDetails.setOrder_type(orderType);
			orderInfoDetails.setGood_count(1);
			orderInfoDetails.setMatflow_price(new BigDecimal(0));
			orderInfoDetails.setGood_price(order_money);
			orderInfoDetails.setGood_sum_price(order_money);
			orderInfoDetailsDao.insertEntity(orderInfoDetails);

		}
		return order_id;
	}

	/**
	 * 返现总金额统计
	 */
	public void recordTongjiWithTotal(Integer user_id, BigDecimal card_amount, TongjiDao tongjiDao) {
		// 2.1、平台返现统计
		Calendar cal = GregorianCalendar.getInstance();
		Tongji tongji = new Tongji();
		tongji.setTongji_type(Keys.TongjiType.TONGJITYPE_20.getIndex());// 平台返现统计(按年)
		tongji.setTongji_year(cal.get(Calendar.YEAR));
		tongji.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		List<Tongji> tongjiList = tongjiDao.selectEntityList(tongji);
		if ((null == tongjiList) || (tongjiList.size() == 0)) {// 新增
			tongji.setAdd_date(new Date());
			tongji.setAdd_uid(1);
			tongji.setModify_date(new Date());
			tongji.setModify_uid(1);
			tongji.setTongji_num1(card_amount);
			tongji.setTongji_num2(new BigDecimal(0));
			tongji.setTongji_num3(new BigDecimal(0));
			tongjiDao.insertEntity(tongji);
		} else if (tongjiList.size() == 1) {
			Tongji tj = new Tongji();
			tj.setId(tongjiList.get(0).getId());
			tj.getMap().put("add_tongji_num1", card_amount);
			tj.setModify_date(new Date());
			tongjiDao.updateEntity(tj);

			tj = new Tongji();
			tj.setId(tongjiList.get(0).getId());
			tj.getMap().put("counting_tongji_num3", "true");
			tj.setModify_date(new Date());
			tongjiDao.updateEntity(tj);
		}
		// 2.2、个人返现统计
		tongji = new Tongji();
		tongji.setTongji_type(Keys.TongjiType.TONGJITYPE_30.getIndex());// 平台返现统计(按年)
		tongji.setTongji_year(cal.get(Calendar.YEAR));
		tongji.setAdd_uid(user_id);
		tongji.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		tongjiList = null;
		tongjiList = tongjiDao.selectEntityList(tongji);
		if ((null == tongjiList) || (tongjiList.size() == 0)) {// 新增
			tongji.setAdd_date(new Date());
			tongji.setModify_date(new Date());
			tongji.setModify_uid(1);
			tongji.setTongji_num1(card_amount);
			tongji.setTongji_num2(new BigDecimal(0));
			tongji.setTongji_num3(new BigDecimal(0));
			tongjiDao.insertEntity(tongji);
		} else if (tongjiList.size() == 1) {
			Tongji tj = new Tongji();
			tj.setId(tongjiList.get(0).getId());
			tj.getMap().put("add_tongji_num1", card_amount);
			tj.setModify_date(new Date());
			tongjiDao.updateEntity(tj);

			tj = new Tongji();
			tj.setId(tongjiList.get(0).getId());
			tj.getMap().put("counting_tongji_num3", "true");
			tj.setModify_date(new Date());
			tongjiDao.updateEntity(tj);
		}
	}

	/**
	 * 已返现总金额统计
	 */
	public void recordTongjiWithCash(Integer user_id, BigDecimal card_amount, TongjiDao tongjiDao) {
		// 2.1、平台已返现统计
		Calendar cal = GregorianCalendar.getInstance();
		Tongji tongji = new Tongji();
		tongji.setTongji_type(Keys.TongjiType.TONGJITYPE_20.getIndex());// 平台返现统计(按年)
		tongji.setTongji_year(cal.get(Calendar.YEAR));
		tongji.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		List<Tongji> tongjiList = tongjiDao.selectEntityList(tongji);
		if ((null != tongjiList) && (tongjiList.size() == 1)) {
			Tongji tj = new Tongji();
			tj.setId(tongjiList.get(0).getId());
			tj.getMap().put("add_tongji_num2", card_amount);
			tj.setModify_date(new Date());
			tongjiDao.updateEntity(tj);

			tj = new Tongji();
			tj.setId(tongjiList.get(0).getId());
			tj.getMap().put("counting_tongji_num3", "true");
			tj.setModify_date(new Date());
			tongjiDao.updateEntity(tj);
		}
		// 2.2、个人已返现统计
		tongji = new Tongji();
		tongji.setTongji_type(Keys.TongjiType.TONGJITYPE_30.getIndex());// 平台返现统计(按年)
		tongji.setTongji_year(cal.get(Calendar.YEAR));
		tongji.setAdd_uid(user_id);
		tongji.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		tongjiList = null;
		tongjiList = tongjiDao.selectEntityList(tongji);
		if ((null != tongjiList) && (tongjiList.size() == 1)) {
			Tongji tj = new Tongji();
			tj.setId(tongjiList.get(0).getId());
			tj.getMap().put("add_tongji_num2", card_amount);
			tj.setModify_date(new Date());
			tongjiDao.updateEntity(tj);

			tj = new Tongji();
			tj.setId(tongjiList.get(0).getId());
			tj.getMap().put("counting_tongji_num3", "true");
			tj.setModify_date(new Date());
			tongjiDao.updateEntity(tj);
		}
	}

	/**
	 * 退款操作 退去个人的余额,正常订单全额退回，失效订单扣除3%手续费
	 * 
	 * @param OrderInfo
	 * @param order_id
	 * @return
	 */
	public void modifyOrderInfoForTk(Integer order_id, OrderInfoDao orderInfoDao, UserInfoDao userInfoDao,
			BaseDataDao baseDataDao, UserBiRecordDao userBiRecordDao) {
		OrderInfo orderInfoQueryUser = new OrderInfo();
		orderInfoQueryUser.setId(order_id);
		orderInfoQueryUser = orderInfoDao.selectEntity(orderInfoQueryUser);
		if (null != orderInfoQueryUser) {
			UserInfo userInfo = new UserInfo();
			userInfo.setId(orderInfoQueryUser.getAdd_user_id());
			userInfo = userInfoDao.selectEntity(userInfo);
			if (null != userInfo) {
				// BigDecimal rmb_to_xiaoFeibi =
				// super.BiToBi2(orderInfoQueryUser.getOrder_money(),
				// Keys.BASE_DATA_ID.BASE_DATA_ID_901.getIndex(), baseDataDao);
				// super.insertUserBiRecord(userInfo.getId(), 1,
				// orderInfoQueryUser.getId(), rmb_to_xiaoFeibi,
				// Keys.BiType.BI_TYPE_200.getIndex(),
				// Keys.BiGetType.BI_GET_TYPE_160.getIndex(), userInfoDao,
				// userBiRecordDao);
				// super.updateUserInfoBi(userInfo.getId(), rmb_to_xiaoFeibi,
				// "add_bi_xiaofei", userInfoDao);

				// 订单金额-物流费用 未发货的，不需要退还物流费
				BigDecimal rmb_to_dianzibi = BiToBi2(orderInfoQueryUser.getOrder_money(),
						Keys.BASE_DATA_ID.BASE_DATA_ID_904.getIndex(), baseDataDao);

				// 如果订单已经失效，退款时则扣除3%的手续费
				if (orderInfoQueryUser.getEnd_date().before(new Date())
						&& orderInfoQueryUser.getOrder_type().intValue() == Keys.OrderType.ORDER_TYPE_10.getIndex()) {
					// 消费商品订单超过失效时间才扣手续费，实物商品订单不扣了
					Double rate = Double.valueOf((100 - Keys.ORDER_TUIKUAN_RATE)) / 100;
					rmb_to_dianzibi = rmb_to_dianzibi.multiply(new BigDecimal(rate));
				}

				insertUserBiRecord(userInfo.getId(), null, 1, null, orderInfoQueryUser.getId(), rmb_to_dianzibi,
						Keys.BiType.BI_TYPE_100.getIndex(), Keys.BiGetType.BI_GET_TYPE_160.getIndex(), null,
						userInfoDao, userBiRecordDao);

				// 1 更新用户的余额
				updateUserInfoBi(userInfo.getId(), rmb_to_dianzibi, "add_bi_dianzi", userInfoDao);

			}
		}
	}

	/**
	 * 更新商品销量和库存、企业销售量
	 * 
	 * @param OrderInfo
	 * @param addOrLose 增加或者减少 0 增加销量 减少库存 1减少销量 增加库存
	 * @return
	 */
	public int modifyOrderInfoCommSaleCountAndInventory(Integer order_id, Integer addOrLose,
			OrderInfoDetailsDao orderInfoDetailsDao, CommTczhPriceDao commTczhPriceDao, CommInfoDao commInfoDao,
			EntpInfoDao entpInfoDao) {

		OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
		orderInfoDetails.setOrder_id(order_id);
		List<OrderInfoDetails> orderInfoDetailsList = orderInfoDetailsDao.selectEntityList(orderInfoDetails);
		int i = 0;
		if (orderInfoDetailsList != null && orderInfoDetailsList.size() > 0) {
			OrderInfoDetails d = new OrderInfoDetails();
			for (OrderInfoDetails item : orderInfoDetailsList) {
				i = updateCommInfoCommSaleCountAndInventory(addOrLose, commTczhPriceDao, commInfoDao, entpInfoDao, i,
						item);
			}
		}
		return i;
	}

	/**
	 * @desc 退货，退款 修改商品套餐库存
	 * @return
	 */
	public int orderReturnUpdateCommInfoCommSaleCountAndInventory(Integer addOrLose, OrderReturnInfo orderReturnInfo,
			OrderReturnInfoDao orderReturnInfoDao, CommTczhPriceDao commTczhPriceDao, CommInfoDao commInfoDao,
			EntpInfoDao entpInfoDao, OrderInfoDetailsDao orderInfoDetailsDao) {
		if (null != orderReturnInfo.getOrder_detail_id()) {
			OrderInfoDetails ods = getOrderInfoDetailsInfo(orderInfoDetailsDao, orderReturnInfo.getOrder_detail_id());
			if (null != ods) {
				updateCommInfoCommSaleCountAndInventory(addOrLose, commTczhPriceDao, commInfoDao, entpInfoDao, 0, ods);
			}
		} else {
			OrderInfoDetails ods = new OrderInfoDetails();
			ods.setOrder_id(orderReturnInfo.getOrder_id());
			List<OrderInfoDetails> odslist = orderInfoDetailsDao.selectEntityList(ods);
			if (null != odslist && odslist.size() > 0) {
				for (OrderInfoDetails item : odslist) {
					updateCommInfoCommSaleCountAndInventory(addOrLose, commTczhPriceDao, commInfoDao, entpInfoDao, 0,
							item);
				}
			}
		}

		return 0;

	}

	public int updateCommInfoCommSaleCountAndInventory(Integer addOrLose, CommTczhPriceDao commTczhPriceDao,
			CommInfoDao commInfoDao, EntpInfoDao entpInfoDao, int i, OrderInfoDetails d) {
		if (null != d.getComm_tczh_id()) {

			CommTczhPrice commTczhPrice = new CommTczhPrice();
			commTczhPrice.setId(d.getComm_tczh_id());
			commTczhPrice = commTczhPriceDao.selectEntity(commTczhPrice);
			if (null != commTczhPrice) {
				CommTczhPrice temp_commTczhPrice = new CommTczhPrice();
				temp_commTczhPrice.setId(commTczhPrice.getId());

				logger.info("===操作库存：" + d.getGood_count());
				if (addOrLose == 0) {
					temp_commTczhPrice.getMap().put("sub_inventory", d.getGood_count());
					temp_commTczhPrice.getMap().put("where_cal_inventory", d.getGood_count());
				}
				if (addOrLose == 1) {
					temp_commTczhPrice.getMap().put("add_inventory", d.getGood_count());
				}
				int count = commTczhPriceDao.updateEntity(temp_commTczhPrice);
				if (count > 0) {
					if (null != d.getComm_id()) {
						CommInfo commInfo = new CommInfo();
						commInfo.setId(d.getComm_id());
						commInfo = commInfoDao.selectEntity(commInfo);
						if (commInfo != null) {

							CommInfo temp_commInfo = new CommInfo();
							temp_commInfo.setId(commInfo.getId());
							if (addOrLose == 0) {
								temp_commInfo.getMap().put("add_sale_count", d.getGood_count());
								temp_commInfo.getMap().put("add_sale_count_update", d.getGood_count());
								if (commInfo.getIs_zingying().intValue() != Keys.CommZyType.COMM_ZY_TYPE_1.getIndex()) {// 京东商品不减少库存
									temp_commInfo.getMap().put("sub_inventory", d.getGood_count());
								}

							}
							if (addOrLose == 1) {
								temp_commInfo.getMap().put("sub_sale_count", d.getGood_count());
								temp_commInfo.getMap().put("sub_sale_count_update", d.getGood_count());
								if (commInfo.getIs_zingying().intValue() != Keys.CommZyType.COMM_ZY_TYPE_1.getIndex()) {// 京东商品不减少库存
									temp_commInfo.getMap().put("add_inventory", d.getGood_count());
								}
							}
							commInfoDao.updateEntity(temp_commInfo);

						}
					}
					if (addOrLose == 0) {
						// 增加企业销售量
						if (null != d.getEntp_id() && null != d.getGood_count()) {
							EntpInfo ei = new EntpInfo();
							ei.setId(d.getEntp_id());
							ei.getMap().put("add_sale_count", d.getGood_count());
							ei.getMap().put("add_sum_sale_money", d.getGood_sum_price());
							entpInfoDao.updateEntity(ei);
						}
					}
					if (addOrLose == 1) {
						// 减少企业销售量
						if (null != d.getEntp_id() && null != d.getGood_count()) {
							EntpInfo ei = new EntpInfo();
							ei.setId(d.getEntp_id());
							ei.getMap().put("sub_sale_count", d.getGood_count());
							ei.getMap().put("sub_sum_sale_money", d.getGood_sum_price());
							entpInfoDao.updateEntity(ei);
						}
					}
				} else { // 发生错误
					logger.info("===回滚===");
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					i = -1;
				}
			}
		}
		return i;
	}

	/**
	 * @param orderInfo 退款，商家审核成功，修改退款订单，审核状态
	 */
	public void modifyTuiKuanAuditState(OrderInfo orderInfo, Integer ods_id, OrderReturnInfoDao orderReturnInfoDao,
			BaseAuditRecordDao baseAuditRecordDao) {
		modifyTuiKuanAuditState(null, null, orderInfo, ods_id, orderReturnInfoDao, baseAuditRecordDao);
	}

	/**
	 * @param orderInfo 退款，商家审核成功，修改退款订单，审核状态
	 */
	public void modifyTuiKuanAuditState(Integer orderReturn_id, Integer audit_state, OrderInfo orderInfo,
			Integer ods_id, OrderReturnInfoDao orderReturnInfoDao, BaseAuditRecordDao baseAuditRecordDao) {

		OrderReturnInfo returnInfo = new OrderReturnInfo();
		if (null != orderReturn_id) {
			returnInfo.setId(orderReturn_id);
		}
		returnInfo.setOrder_id(orderInfo.getId());
		if (null != ods_id) {
			returnInfo.setOrder_detail_id(ods_id);
		}
		returnInfo = orderReturnInfoDao.selectEntity(returnInfo);

		if (null == audit_state) {
			audit_state = returnInfo.getAudit_state();
		}

		returnInfo.setTk_status(1);
		returnInfo.setIs_confirm(1);
		returnInfo.setAudit_state(audit_state);
		orderReturnInfoDao.updateEntity(returnInfo);

		BaseAuditRecord baseAudit = new BaseAuditRecord();
		baseAudit.setOpt_type(Keys.OptType.OPT_TYPE_11.getIndex());
		baseAudit.setLink_id(returnInfo.getId());
		baseAudit.setLink_table("OrderReturnInfo");
		baseAudit = baseAuditRecordDao.selectEntity(baseAudit);
		if (null != baseAudit) {
			baseAudit.setAudit_state(audit_state);
			baseAudit.setAudit_user_id(orderInfo.getEntp_id());
			baseAudit.setAudit_user_name(orderInfo.getEntp_name());
			baseAudit.setAudit_date(new Date());
			baseAuditRecordDao.updateEntity(baseAudit);
		}

	}

	/*
	 * 获取当前时间之前或之后几小时 hour
	 */

	public static String getTimeByHour(int hour) {

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);

		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());

	}

	/*
	 * 获取当前时间之前或之后几分钟 minute
	 */

	public static Date getTimeByMinute(int minute) {

		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.MINUTE, minute);
		logger.info("时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
		return calendar.getTime();
	}

	public void createSysOperLog(int oper_type, String oper_memo, String logString, UserInfo userInfo,
			SysOperLogDao sysOperLogDao) {
		if (userInfo != null) {
			SysOperLog sysOperLog = new SysOperLog();
			sysOperLog.setOper_type(oper_type);
			String oper_name = "";
			for (SysOperType sot : Keys.SysOperType.values()) {
				if (sot.getIndex() == oper_type) {
					oper_name = sot.getName();
					break;
				}
			}
			sysOperLog.setOper_name(oper_name);
			sysOperLog.setOper_time(new Date());
			sysOperLog.setOper_memo(oper_memo);
			sysOperLog.setOper_uid(userInfo.getId());
			sysOperLog.setOper_uname(userInfo.getUser_name());
			sysOperLogDao.insertEntity(sysOperLog);
		}
	}

	public String getCommInfoName(Integer id, CommInfoDao commInfoDao) {
		CommInfo a = new CommInfo();
		a.setId(id);
		a.setIs_del(0);
		a = commInfoDao.selectEntity(a);
		if (null != a) {
			return a.getComm_name();
		}

		return "";
	}

	public String getBaseClassName(Integer id, BaseClassDao baseClassDao) {
		BaseClass a = new BaseClass();
		a.setCls_id(id);
		a.setIs_del(0);
		a = baseClassDao.selectEntity(a);
		if (null != a) {
			return a.getCls_name();
		}

		return "";
	}

	/**
	 * 减订单中商品库存
	 * 
	 * @param OrderInfo
	 * @return
	 */
	public int updateCommInfoInventory(Integer order_id, Integer addOrLose, OrderInfoDetailsDao orderInfoDetailsDao,
			CommTczhPriceDao commTczhPriceDao) {
		int i = 0;
		OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
		orderInfoDetails.setOrder_id(order_id);
		List<OrderInfoDetails> orderInfoDetailsList = orderInfoDetailsDao.selectEntityList(orderInfoDetails);
		if (orderInfoDetailsList != null && orderInfoDetailsList.size() > 0) {
			OrderInfoDetails ods = new OrderInfoDetails();
			for (int z = 0; z < orderInfoDetailsList.size(); z++) {
				i++;
				ods = orderInfoDetailsList.get(z);
				if (ods.getComm_id() != null) {
					if (ods.getComm_tczh_id() != null) {
						CommTczhPrice commTczhPrice = new CommTczhPrice();
						commTczhPrice.setId(ods.getComm_tczh_id());
						commTczhPrice = commTczhPriceDao.selectEntity(commTczhPrice);
						if (commTczhPrice != null) {
							CommTczhPrice update_tczh_price = new CommTczhPrice();
							update_tczh_price.setId(commTczhPrice.getId());

							if (addOrLose == 0) {
								update_tczh_price.getMap().put("sub_inventory", ods.getGood_count());
								update_tczh_price.getMap().put("where_cal_inventory", ods.getGood_count());
							}
							if (addOrLose == 1) {
								update_tczh_price.getMap().put("add_inventory", ods.getGood_count());
							}
							int count = commTczhPriceDao.updateEntity(update_tczh_price);
						}
					}
				}
			}
		}
		return i;
	}

	public BigDecimal getBaseDataPreDecimal_1(Integer id, BaseDataDao baseDataDao) {
		BaseData baseData = new BaseData();
		baseData.setId(id);
		baseData.setIs_del(0);
		baseData = baseDataDao.selectEntity(baseData);

		return baseData.getPre_decimal1();
	}

	public void reckonRebateAndAid(Integer order_id, OrderInfoDao orderInfoDao, OrderInfoDetailsDao orderInfoDetailsDao,
			UserInfoDao userInfoDao, UserScoreRecordDao userScoreRecordDao, UserRelationParDao userRelationParDao,
			UserBiRecordDao userBiRecordDao, EntpInfoDao entpInfoDao, ServiceCenterInfoDao serviceCenterInfoDao,
			Date day, BaseDataDao baseDataDao, UserJifenRecordDao userJifenRecordDao, TongjiDao tongjiDao,
			CommInfoDao commInfoDao, CommInfoPoorsDao commInfoPoorsDao, VillageInfoDao villageInfoDao) {
		BaseData systemType = getBaseData(Keys.SystemType.SystemType_10001.getIndex(), baseDataDao);
		int systemCode = 0;// 默认返利模式

		if (systemType != null) {
			if (systemType.getPre_number() != null) {
				systemCode = systemType.getPre_number();
			}
		}
		switch (systemCode) {
		case 0:// 默认返利模式

			// TODO
			// 1、计算商家货款并转入待转账户
			// 2、计算会员消费奖励
			// 3、计算直接邀请奖励
			// 4、计算驿站奖励
			// 5、计算县域合伙人奖励
			// 6、计算扶贫金
			BaseImplForTiaoFu imp = new BaseImplForTiaoFu();
			imp.reckonRebateAndAid(order_id, orderInfoDao, orderInfoDetailsDao, userInfoDao, userScoreRecordDao,
					userRelationParDao, userBiRecordDao, entpInfoDao, serviceCenterInfoDao, day, baseDataDao,
					userJifenRecordDao, tongjiDao, commInfoDao, commInfoPoorsDao, villageInfoDao);
			break;
		case 10001:// 10001利仁多级返现模式
			// TODO
			// 1、计算商家货款并转入待转账户
			// 2、计算会员消费奖励
			// 3、计算直接邀请奖励
			// 4、计算驿站奖励
			// 5、计算县域合伙人奖励
			// 6、计算扶贫金
			BaseImplForTiaoFu impl1 = new BaseImplForTiaoFu();
			impl1.reckonRebateAndAid(order_id, orderInfoDao, orderInfoDetailsDao, userInfoDao, userScoreRecordDao,
					userRelationParDao, userBiRecordDao, entpInfoDao, serviceCenterInfoDao, day, baseDataDao,
					userJifenRecordDao, tongjiDao, commInfoDao, commInfoPoorsDao, villageInfoDao);
			break;
		}
	}

	public void grantBiLockToUser(Integer order_id, OrderInfoDao orderInfoDao, OrderInfoDetailsDao orderInfoDetailsDao,
			UserInfoDao userInfoDao, UserBiRecordDao userBiRecordDao, EntpInfoDao entpInfoDao, BaseDataDao baseDataDao,
			UserJifenRecordDao userJifenRecordDao, PoorCuoShiDao poorCuoShiDao) {
		BaseData systemType = getBaseData(Keys.SystemType.SystemType_10001.getIndex(), baseDataDao);
		int systemCode = 0;// 默认返利模式

		if (systemType != null) {
			if (systemType.getPre_number() != null) {
				systemCode = systemType.getPre_number();
			}
		}
		switch (systemCode) {
		case 0:// 默认返利模式
			BaseImplForTiaoFu imp = new BaseImplForTiaoFu();
			imp.grantBiLockToUser(order_id, orderInfoDao, orderInfoDetailsDao, userInfoDao, userBiRecordDao,
					entpInfoDao, baseDataDao, userJifenRecordDao, poorCuoShiDao);
			break;
		case 10001:// 10001利仁多级返现模式
			BaseImplForTiaoFu imp1 = new BaseImplForTiaoFu();
			imp1.grantBiLockToUser(order_id, orderInfoDao, orderInfoDetailsDao, userInfoDao, userBiRecordDao,
					entpInfoDao, baseDataDao, userJifenRecordDao, poorCuoShiDao);
			break;
		}
	}

	public void insertRecord(VillageDynamicRecord insertRecord, Integer id, Integer type, Integer link_user_id,
			String link_user_name, Integer add_user_id, String add_user_name,
			VillageDynamicRecordDao villageDynamicRecordDao) {
		insertRecord(insertRecord, add_user_id, type, link_user_id, link_user_name, add_user_id, add_user_name, null,
				villageDynamicRecordDao);
	}

	public void insertRecord(VillageDynamicRecord insertRecord, Integer id, Integer type, Integer link_user_id,
			String link_user_name, Integer add_user_id, String add_user_name, Integer village_id,
			VillageDynamicRecordDao villageDynamicRecordDao) {
		if (null == insertRecord) {
			insertRecord = new VillageDynamicRecord();
		}
		insertRecord.setLink_id(id);
		insertRecord.setRecord_type(type);
		if (type == Keys.DynamicRecordType.DynamicRecordType_1.getIndex()) {
			insertRecord.setRemark(add_user_name + "关注了你");
		}
		if (type == Keys.DynamicRecordType.DynamicRecordType_2.getIndex()) {
			insertRecord.setRemark("刚买商品：" + insertRecord.getComm_name());
		}
		if (type == Keys.DynamicRecordType.DynamicRecordType_3.getIndex()) {
			insertRecord.setRemark(add_user_name + "评论了你");
		}
		if (type == Keys.DynamicRecordType.DynamicRecordType_4.getIndex()) {
			insertRecord.setRemark(add_user_name + "回复了你");
		}
		if (type == Keys.DynamicRecordType.DynamicRecordType_5.getIndex()) {
			insertRecord.setRemark(add_user_name + "赞了你");
		}
		if (type == Keys.DynamicRecordType.DynamicRecordType_6.getIndex()) {
			insertRecord.setRemark(add_user_name + "发布了动态");
		}
		if (type == Keys.DynamicRecordType.DynamicRecordType_7.getIndex()) {
			insertRecord.setRemark(add_user_name + "发布了商品");
		}
		if (null != link_user_id) {
			insertRecord.setTo_user_id(link_user_id);
		}
		if (null != link_user_name) {
			insertRecord.setTo_user_name(link_user_name);
		}
		if (null != village_id) {
			insertRecord.setVillage_id(village_id);
		}
		insertRecord.setAdd_date(new Date());
		insertRecord.setAdd_user_id(add_user_id);
		insertRecord.setAdd_user_name(add_user_name);
		villageDynamicRecordDao.insertEntity(insertRecord);
	}

	/**
	 * basePro
	 *
	 * @return
	 */
	public BaseProvince getBaseProvince(Long p_index) {
		BaseProvince temp = new BaseProvince();
		temp.setP_index(p_index);
		temp.setIs_del(0);
		temp = baseProvinceDao.selectEntity(temp);
		return temp;
	}

	/**
	 * 同步上传京东订单
	 * 
	 * @throws IOException
	 * @throws HttpException
	 */
	@SuppressWarnings("deprecation")
	public String syncUploadJdOrder(Integer order_id, OrderInfoDao orderInfoDao,
			OrderInfoDetailsDao orderInfoDetailsDao) throws HttpException, IOException {
		String flag = "0";
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(order_id);
		orderInfo.setOrder_type(Keys.OrderType.ORDER_TYPE_30.getIndex());
		orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_0.getIndex());// 已预订
		orderInfo.setIs_sync_jd(0);// 京东订单未创建，则现在进行同步创建
		orderInfo = orderInfoDao.selectEntity(orderInfo);
		if (null != orderInfo) {// 上传创建京东订单
			OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
			orderInfoDetails.setOrder_id(order_id);
			List<OrderInfoDetails> orderInfoDetailsList = orderInfoDetailsDao
					.selectOrderInfoDetailsListByCommInfo(orderInfoDetails);

			String province = "";
			String city = "";
			String county = "";
			String town = "";
			if (null != orderInfo.getShipping_address_id()) {
				ShippingAddress shippingAddress = new ShippingAddress();
				shippingAddress.setId(orderInfo.getShipping_address_id());
				shippingAddress = shippingAddressDao.selectEntity(shippingAddress);
				if (null != shippingAddress) {
					shippingAddress.getRel_province();
					// 这个地方需要去查找对于的京东代码
					BaseProvince tempProvince = this.getBaseProvince(Long.valueOf(shippingAddress.getRel_province()));
					BaseProvince tempCity = this.getBaseProvince(Long.valueOf(shippingAddress.getRel_city()));
					BaseProvince tempRegion = this.getBaseProvince(Long.valueOf(shippingAddress.getRel_region()));
					BaseProvince tempRegionFour = this.getBaseProvince(shippingAddress.getRel_region_four());
					province = tempProvince.getJd_area_id().toString();

					if (null == tempCity.getJd_area_id()) {// 如果市是空值
						city = (null != tempRegion.getJd_area_id() ? tempRegion.getJd_area_id().toString() : "");
						county = (null != tempRegionFour.getJd_area_id() ? tempRegionFour.getJd_area_id().toString()
								: "");
					} else if (tempCity.getJd_area_id().intValue() == tempRegion.getJd_area_id().intValue()) {// 如果发现二级和三级是一样的
						city = (null != tempRegion.getJd_area_id() ? tempRegion.getJd_area_id().toString() : "");
						county = (null != tempRegionFour.getJd_area_id() ? tempRegionFour.getJd_area_id().toString()
								: "");
					} else {
						city = (null != tempCity.getJd_area_id() ? tempCity.getJd_area_id().toString() : "");
						county = (null != tempRegion.getJd_area_id() ? tempRegion.getJd_area_id().toString() : "");
						town = (null != tempRegionFour.getJd_area_id() ? tempRegionFour.getJd_area_id().toString()
								: "");
					}
				}

			}

			StringBuffer orderJson = new StringBuffer("");
			orderJson.append("{");
			orderJson.append("\"AccountId\": \"hfex\"");// 账户编号
			orderJson.append(",\"Address\": \"" + orderInfo.getRel_addr() + "\"");// 收货地址
			orderJson.append(",\"Province\": " + province + "");// 省份编号
			orderJson.append(",\"City\": \"" + city + "\"");// 城市编号
			orderJson.append(",\"County\": \"" + county + "\"");// 地区编号
			orderJson.append(",\"town\":\"" + town + "\"");// 乡镇编号
			// String ctime = "2018-03-02T16:19:37.9381171+08:00";
			// String ctime = DateTools.getStringDate(orderInfo.getAdd_date(), "yyyy-MM-dd'T'HH:mm:ss.sss+08:00");
			// orderJson.append(",\"ctime\": \"" + ctime + "\"");// 创建时间
			orderJson.append(
					",\"Freight\": " + orderInfo.getMatflow_price().multiply(new BigDecimal(100)).intValue() + "");// 运费金
			// orderJson.append(",\"id\": \"" + orderInfo.getTrade_index() + "\"");// 订单的编号
			// orderJson.append(",\"jdoid\": \"" + orderInfo.getTrade_index() + "\"");// 京东订单的编号
			orderJson.append(",\"Mobile\": \"" + orderInfo.getRel_phone() + "\"");// 收货手机号码
			orderJson.append(",\"Name\": \"" + orderInfo.getRel_name() + "\"");// 收货人姓名
			// orderJson.append(",\"orderid\": \"" + orderInfo.getTrade_index() + "\"");// 订单编号
			// orderJson.append(",\"parent\": \"\"");// 父订单编号
			// String paytime = "2018-03-02T16:19:37.9381171+08:00";
			// String paytime = DateTools.getStringDate(orderInfo.getPay_date(),
			// "yyyy-MM-dd'T'HH:mm:ss.sss+08:00");
			// orderJson.append(",\"paytime\": \"" + ctime + "\"");// 支付时间
			// orderJson.append(",\"split\": 0");// 是否拆单（0=未拆 1=拆单）
			// orderJson.append(",\"status\": 0");// 订单状态
			orderJson.append(",\"Uid\": \"" + Keys.jd_customer_uid + "\"");// 用户编号
			orderJson.append(",\"TradeItems\": [");

			// BigDecimal sumprice = orderInfo.getMatflow_price();
			if (null != orderInfoDetailsList && orderInfoDetailsList.size() > 0) {
				for (int i = 0; i < orderInfoDetailsList.size(); i++) {
					if (i > 0) {
						orderJson.append(",");
					}

					orderJson.append("{\"Count\": " + orderInfoDetailsList.get(i).getGood_count() + "");// 商品数量

					// orderJson.append(",\"id\": " + orderInfoDetailsList.get(i).getId() + "");// 订单详情ID
					// orderJson.append(",\"jdprice\": "
					// + new BigDecimal(orderInfoDetailsList.get(i).getMap().get("jd_price").toString()).multiply(
					// new BigDecimal(100)).intValue() + "");// 京东销售价
					// /orderJson.append(",\"order\": \"" + orderInfo.getTrade_index() + "\"");// 订单ID
					// TODO 测试使用的X-YUAN-APPKEY，price以jdprice填充，正式系统要需修改
					// orderJson.append(",\"price\": "+new
					// BigDecimal(orderInfoDetailsList.get(i).getMap().get("jd_price").toString()).multiply(new
					// BigDecimal(100)).intValue()+"");//京东代理价
					// TODO 正式系统使用下面的方法
					// orderJson.append(",\"price\": "
					// + new BigDecimal(orderInfoDetailsList.get(i).getMap().get("jd_agent_price").toString())
					// .multiply(new BigDecimal(100)).intValue() + "");// 京东代理价

					orderJson.append(
							",\"Sku\": \"" + orderInfoDetailsList.get(i).getMap().get("jd_skuid").toString() + "\"}");// 京东skuid

					// TODO 测试使用的X-YUAN-APPKEY，price以jdprice填充，正式系统要需修改
					// sumprice = sumprice.add(new
					// BigDecimal(orderInfoDetailsList.get(i).getMap().get("jd_price").toString()).multiply(new
					// BigDecimal(orderInfoDetailsList.get(i).getGood_count())));
					// TODO 正式系统使用下面的方法
					// sumprice = sumprice.add(new BigDecimal(orderInfoDetailsList.get(i).getMap().get("jd_agent_price")
					// .toString()).multiply(new BigDecimal(orderInfoDetailsList.get(i).getGood_count())));
				}

			}
			orderJson.append("]");
			// orderJson.append(",\"sumprice\": " + sumprice.multiply(new BigDecimal(100)).intValue() + "");// 订单总额
			orderJson.append("}");

			logger.warn("================" + orderJson.toString());

			String info = JdApiUtil.createJdOrder(orderJson.toString());// 创建京东订单

			logger.warn("=======syncUploadJdOrder=========" + info);
			if (StringUtils.isNotBlank(info)) {
				JSONObject obj = JSONObject.parseObject(info);
				if (null != obj && null != obj.get("StatusCode")) {
					if (Keys.JD_API_RESULT_STATUS_CODE.equals(obj.get("StatusCode").toString())
							&& null != obj.get("Data")) {// 订单同步成功
						OrderInfo orderInfoUpdate = new OrderInfo();
						orderInfoUpdate.setId(order_id);
						orderInfoUpdate.setIs_sync_jd(1);
						orderInfoUpdate.setSync_date(new Date());
						orderInfoUpdate.setJd_order_no(obj.get("Data").toString());// Jd_order_no，这个是接口查询京东订单的参数

						String jdOrderInfo = JdApiUtil.getJdOrderInfo(obj.get("Data").toString());// 查询创建的京东订单的订单号
						JSONObject jdOrderobj = JSONObject.parseObject(jdOrderInfo);
						if (null != obj && null != obj.get("StatusCode")) {
							if (Keys.JD_API_RESULT_STATUS_CODE.equals(jdOrderobj.get("StatusCode").toString())
									&& null != obj.get("Data")) {
								JSONObject data = JSONObject.parseObject(jdOrderobj.get("Data").toString());
								orderInfoUpdate.setJd_oid(data.get("jdoid").toString());
							}
						}

						orderInfoDao.updateEntity(orderInfoUpdate);
						flag = "1";
					}
				}
			}
		}
		// 0：失败，1：成功
		return flag;
	}

	/**
	 * 同步确认京东订单
	 * 
	 * @throws IOException
	 * @throws HttpException
	 */

	public String syncConfirmJdOrder(Integer order_id, OrderInfoDao orderInfoDao) throws HttpException, IOException {
		String flag = "0";
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(order_id);
		orderInfo.setOrder_type(Keys.OrderType.ORDER_TYPE_30.getIndex());
		orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_10.getIndex());// 已支付，待发货
		orderInfo.setIs_sync_jd(1);// 京东订单已创建
		orderInfo.setIs_confirm_jd(0);// 京东订单未确认，则现在进行同步确认
		orderInfo = orderInfoDao.selectEntity(orderInfo);
		if (null != orderInfo) {
			StringBuffer accountJson = new StringBuffer("");
			String appkey = Keys.jd_yuan_appkey;
			String appsecret = Keys.jd_yuan_secret;
			String timestamp = String.valueOf(new Date().getTime());
			String signature = EncryptUtilsV2.MD5Encode("appkey=" + appkey + "&appsecret=" + appsecret + "&orderid="
					+ orderInfo.getJd_order_no() + "&timestamp=" + timestamp + "#").toLowerCase();// 获取签名
			// 请求参数封装
			accountJson.append("{");
			accountJson.append("\"appkey\":\"" + appkey + "\"");
			accountJson.append(",\"timestamp\":" + timestamp + "");
			accountJson.append(",\"signature\":\"" + signature + "\"");
			accountJson.append(",\"orderid\":\"" + orderInfo.getJd_order_no() + "\"");
			accountJson.append("}");

			String put_info = JdApiUtil.confirmJdOrder(accountJson.toString());// 确认京东订单

			logger.info("确认信息：" + put_info);
			if (StringUtils.isNotBlank(put_info)) {
				JSONObject put_obj = JSONObject.parseObject(put_info);
				if (null != put_obj && null != put_obj.get("StatusCode")) {
					if (Keys.JD_API_RESULT_STATUS_CODE.equals(put_obj.get("StatusCode").toString())) {// 订单确认成功
						OrderInfo orderInfoUpdate = new OrderInfo();
						orderInfoUpdate.setId(order_id);
						orderInfoUpdate.setIs_confirm_jd(1);
						orderInfoUpdate.setJd_confirm_date(new Date());
						orderInfoDao.updateEntity(orderInfoUpdate);

						flag = "1";
					}
				}
			}
		}
		// 0：失败，1：成功
		return flag;
	}

	/**
	 * 同步下载更新京东订单
	 * 
	 * @throws IOException
	 * @throws HttpException
	 */
	public void syncDownloadJdOrder(Integer order_id, OrderInfoDao orderInfoDao) throws HttpException, IOException {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(order_id);
		orderInfo.setOrder_type(Keys.OrderType.ORDER_TYPE_30.getIndex());
		orderInfo.getMap().put("order_state_in", "10,20");
		orderInfo.setIs_sync_jd(1);// 已创建
		orderInfo.setIs_confirm_jd(1);// 已确认，现在更新订单状态
		orderInfo = orderInfoDao.selectEntity(orderInfo);
		if (null != orderInfo) {

			String info = JdApiUtil.getJdOrderInfo(orderInfo.getJd_order_no());// 查询京东订单

			JSONObject obj = JSONObject.parseObject(info);
			if (null != obj && null != obj.get("StatusCode")) {
				if (Keys.JD_API_RESULT_STATUS_CODE.equals(obj.get("StatusCode").toString())) {// 接口查询成功
					String result_str = JSONObject.toJSONString(obj.get("Data"));
					JSONObject result = JSONObject.parseObject(result_str);
					if (null != result) {
						String status = (null != result.get("status") ? result.get("status").toString() : "");

						OrderInfo orderInfoUpdate = new OrderInfo();
						orderInfoUpdate.setId(order_id);
						if ("2".equals(status)) {// 京东：表示配送途中
							orderInfoUpdate.setOrder_state(Keys.OrderState.ORDER_STATE_20.getIndex());
							orderInfoUpdate.setFahuo_date(new Date());
							orderInfoUpdate.setFahuo_remark("京东快递发货");
						} else if ("3".equals(status)) {// 京东：表示妥投，订单完结
							orderInfoUpdate.setOrder_state(Keys.OrderState.ORDER_STATE_40.getIndex());
							orderInfoUpdate.setQrsh_date(new Date());
						} else if ("4".equals(status)) {// 京东：表示拒收
							orderInfoUpdate.setOrder_state(Keys.OrderState.ORDER_STATE_X90.getIndex());
							orderInfoUpdate.setRemark("客户拒收，请处理及时处理订单");
						} else if ("5".equals(status)) {// 京东：表示取消
							orderInfoUpdate.setOrder_state(Keys.OrderState.ORDER_STATE_X90.getIndex());
							orderInfoUpdate.setRemark("京东取消，请处理及时处理订单");
						}
						if (null != orderInfoUpdate.getOrder_state()) {
							orderInfoDao.updateEntity(orderInfoUpdate);
						}

						// 更新京东物流订单
						String jdoid = (null != result.get("jdoid") ? result.get("jdoid").toString() : "");
						if (StringUtils.isNotBlank(jdoid)) {
							WlCompInfo wlCompInfo = new WlCompInfo();
							wlCompInfo.setId(0);// 京东物流查询
							wlCompInfo = this.wlCompInfoDao.selectEntity(wlCompInfo);
							if (null != wlCompInfo) {
								WlOrderInfo wlOrderInfo = new WlOrderInfo();
								wlOrderInfo.setOrder_id(order_id);
								wlOrderInfo.setWl_comp_id(wlCompInfo.getId());
								wlOrderInfo.setWl_code(wlCompInfo.getWl_code());
								wlOrderInfo.setWl_comp_name(wlCompInfo.getWl_comp_name());
								wlOrderInfo.setWaybill_no(jdoid);
								wlOrderInfo.setEntp_id(orderInfo.getEntp_id());
								wlOrderInfo.setAdd_date(new Date());
								wlOrderInfo.setAdd_user_id(1);
								this.wlOrderInfoDao.insertEntity(wlOrderInfo);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 获取京东商品库存
	 */
	public String getJdProductStocks(String json) {
		String info = "";
		try {
			HttpClient httpclient = new HttpClient();
			PostMethod post = new PostMethod("http://api.jd.yuan.cn/product/stocks");
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			post.setRequestHeader("Content-type", "application/json; charset=utf-8");
			post.setRequestHeader("X-YUAN-APPKEY", Keys.jd_yuan_appkey);
			post.setRequestBody(json);

			httpclient.executeMethod(post);
			info = new String(post.getResponseBody(), "utf-8");
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * 获取京东商品运费
	 */
	public String getJdProductFreight(String json) {
		String info = "";
		try {
			HttpClient httpclient = new HttpClient();
			PostMethod post = new PostMethod("http://api.jd.yuan.cn/product/freight");
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			post.setRequestHeader("Content-type", "application/json; charset=utf-8");
			post.setRequestHeader("X-YUAN-APPKEY", Keys.jd_yuan_appkey);
			post.setRequestBody(json);

			httpclient.executeMethod(post);
			info = new String(post.getResponseBody(), "utf-8");
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * 同步取消京东订单
	 * 
	 * @throws IOException
	 * @throws HttpException
	 */

	public String syncCancelJdOrder(Integer order_id, OrderInfoDao orderInfoDao) {
		String flag = "0";
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(order_id);
		orderInfo.setOrder_type(Keys.OrderType.ORDER_TYPE_30.getIndex());
		orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_X10.getIndex());//
		orderInfo.setIs_sync_jd(1);// 京东订单已创建
		orderInfo.setIs_confirm_jd(0);// 京东订单未确认，则现在进行同步取消
		orderInfo = orderInfoDao.selectEntity(orderInfo);
		if (null != orderInfo) {

			String info = JdApiUtil.cancelJdOrder(orderInfo.getJd_order_no());// 取消京东订单

			logger.warn("取消信息：" + info);
			if (StringUtils.isNotBlank(info)) {
				JSONObject obj = JSONObject.parseObject(info);
				if (null != obj && null != obj.get("StatusCode")) {
					if (Keys.JD_API_RESULT_STATUS_CODE.equals(obj.get("StatusCode").toString())) {// 订单取消成功
						flag = "1";
					}
				}
			}
		}
		// 0：失败，1：成功
		return flag;
	}

	/**
	 * 只计算扶贫金额
	 * 
	 * @param order_id
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
	public void reckonAidMoney(Integer order_id, OrderInfoDao orderInfoDao, OrderInfoDetailsDao orderInfoDetailsDao,
			UserInfoDao userInfoDao, UserScoreRecordDao userScoreRecordDao, UserRelationParDao userRelationParDao,
			UserBiRecordDao userBiRecordDao, EntpInfoDao entpInfoDao, ServiceCenterInfoDao serviceCenterInfoDao,
			Date day, BaseDataDao baseDataDao, UserJifenRecordDao userJifenRecordDao, TongjiDao tongjiDao,
			CommInfoDao commInfoDao, CommInfoPoorsDao commInfoPoorsDao, VillageInfoDao villageInfoDao) {
		// 1、计算扶贫金
		OrderInfo orderInfo = new OrderInfo();
		// orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_10.getIndex());//
		// 订单支付成功
		orderInfo.setId(order_id);
		orderInfo = orderInfoDao.selectEntity(orderInfo);
		if (null != orderInfo) {
			// 将消费金额增加到会员累计消费额度
			this.recordTongjiWithTotal(orderInfo.getAdd_user_id(), orderInfo.getOrder_money(), tongjiDao);

			// 更新会员累计消费金额
			UserInfo u_leiji = new UserInfo();
			u_leiji.setId(orderInfo.getAdd_user_id());
			u_leiji.getMap().put("add_leiji_money_user", orderInfo.getOrder_money());
			userInfoDao.updateEntity(u_leiji);

			OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
			orderInfoDetails.setOrder_id(order_id);
			orderInfoDetails.setIs_tuihuo(0);// 未退货
			List<OrderInfoDetails> orderInfoDetailsList = orderInfoDetailsDao.selectEntityList(orderInfoDetails);
			BigDecimal sum_aid_money = new BigDecimal(0);// 订单总扶贫金额
			if (null != orderInfoDetailsList && orderInfoDetailsList.size() > 0) {
				for (OrderInfoDetails ods : orderInfoDetailsList) {
					CommInfo commInfo = new CommInfo();
					commInfo.setId(ods.getComm_id());
					commInfo = commInfoDao.selectEntity(commInfo);

					BigDecimal aid_money = new BigDecimal(0);// 订单明细的扶贫金额
					if (null != commInfo && 1 == commInfo.getIs_aid()) {
						aid_money = ods.getGood_sum_price().multiply(commInfo.getAid_scale())
								.divide(new BigDecimal(100));// 商品扶贫金额
					}
					if (aid_money.compareTo(new BigDecimal(0)) == -1) {
						aid_money = new BigDecimal(0);
					}

					// 更新订单明细
					OrderInfoDetails od = new OrderInfoDetails();
					od.setId(ods.getId());
					od.setSum_aid_money(aid_money);
					orderInfoDetailsDao.updateEntity(od);

					sum_aid_money = sum_aid_money.add(aid_money);// 订单明细的扶贫金额累计到订单明细的扶贫金额

					// TODO 更新商品信息
					CommInfo ci = new CommInfo();
					ci.setId(ods.getComm_id());
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
							List<CommInfoPoors> commInfoPoorsList = commInfoPoorsDao.selectEntityList(commInfoPoors);
							if (null != commInfoPoorsList && commInfoPoorsList.size() > 0) {
								for (CommInfoPoors p : commInfoPoorsList) {
									insertUserBiAidLock(p.getPoor_id(), Keys.BiGetType.BI_GET_TYPE_500.getIndex(),
											orderInfo.getId(), ods.getId(),
											aid_money.divide(new BigDecimal(commInfoPoorsList.size()), 2,
													BigDecimal.ROUND_DOWN),
											userInfoDao, userBiRecordDao);
								}
							}
						}
					}

				}
			}

			// 计算并更新扶贫金额
			OrderInfo oi = new OrderInfo();
			oi.setId(orderInfo.getId());
			oi.setXiadan_user_sum(sum_aid_money);// 返现金额+扶贫金]\
			orderInfoDao.updateEntity(oi);
		}
	}

	/**
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
	 * 更新用户扶贫金
	 * 
	 * @param poor_id 贫困户ID
	 * @param bi_get_type 获取来源
	 * @param order_id 订单id
	 * @param link_id 关联id
	 * @param bi_dianzi 金额
	 * @param userInfoDao
	 * @param userBiRecordDao
	 */
	public int insertUserBiAid(Integer poor_id, Integer bi_get_type, Integer order_id, Integer link_id,
			BigDecimal bi_dianzi, UserInfoDao userInfoDao, UserBiRecordDao userBiRecordDao) {
		UserInfo ui = new UserInfo();
		ui.setIs_poor(1);
		ui.setPoor_id(poor_id);
		ui.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		ui = userInfoDao.selectEntity(ui);
		if (null == ui || null == ui.getId()) {
			return -1;
		}
		this.insertUserBiRecord(ui.getId(), null, -1, order_id, link_id, bi_dianzi, Keys.BiType.BI_TYPE_500.getIndex(),
				bi_get_type, null, userInfoDao, userBiRecordDao);

		// 一、获取待返余额记录
		UserInfo update_user = new UserInfo();
		update_user.setId(ui.getId());
		update_user.getMap().put("sub_bi_aid", bi_dianzi);
		update_user.getMap().put("add_bi_aid_sended", bi_dianzi);
		int i = userInfoDao.updateEntity(update_user);
		if (i == 0) {
			i = -10;
		}
		return i;

	}

	public void grantBiAidLockToUser(Integer order_id, OrderInfoDao orderInfoDao,
			OrderInfoDetailsDao orderInfoDetailsDao, UserInfoDao userInfoDao, UserBiRecordDao userBiRecordDao,
			EntpInfoDao entpInfoDao, BaseDataDao baseDataDao, UserJifenRecordDao userJifenRecordDao,
			PoorCuoShiDao poorCuoShiDao) {

		// 2、发放扶贫金额
		UserBiRecord userBiRecord = new UserBiRecord();
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
						xf_user_name = oi.getAdd_user_name().substring(0, 3) + "****"
								+ oi.getAdd_user_name().substring(7);
					} else if (oi.getAdd_user_name().length() > 3) {
						xf_user_name = oi.getAdd_user_name().substring(0, 3) + "****";
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
	}

	public void insertOrderReturnAuditRecord(OrderInfo orderInfo, int id, BaseAuditRecordDao baseAuditRecordDao) {
		BaseAuditRecord baseAudit = new BaseAuditRecord();
		baseAudit.setOpt_type(Keys.OptType.OPT_TYPE_11.getIndex());
		baseAudit.setLink_id(id);
		baseAudit.setLink_table("OrderReturnInfo");
		baseAudit.setOpt_note(orderInfo.getAdd_user_name());
		baseAudit.setAdd_user_id(orderInfo.getAdd_user_id());
		baseAudit.setAdd_user_name(orderInfo.getAdd_user_name());
		baseAudit.setAdd_date(new Date());
		baseAudit.setAudit_state(0);
		baseAudit.setRemark(orderInfo.getOrder_state().toString());
		baseAuditRecordDao.insertEntity(baseAudit);
	}

	public OrderReturnInfo getOrderReturn(Integer id, OrderReturnInfoDao orderReturnInfoDao) {
		OrderReturnInfo orderReturnInfo = new OrderReturnInfo();
		orderReturnInfo.setId(id);
		orderReturnInfo.setIs_del(0);
		orderReturnInfo = orderReturnInfoDao.selectEntity(orderReturnInfo);
		return orderReturnInfo;
	}

	public OrderInfo getOrder(Integer id, OrderInfoDao orderInfoDao) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(id);
		orderInfo = orderInfoDao.selectEntity(orderInfo);
		return orderInfo;
	}

	public OrderInfoDetails getOrderInfoDetailsInfo(OrderInfoDetailsDao orderInfoDetailsDao, int ods_id) {
		OrderInfoDetails ods = new OrderInfoDetails();
		ods.setId(ods_id);
		ods = orderInfoDetailsDao.selectEntity(ods);
		return ods;
	}

	/**
	 * @desc 退货订单号
	 */
	public String getReturnNo() {
		String retrun_no = "th" + this.creatTradeIndex();
		return retrun_no;
	}

	/**
	 * 无人超市扶贫
	 * 
	 * @param order_id
	 * @param orderInfoDao
	 * @param orderInfoDetailsDao
	 * @param userInfoDao
	 * @param userRelationParDao
	 * @param userBiRecordDao
	 * @param entpInfoDao
	 * @param baseDataDao
	 * @param userJifenRecordDao
	 * @param commInfoDao
	 * @param commInfoPoorsDao
	 */

	public JSONObject superMarketHelpPoor(Integer order_id, OrderInfoDao orderInfoDao,
			OrderInfoDetailsDao orderInfoDetailsDao, UserInfoDao userInfoDao, UserRelationParDao userRelationParDao,
			UserBiRecordDao userBiRecordDao, EntpInfoDao entpInfoDao, BaseDataDao baseDataDao,
			UserJifenRecordDao userJifenRecordDao, CommInfoDao commInfoDao, CommInfoPoorsDao commInfoPoorsDao,
			PoorInfoDao poorInfoDao) {

		JSONObject jsonObject = new JSONObject();

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_50.getIndex());// 订单支付成功
		orderInfo.setId(order_id);
		orderInfo.setOrder_type(Keys.OrderType.ORDER_TYPE_40.getIndex());
		orderInfo = orderInfoDao.selectEntity(orderInfo);

		OrderInfo orderInfoJSON = new OrderInfo();
		if (null != orderInfo) {

			UserInfo userQuery = new UserInfo();
			userQuery.setId(orderInfo.getAdd_user_id());
			userQuery = userInfoDao.selectEntity(userQuery);
			if (null == userQuery.getReal_name()) {
				orderInfoJSON.setAdd_user_name(userQuery.getUser_name());
			} else {
				orderInfoJSON.setAdd_user_name(userQuery.getReal_name());
			}

			orderInfoJSON.setOrder_num(orderInfo.getOrder_num());
			orderInfoJSON.setOrder_money(orderInfo.getOrder_money());

			OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
			orderInfoDetails.setOrder_id(order_id);
			List<OrderInfoDetails> orderInfoDetailsList = orderInfoDetailsDao.selectEntityList(orderInfoDetails);

			List<CommInfoPoors> list = new ArrayList<CommInfoPoors>();

			List<CommInfoPoors> commInfoPoorsJSONList = new ArrayList<CommInfoPoors>();

			BigDecimal sum_aid_money = new BigDecimal(0);// 订单总扶贫金额
			String msg = "";
			if (null != orderInfoDetailsList && orderInfoDetailsList.size() > 0) {
				for (OrderInfoDetails ods : orderInfoDetailsList) {
					CommInfo commInfo = new CommInfo();
					commInfo.setId(ods.getComm_id());
					commInfo = commInfoDao.selectEntity(commInfo);

					BigDecimal aid_money = new BigDecimal(0);// 订单明细的扶贫金额
					if (null != commInfo && 1 == commInfo.getIs_aid()) {
						aid_money = ods.getGood_sum_price().multiply(commInfo.getAid_scale())
								.divide(new BigDecimal(100), 2, BigDecimal.ROUND_DOWN);// 商品扶贫金额
					}
					if (aid_money.compareTo(new BigDecimal(0)) == -1) {
						aid_money = new BigDecimal(0);
					}
					// 更新订单明细
					OrderInfoDetails od = new OrderInfoDetails();
					od.setId(ods.getId());
					od.setSum_aid_money(aid_money);
					orderInfoDetailsDao.updateEntity(od);

					sum_aid_money = sum_aid_money.add(aid_money);// 订单明细的扶贫金额累计到订单明细的扶贫金额

					// TODO 更新商品信息
					CommInfo ci = new CommInfo();
					ci.setId(ods.getComm_id());
					ci.getMap().put("add_sum_aid_money", aid_money);
					commInfoDao.updateEntity(ci);

					if (aid_money.compareTo(new BigDecimal(0)) == 1) {// 扶贫金额大于0
						CommInfoPoors commInfoPoors = new CommInfoPoors();
						commInfoPoors.setComm_id(ods.getComm_id());
						List<CommInfoPoors> commInfoPoorsList = commInfoPoorsDao.selectEntityList(commInfoPoors);
						if (null != commInfoPoorsList && commInfoPoorsList.size() > 0) {
							for (CommInfoPoors p : commInfoPoorsList) {

								CommInfoPoors commInfoPoorsJSON = new CommInfoPoors();

								supermarketUserBiAidLock(p.getPoor_id(), Keys.BiGetType.BI_GET_TYPE_500.getIndex(),
										orderInfo.getId(), ods.getId(),
										aid_money.divide(new BigDecimal(commInfoPoorsList.size()), 2,
												BigDecimal.ROUND_DOWN),
										userInfoDao, userBiRecordDao);
								PoorInfo poorInfo = new PoorInfo();
								poorInfo.setId(p.getPoor_id());
								poorInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
								poorInfo.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
								poorInfo = poorInfoDao.selectEntity(poorInfo);

								p.getMap().put("poorInfo", poorInfo);

								if (null != poorInfo) {
									PoorInfo poorInfoJSON = new PoorInfo();
									poorInfoJSON.setReal_name(poorInfo.getReal_name());
									poorInfoJSON.setHead_logo(poorInfo.getHead_logo());
									commInfoPoorsJSON.getMap().put("poorInfo", poorInfoJSON);
								}

								UserInfo user = new UserInfo();
								user.setIs_del(0);
								user.setIs_poor(1);
								user.setPoor_id(p.getPoor_id());
								user = userInfoDao.selectEntity(user);
								p.getMap().put("user", user);

								if (null != user) {
									UserInfo userJSON = new UserInfo();
									userJSON.setBi_aid(user.getBi_aid());
									commInfoPoorsJSON.getMap().put("user", userJSON);
								}

								VillageInfo villageInfo = new VillageInfo();
								villageInfo.setId(poorInfo.getVillage_id());
								villageInfo.setIs_del(0);
								villageInfo.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
								villageInfo = villageInfoDao.selectEntity(villageInfo);
								p.getMap().put("villageInfo", villageInfo);

								if (null != villageInfo) {
									VillageInfo villageInfoJSON = new VillageInfo();
									villageInfoJSON.setVillage_name(villageInfo.getVillage_name());
									commInfoPoorsJSON.getMap().put("villageInfo", villageInfoJSON);
								}

								if (null != poorInfo && null != villageInfo) {
									msg += "您帮扶了" + villageInfo.getVillage_name() + poorInfo.getReal_name() + "获得扶贫金"
											+ aid_money.setScale(2, BigDecimal.ROUND_DOWN) + "元";
								}

								commInfoPoorsJSONList.add(commInfoPoorsJSON);

							}
							list.addAll(commInfoPoorsList);
						}

						// ods.getMap().put("commInfoPoorsList",
						// commInfoPoorsList);
						// jsonObject.put("orderInfoDetailsList",
						// orderInfoDetailsList);
					}
				}
			}
			jsonObject.put("commInfoPoorsList", commInfoPoorsJSONList);
			jsonObject.put("orderInfo", orderInfoJSON);
			jsonObject.put("sum_aid_money", sum_aid_money);
			jsonObject.put("msg", msg);
			return jsonObject;
		}
		return null;
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
	public int supermarketUserBiAidLock(Integer poor_id, Integer bi_get_type, Integer order_id, Integer link_id,
			BigDecimal bi_dianzi, UserInfoDao userInfoDao, UserBiRecordDao userBiRecordDao) {
		UserInfo ui = new UserInfo();
		ui.setIs_poor(1);
		ui.setPoor_id(poor_id);
		ui.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		ui = userInfoDao.selectEntity(ui);
		int count = 0;
		if (null != ui && null != ui.getId()) {
			this.insertUserBiRecord(ui.getId(), null, 1, order_id, link_id, bi_dianzi,
					Keys.BiType.BI_TYPE_500.getIndex(), bi_get_type, null, userInfoDao, userBiRecordDao);

			// 一、获取待返余额记录
			UserInfo update_user = new UserInfo();
			update_user.setId(ui.getId());
			update_user.getMap().put("add_bi_aid", bi_dianzi);
			count = userInfoDao.updateEntity(update_user);
		}
		return count;
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
	public int supermarketUserBiAid(Integer user_id, Integer bi_get_type, Integer order_id, Integer link_id,
			BigDecimal bi_dianzi, String entp_name, String add_user_name, UserInfoDao userInfoDao,
			UserBiRecordDao userBiRecordDao, PoorCuoShiDao poorCuoShiDao) {
		int count = 0;
		this.insertUserBiRecord(user_id, null, 1, order_id, link_id, bi_dianzi, Keys.BiType.BI_TYPE_500.getIndex(),
				bi_get_type, null, userInfoDao, userBiRecordDao);

		// 添加扶贫记录到扶贫措施中
		UserInfo ui = new UserInfo();
		ui.setId(user_id);
		ui = userInfoDao.selectEntity(ui);
		if (null != ui && null != ui.getPoor_id()) {
			PoorCuoShi poorCuoShi = new PoorCuoShi();
			poorCuoShi.setLink_id(ui.getPoor_id());
			poorCuoShi.setDan_wei_name(entp_name);
			String xf_user_name = "******";
			if (add_user_name.length() >= 11) {
				xf_user_name = add_user_name.substring(0, 3) + "***" + add_user_name.substring(7);
			} else if (add_user_name.length() > 3) {
				xf_user_name = add_user_name.substring(0, 3) + "***";
			} else if (add_user_name.length() > 0) {
				xf_user_name = add_user_name.substring(0, 1) + "***";
			}
			poorCuoShi.setContent(
					"会员：" + xf_user_name + "在平台购物，贫困户获取扶贫金额：" + bi_dianzi.setScale(2, BigDecimal.ROUND_HALF_UP) + "元");
			poorCuoShi.setHelp_date(new Date());
			poorCuoShi.setAdd_date(new Date());
			poorCuoShi.setAdd_user_id(1);
			poorCuoShiDao.insertEntity(poorCuoShi);
		}

		// 一、获取待返余额记录
		UserInfo update_user = new UserInfo();
		update_user.setId(user_id);
		update_user.getMap().put("add_bi_aid", bi_dianzi);
		count = userInfoDao.updateEntity(update_user);
		return count;
	}

	public void insertErrorLogForInsertUserRecord(Integer bi_type, Integer user_id, UserBiRecordDao userBiRecordDao,
			UserInfoDao userInfoDao) {

		UserBiRecord userBiRecord = new UserBiRecord();
		userBiRecord.setAdd_user_id(user_id);
		userBiRecord.setBi_type(bi_type);
		userBiRecord.getMap().put("order_by", "a.id desc");
		userBiRecord.getRow().setCount(1);
		List<UserBiRecord> userBiRecordList = userBiRecordDao.selectEntityList(userBiRecord);
		if (userBiRecordList != null && userBiRecordList.size() > 0) {
			UserBiRecord lasttRecord = userBiRecordList.get(0);
			UserInfo uInfo = new UserInfo();
			uInfo.setId(user_id);
			uInfo.setIs_del(0);
			uInfo = userInfoDao.selectEntity(uInfo);
			if (uInfo != null) {
				SysOperLog sLog = new SysOperLog();
				sLog.setOper_type(Keys.SysOperType.SysOperType_80.getIndex());
				sLog.setOper_name(Keys.SysOperType.SysOperType_80.getName());
				sLog.setOper_uid(user_id);
				sLog.setOper_uname(uInfo.getUser_name());
				sLog.setOper_time(new Date());

				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				// sLog.setOper_uip(oper_uip);
				if (bi_type == Keys.BiType.BI_TYPE_100.getIndex()) {// 余额
					if (uInfo.getBi_dianzi().compareTo(lasttRecord.getBi_no_after()) != 0) {
						sLog.setOper_memo(
								"用户:" + uInfo.getUser_name() + "," + formatter.format(lasttRecord.getAdd_date()) + ","
										+ lasttRecord.getBi_get_memo() + "异常");
						sysOperLogDao.insertEntity(sLog);
					}
				}
				if (bi_type == Keys.BiType.BI_TYPE_300.getIndex()) {// 货款
					if (uInfo.getBi_huokuan().compareTo(lasttRecord.getBi_no_after()) != 0) {
						sLog.setOper_memo(
								"用户:" + uInfo.getUser_name() + "," + formatter.format(lasttRecord.getAdd_date()) + ","
										+ lasttRecord.getBi_get_memo() + "异常");
						sysOperLogDao.insertEntity(sLog);
					}
				}
			}
		}
	}

	/**
	 * 插入用户余额福利金购物记录
	 */
	public void insertUserWelfareBuyBiRecord(Integer add_user_id, UserInfo ui_dest, Integer chu_or_ru, Integer order_id,
			Integer link_id, BigDecimal bi_no, Integer bi_type, Integer get_tpye, BigDecimal get_rate,
			UserInfoDao userInfoDao, UserBiRecordDao userBiRecordDao, BigDecimal welfare_no) {
		UserInfo ui = this.getUserInfo(add_user_id, userInfoDao);

		UserBiRecord userBiRecord = new UserBiRecord();
		userBiRecord.setAdd_user_id(add_user_id);
		userBiRecord.setAdd_uname(ui.getUser_name());
		userBiRecord.setAdd_date(new Date());
		userBiRecord.setBi_chu_or_ru(chu_or_ru);
		userBiRecord.setGet_rate(get_rate);
		if (null != link_id) {
			userBiRecord.setLink_id(link_id);
		}
		if (null != order_id) {
			userBiRecord.setOrder_id(order_id);
		}

		// TODO 这个地方增加预警
		this.insertErrorLogForInsertUserRecord(bi_type, add_user_id, userBiRecordDao, userInfoDao);

		if (bi_type.intValue() == Keys.BiType.BI_TYPE_100.getIndex()) {
			userBiRecord.setBi_no_before(ui.getBi_dianzi());
			userBiRecord.setBi_no(bi_no);

			userBiRecord.setWelfare_no_before(ui.getBi_welfare());
			userBiRecord.setWelfare_no(welfare_no);
			if (chu_or_ru == 1) {// 入，增加
				userBiRecord.setBi_no_after(ui.getBi_dianzi().add(bi_no));
				userBiRecord.setWelfare_no_after(ui.getBi_welfare().add(welfare_no));
			} else {// 出，减少
				userBiRecord.setBi_no_after(ui.getBi_dianzi().subtract(bi_no));
				userBiRecord.setWelfare_no_after(ui.getBi_welfare().subtract(welfare_no));
			}
		}

		userBiRecord.setBi_type(bi_type);
		String bi_get_type_name = "";
		for (BiGetType temp : Keys.BiGetType.values()) {
			if (get_tpye == temp.getIndex()) {
				bi_get_type_name = temp.getName();
				break;
			}
		}
		userBiRecord.setBi_get_type(get_tpye);
		userBiRecord.setBi_get_memo(bi_get_type_name);

		if (null != ui_dest) {// 余额转让记录
			Double bi_rate = (Double) ui_dest.getMap().get("bi_rate");
			userBiRecord.setDest_uid(ui_dest.getId());
			userBiRecord.setDest_uname(ui_dest.getUser_name());
			userBiRecord.setBi_rate(new BigDecimal(bi_rate));
		}
		userBiRecordDao.insertEntity(userBiRecord);

	}

	public int compareTo(BigDecimal a, BigDecimal b) {
		return a.compareTo(b);
	}

	/**
	 * 直接增加用户货款币
	 */
	public void insertUserBiHuokuanRecord(Integer user_id, Integer bi_get_type, Integer order_id, Integer link_id,
			BigDecimal bi_dianzi, UserInfoDao userInfoDao, UserBiRecordDao userBiRecordDao) {
		this.insertUserBiRecord(user_id, null, 1, order_id, link_id, bi_dianzi, Keys.BiType.BI_TYPE_300.getIndex(),
				bi_get_type, null, userInfoDao, userBiRecordDao);

		// 一、获取待返余额记录
		UserInfo update_user = new UserInfo();
		update_user.setId(user_id);
		update_user.getMap().put("add_bi_huokuan", bi_dianzi);
		userInfoDao.updateEntity(update_user);
	}

	/**
	 * 根据商品类别获取商品编号
	 */
	public String getCommNoBase(Integer cls_id, BaseClassDao baseClassDao) {
		String comm_no = "";
		BaseClass baseClass = new BaseClass();
		baseClass.setCls_id(Integer.valueOf(cls_id));
		baseClass.setIs_del(0);
		baseClass = baseClassDao.selectEntity(baseClass);
		if (null != baseClass) {
			CommInfo commInfo = new CommInfo();
			Integer count = baseClassDao.selectEntityCount(baseClass);
			if (StringUtils.isBlank(baseClass.getCls_code())) {// 如果为空的话，主动调用
				Integer step_2 = 1;

				BaseClass baseClass2 = new BaseClass();
				baseClass2.setCls_level(2);
				List<BaseClass> baseClass2List = baseClassDao.selectEntityList(baseClass2);
				for (BaseClass bp2 : baseClass2List) {

					String level_1 = StringUtils.leftPad(String.valueOf(step_2++), 2, "0");
					String level_2 = "00";
					String level_3 = "000";
					String clscode2 = level_1.concat(level_2).concat(level_3);

					BaseClass tmp_update_2 = new BaseClass();
					tmp_update_2.setCls_id(bp2.getCls_id());
					tmp_update_2.setCls_code(clscode2);
					baseClassDao.updateEntity(tmp_update_2);

					BaseClass baseClass3 = new BaseClass();
					baseClass3.setCls_level(3);
					baseClass3.setPar_id(tmp_update_2.getCls_id());
					List<BaseClass> baseClass3List = baseClassDao.selectEntityList(baseClass3);
					Integer step_3 = 1;
					for (BaseClass bp3 : baseClass3List) {
						level_1 = StringUtils.substring(clscode2, 0, 2);
						level_2 = StringUtils.leftPad(String.valueOf(step_3++), 2, "0");
						level_3 = "000";

						String clscode3 = level_1.concat(level_2).concat(level_3);
						BaseClass tmp_update_3 = new BaseClass();
						tmp_update_3.setCls_id(bp3.getCls_id());
						tmp_update_3.setCls_code(clscode3);
						baseClassDao.updateEntity(tmp_update_3);

						BaseClass baseClass4 = new BaseClass();
						baseClass4.setCls_level(4);
						baseClass4.setPar_id(tmp_update_3.getCls_id());

						List<BaseClass> baseClass4List = baseClassDao.selectEntityList(baseClass4);
						Integer step_4 = 1;
						for (BaseClass bp4 : baseClass4List) {
							level_1 = StringUtils.substring(clscode3, 0, 2);
							level_2 = StringUtils.substring(clscode3, 2, 4);
							level_3 = StringUtils.leftPad(String.valueOf(step_4++), 3, "0");

							String clscode4 = level_1.concat(level_2).concat(level_3);
							BaseClass tmp_update_4 = new BaseClass();
							tmp_update_4.setCls_id(bp4.getCls_id());
							tmp_update_4.setCls_code(clscode4);
							baseClassDao.updateEntity(tmp_update_4);
						}

					}
				}
			}
			BaseClass bpz = new BaseClass();
			bpz.setCls_id(Integer.valueOf(cls_id));
			bpz.setIs_del(0);
			bpz = baseClassDao.selectEntity(bpz);
			if (null != bpz) {
				comm_no = bpz.getCls_code() + StringUtils.leftPad(String.valueOf(count), 4, "0");
			}

		}
		return comm_no;
	}

	/**
	 * 创建订单（按状态和系统平台）公共方法
	 * 
	 * @param order_money 金额
	 * @param userInfo 用户
	 * @param trade_index 交易号
	 * @param comm_name 商品名称
	 * @param pay_type 支付类型
	 * @param orderType 订单类型
	 * @param orderInfoDao
	 * @param orderInfoDetailsDao
	 * @param trade_no 订单号
	 * @param orderState 订单状态
	 * @param payPlatform 系统平台
	 */
	public int createOrderInfoPublic(BigDecimal order_money, UserInfo userInfo, String trade_index, String comm_name,
			Integer pay_type, int orderType, OrderInfoDao orderInfoDao, OrderInfoDetailsDao orderInfoDetailsDao,
			String trade_no, int orderState, int payPlatform) {
		// 需要插入订单信息 以及直接插入

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setTrade_index(trade_index);
		orderInfo.setTrade_no(trade_no);
		orderInfo.setOrder_num(1);
		orderInfo.setOrder_money(order_money);
		orderInfo.setReal_pay_money(orderInfo.getOrder_money());
		orderInfo.setMatflow_price(new BigDecimal("0"));
		orderInfo.setOrder_weight(new BigDecimal("0"));
		orderInfo.setPay_type(pay_type);
		if (orderState == Keys.OrderState.ORDER_STATE_50.getIndex()) {// 创建支付完成并结束的订单
			orderInfo.setPay_date(new Date());
			orderInfo.setFinish_date(new Date());
		}

		orderInfo.setOrder_date(new Date());
		orderInfo.setAdd_date(new Date());
		orderInfo.setAdd_user_id(userInfo.getId());
		orderInfo.setAdd_user_name(userInfo.getUser_name());

		if (null != userInfo.getMobile()) {
			orderInfo.setRel_phone(userInfo.getMobile());
		}

		// 保存下管理ID到trade_merger_index，方便删除
		if (null != userInfo.getMap().get("tixian_id")) {
			orderInfo.setTrade_merger_index("tixian_" + String.valueOf(userInfo.getMap().get("tixian_id")));
		}
		orderInfo.setOrder_state(orderState);
		orderInfo.setRel_name(userInfo.getUser_name());
		orderInfo.setPay_platform(payPlatform);

		orderInfo.setOrder_type(orderType);
		int order_id = orderInfoDao.insertEntity(orderInfo);
		if (order_id > 0) {
			OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
			orderInfoDetails.setOrder_id(order_id);
			orderInfoDetails.setComm_name(comm_name);
			orderInfoDetails.setOrder_type(orderType);
			orderInfoDetails.setGood_count(1);
			orderInfoDetails.setMatflow_price(new BigDecimal(0));
			orderInfoDetails.setGood_price(order_money);
			orderInfoDetails.setGood_sum_price(order_money);
			orderInfoDetailsDao.insertEntity(orderInfoDetails);

		}
		return order_id;
	}

}
