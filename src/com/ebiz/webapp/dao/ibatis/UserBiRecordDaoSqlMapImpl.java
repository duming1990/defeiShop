package com.ebiz.webapp.dao.ibatis;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.UserBiRecordDao;
import com.ebiz.webapp.domain.UserBiRecord;

/**
 * @author Wu,Yang
 * @version 2015-11-21 14:17
 */
@Service
public class UserBiRecordDaoSqlMapImpl extends EntityDaoSqlMapImpl<UserBiRecord> implements UserBiRecordDao {

	public BigDecimal selectUserBiSum(UserBiRecord t) {
		return (BigDecimal) super.getSqlMapClientTemplate().queryForObject("selectUserBiSum", t);
	}

	@SuppressWarnings("unchecked")
	public List<UserBiRecord> selectUserBiRecordPaginatedListByOrder(UserBiRecord t) {
		return super.getSqlMapClientTemplate().queryForList("selectUserBiRecordPaginatedListByOrder", t);
	}

	public Integer selectUserBiRecordCountByOrder(UserBiRecord t) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectUserBiRecordCountByOrder", t);
	}

	public UserBiRecord selectUserBiAndTianfanSum(UserBiRecord t) {
		return (UserBiRecord) super.getSqlMapClientTemplate().queryForObject("selectUserBiAndTianfanSum", t);
	}

	@SuppressWarnings("unchecked")
	public List<UserBiRecord> selectCorporateHelpDynamicList(UserBiRecord t) {
		return super.getSqlMapClientTemplate().queryForList("selectCorporateHelpDynamicList", t);
	}

	@SuppressWarnings("unchecked")
	public List<UserBiRecord> selectCorporateHelpList(UserBiRecord t) {
		return super.getSqlMapClientTemplate().queryForList("selectCorporateHelpList", t);
	}

	@SuppressWarnings("unchecked")
	public List<UserBiRecord> selectUserBiRecordRewardList(UserBiRecord t) {
		return super.getSqlMapClientTemplate().queryForList("selectUserBiRecordRewardList", t);
	}

	@Override
	public Integer selectUserBiRecordRewardCount(UserBiRecord t) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectUserBiRecordRewardCount", t);
	}

}
