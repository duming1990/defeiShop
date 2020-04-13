package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.VillageTourDao;
import com.ebiz.webapp.domain.VillageTour;
import com.ebiz.webapp.service.VillageTourService;

/**
 * @author Wu,Yang
 * @version 2018-08-22 14:47
 */
@Service
public class VillageTourServiceImpl implements VillageTourService {

	@Resource
	private VillageTourDao villageTourDao;
	

	public Integer createVillageTour(VillageTour t) {
		return this.villageTourDao.insertEntity(t);
	}

	public VillageTour getVillageTour(VillageTour t) {
		return this.villageTourDao.selectEntity(t);
	}

	public Integer getVillageTourCount(VillageTour t) {
		return this.villageTourDao.selectEntityCount(t);
	}

	public List<VillageTour> getVillageTourList(VillageTour t) {
		return this.villageTourDao.selectEntityList(t);
	}

	public int modifyVillageTour(VillageTour t) {
		return this.villageTourDao.updateEntity(t);
	}

	public int removeVillageTour(VillageTour t) {
		return this.villageTourDao.deleteEntity(t);
	}

	public List<VillageTour> getVillageTourPaginatedList(VillageTour t) {
		return this.villageTourDao.selectEntityPaginatedList(t);
	}

}
