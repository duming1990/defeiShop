package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.Freight;

/**
 * @author Wu,Yang
 * @version 2014-05-22 19:36
 */
public interface FreightService {

	Integer createFreight(Freight t);

	int modifyFreight(Freight t);

	int removeFreight(Freight t);

	Freight getFreight(Freight t);

	List<Freight> getFreightList(Freight t);

	Integer getFreightCount(Freight t);

	List<Freight> getFreightPaginatedList(Freight t);

}