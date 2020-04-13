package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.PoorFamily;

/**
 * @author Wu,Yang
 * @version 2018-03-01 15:13
 */
public interface PoorFamilyService {

	Integer createPoorFamily(PoorFamily t);

	int modifyPoorFamily(PoorFamily t);

	int removePoorFamily(PoorFamily t);

	PoorFamily getPoorFamily(PoorFamily t);

	List<PoorFamily> getPoorFamilyList(PoorFamily t);

	Integer getPoorFamilyCount(PoorFamily t);

	List<PoorFamily> getPoorFamilyPaginatedList(PoorFamily t);

}