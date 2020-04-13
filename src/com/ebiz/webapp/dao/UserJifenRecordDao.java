package com.ebiz.webapp.dao;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.UserJifenRecord;

/**
 * @author Wu,Yang
 * @version 2016-01-02 21:16
 */
public interface UserJifenRecordDao extends EntityDao<UserJifenRecord> {

	public UserJifenRecord selectUserJifenRecordSum(UserJifenRecord t);
}
