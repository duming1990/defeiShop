package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.VillageDynamicReport;

/**
 * @author Wu,Yang
 * @version 2018-01-31 17:47
 */
public interface VillageDynamicReportService {

	Integer createVillageDynamicReport(VillageDynamicReport t);

	int modifyVillageDynamicReport(VillageDynamicReport t);

	int removeVillageDynamicReport(VillageDynamicReport t);

	VillageDynamicReport getVillageDynamicReport(VillageDynamicReport t);

	List<VillageDynamicReport> getVillageDynamicReportList(VillageDynamicReport t);

	Integer getVillageDynamicReportCount(VillageDynamicReport t);

	List<VillageDynamicReport> getVillageDynamicReportPaginatedList(VillageDynamicReport t);

}