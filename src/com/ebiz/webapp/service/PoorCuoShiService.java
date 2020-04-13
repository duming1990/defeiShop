package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.PoorCuoShi;

/**
 * @author Wu,Yang
 * @version 2018-03-01 15:13
 */
public interface PoorCuoShiService {

	Integer createPoorCuoShi(PoorCuoShi t);

	int modifyPoorCuoShi(PoorCuoShi t);

	int removePoorCuoShi(PoorCuoShi t);

	PoorCuoShi getPoorCuoShi(PoorCuoShi t);

	List<PoorCuoShi> getPoorCuoShiList(PoorCuoShi t);

	Integer getPoorCuoShiCount(PoorCuoShi t);

	List<PoorCuoShi> getPoorCuoShiPaginatedList(PoorCuoShi t);

}