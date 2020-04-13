package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.BaseBrandInfoDao;
import com.ebiz.webapp.domain.BaseBrandInfo;
import com.ebiz.webapp.service.BaseBrandInfoService;

/**
 * @author Wu,Yang
 * @version 2012-05-18 14:18
 */
@Service
public class BaseBrandInfoServiceImpl implements BaseBrandInfoService {

	@Resource
	private BaseBrandInfoDao baseBrandInfoDao;

	public Integer createBaseBrandInfo(BaseBrandInfo t) {
		return this.baseBrandInfoDao.insertEntity(t);
	}

	public BaseBrandInfo getBaseBrandInfo(BaseBrandInfo t) {
		return this.baseBrandInfoDao.selectEntity(t);
	}

	public Integer getBaseBrandInfoCount(BaseBrandInfo t) {
		return this.baseBrandInfoDao.selectEntityCount(t);
	}

	public List<BaseBrandInfo> getBaseBrandInfoList(BaseBrandInfo t) {
		return this.baseBrandInfoDao.selectEntityList(t);
	}

	public int modifyBaseBrandInfo(BaseBrandInfo t) {
		return this.baseBrandInfoDao.updateEntity(t);
	}

	public int removeBaseBrandInfo(BaseBrandInfo t) {
		return this.baseBrandInfoDao.deleteEntity(t);
	}

	public List<BaseBrandInfo> getBaseBrandInfoPaginatedList(BaseBrandInfo t) {
		return this.baseBrandInfoDao.selectEntityPaginatedList(t);
	}

}
