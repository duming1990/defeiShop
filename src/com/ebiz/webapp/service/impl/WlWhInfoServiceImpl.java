package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.WlWhInfoDao;
import com.ebiz.webapp.domain.WlWhInfo;
import com.ebiz.webapp.service.WlWhInfoService;

/**
 * @author Wu,Yang
 * @version 2014-10-20 10:42
 */
@Service
public class WlWhInfoServiceImpl implements WlWhInfoService {

	@Resource
	private WlWhInfoDao wlWhInfoDao;

	public Integer createWlWhInfo(WlWhInfo t) {
		return this.wlWhInfoDao.insertEntity(t);
	}

	public WlWhInfo getWlWhInfo(WlWhInfo t) {
		return this.wlWhInfoDao.selectEntity(t);
	}

	public Integer getWlWhInfoCount(WlWhInfo t) {
		return this.wlWhInfoDao.selectEntityCount(t);
	}

	public List<WlWhInfo> getWlWhInfoList(WlWhInfo t) {
		return this.wlWhInfoDao.selectEntityList(t);
	}

	public int modifyWlWhInfo(WlWhInfo t) {
		return this.wlWhInfoDao.updateEntity(t);
	}

	public int removeWlWhInfo(WlWhInfo t) {
		return this.wlWhInfoDao.deleteEntity(t);
	}

	public List<WlWhInfo> getWlWhInfoPaginatedList(WlWhInfo t) {
		return this.wlWhInfoDao.selectEntityPaginatedList(t);
	}

}
