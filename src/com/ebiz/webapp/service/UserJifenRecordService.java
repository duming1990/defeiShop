package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.UserJifenRecord;

/**
 * @author Wu,Yang
 * @version 2016-01-02 21:16
 */
public interface UserJifenRecordService {

	Integer createUserJifenRecord(UserJifenRecord t);

	int modifyUserJifenRecord(UserJifenRecord t);

	int removeUserJifenRecord(UserJifenRecord t);

	UserJifenRecord getUserJifenRecord(UserJifenRecord t);

	List<UserJifenRecord> getUserJifenRecordList(UserJifenRecord t);

	Integer getUserJifenRecordCount(UserJifenRecord t);

	List<UserJifenRecord> getUserJifenRecordPaginatedList(UserJifenRecord t);

	UserJifenRecord getUserJifenRecordSum(UserJifenRecord t);

}