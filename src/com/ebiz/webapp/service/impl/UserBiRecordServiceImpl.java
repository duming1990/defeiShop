package com.ebiz.webapp.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.UserBiRecordDao;
import com.ebiz.webapp.domain.UserBiRecord;
import com.ebiz.webapp.service.UserBiRecordService;

/**
 * @author Wu,Yang
 * @version 2015-11-21 14:17
 */
@Service
public class UserBiRecordServiceImpl implements UserBiRecordService {

	@Resource
	private UserBiRecordDao userBiRecordDao;

	public Integer createUserBiRecord(UserBiRecord t) {
		return this.userBiRecordDao.insertEntity(t);
	}

	public UserBiRecord getUserBiRecord(UserBiRecord t) {
		return this.userBiRecordDao.selectEntity(t);
	}

	public Integer getUserBiRecordCount(UserBiRecord t) {
		return this.userBiRecordDao.selectEntityCount(t);
	}

	public List<UserBiRecord> getUserBiRecordList(UserBiRecord t) {
		return this.userBiRecordDao.selectEntityList(t);
	}

	public int modifyUserBiRecord(UserBiRecord t) {
		return this.userBiRecordDao.updateEntity(t);
	}

	public int removeUserBiRecord(UserBiRecord t) {
		return this.userBiRecordDao.deleteEntity(t);
	}

	public List<UserBiRecord> getUserBiRecordPaginatedList(UserBiRecord t) {
		return this.userBiRecordDao.selectEntityPaginatedList(t);
	}

	public BigDecimal getUserBiSum(UserBiRecord t) {
		return this.userBiRecordDao.selectUserBiSum(t);
	}

	public Integer getUserBiRecordCountByOrder(UserBiRecord t) {
		return this.userBiRecordDao.selectUserBiRecordCountByOrder(t);
	}

	public List<UserBiRecord> getUserBiRecordPaginatedListByOrder(UserBiRecord t) {
		return this.userBiRecordDao.selectUserBiRecordPaginatedListByOrder(t);
	}

	public List<UserBiRecord> getCorporateHelpDynamicList(UserBiRecord t) {
		return this.userBiRecordDao.selectCorporateHelpDynamicList(t);
	}

	public List<UserBiRecord> getCorporateHelpList(UserBiRecord t) {
		return this.userBiRecordDao.selectCorporateHelpList(t);
	}

	public List<UserBiRecord> getUserBiRecordRewardList(UserBiRecord t) {
		return this.userBiRecordDao.selectUserBiRecordRewardList(t);
	}

	public Integer getUserBiRecordRewardCount(UserBiRecord t) {
		return this.userBiRecordDao.selectUserBiRecordRewardCount(t);
	}
}
