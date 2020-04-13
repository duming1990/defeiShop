package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.EntpCommClass;

/**
 * @author Wu,Yang
 * @version 2018-07-10 15:03
 */
public interface EntpCommClassService {

	Integer createEntpCommClass(EntpCommClass t);

	int modifyEntpCommClass(EntpCommClass t);

	int removeEntpCommClass(EntpCommClass t);

	EntpCommClass getEntpCommClass(EntpCommClass t);

	List<EntpCommClass> getEntpCommClassList(EntpCommClass t);

	Integer getEntpCommClassCount(EntpCommClass t);

	List<EntpCommClass> getEntpCommClassPaginatedList(EntpCommClass t);

}