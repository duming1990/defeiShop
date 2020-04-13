package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.JdAreasDao;
import com.ebiz.webapp.domain.JdAreas;
import com.ebiz.webapp.service.JdAreasService;

/**
 * @author Wu,Yang
 * @version 2018-03-04 16:08
 */
@Service
public class JdAreasServiceImpl implements JdAreasService {

	@Resource
	private JdAreasDao jdAreasDao;
	

	public Integer createJdAreas(JdAreas t) {
		return this.jdAreasDao.insertEntity(t);
	}

	public JdAreas getJdAreas(JdAreas t) {
		return this.jdAreasDao.selectEntity(t);
	}

	public Integer getJdAreasCount(JdAreas t) {
		return this.jdAreasDao.selectEntityCount(t);
	}

	public List<JdAreas> getJdAreasList(JdAreas t) {
		return this.jdAreasDao.selectEntityList(t);
	}

	public int modifyJdAreas(JdAreas t) {
		return this.jdAreasDao.updateEntity(t);
	}

	public int removeJdAreas(JdAreas t) {
		return this.jdAreasDao.deleteEntity(t);
	}

	public List<JdAreas> getJdAreasPaginatedList(JdAreas t) {
		return this.jdAreasDao.selectEntityPaginatedList(t);
	}

}
