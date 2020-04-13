package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.RwHbYhqDao;
import com.ebiz.webapp.domain.RwHbYhq;
import com.ebiz.webapp.service.RwHbYhqService;

/**
 * @author Wu,Yang
 * @version 2016-06-16 18:18
 */
@Service
public class RwHbYhqServiceImpl implements RwHbYhqService {

	@Resource
	private RwHbYhqDao rwHbYhqDao;
	

	public Integer createRwHbYhq(RwHbYhq t) {
		return this.rwHbYhqDao.insertEntity(t);
	}

	public RwHbYhq getRwHbYhq(RwHbYhq t) {
		return this.rwHbYhqDao.selectEntity(t);
	}

	public Integer getRwHbYhqCount(RwHbYhq t) {
		return this.rwHbYhqDao.selectEntityCount(t);
	}

	public List<RwHbYhq> getRwHbYhqList(RwHbYhq t) {
		return this.rwHbYhqDao.selectEntityList(t);
	}

	public int modifyRwHbYhq(RwHbYhq t) {
		return this.rwHbYhqDao.updateEntity(t);
	}

	public int removeRwHbYhq(RwHbYhq t) {
		return this.rwHbYhqDao.deleteEntity(t);
	}

	public List<RwHbYhq> getRwHbYhqPaginatedList(RwHbYhq t) {
		return this.rwHbYhqDao.selectEntityPaginatedList(t);
	}

}
