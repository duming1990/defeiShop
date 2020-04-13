package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.BasePopedomDao;
import com.ebiz.webapp.domain.BasePopedom;
import com.ebiz.webapp.service.BasePopedomService;

/**
 * @author Wu,Yang
 * @version 2012-02-14 10:56
 */
@Service
public class BasePopedomServiceImpl implements BasePopedomService {

	@Resource
	private BasePopedomDao basePopedomDao;

	public Integer createBasePopedom(BasePopedom t) {
		return this.basePopedomDao.insertEntity(t);
	}

	public BasePopedom getBasePopedom(BasePopedom t) {
		return this.basePopedomDao.selectEntity(t);
	}

	public Integer getBasePopedomCount(BasePopedom t) {
		return this.basePopedomDao.selectEntityCount(t);
	}

	public List<BasePopedom> getBasePopedomList(BasePopedom t) {
		return this.basePopedomDao.selectEntityList(t);
	}

	public int modifyBasePopedom(BasePopedom t) {
		return this.basePopedomDao.updateEntity(t);
	}

	public int removeBasePopedom(BasePopedom t) {
		return this.basePopedomDao.deleteEntity(t);
	}

	public List<BasePopedom> getBasePopedomPaginatedList(BasePopedom t) {
		return this.basePopedomDao.selectEntityPaginatedList(t);
	}

}
