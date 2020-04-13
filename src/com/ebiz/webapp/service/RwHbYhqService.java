package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.RwHbYhq;

/**
 * @author Wu,Yang
 * @version 2016-06-16 18:18
 */
public interface RwHbYhqService {

	Integer createRwHbYhq(RwHbYhq t);

	int modifyRwHbYhq(RwHbYhq t);

	int removeRwHbYhq(RwHbYhq t);

	RwHbYhq getRwHbYhq(RwHbYhq t);

	List<RwHbYhq> getRwHbYhqList(RwHbYhq t);

	Integer getRwHbYhqCount(RwHbYhq t);

	List<RwHbYhq> getRwHbYhqPaginatedList(RwHbYhq t);

}