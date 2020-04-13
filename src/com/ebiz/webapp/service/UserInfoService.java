package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.UserInfo;

/**
 * @author Wu,Yang
 * @version 2011-04-20 02:13:04
 */
public interface UserInfoService {

	Integer createUserInfo(UserInfo t);

	int modifyUserInfo(UserInfo t);

	int removeUserInfo(UserInfo t);

	UserInfo getUserInfo(UserInfo t);

	List<UserInfo> getUserInfoList(UserInfo t);

	Integer getUserInfoCount(UserInfo t);

	List<UserInfo> getUserInfoPaginatedList(UserInfo t);

	UserInfo getUserInfoWithSum(UserInfo t);

	void updateUserInfoYmIdAndInsertUserRelation(UserInfo t);

	void modifyUserInfoRelation(UserInfo t);

	Integer getSpokesmanRankingListCount(UserInfo t);

	List<UserInfo> getSpokesmanRankingList(UserInfo t);

	List<UserInfo> getUserSpeciaList(UserInfo t);

	Integer getUserSpeciaCount(UserInfo t);
}
