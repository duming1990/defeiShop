package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.UserMoneyApply;

/**
 * @author Wu,Yang
 * @version 2015-11-21 14:17
 */
public interface UserMoneyApplyService {

	Integer createUserMoneyApply(UserMoneyApply t);

	int modifyUserMoneyApply(UserMoneyApply t);

	int removeUserMoneyApply(UserMoneyApply t);

	UserMoneyApply getUserMoneyApply(UserMoneyApply t);

	List<UserMoneyApply> getUserMoneyApplyList(UserMoneyApply t);

	Integer getUserMoneyApplyCount(UserMoneyApply t);

	UserMoneyApply getUserMoneyApplyForMoneyTongJi(UserMoneyApply t);

	List<UserMoneyApply> getUserMoneyApplyPaginatedList(UserMoneyApply t);

}