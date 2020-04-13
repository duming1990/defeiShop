package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.VillageContactGroupDao;
import com.ebiz.webapp.domain.VillageContactGroup;
import com.ebiz.webapp.service.VillageContactGroupService;

/**
 * @author Wu,Yang
 * @version 2018-01-26 15:45
 */
@Service
public class VillageContactGroupServiceImpl implements VillageContactGroupService {

	@Resource
	private VillageContactGroupDao villageContactGroupDao;

	public Integer createVillageContactGroup(VillageContactGroup t) {
		return this.villageContactGroupDao.insertEntity(t);
	}

	public VillageContactGroup getVillageContactGroup(VillageContactGroup t) {
		return this.villageContactGroupDao.selectEntity(t);
	}

	public Integer getVillageContactGroupCount(VillageContactGroup t) {
		return this.villageContactGroupDao.selectEntityCount(t);
	}

	public List<VillageContactGroup> getVillageContactGroupList(VillageContactGroup t) {
		return this.villageContactGroupDao.selectEntityList(t);
	}

	public int modifyVillageContactGroup(VillageContactGroup t) {
		return this.villageContactGroupDao.updateEntity(t);
	}

	public int removeVillageContactGroup(VillageContactGroup t) {
		return this.villageContactGroupDao.deleteEntity(t);
	}

	public List<VillageContactGroup> getVillageContactGroupPaginatedList(VillageContactGroup t) {
		return this.villageContactGroupDao.selectEntityPaginatedList(t);
	}

	public List<VillageContactGroup> getVillageContactGroupNameAndCount(VillageContactGroup t) {
		return this.villageContactGroupDao.selectVillageContactGroupNameAndCount(t);
	}

}
