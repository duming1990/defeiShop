package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.CommInfoPoors;

/**
 * @author Wu,Yang
 * @version 2018-01-22 10:47
 */
public interface CommInfoPoorsService {

	Integer createCommInfoPoors(CommInfoPoors t);

	int modifyCommInfoPoors(CommInfoPoors t);

	int removeCommInfoPoors(CommInfoPoors t);

	CommInfoPoors getCommInfoPoors(CommInfoPoors t);

	List<CommInfoPoors> getCommInfoPoorsList(CommInfoPoors t);

	Integer getCommInfoPoorsCount(CommInfoPoors t);

	List<CommInfoPoors> getCommInfoPoorsPaginatedList(CommInfoPoors t);

	int removeAndInsertCommInfoPoors(CommInfoPoors t);

}