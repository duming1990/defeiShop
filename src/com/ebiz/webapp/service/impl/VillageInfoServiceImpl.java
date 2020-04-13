package com.ebiz.webapp.service.impl;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.dao.BaseAuditRecordDao;
import com.ebiz.webapp.dao.BaseDataDao;
import com.ebiz.webapp.dao.BaseImgsDao;
import com.ebiz.webapp.dao.BaseProvinceDao;
import com.ebiz.webapp.dao.RoleUserDao;
import com.ebiz.webapp.dao.SysOperLogDao;
import com.ebiz.webapp.dao.UserInfoDao;
import com.ebiz.webapp.dao.UserRelationDao;
import com.ebiz.webapp.dao.UserRelationParDao;
import com.ebiz.webapp.dao.VillageInfoDao;
import com.ebiz.webapp.dao.VillageMemberDao;
import com.ebiz.webapp.domain.BaseAuditRecord;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.BaseImgs;
import com.ebiz.webapp.domain.SysOperLog;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.VillageInfo;
import com.ebiz.webapp.domain.VillageMember;
import com.ebiz.webapp.service.VillageInfoService;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DateTools;
import com.ebiz.webapp.web.util.HttpUtils;
import com.ebiz.webapp.web.util.ZxingUtils;

/**
 * @author Wu,Yang
 * @version 2018-01-15 16:11
 */
@Service
public class VillageInfoServiceImpl extends BaseImpl implements VillageInfoService {

	@Resource
	private VillageInfoDao villageInfoDao;

	@Resource
	private UserInfoDao userInfoDao;

	@Resource
	private RoleUserDao roleUserDao;

	@Resource
	private SysOperLogDao sysOperLogDao;

	@Resource
	private BaseProvinceDao baseProvinceDao;

	@Resource
	private BaseAuditRecordDao baseAuditRecordDao;

	@Resource
	private UserRelationDao userRelationDao;

	@Resource
	private UserRelationParDao userRelationParDao;

	@Resource
	private BaseImgsDao baseImgsDao;

	@Resource
	private VillageMemberDao villageMemberDao;

	@Resource
	private BaseDataDao baseDataDao;

	public Integer createVillageInfo(VillageInfo t) {
		Integer id = this.villageInfoDao.insertEntity(t);
		// 审核通过是添加村站默认分类
		if (t.getMap().get("add_fen_lei") != null) {
			String[] type_name = { "肉类", "禽蛋", "水果", " 蔬菜", "干货", "粮油", " 水产", " 传统食品", "手工艺品" };
			for (int i = 0; i < type_name.length; i++) {
				BaseData baData = new BaseData();
				baData.setType_name(type_name[i]);
				baData.setType(Keys.BaseDataType.Base_Data_type_1123.getIndex());
				baData.setAdd_user_id(id);
				baData.setPre_number3(id);
				baData.setAdd_date(new Date());
				baData.setRemark("添加村站默认分类");
				this.baseDataDao.insertEntity(baData);
			}
		}
		return id;
	}

	public VillageInfo getVillageInfo(VillageInfo t) {
		return this.villageInfoDao.selectEntity(t);
	}

	public Integer getVillageInfoCount(VillageInfo t) {
		return this.villageInfoDao.selectEntityCount(t);
	}

	public List<VillageInfo> getVillageInfoList(VillageInfo t) {
		return this.villageInfoDao.selectEntityList(t);
	}

	public int modifyVillageInfo(VillageInfo t) {
		int flag = this.villageInfoDao.updateEntity(t);

		if (t.getMap().get("creat_village_user") != null) {
			// 驿站用户
			UserInfo uInfo = (UserInfo) t.getMap().get("creat_village_user");
			int u_id = this.userInfoDao.insertEntity(uInfo);
			UserInfo userInfoQuery = new UserInfo();
			userInfoQuery.setId(u_id);
			userInfoQuery = this.userInfoDao.selectEntity(userInfoQuery);
			logger.info("=============jsonObject===============================");
			if (null != userInfoQuery) {

				// 审核通过生成对应的二维码信息

				String uploadDir = "files" + File.separator + "village";

				String ctxDir = (String) t.getMap().get("ctxDir");
				if (!ctxDir.endsWith(File.separator)) {
					ctxDir = ctxDir + File.separator;
				}
				File savePath = new File(ctxDir + uploadDir);
				if (!savePath.exists()) {
					savePath.mkdirs();
				}
				String ctx = (String) t.getMap().get("ctx");
				String imgPath = ctxDir + uploadDir + File.separator + t.getId() + ".png";
				String share_url = ctx + "/m/MVillage.do?method=index&id=" + t.getId();
				File imgFile = new File(imgPath);
				if (!imgFile.exists()) {
					String waterMarkPath = ctxDir + "styles/imagesPublic/qrCodeWater.png";
					ZxingUtils.encodeQrcode(share_url, imgPath, 400, 400, waterMarkPath);
					VillageInfo villageInfoUpdate = new VillageInfo();
					villageInfoUpdate.setId(t.getId());
					villageInfoUpdate.setVillage_qrcode(uploadDir + File.separator + t.getId() + ".png");
					this.villageInfoDao.updateEntity(villageInfoUpdate);
				}
				Map<String, String> map = new HashMap<String, String>();// 小程序二维码图片
				map.put("path", "pages/village/village");
				map.put("scene", t.getId().toString());
				String sendGet = HttpUtils.sendGet(Keys.api_url + "/v1/mobile/weixin/getWeixinQrCode", map);
				logger.info("=============jsonObject===============================" + sendGet);
				JSONObject jsonObject = JSONObject.parseObject(sendGet);
				JSONObject url = JSONObject.parseObject(jsonObject.getString("data"));
				String qrcode_path = url.getString("url");
				if (StringUtils.isNotBlank(qrcode_path)) {
					VillageInfo villageInfo = new VillageInfo();
					villageInfo.setId(t.getId());
					villageInfo.setVillage_qrcode_path(qrcode_path);
					villageInfoDao.updateEntity(villageInfo);
				}

				super.roleUserOpt(userInfoQuery, true, roleUserDao);
				// 这个地方需要调用插入上级用户，合伙人默认直接插入admin
				super.insertUserRelationAndPar(u_id, uInfo.getYmid(), userRelationDao, userInfoDao, userRelationParDao);
			}
		}

		if (null != t.getMap().get("creat_village_person_user")) {
			UserInfo user = (UserInfo) t.getMap().get("creat_village_person_user");
			// user.setYmid(t.getId().toString());
			int u_id = this.userInfoDao.insertEntity(user);
			UserInfo userInfoQuery = new UserInfo();
			userInfoQuery.setId(u_id);
			userInfoQuery = this.userInfoDao.selectEntity(userInfoQuery);
			if (null != userInfoQuery) {
				super.roleUserOpt(userInfoQuery, true, roleUserDao);
				// 这个地方需要调用插入上级用户，合伙人默认直接插入admin
				super.insertUserRelationAndPar(u_id, user.getYmid(), userRelationDao, userInfoDao, userRelationParDao);
			}

			VillageMember member = new VillageMember();
			member.setVillage_id(t.getId());
			member.setAdd_user_id(user.getId());
			member.setIs_del(0);
			member = this.villageMemberDao.selectEntity(member);
			if (null == member) {
				// 默认加入村圈子
				VillageMember insertVillageMember = new VillageMember();
				insertVillageMember.setVillage_id(t.getId());
				insertVillageMember.setUser_id(user.getId());
				insertVillageMember.setUser_name(user.getReal_name());
				insertVillageMember.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
				insertVillageMember.setAdd_date(new Date());
				insertVillageMember.setAdd_user_id(user.getId());
				insertVillageMember.setAdd_user_name(user.getReal_name());
				insertVillageMember.setIs_del(0);
				insertVillageMember.setAudit_user_id(user.getAdd_user_id());
				insertVillageMember.setAudit_date(new Date());
				insertVillageMember.setAudit_desc("村站管理者自动加入该村");
				// insertVillageMember.setMobile(user.getMobile());
				this.villageMemberDao.insertEntity(insertVillageMember);
				
				//村民数量自动加1
				VillageInfo v = new VillageInfo();
				v.setId(t.getId());
				v.getMap().put("add_count", 1);
				this.villageInfoDao.updateEntity(v);
				
			}

			// 插入实名认证审核记录base_audit_record
			BaseAuditRecord entity = new BaseAuditRecord();
			entity.setOpt_type(Keys.OptType.OPT_TYPE_10.getIndex());
			entity.setOpt_note(user.getReal_name());
			entity.setLink_id(Integer.valueOf(u_id));
			entity.setLink_table("USER_INFO");
			entity.setAdd_date(new Date());
			entity.setAdd_user_id(user.getAdd_user_id());
			entity.setAdd_user_name(user.getUser_name());
			if (null != t.getMap().get("add_user_name")) {
				String add_user_name = (String) t.getMap().get("add_user_name");
				entity.setAudit_user_name(add_user_name);
			}
			entity.setAudit_state(t.getAudit_state());
			entity.setAudit_note("村站审核通过，村管理员自动实名认证");
			entity.setAudit_user_id(user.getAdd_user_id());
			entity.setAudit_date(new Date());
			this.baseAuditRecordDao.insertEntity(entity);
			// 身份证正反面插入BaseImgs----先删
			BaseImgs img = new BaseImgs();
			img.getMap().put("link_id", entity.getLink_id());
			img.getMap().put("img_type", Keys.BaseImgsType.Base_Imgs_TYPE_10.getIndex());
			baseImgsDao.deleteEntity(img);
			// ----后增
			if (user.getImg_id_card_zm() != null) {
				BaseImgs baseImgs = new BaseImgs();
				baseImgs.setLink_id(entity.getLink_id());
				baseImgs.setImg_type(Keys.BaseImgsType.Base_Imgs_TYPE_10.getIndex());
				baseImgs.setFile_path(user.getImg_id_card_zm());
				baseImgsDao.insertEntity(baseImgs);
			}
			if (user.getImg_id_card_fm() != null) {
				BaseImgs baseImgs = new BaseImgs();
				baseImgs.setLink_id(entity.getLink_id());
				baseImgs.setImg_type(Keys.BaseImgsType.Base_Imgs_TYPE_10.getIndex());
				baseImgs.setFile_path(user.getImg_id_card_fm());
				baseImgsDao.insertEntity(baseImgs);
			}

		}

		if (t.getMap().get("update_person_user") != null) {
			// 更新个人用户is_village==0
			UserInfo person_user = (UserInfo) t.getMap().get("update_person_user");
			this.userInfoDao.updateEntity(person_user);
			super.roleUserOpt(person_user, true, roleUserDao);
		}

		if (null != t.getMap().get("del_village_user")) {
			UserInfo user = (UserInfo) t.getMap().get("update_village_user");
			this.userInfoDao.updateEntity(user);
		}

		if (t.getMap().get("active_village_person_user") != null) {
			// 更新个人用户is_village==1
			UserInfo person_user = (UserInfo) t.getMap().get("active_village_person_user");
			this.userInfoDao.updateEntity(person_user);
			super.roleUserOpt(person_user, true, roleUserDao);

			VillageMember member = new VillageMember();
			member.setVillage_id(t.getId());
			member.setAdd_user_id(person_user.getId());
			member.setIs_del(0);
			member = this.villageMemberDao.selectEntity(member);
			if (null == member) {
				// 默认加入村圈子
				VillageMember insertVillageMember = new VillageMember();
				insertVillageMember.setVillage_id(t.getId());
				insertVillageMember.setUser_id(person_user.getId());
				insertVillageMember.setUser_name(person_user.getReal_name());
				insertVillageMember.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
				insertVillageMember.setAdd_date(new Date());
				insertVillageMember.setAdd_user_id(person_user.getId());
				insertVillageMember.setAdd_user_name(person_user.getReal_name());
				insertVillageMember.setIs_del(0);
				insertVillageMember.setMobile(person_user.getMobile());
				insertVillageMember.setAudit_user_id(person_user.getUpdate_user_id());
				insertVillageMember.setAudit_date(new Date());
				insertVillageMember.setAudit_desc("村站管理者自动加入该村");
				this.villageMemberDao.insertEntity(insertVillageMember);
				
				//村民数量自动加1
				VillageInfo v = new VillageInfo();
				v.setId(t.getId());
				v.getMap().put("add_count", 1);
				this.villageInfoDao.updateEntity(v);
			}
			VillageInfo villageInfo = new VillageInfo();
			villageInfo.setId(t.getId());
			villageInfo = villageInfoDao.selectEntity(villageInfo);
			if (null != villageInfo && StringUtils.isBlank(villageInfo.getVillage_qrcode_path())) {
				Map<String, String> map = new HashMap<String, String>();// 小程序二维码图片
				map.put("path", "pages/village/village");
				map.put("scene", t.getId().toString());
				String sendGet = HttpUtils.sendGet(Keys.api_url + "/v1/mobile/weixin/getWeixinQrCode", map);
				logger.info("=============jsonObject===============================" + sendGet);
				JSONObject jsonObject = JSONObject.parseObject(sendGet);
				JSONObject url = JSONObject.parseObject(jsonObject.getString("data"));
				String qrcode_path = url.getString("url");
				if (StringUtils.isNotBlank(qrcode_path)) {
					VillageInfo villageInfoUpdate = new VillageInfo();
					villageInfoUpdate.setId(t.getId());
					villageInfoUpdate.setVillage_qrcode_path(qrcode_path);
					villageInfoDao.updateEntity(villageInfoUpdate);
				}
			}
		}

		if (t.getMap().get("activate_village_user") != null) {
			UserInfo uInfo = (UserInfo) t.getMap().get("activate_village_user");
			this.userInfoDao.updateEntity(uInfo);
			super.roleUserOpt(uInfo, true, roleUserDao);
		}

		if (null != t.getMap().get("cancel_link_user_info_is_village")) {// 取消村驿站，修改关联用户信息
			// ---操作日志----
			SysOperLog sysOperLog = new SysOperLog();
			sysOperLog.setOper_type(Keys.SysOperType.SysOperType_80.getIndex());
			sysOperLog.setOper_name(Keys.SysOperType.SysOperType_80.getName());
			sysOperLog.setOper_time(new Date());

			if (null != t.getMap().get("cancel_oper_uid")
					&& GenericValidator.isInt(t.getMap().get("cancel_oper_uid").toString())) {
				sysOperLog.setOper_uid(new Integer(t.getMap().get("cancel_oper_uid").toString()));
			}

			if (null != t.getMap().get("cancel_oper_uname")) {
				sysOperLog.setOper_uname(t.getMap().get("cancel_oper_uname").toString());
			}

			sysOperLog.setLink_id(t.getId());
			VillageInfo village = new VillageInfo();
			village.setId(t.getId());
			village.setIs_virtual(t.getIs_virtual());
			village = this.villageInfoDao.selectEntity(village);

			if (null != village) {
				String oper_memo = "取消驿站：" + village.getVillage_name() + ",失效时间：" + DateTools.getStringDate(new Date());
				sysOperLog.setOper_memo(oper_memo);
			}

			this.sysOperLogDao.insertEntity(sysOperLog);
			// ---修改关联用户is_village----
			UserInfo uInfo = new UserInfo();
			uInfo.setOwn_village_id(t.getId());
			uInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
			uInfo.setIs_village(1);
			// 个人用户
			uInfo.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());
			UserInfo userInfo = this.userInfoDao.selectEntity(uInfo);

			if (null != userInfo) {
				// is_village改为0，own_village_id改为null
				userInfo.setIs_village(0);// 取消服务中心
				userInfo.setOwn_village_id(null);
				userInfo.setOwn_village_name(null);
				userInfo.setUpdate_date(new Date());
				userInfo.setUpdate_user_id(t.getUpdate_user_id());
				this.userInfoDao.updateEntity(userInfo);
				// 去除村个人用户的驿站权限
				super.roleUserOpt(userInfo, true, roleUserDao);

				BaseAuditRecord baseAuditRecord = new BaseAuditRecord();
				baseAuditRecord.setLink_id(t.getId());
				baseAuditRecord.setLink_table("VillageInfo");
				baseAuditRecord.setAudit_note(t.getAudit_desc());
				if (null != t.getMap().get("cancel_oper_uid")
						&& GenericValidator.isInt(t.getMap().get("cancel_oper_uid").toString())) {
					baseAuditRecord.setAudit_user_id(new Integer(t.getMap().get("cancel_oper_uid").toString()));
				}

				if (null != t.getMap().get("cancel_oper_uname")) {
					baseAuditRecord.setAudit_user_name(t.getMap().get("cancel_oper_uname").toString());
				}
				baseAuditRecord.setAudit_state(t.getAudit_state());
				baseAuditRecord.setOpt_type(Keys.OptType.OPT_TYPE_4.getIndex());
				baseAuditRecordDao.insertEntity(baseAuditRecord);
			}
			// 村站用户
			uInfo.setUser_type(Keys.UserType.USER_TYPE_4.getIndex());
			UserInfo user = this.userInfoDao.selectEntity(uInfo);

			if (null != user) {
				// is_village改为0
				user.setIs_village(0);// 取消村驿站
				user.setIs_active(0);// 未激活
				user.setUpdate_date(new Date());
				user.setUpdate_user_id(t.getUpdate_user_id());
				this.userInfoDao.updateEntity(user);
				// 去除村驿站用户的权限
				super.roleUserOpt(user, true, roleUserDao);
			}
		}
		return flag;
	}

	public int removeVillageInfo(VillageInfo t) {
		return this.villageInfoDao.deleteEntity(t);
	}

	public List<VillageInfo> getVillageInfoPaginatedList(VillageInfo t) {
		return this.villageInfoDao.selectEntityPaginatedList(t);
	}
}
