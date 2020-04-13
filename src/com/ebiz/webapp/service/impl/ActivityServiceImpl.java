package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.ActivityDao;
import com.ebiz.webapp.domain.Activity;
import com.ebiz.webapp.service.ActivityService;

/**
 * @author Wu,Yang
 * @version 2019-04-04 11:00
 */
@Service
public class ActivityServiceImpl implements ActivityService {

	@Resource
	private ActivityDao activityDao;

	public Integer createActivity(Activity t) {
		return this.activityDao.insertEntity(t);
	}

	public Activity getActivity(Activity t) {
		return this.activityDao.selectEntity(t);
	}

	public Integer getActivityCount(Activity t) {
		return this.activityDao.selectEntityCount(t);
	}

	public List<Activity> getActivityList(Activity t) {
		return this.activityDao.selectEntityList(t);
	}

	public int modifyActivity(Activity t) {
		return this.activityDao.updateEntity(t);
	}

	public int removeActivity(Activity t) {
		return this.activityDao.deleteEntity(t);
	}

	public List<Activity> getActivityPaginatedList(Activity t) {
		return this.activityDao.selectEntityPaginatedList(t);
	}

	@Override
	public Activity selectActivityOrderSum(Activity t) {
		// TODO Auto-generated method stub
		return this.activityDao.selectActivityOrderSum(t);
	}

}
