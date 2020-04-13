package com.ebiz.webapp.dao;

import java.util.List;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.UserInfo;

/**
 * @author Wu,Yang
 * @version 2011-04-20 15:38
 */
public interface UserInfoDao extends EntityDao<UserInfo> {
	public UserInfo selectUserInfoWithSum(UserInfo t);

	public void proUnionscoreGet(UserInfo t);

	Integer selectSpokesmanRankingListCount(UserInfo t);

	List<UserInfo> selectSpokesmanRankingList(UserInfo t);

	List<UserInfo> selectUserSpeciaList(UserInfo t);

	Integer selectUserSpeciaCount(UserInfo t);
}
