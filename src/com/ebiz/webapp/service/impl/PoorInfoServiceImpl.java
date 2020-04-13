package com.ebiz.webapp.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.BaseAuditRecordDao;
import com.ebiz.webapp.dao.BaseImgsDao;
import com.ebiz.webapp.dao.PoorCuoShiDao;
import com.ebiz.webapp.dao.PoorFamilyDao;
import com.ebiz.webapp.dao.PoorInfoDao;
import com.ebiz.webapp.dao.UserBiRecordDao;
import com.ebiz.webapp.dao.UserInfoDao;
import com.ebiz.webapp.dao.UserRelationDao;
import com.ebiz.webapp.dao.UserRelationParDao;
import com.ebiz.webapp.dao.VillageInfoDao;
import com.ebiz.webapp.dao.VillageMemberDao;
import com.ebiz.webapp.domain.BaseAuditRecord;
import com.ebiz.webapp.domain.BaseImgs;
import com.ebiz.webapp.domain.PoorFamily;
import com.ebiz.webapp.domain.PoorInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.VillageInfo;
import com.ebiz.webapp.domain.VillageMember;
import com.ebiz.webapp.service.PoorInfoService;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 * @version 2018-01-20 15:08
 */
@Service
public class PoorInfoServiceImpl extends BaseImpl implements PoorInfoService {

	@Resource
	private PoorInfoDao poorInfoDao;

	@Resource
	private UserInfoDao userInfoDao;

	@Resource
	private UserRelationDao userRelationDao;

	@Resource
	private UserRelationParDao userRelationParDao;

	@Resource
	private BaseAuditRecordDao baseAuditRecordDao;

	@Resource
	private BaseImgsDao baseImgsDao;

	@Resource
	private PoorFamilyDao poorFamilyDao;

	@Resource
	private VillageMemberDao villageMemberDao;

	@Resource
	private VillageInfoDao villageInfoDao;

	@Resource
	private UserBiRecordDao userBiRecordDao;

	@Resource
	private PoorCuoShiDao poorCuoShiDao;

	public Integer createPoorInfo(PoorInfo t) {
		return this.poorInfoDao.insertEntity(t);
	}

	// 导入贫困户
	public Integer createImportPoorInfo(PoorInfo t) {
		int count = 0;
		List<PoorInfo> poorInfoList = t.getPoorInfoList();
		if (poorInfoList != null && poorInfoList.size() > 0) {
			for (PoorInfo temp : poorInfoList) {
				int flag = this.poorInfoDao.insertEntity(temp);
				if (Integer.valueOf(flag) > 0) {
					count++;
					List<PoorFamily> poorFamilyList = temp.getPoorFamilyList();
					if (poorFamilyList != null && poorFamilyList.size() > 0) {
						for (PoorFamily cur : poorFamilyList) {
							cur.setLink_id(flag);
							this.poorFamilyDao.insertEntity(cur);
						}
					}
				}
			}
		}
		return count;
	}

	public PoorInfo getPoorInfo(PoorInfo t) {
		return this.poorInfoDao.selectEntity(t);
	}

	public Integer getPoorInfoCount(PoorInfo t) {
		return this.poorInfoDao.selectEntityCount(t);
	}

	public List<PoorInfo> getPoorInfoList(PoorInfo t) {
		return this.poorInfoDao.selectEntityList(t);
	}

	public int modifyPoorInfo(PoorInfo t) {

		if (null != t.getMap().get("creat_poor_user")) {
			UserInfo user = (UserInfo) t.getMap().get("creat_poor_user");
			int flag = this.userInfoDao.insertEntity(user);

			// 这个地方需要调用插入上级用户，合伙人默认直接插入admin
			super.insertUserRelationAndPar(flag, user.getYmid(), userRelationDao, userInfoDao, userRelationParDao);

			// 插入实名认证审核记录base_audit_record
			BaseAuditRecord entity = new BaseAuditRecord();
			entity.setOpt_type(Keys.OptType.OPT_TYPE_10.getIndex());
			entity.setOpt_note(user.getReal_name());
			entity.setLink_id(Integer.valueOf(flag));
			entity.setLink_table("USER_INFO");
			entity.setAdd_date(new Date());
			entity.setAdd_user_id(user.getAdd_user_id());
			entity.setAdd_user_name(user.getUser_name());
			if (null != t.getMap().get("add_user_name")) {
				String add_user_name = (String) t.getMap().get("add_user_name");
				entity.setAudit_user_name(add_user_name);
			}
			entity.setAudit_state(t.getAudit_state());
			entity.setAudit_note("贫困户审核通过，自动实名认证");
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
			if (null != t.getMap().get("poorInfo")) {
				PoorInfo pooInfo = (PoorInfo) t.getMap().get("poorInfo");
				// 默认加入村通讯录
				VillageMember villageMenber = new VillageMember();
				villageMenber.setVillage_id(pooInfo.getVillage_id());
				villageMenber.setUser_id(flag);
				villageMenber.setIs_del(0);
				Integer num = villageMemberDao.selectEntityCount(villageMenber);
				if (num == 0) {
					villageMenber.setUser_id(flag);
					villageMenber.setUser_name(user.getUser_name());
					villageMenber.setAudit_state(1);
					villageMenber.setAudit_date(new Date());
					villageMenber.setAudit_user_id(user.getAdd_user_id());
					villageMenber.setAdd_date(new Date());
					villageMenber.setAdd_user_id(pooInfo.getAdd_user_id());
					villageMenber.setAdd_user_name(pooInfo.getAdd_user_name());
					this.villageMemberDao.insertEntity(villageMenber);
				}

				VillageInfo updateVillage = new VillageInfo();
				updateVillage.setId(pooInfo.getVillage_id());
				updateVillage.getMap().put("add_count", 1);
				villageInfoDao.updateEntity(updateVillage);

			}

			t.setUser_id(flag);
			t.setUser_name(user.getUser_name());
		}

		if (null != t.getMap().get("update_poor_user_cun_zai")) {
			UserInfo user = (UserInfo) t.getMap().get("update_poor_user_cun_zai");
			this.userInfoDao.updateEntity(user);
			t.setUser_id(user.getId());
			t.setUser_name(user.getUser_name());
		}

		if (null != t.getMap().get("update_poor_user")) {
			UserInfo user = (UserInfo) t.getMap().get("update_poor_user");
			user.setReal_name(t.getReal_name());
			user.setSex(t.getSex());
			user.setBirthday(t.getBrithday());
			user.setUpdate_date(new Date());
			this.userInfoDao.updateEntity(user);
		}

		if (null != t.getMap().get("cancel_link_user_info_is_poor")) {
			UserInfo user = new UserInfo();
			user.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());
			user.setIs_poor(1);
			user.setPoor_id(t.getId());
			user = this.userInfoDao.selectEntity(user);
			user.setUpdate_date(new Date());
			user.setUpdate_user_id(t.getUpdate_user_id());
			user.setIs_poor(0);
			this.userInfoDao.updateEntity(user);
		}
		return this.poorInfoDao.updateEntity(t);
	}

	public int removePoorInfo(PoorInfo t) {
		return this.poorInfoDao.deleteEntity(t);
	}

	public List<PoorInfo> getPoorInfoPaginatedList(PoorInfo t) {
		return this.poorInfoDao.selectEntityPaginatedList(t);
	}

	public List<PoorInfo> getPoorInfoListWithAidMoney(PoorInfo t) {
		return this.poorInfoDao.selectPoorInfoListWithAidMoney(t);
	}

	public List<PoorInfo> getPoorMoneyReport(PoorInfo t) {
		return this.poorInfoDao.selectPoorMoneyReport(t);
	}

	/**
	 * 九个挑夫生鲜扶贫扶贫
	 */
	public int createPoorInfoAid(Integer user_id, Integer bi_get_type, BigDecimal money, String entp_name,
			String add_user_name) {

		return super.supermarketUserBiAid(user_id, bi_get_type, null, null, money, entp_name, add_user_name,
				userInfoDao, userBiRecordDao, poorCuoShiDao);

	}

	@Override
	public Integer getVillageManagerPoorInfoCount(PoorInfo t) {
		return this.poorInfoDao.selectVillageManagerPoorInfoCount(t);
	}

	@Override
	public List<PoorInfo> getVillageManagerPoorInfoPaginatedList(PoorInfo t) {
		return this.poorInfoDao.selectVillageManagerPoorInfoPaginatedList(t);
	}

	@Override
	public List<PoorInfo> getPoorInfoListSortByCommCount(PoorInfo t) {
		return poorInfoDao.selectPoorInfoListSortByCommCount(t);
	}
}
