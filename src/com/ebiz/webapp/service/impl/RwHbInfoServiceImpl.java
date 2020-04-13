package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.RwHbInfoDao;
import com.ebiz.webapp.domain.RwHbInfo;
import com.ebiz.webapp.service.RwHbInfoService;

/**
 * @author Wu,Yang
 * @version 2016-06-16 18:18
 */
@Service
public class RwHbInfoServiceImpl implements RwHbInfoService {

	@Resource
	private RwHbInfoDao rwHbInfoDao;
	

	public Integer createRwHbInfo(RwHbInfo t) {
		return this.rwHbInfoDao.insertEntity(t);
	}

	public RwHbInfo getRwHbInfo(RwHbInfo t) {
		return this.rwHbInfoDao.selectEntity(t);
	}

	public Integer getRwHbInfoCount(RwHbInfo t) {
		return this.rwHbInfoDao.selectEntityCount(t);
	}

	public List<RwHbInfo> getRwHbInfoList(RwHbInfo t) {
		return this.rwHbInfoDao.selectEntityList(t);
	}

	public int modifyRwHbInfo(RwHbInfo t) {
		return this.rwHbInfoDao.updateEntity(t);
	}

	public int removeRwHbInfo(RwHbInfo t) {
		return this.rwHbInfoDao.deleteEntity(t);
	}

	public List<RwHbInfo> getRwHbInfoPaginatedList(RwHbInfo t) {
		return this.rwHbInfoDao.selectEntityPaginatedList(t);
	}

}
