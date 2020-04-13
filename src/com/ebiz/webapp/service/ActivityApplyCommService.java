package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.ActivityApplyComm;

/**
 * @author Wu,Yang
 * @version 2019-04-04 11:00
 */
public interface ActivityApplyCommService {

	Integer createActivityApplyComm(ActivityApplyComm t);

	int modifyActivityApplyComm(ActivityApplyComm t);

	int removeActivityApplyComm(ActivityApplyComm t);

	ActivityApplyComm getActivityApplyComm(ActivityApplyComm t);

	List<ActivityApplyComm> getActivityApplyCommList(ActivityApplyComm t);

	Integer getActivityApplyCommCount(ActivityApplyComm t);

	List<ActivityApplyComm> getActivityApplyCommPaginatedList(ActivityApplyComm t);

	List<ActivityApplyComm> selectActivityApplyCommOrderList(ActivityApplyComm t);

	Integer selectActivityApplyCommOrderCount(ActivityApplyComm t);

	Integer selectActivityApplyCommTcCount(ActivityApplyComm t);

	Integer selectActivityApplyCommOrderEntpCount(ActivityApplyComm t);

	List<ActivityApplyComm> selectActivityApplyCommEntpList(ActivityApplyComm t);

	List<ActivityApplyComm> selectActivityApplyCommOrderEntpList(ActivityApplyComm t);

	List<ActivityApplyComm> selectActivityApplyCommTcList(ActivityApplyComm t);

	List<ActivityApplyComm> selectActivityApplyCommAuditList(ActivityApplyComm t);

}