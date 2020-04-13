package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.CommWelfareApply;

/**
 * @author Wu,Yang
 * @version 2019-03-19 09:14
 */
public interface CommWelfareApplyService {

	Integer createCommWelfareApply(CommWelfareApply t);

	int modifyCommWelfareApply(CommWelfareApply t);

	int removeCommWelfareApply(CommWelfareApply t);

	CommWelfareApply getCommWelfareApply(CommWelfareApply t);

	List<CommWelfareApply> getCommWelfareApplyList(CommWelfareApply t);

	Integer getCommWelfareApplyCount(CommWelfareApply t);

	List<CommWelfareApply> getCommWelfareApplyPaginatedList(CommWelfareApply t);

}