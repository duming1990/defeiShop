package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.SysOperLogDao;
import com.ebiz.webapp.domain.SysOperLog;
import com.ebiz.webapp.service.SysOperLogService;

/**
 * @author Wu,Yang
 * @version 2013-12-05 07:20
 */
@Service
public class SysOperLogServiceImpl implements SysOperLogService {

	@Resource
	private SysOperLogDao sysOperLogDao;

	public Integer createSysOperLog(SysOperLog t) {
		return this.sysOperLogDao.insertEntity(t);
	}

	public SysOperLog getSysOperLog(SysOperLog t) {
		return this.sysOperLogDao.selectEntity(t);
	}

	public Integer getSysOperLogCount(SysOperLog t) {
		return this.sysOperLogDao.selectEntityCount(t);
	}

	public List<SysOperLog> getSysOperLogList(SysOperLog t) {
		return this.sysOperLogDao.selectEntityList(t);
	}

	public int modifySysOperLog(SysOperLog t) {
		return this.sysOperLogDao.updateEntity(t);
	}

	public int removeSysOperLog(SysOperLog t) {
		return this.sysOperLogDao.deleteEntity(t);
	}

	public List<SysOperLog> getSysOperLogPaginatedList(SysOperLog t) {
		return this.sysOperLogDao.selectEntityPaginatedList(t);
	}

}
