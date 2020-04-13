package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.BaseDataDao;
import com.ebiz.webapp.dao.EntpInfoDao;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.service.BaseDataService;

/**
 * @author Wu,Yang
 * @version 2011-04-20 02:13:04
 */
@Service
public class BaseDataServiceImpl extends BaseImpl implements BaseDataService {

	@Resource
	private BaseDataDao baseDataDao;

	@Resource
	private EntpInfoDao entpInfoDao;

	public Integer createBaseData(BaseData t) {
		int id = this.baseDataDao.insertEntity(t);
		return id;
	}

	public int modifyBaseData(BaseData t) {
		int baseDataCountReturn = this.baseDataDao.updateEntity(t);
		return baseDataCountReturn;
	}

	public int removeBaseData(BaseData t) {
		return this.baseDataDao.deleteEntity(t);
	}

	public BaseData getBaseData(BaseData t) {
		return this.baseDataDao.selectEntity(t);
	}

	public Integer getBaseDataCount(BaseData t) {
		return this.baseDataDao.selectEntityCount(t);
	}

	public List<BaseData> getBaseDataList(BaseData t) {
		return this.baseDataDao.selectEntityList(t);
	}

	public List<BaseData> getBaseDataPaginatedList(BaseData t) {
		return this.baseDataDao.selectEntityPaginatedList(t);
	}

}
