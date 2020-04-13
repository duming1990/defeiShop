package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.VillageDynamicReportDao;
import com.ebiz.webapp.domain.VillageDynamicReport;
import com.ebiz.webapp.service.VillageDynamicReportService;

/**
 * @author Wu,Yang
 * @version 2018-01-31 17:47
 */
@Service
public class VillageDynamicReportServiceImpl implements VillageDynamicReportService {

	@Resource
	private VillageDynamicReportDao villageDynamicReportDao;
	

	public Integer createVillageDynamicReport(VillageDynamicReport t) {
		return this.villageDynamicReportDao.insertEntity(t);
	}

	public VillageDynamicReport getVillageDynamicReport(VillageDynamicReport t) {
		return this.villageDynamicReportDao.selectEntity(t);
	}

	public Integer getVillageDynamicReportCount(VillageDynamicReport t) {
		return this.villageDynamicReportDao.selectEntityCount(t);
	}

	public List<VillageDynamicReport> getVillageDynamicReportList(VillageDynamicReport t) {
		return this.villageDynamicReportDao.selectEntityList(t);
	}

	public int modifyVillageDynamicReport(VillageDynamicReport t) {
		return this.villageDynamicReportDao.updateEntity(t);
	}

	public int removeVillageDynamicReport(VillageDynamicReport t) {
		return this.villageDynamicReportDao.deleteEntity(t);
	}

	public List<VillageDynamicReport> getVillageDynamicReportPaginatedList(VillageDynamicReport t) {
		return this.villageDynamicReportDao.selectEntityPaginatedList(t);
	}

}
