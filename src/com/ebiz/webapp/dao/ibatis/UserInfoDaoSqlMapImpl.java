package com.ebiz.webapp.dao.ibatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.UserInfoDao;
import com.ebiz.webapp.domain.UserInfo;

/**
 * @author Wu,Yang
 * @version 2011-04-20 15:38
 */
@Service
public class UserInfoDaoSqlMapImpl extends EntityDaoSqlMapImpl<UserInfo> implements UserInfoDao {
	public UserInfo selectUserInfoWithSum(UserInfo t) {
		return (UserInfo) super.getSqlMapClientTemplate().queryForObject("selectUserInfoWithSum", t);
	}

	public void proUnionscoreGet(UserInfo t) {
		super.getSqlMapClientTemplate().queryForObject("proUnionscoreGet", t);
	}

	@Override
	public Integer selectSpokesmanRankingListCount(UserInfo t) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectSpokesmanRankingListCount", t);
	}

	@SuppressWarnings("unchecked")
	public List<UserInfo> selectSpokesmanRankingList(UserInfo t) {
		return super.getSqlMapClientTemplate().queryForList("selectSpokesmanRankingList", t);
	}

	@SuppressWarnings("unchecked")
	public List<UserInfo> selectUserSpeciaList(UserInfo t) {
		return super.getSqlMapClientTemplate().queryForList("selectUserSpeciaList", t);
	}

	@Override
	public Integer selectUserSpeciaCount(UserInfo t) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectUserSpeciaCount", t);
	}
}
