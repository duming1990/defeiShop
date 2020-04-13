package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.ResetPasswordRecord;

/**
 * @author Li,Ka
 * @version 2013-07-23 10:05
 */
public interface ResetPasswordRecordService {

	Integer createResetPasswordRecord(ResetPasswordRecord t);

	int modifyResetPasswordRecord(ResetPasswordRecord t);

	int removeResetPasswordRecord(ResetPasswordRecord t);

	ResetPasswordRecord getResetPasswordRecord(ResetPasswordRecord t);

	List<ResetPasswordRecord> getResetPasswordRecordList(ResetPasswordRecord t);

	Integer getResetPasswordRecordCount(ResetPasswordRecord t);

	List<ResetPasswordRecord> getResetPasswordRecordPaginatedList(ResetPasswordRecord t);

}