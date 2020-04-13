package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.FreightDetailDao;
import com.ebiz.webapp.domain.FreightDetail;
import com.ebiz.webapp.service.FreightDetailService;

/**
 * @author Wu,Yang
 * @version 2014-05-22 19:37
 */
@Service
public class FreightDetailServiceImpl implements FreightDetailService {

	@Resource
	private FreightDetailDao freightDetailDao;

	public Integer createFreightDetail(FreightDetail t) {
		return this.freightDetailDao.insertEntity(t);
	}

	public FreightDetail getFreightDetail(FreightDetail t) {
		return this.freightDetailDao.selectEntity(t);
	}

	public Integer getFreightDetailCount(FreightDetail t) {
		return this.freightDetailDao.selectEntityCount(t);
	}

	public List<FreightDetail> getFreightDetailList(FreightDetail t) {
		return this.freightDetailDao.selectEntityList(t);
	}

	public int modifyFreightDetail(FreightDetail t) {
		return this.freightDetailDao.updateEntity(t);
	}

	public int removeFreightDetail(FreightDetail t) {
		return this.freightDetailDao.deleteEntity(t);
	}

	public List<FreightDetail> getFreightDetailPaginatedList(FreightDetail t) {
		return this.freightDetailDao.selectEntityPaginatedList(t);
	}

}
