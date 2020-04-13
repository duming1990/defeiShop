package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.VillageDynamicRecordDao;
import com.ebiz.webapp.domain.VillageDynamicRecord;
import com.ebiz.webapp.service.VillageDynamicRecordService;

/**
 * @author Wu,Yang
 * @version 2018-01-26 15:45
 */
@Service
public class VillageDynamicRecordServiceImpl implements VillageDynamicRecordService {

	@Resource
	private VillageDynamicRecordDao villageDynamicRecordDao;
	

	public Integer createVillageDynamicRecord(VillageDynamicRecord t) {
		return this.villageDynamicRecordDao.insertEntity(t);
	}

	public VillageDynamicRecord getVillageDynamicRecord(VillageDynamicRecord t) {
		return this.villageDynamicRecordDao.selectEntity(t);
	}

	public Integer getVillageDynamicRecordCount(VillageDynamicRecord t) {
		return this.villageDynamicRecordDao.selectEntityCount(t);
	}

	public List<VillageDynamicRecord> getVillageDynamicRecordList(VillageDynamicRecord t) {
		return this.villageDynamicRecordDao.selectEntityList(t);
	}

	public int modifyVillageDynamicRecord(VillageDynamicRecord t) {
		return this.villageDynamicRecordDao.updateEntity(t);
	}

	public int removeVillageDynamicRecord(VillageDynamicRecord t) {
		return this.villageDynamicRecordDao.deleteEntity(t);
	}

	public List<VillageDynamicRecord> getVillageDynamicRecordPaginatedList(VillageDynamicRecord t) {
		return this.villageDynamicRecordDao.selectEntityPaginatedList(t);
	}

}
