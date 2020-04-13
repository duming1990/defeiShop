package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.BaseAuditRecord;

/**
 * @author Wu,Yang
 * @version 2016-01-05 16:48
 */
public interface BaseAuditRecordService {

	Integer createBaseAuditRecord(BaseAuditRecord t);

	int modifyBaseAuditRecord(BaseAuditRecord t);

	int removeBaseAuditRecord(BaseAuditRecord t);

	BaseAuditRecord getBaseAuditRecord(BaseAuditRecord t);

	List<BaseAuditRecord> getBaseAuditRecordList(BaseAuditRecord t);

	Integer getBaseAuditRecordCount(BaseAuditRecord t);

	List<BaseAuditRecord> getBaseAuditRecordPaginatedList(BaseAuditRecord t);

}