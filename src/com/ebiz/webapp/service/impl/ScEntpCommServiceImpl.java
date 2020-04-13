package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.ScEntpCommDao;
import com.ebiz.webapp.domain.ScEntpComm;
import com.ebiz.webapp.service.ScEntpCommService;

/**
 * @author Wu,Yang
 * @version 2014-05-27 18:15
 */
@Service
public class ScEntpCommServiceImpl implements ScEntpCommService {

	@Resource
	private ScEntpCommDao scEntpCommDao;

	public Integer createScEntpComm(ScEntpComm t) {
		return this.scEntpCommDao.insertEntity(t);
	}

	public ScEntpComm getScEntpComm(ScEntpComm t) {
		return this.scEntpCommDao.selectEntity(t);
	}

	public Integer getScEntpCommCount(ScEntpComm t) {
		return this.scEntpCommDao.selectEntityCount(t);
	}

	public List<ScEntpComm> getScEntpCommList(ScEntpComm t) {
		return this.scEntpCommDao.selectEntityList(t);
	}

	public int modifyScEntpComm(ScEntpComm t) {
		return this.scEntpCommDao.updateEntity(t);
	}

	public int removeScEntpComm(ScEntpComm t) {
		return this.scEntpCommDao.deleteEntity(t);
	}

	public List<ScEntpComm> getScEntpCommPaginatedList(ScEntpComm t) {
		return this.scEntpCommDao.selectEntityPaginatedList(t);
	}

}
