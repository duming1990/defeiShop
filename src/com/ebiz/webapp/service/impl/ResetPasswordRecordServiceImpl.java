package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.ResetPasswordRecordDao;
import com.ebiz.webapp.domain.ResetPasswordRecord;
import com.ebiz.webapp.service.ResetPasswordRecordService;

/**
 * @author Li,Ka
 * @version 2013-07-23 10:05
 */
@Service
public class ResetPasswordRecordServiceImpl implements ResetPasswordRecordService {

	@Resource
	private ResetPasswordRecordDao resetPasswordRecordDao;

	public Integer createResetPasswordRecord(ResetPasswordRecord t) {
		return this.resetPasswordRecordDao.insertEntity(t);
	}

	public ResetPasswordRecord getResetPasswordRecord(ResetPasswordRecord t) {
		return this.resetPasswordRecordDao.selectEntity(t);
	}

	public Integer getResetPasswordRecordCount(ResetPasswordRecord t) {
		return this.resetPasswordRecordDao.selectEntityCount(t);
	}

	public List<ResetPasswordRecord> getResetPasswordRecordList(ResetPasswordRecord t) {
		return this.resetPasswordRecordDao.selectEntityList(t);
	}

	public int modifyResetPasswordRecord(ResetPasswordRecord t) {
		return this.resetPasswordRecordDao.updateEntity(t);
	}

	public int removeResetPasswordRecord(ResetPasswordRecord t) {
		return this.resetPasswordRecordDao.deleteEntity(t);
	}

	public List<ResetPasswordRecord> getResetPasswordRecordPaginatedList(ResetPasswordRecord t) {
		return this.resetPasswordRecordDao.selectEntityPaginatedList(t);
	}

}
