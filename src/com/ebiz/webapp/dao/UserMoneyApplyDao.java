package com.ebiz.webapp.dao;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.UserMoneyApply;

/**
 * @author Wu,Yang
 * @version 2015-11-21 14:17
 */
public interface UserMoneyApplyDao extends EntityDao<UserMoneyApply> {

	UserMoneyApply selectEntityForMoneyTongJi(UserMoneyApply t);

}
