package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.UserMoneyApplyDao;
import com.ebiz.webapp.domain.UserMoneyApply;

/**
 * @author Wu,Yang
 * @version 2015-11-21 14:17
 */
@Service
public class UserMoneyApplyDaoSqlMapImpl extends EntityDaoSqlMapImpl<UserMoneyApply> implements UserMoneyApplyDao {

	@Override
	public UserMoneyApply selectEntityForMoneyTongJi(UserMoneyApply t) {
		return (UserMoneyApply) super.getSqlMapClientTemplate().queryForObject("selectUserMoneyApplyForMoneyTongJi", t);
	}

}
