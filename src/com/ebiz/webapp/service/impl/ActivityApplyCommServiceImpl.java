package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.ActivityApplyCommDao;
import com.ebiz.webapp.domain.ActivityApplyComm;
import com.ebiz.webapp.service.ActivityApplyCommService;

/**
 * @author Wu,Yang
 * @version 2019-04-04 11:00
 */
@Service
public class ActivityApplyCommServiceImpl implements ActivityApplyCommService {

	@Resource
	private ActivityApplyCommDao activityApplyCommDao;

	public Integer createActivityApplyComm(ActivityApplyComm t) {
		return this.activityApplyCommDao.insertEntity(t);
	}

	public ActivityApplyComm getActivityApplyComm(ActivityApplyComm t) {
		return this.activityApplyCommDao.selectEntity(t);
	}

	public Integer getActivityApplyCommCount(ActivityApplyComm t) {
		return this.activityApplyCommDao.selectEntityCount(t);
	}

	public List<ActivityApplyComm> getActivityApplyCommList(ActivityApplyComm t) {
		return this.activityApplyCommDao.selectEntityList(t);
	}

	public int modifyActivityApplyComm(ActivityApplyComm t) {
		return this.activityApplyCommDao.updateEntity(t);
	}

	public int removeActivityApplyComm(ActivityApplyComm t) {
		return this.activityApplyCommDao.deleteEntity(t);
	}

	public List<ActivityApplyComm> getActivityApplyCommPaginatedList(ActivityApplyComm t) {
		return this.activityApplyCommDao.selectEntityPaginatedList(t);
	}

	public List<ActivityApplyComm> selectActivityApplyCommOrderList(ActivityApplyComm t) {
		return this.activityApplyCommDao.selectActivityApplyCommOrderList(t);
	}

	public Integer selectActivityApplyCommOrderCount(ActivityApplyComm t) {
		return this.activityApplyCommDao.selectActivityApplyCommOrderCount(t);
	}

	public Integer selectActivityApplyCommTcCount(ActivityApplyComm t) {
		return this.activityApplyCommDao.selectActivityApplyCommTcCount(t);
	}

	public Integer selectActivityApplyCommOrderEntpCount(ActivityApplyComm t) {
		return this.activityApplyCommDao.selectActivityApplyCommOrderEntpCount(t);
	}

	public List<ActivityApplyComm> selectActivityApplyCommEntpList(ActivityApplyComm t) {
		return this.activityApplyCommDao.selectActivityApplyCommEntpList(t);
	}

	public List<ActivityApplyComm> selectActivityApplyCommOrderEntpList(ActivityApplyComm t) {
		return this.activityApplyCommDao.selectActivityApplyCommOrderEntpList(t);
	}

	public List<ActivityApplyComm> selectActivityApplyCommTcList(ActivityApplyComm t) {
		return this.activityApplyCommDao.selectActivityApplyCommTcList(t);
	}

	@Override
	public List<ActivityApplyComm> selectActivityApplyCommAuditList(ActivityApplyComm t) {
		return this.activityApplyCommDao.selectActivityApplyCommAuditList(t);
	}

}
