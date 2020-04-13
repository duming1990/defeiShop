package com.ebiz.webapp.dao;

import java.math.BigDecimal;
import java.util.List;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.UserBiRecord;

/**
 * @author Wu,Yang
 * @version 2015-11-21 14:17
 */
public interface UserBiRecordDao extends EntityDao<UserBiRecord> {
	public BigDecimal selectUserBiSum(UserBiRecord t);

	public List<UserBiRecord> selectUserBiRecordPaginatedListByOrder(UserBiRecord t);

	public Integer selectUserBiRecordCountByOrder(UserBiRecord t);

	public UserBiRecord selectUserBiAndTianfanSum(UserBiRecord t);

	public List<UserBiRecord> selectCorporateHelpDynamicList(UserBiRecord t);

	public List<UserBiRecord> selectCorporateHelpList(UserBiRecord t);

	public List<UserBiRecord> selectUserBiRecordRewardList(UserBiRecord t);

	public Integer selectUserBiRecordRewardCount(UserBiRecord t);

}
