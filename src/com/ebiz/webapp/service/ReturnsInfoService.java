package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.ReturnsInfo;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:55
 */
public interface ReturnsInfoService {

	Integer createReturnsInfo(ReturnsInfo t);

	int modifyReturnsInfo(ReturnsInfo t);

	int removeReturnsInfo(ReturnsInfo t);

	ReturnsInfo getReturnsInfo(ReturnsInfo t);

	List<ReturnsInfo> getReturnsInfoList(ReturnsInfo t);

	Integer getReturnsInfoCount(ReturnsInfo t);

	List<ReturnsInfo> getReturnsInfoPaginatedList(ReturnsInfo t);

	void caculateCreditsAndStockAndSales(ReturnsInfo t);

}