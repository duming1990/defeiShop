package com.ebiz.webapp.service;

import java.math.BigDecimal;
import java.util.List;

import com.ebiz.webapp.domain.UserBiRecord;

/**
 * @author Wu,Yang
 * @version 2015-11-21 14:17
 */
public interface UserBiRecordService {

	Integer createUserBiRecord(UserBiRecord t);

	int modifyUserBiRecord(UserBiRecord t);

	int removeUserBiRecord(UserBiRecord t);

	UserBiRecord getUserBiRecord(UserBiRecord t);

	List<UserBiRecord> getUserBiRecordList(UserBiRecord t);

	Integer getUserBiRecordCount(UserBiRecord t);

	List<UserBiRecord> getUserBiRecordPaginatedList(UserBiRecord t);

	BigDecimal getUserBiSum(UserBiRecord t);

	Integer getUserBiRecordCountByOrder(UserBiRecord t);

	List<UserBiRecord> getUserBiRecordPaginatedListByOrder(UserBiRecord t);

	List<UserBiRecord> getCorporateHelpDynamicList(UserBiRecord t);

	List<UserBiRecord> getCorporateHelpList(UserBiRecord t);

	List<UserBiRecord> getUserBiRecordRewardList(UserBiRecord t);

	Integer getUserBiRecordRewardCount(UserBiRecord t);

}