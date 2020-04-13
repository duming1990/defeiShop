package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.ReturnsInfoFj;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:55
 */
public interface ReturnsInfoFjService {

	Integer createReturnsInfoFj(ReturnsInfoFj t);

	int modifyReturnsInfoFj(ReturnsInfoFj t);

	int removeReturnsInfoFj(ReturnsInfoFj t);

	ReturnsInfoFj getReturnsInfoFj(ReturnsInfoFj t);

	List<ReturnsInfoFj> getReturnsInfoFjList(ReturnsInfoFj t);

	Integer getReturnsInfoFjCount(ReturnsInfoFj t);

	List<ReturnsInfoFj> getReturnsInfoFjPaginatedList(ReturnsInfoFj t);

}