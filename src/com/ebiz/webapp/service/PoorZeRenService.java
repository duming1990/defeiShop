package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.PoorZeRen;

/**
 * @author Wu,Yang
 * @version 2018-03-01 15:13
 */
public interface PoorZeRenService {

	Integer createPoorZeRen(PoorZeRen t);

	int modifyPoorZeRen(PoorZeRen t);

	int removePoorZeRen(PoorZeRen t);

	PoorZeRen getPoorZeRen(PoorZeRen t);

	List<PoorZeRen> getPoorZeRenList(PoorZeRen t);

	Integer getPoorZeRenCount(PoorZeRen t);

	List<PoorZeRen> getPoorZeRenPaginatedList(PoorZeRen t);

}