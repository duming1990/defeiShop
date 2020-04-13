package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.ActivityApply;

/**
 * @author Wu,Yang
 * @version 2019-04-04 11:00
 */
public interface ActivityApplyService {

	Integer createActivityApply(ActivityApply t);

	int modifyActivityApply(ActivityApply t);

	int removeActivityApply(ActivityApply t);

	ActivityApply getActivityApply(ActivityApply t);

	List<ActivityApply> getActivityApplyList(ActivityApply t);

	Integer getActivityApplyCount(ActivityApply t);

	Integer getActivityApplyacCount(ActivityApply t);

	List<ActivityApply> getActivityApplyPaginatedList(ActivityApply t);

	List<ActivityApply> selectActivityApplyOrderSum(ActivityApply t);

	List<ActivityApply> selectActivityApplyOrderac(ActivityApply t);

}