package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.WlCompMatflowDao;
import com.ebiz.webapp.domain.WlCompMatflow;
import com.ebiz.webapp.service.WlCompMatflowService;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:54
 */
@Service
public class WlCompMatflowServiceImpl implements WlCompMatflowService {

	@Resource
	private WlCompMatflowDao wlCompMatflowDao;

	public Integer createWlCompMatflow(WlCompMatflow t) {
		return this.wlCompMatflowDao.insertEntity(t);
	}

	public WlCompMatflow getWlCompMatflow(WlCompMatflow t) {
		return this.wlCompMatflowDao.selectEntity(t);
	}

	public Integer getWlCompMatflowCount(WlCompMatflow t) {
		return this.wlCompMatflowDao.selectEntityCount(t);
	}

	public List<WlCompMatflow> getWlCompMatflowList(WlCompMatflow t) {
		return this.wlCompMatflowDao.selectEntityList(t);
	}

	public int modifyWlCompMatflow(WlCompMatflow t) {
		return this.wlCompMatflowDao.updateEntity(t);
	}

	public int removeWlCompMatflow(WlCompMatflow t) {
		return this.wlCompMatflowDao.deleteEntity(t);
	}

	public List<WlCompMatflow> getWlCompMatflowPaginatedList(WlCompMatflow t) {
		return this.wlCompMatflowDao.selectEntityPaginatedList(t);
	}

}
