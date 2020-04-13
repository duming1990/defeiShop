package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.UserJifenRecordDao;
import com.ebiz.webapp.domain.UserJifenRecord;

/**
 * @author Wu,Yang
 * @version 2016-01-02 21:16
 */
@Service
public class UserJifenRecordDaoSqlMapImpl extends EntityDaoSqlMapImpl<UserJifenRecord> implements UserJifenRecordDao {

	@Override
	public UserJifenRecord selectUserJifenRecordSum(UserJifenRecord t) {
		System.out.println("=====");
		return (UserJifenRecord) super.getSqlMapClientTemplate().queryForObject("selectUserJifenRecordSum", t);
	}
}
