package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.Activity;

/**
 * @author Wu,Yang
 * @version 2019-04-04 11:00
 */
public interface ActivityService {

	Integer createActivity(Activity t);

	int modifyActivity(Activity t);

	int removeActivity(Activity t);

	Activity getActivity(Activity t);

	List<Activity> getActivityList(Activity t);

	Integer getActivityCount(Activity t);

	List<Activity> getActivityPaginatedList(Activity t);

	Activity selectActivityOrderSum(Activity t);

}