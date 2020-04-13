package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.VillageDynamicRecord;

/**
 * @author Wu,Yang
 * @version 2018-01-26 15:45
 */
public interface VillageDynamicRecordService {

	Integer createVillageDynamicRecord(VillageDynamicRecord t);

	int modifyVillageDynamicRecord(VillageDynamicRecord t);

	int removeVillageDynamicRecord(VillageDynamicRecord t);

	VillageDynamicRecord getVillageDynamicRecord(VillageDynamicRecord t);

	List<VillageDynamicRecord> getVillageDynamicRecordList(VillageDynamicRecord t);

	Integer getVillageDynamicRecordCount(VillageDynamicRecord t);

	List<VillageDynamicRecord> getVillageDynamicRecordPaginatedList(VillageDynamicRecord t);

}