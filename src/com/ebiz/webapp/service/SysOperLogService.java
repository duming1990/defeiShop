package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.SysOperLog;

/**
 * @author Wu,Yang
 * @version 2013-12-05 07:20
 */
public interface SysOperLogService {

	Integer createSysOperLog(SysOperLog t);

	int modifySysOperLog(SysOperLog t);

	int removeSysOperLog(SysOperLog t);

	SysOperLog getSysOperLog(SysOperLog t);

	List<SysOperLog> getSysOperLogList(SysOperLog t);

	Integer getSysOperLogCount(SysOperLog t);

	List<SysOperLog> getSysOperLogPaginatedList(SysOperLog t);

}