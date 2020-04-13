package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.FreightDetail;

/**
 * @author Wu,Yang
 * @version 2014-05-22 19:37
 */
public interface FreightDetailService {

	Integer createFreightDetail(FreightDetail t);

	int modifyFreightDetail(FreightDetail t);

	int removeFreightDetail(FreightDetail t);

	FreightDetail getFreightDetail(FreightDetail t);

	List<FreightDetail> getFreightDetailList(FreightDetail t);

	Integer getFreightDetailCount(FreightDetail t);

	List<FreightDetail> getFreightDetailPaginatedList(FreightDetail t);

}