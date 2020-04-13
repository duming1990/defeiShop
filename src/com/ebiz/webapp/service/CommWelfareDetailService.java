package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.CommWelfareDetail;

/**
 * @author Wu,Yang
 * @version 2019-03-19 09:14
 */
public interface CommWelfareDetailService {

	Integer createCommWelfareDetail(CommWelfareDetail t);

	int modifyCommWelfareDetail(CommWelfareDetail t);

	int removeCommWelfareDetail(CommWelfareDetail t);

	CommWelfareDetail getCommWelfareDetail(CommWelfareDetail t);

	List<CommWelfareDetail> getCommWelfareDetailList(CommWelfareDetail t);

	List<CommWelfareDetail> getCommListWithWelfareDetail(CommWelfareDetail t);

	Integer getCommWelfareDetailCount(CommWelfareDetail t);

	List<CommWelfareDetail> getCommWelfareDetailPaginatedList(CommWelfareDetail t);

}