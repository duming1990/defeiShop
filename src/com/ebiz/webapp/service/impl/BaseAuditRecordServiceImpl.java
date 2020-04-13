package com.ebiz.webapp.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.BaseAuditRecordDao;
import com.ebiz.webapp.dao.BaseImgsDao;
import com.ebiz.webapp.dao.CommInfoDao;
import com.ebiz.webapp.dao.UserInfoDao;
import com.ebiz.webapp.domain.BaseAuditRecord;
import com.ebiz.webapp.domain.BaseImgs;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.service.BaseAuditRecordService;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.WeiXinSendMessageUtils;

/**
 * @author Wu,Yang
 * @version 2016-01-05 16:48
 */
@Service
public class BaseAuditRecordServiceImpl implements BaseAuditRecordService {

	@Resource
	private BaseAuditRecordDao baseAuditRecordDao;

	@Resource
	private CommInfoDao commInfoDao;

	@Resource
	private UserInfoDao userInfoDao;

	@Resource
	private BaseImgsDao baseImgsDao;

	public Integer createBaseAuditRecord(BaseAuditRecord t) {
		int id = this.baseAuditRecordDao.insertEntity(t);
		if (id > 0) {
			if (null != t.getMap().get("modUserInfo")) {
				UserInfo uiInfo = (UserInfo) t.getMap().get("modUserInfo");

				this.userInfoDao.updateEntity(uiInfo);

				if (null != uiInfo.getMap().get("basefiles")) {
					if (null != uiInfo.getId()) {
						BaseImgs img = new BaseImgs();
						img.getMap().put("link_id", uiInfo.getId());
						img.getMap().put("img_type", Keys.BaseImgsType.Base_Imgs_TYPE_10.getIndex());
						baseImgsDao.deleteEntity(img);

						String[] bfs = (String[]) uiInfo.getMap().get("basefiles");
						for (int i = 0; i < bfs.length; i++) {
							if (StringUtils.isNotBlank(bfs[i])) {
								BaseImgs baseImgs = new BaseImgs();
								baseImgs.setLink_id(uiInfo.getId());
								baseImgs.setImg_type(Keys.BaseImgsType.Base_Imgs_TYPE_10.getIndex());
								baseImgs.setFile_path(bfs[i]);
								baseImgsDao.insertEntity(baseImgs);
							} else {
								continue;
							}
						}
					}
				}
			}
		}

		return id;
	}

	public BaseAuditRecord getBaseAuditRecord(BaseAuditRecord t) {
		return this.baseAuditRecordDao.selectEntity(t);
	}

	public Integer getBaseAuditRecordCount(BaseAuditRecord t) {
		return this.baseAuditRecordDao.selectEntityCount(t);
	}

	public List<BaseAuditRecord> getBaseAuditRecordList(BaseAuditRecord t) {
		return this.baseAuditRecordDao.selectEntityList(t);
	}

	public int modifyBaseAuditRecord(BaseAuditRecord t) {
		int updateId = this.baseAuditRecordDao.updateEntity(t);
		if (updateId > 0) {
			if (null != t.getMap().get("modUserInfo")) {
				UserInfo uiInfo = (UserInfo) t.getMap().get("modUserInfo");

				this.userInfoDao.updateEntity(uiInfo);
				if (null != uiInfo.getMap().get("basefiles")) {
					BaseAuditRecord entity = new BaseAuditRecord();
					entity.setId(t.getId());
					entity.setOpt_type(Keys.OptType.OPT_TYPE_10.getIndex());
					entity.setLink_table("USER_INFO");
					entity = baseAuditRecordDao.selectEntity(entity);

					if (null != entity.getLink_id()) {
						BaseImgs img = new BaseImgs();
						img.getMap().put("link_id", entity.getLink_id());
						img.getMap().put("img_type", Keys.BaseImgsType.Base_Imgs_TYPE_10.getIndex());
						baseImgsDao.deleteEntity(img);
						String[] bfs = (String[]) uiInfo.getMap().get("basefiles");
						for (int i = 0; i < bfs.length; i++) {
							if (StringUtils.isNotBlank(bfs[i])) {
								BaseImgs baseImgs = new BaseImgs();
								baseImgs.setLink_id(entity.getLink_id());
								baseImgs.setImg_type(Keys.BaseImgsType.Base_Imgs_TYPE_10.getIndex());
								baseImgs.setFile_path(bfs[i]);
								baseImgsDao.insertEntity(baseImgs);
							} else {
								continue;
							}
						}
					}
				}
			}

		}

		if (null != t.getMap().get("modify_real_name")) {
			BaseAuditRecord entity = new BaseAuditRecord();
			entity.setId(t.getId());
			entity = baseAuditRecordDao.selectEntity(entity);

			UserInfo userInfo = new UserInfo();
			userInfo.setId(entity.getLink_id());
			if (entity.getAudit_state().intValue() == 1) {
				userInfo.setReal_name(entity.getOpt_note());
				userInfo.setIs_renzheng(1);
			} else {
				userInfo.setIs_renzheng(0);
			}
			userInfoDao.updateEntity(userInfo);
			WeiXinSendMessageUtils.realNameAudit(userInfo, entity);
		}

		if (null != t.getMap().get("is_sell_audit")) {
			BaseAuditRecord baR = new BaseAuditRecord();
			baR.setId(t.getId());
			baR = baseAuditRecordDao.selectEntity(baR);

			CommInfo commInfo = new CommInfo();
			commInfo.setId(baR.getLink_id());
			if (1 == t.getAudit_state()) {
				if (baR.getOpt_type().equals(Keys.OptType.OPT_TYPE_2.getIndex())) {
					commInfo.setIs_sell(Keys.IsSell.IS_SELL_1.getIndex());
					commInfo.setUp_date(new Date());
					commInfo.setDown_date(DateUtils.addDays(new Date(), 7));
				} else if (baR.getOpt_type().equals(Keys.OptType.OPT_TYPE_3.getIndex())) {
					commInfo.setIs_sell(Keys.IsSell.IS_SELL_0.getIndex());
				}

			}
			commInfo.setUpdate_date(new Date());
			commInfoDao.updateEntity(commInfo);
		}
		return updateId;
	}

	public int removeBaseAuditRecord(BaseAuditRecord t) {
		return this.baseAuditRecordDao.deleteEntity(t);
	}

	public List<BaseAuditRecord> getBaseAuditRecordPaginatedList(BaseAuditRecord t) {
		return this.baseAuditRecordDao.selectEntityPaginatedList(t);
	}

}
