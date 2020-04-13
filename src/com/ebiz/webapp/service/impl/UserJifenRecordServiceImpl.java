package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.UserJifenRecordDao;
import com.ebiz.webapp.domain.UserJifenRecord;
import com.ebiz.webapp.service.UserJifenRecordService;

/**
 * @author Wu,Yang
 * @version 2016-01-02 21:16
 */
@Service
public class UserJifenRecordServiceImpl implements UserJifenRecordService {

	@Resource
	private UserJifenRecordDao userJifenRecordDao;

	public Integer createUserJifenRecord(UserJifenRecord t) {
		return this.userJifenRecordDao.insertEntity(t);
	}

	public UserJifenRecord getUserJifenRecord(UserJifenRecord t) {
		return this.userJifenRecordDao.selectEntity(t);
	}

	public Integer getUserJifenRecordCount(UserJifenRecord t) {
		return this.userJifenRecordDao.selectEntityCount(t);
	}

	public List<UserJifenRecord> getUserJifenRecordList(UserJifenRecord t) {
		return this.userJifenRecordDao.selectEntityList(t);
	}

	public int modifyUserJifenRecord(UserJifenRecord t) {
		return this.userJifenRecordDao.updateEntity(t);
	}

	public int removeUserJifenRecord(UserJifenRecord t) {
		return this.userJifenRecordDao.deleteEntity(t);
	}

	public List<UserJifenRecord> getUserJifenRecordPaginatedList(UserJifenRecord t) {
		return this.userJifenRecordDao.selectEntityPaginatedList(t);
	}

	public UserJifenRecord getUserJifenRecordSum(UserJifenRecord t) {
		return this.userJifenRecordDao.selectUserJifenRecordSum(t);
	}
}
