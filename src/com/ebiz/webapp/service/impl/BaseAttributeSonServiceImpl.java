package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.BaseAttributeSonDao;
import com.ebiz.webapp.domain.BaseAttributeSon;
import com.ebiz.webapp.service.BaseAttributeSonService;

/**
 * @author Wu,Yang
 * @version 2012-05-30 08:53
 */
@Service
public class BaseAttributeSonServiceImpl implements BaseAttributeSonService {

	@Resource
	private BaseAttributeSonDao baseAttributeSonDao;

	public Integer createBaseAttributeSon(BaseAttributeSon t) {
		return this.baseAttributeSonDao.insertEntity(t);
	}

	public BaseAttributeSon getBaseAttributeSon(BaseAttributeSon t) {
		return this.baseAttributeSonDao.selectEntity(t);
	}

	public Integer getBaseAttributeSonCount(BaseAttributeSon t) {
		return this.baseAttributeSonDao.selectEntityCount(t);
	}

	public List<BaseAttributeSon> getBaseAttributeSonList(BaseAttributeSon t) {
		return this.baseAttributeSonDao.selectEntityList(t);
	}

	public int modifyBaseAttributeSon(BaseAttributeSon t) {
		return this.baseAttributeSonDao.updateEntity(t);
	}

	public int removeBaseAttributeSon(BaseAttributeSon t) {
		return this.baseAttributeSonDao.deleteEntity(t);
	}

	public List<BaseAttributeSon> getBaseAttributeSonPaginatedList(BaseAttributeSon t) {
		return this.baseAttributeSonDao.selectEntityPaginatedList(t);
	}

}
