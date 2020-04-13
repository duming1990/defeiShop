package com.ebiz.webapp.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.BaseDataDao;
import com.ebiz.webapp.dao.UserInfoDao;
import com.ebiz.webapp.dao.UserScoreRecordDao;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.UserScoreRecord;
import com.ebiz.webapp.service.UserScoreRecordService;

/**
 * @author Wu,Yang
 * @version 2014-05-27 16:43
 */
@Service
public class UserScoreRecordServiceImpl implements UserScoreRecordService {

	@Resource
	private UserScoreRecordDao userScoreRecordDao;

	@Resource
	private BaseDataDao BaseDataDao;

	@Resource
	private UserInfoDao userInfoDao;

	@Override
	public Integer createUserScoreRecord(UserScoreRecord t) {
		UserInfo ui = (UserInfo) t.getMap().get("uiForUserScore");
		// 用户签到积分
		if (null != ui) {
			if (null != ui.getId()) {

				BaseData baseData = new BaseData();
				baseData.setIs_del(0);
				baseData.setType(6620);
				baseData = BaseDataDao.selectEntity(baseData);
				if (null != baseData) {

					BigDecimal get_jf_count = ui.getCur_score().add(new BigDecimal(baseData.getPre_number()));
					UserInfo userInfo = new UserInfo();
					userInfo.setId(ui.getId());
					userInfo.setCur_score(get_jf_count);

					BaseData baseData2 = new BaseData();
					baseData2.setType(200);
					baseData2.setIs_del(0);
					baseData2.getMap().put("order_by_pre_number", true);
					List<BaseData> baseDataList = BaseDataDao.selectEntityList(baseData);
					for (BaseData bd : baseDataList) {// 判断用户是不是需要升级
						if (get_jf_count.compareTo(new BigDecimal(bd.getPre_number())) >= 0) {
							userInfo.setUser_level(bd.getId());
						}
					}
					userInfoDao.updateEntity(userInfo);
				}
			}
		}

		return this.userScoreRecordDao.insertEntity(t);
	}

	@Override
	public UserScoreRecord getUserScoreRecord(UserScoreRecord t) {
		return this.userScoreRecordDao.selectEntity(t);
	}

	@Override
	public Integer getUserScoreRecordCount(UserScoreRecord t) {
		return this.userScoreRecordDao.selectEntityCount(t);
	}

	@Override
	public List<UserScoreRecord> getUserScoreRecordList(UserScoreRecord t) {
		return this.userScoreRecordDao.selectEntityList(t);
	}

	@Override
	public int modifyUserScoreRecord(UserScoreRecord t) {
		return this.userScoreRecordDao.updateEntity(t);
	}

	@Override
	public int removeUserScoreRecord(UserScoreRecord t) {
		return this.userScoreRecordDao.deleteEntity(t);
	}

	@Override
	public List<UserScoreRecord> getUserScoreRecordPaginatedList(UserScoreRecord t) {
		return this.userScoreRecordDao.selectEntityPaginatedList(t);
	}

}
