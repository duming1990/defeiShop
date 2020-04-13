package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.EntpCommClassDao;
import com.ebiz.webapp.domain.EntpCommClass;
import com.ebiz.webapp.service.EntpCommClassService;

/**
 * @author Wu,Yang
 * @version 2018-07-10 15:03
 */
@Service
public class EntpCommClassServiceImpl implements EntpCommClassService {

	@Resource
	private EntpCommClassDao entpCommClassDao;
	

	public Integer createEntpCommClass(EntpCommClass t) {
		return this.entpCommClassDao.insertEntity(t);
	}

	public EntpCommClass getEntpCommClass(EntpCommClass t) {
		return this.entpCommClassDao.selectEntity(t);
	}

	public Integer getEntpCommClassCount(EntpCommClass t) {
		return this.entpCommClassDao.selectEntityCount(t);
	}

	public List<EntpCommClass> getEntpCommClassList(EntpCommClass t) {
		return this.entpCommClassDao.selectEntityList(t);
	}

	public int modifyEntpCommClass(EntpCommClass t) {
		return this.entpCommClassDao.updateEntity(t);
	}

	public int removeEntpCommClass(EntpCommClass t) {
		return this.entpCommClassDao.deleteEntity(t);
	}

	public List<EntpCommClass> getEntpCommClassPaginatedList(EntpCommClass t) {
		return this.entpCommClassDao.selectEntityPaginatedList(t);
	}

}
