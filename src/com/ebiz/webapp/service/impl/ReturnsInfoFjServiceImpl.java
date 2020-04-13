package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.ReturnsInfoFjDao;
import com.ebiz.webapp.domain.ReturnsInfoFj;
import com.ebiz.webapp.service.ReturnsInfoFjService;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:55
 */
@Service
public class ReturnsInfoFjServiceImpl implements ReturnsInfoFjService {

	@Resource
	private ReturnsInfoFjDao returnsInfoFjDao;

	public Integer createReturnsInfoFj(ReturnsInfoFj t) {
		return this.returnsInfoFjDao.insertEntity(t);
	}

	public ReturnsInfoFj getReturnsInfoFj(ReturnsInfoFj t) {
		return this.returnsInfoFjDao.selectEntity(t);
	}

	public Integer getReturnsInfoFjCount(ReturnsInfoFj t) {
		return this.returnsInfoFjDao.selectEntityCount(t);
	}

	public List<ReturnsInfoFj> getReturnsInfoFjList(ReturnsInfoFj t) {
		return this.returnsInfoFjDao.selectEntityList(t);
	}

	public int modifyReturnsInfoFj(ReturnsInfoFj t) {
		return this.returnsInfoFjDao.updateEntity(t);
	}

	public int removeReturnsInfoFj(ReturnsInfoFj t) {
		return this.returnsInfoFjDao.deleteEntity(t);
	}

	public List<ReturnsInfoFj> getReturnsInfoFjPaginatedList(ReturnsInfoFj t) {
		return this.returnsInfoFjDao.selectEntityPaginatedList(t);
	}

}
