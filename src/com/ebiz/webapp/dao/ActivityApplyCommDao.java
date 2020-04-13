package com.ebiz.webapp.dao;

import java.util.List;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.ActivityApplyComm;

/**
 * @author Wu,Yang
 * @version 2019-04-04 11:00
 */
public interface ActivityApplyCommDao extends EntityDao<ActivityApplyComm> {

	List<ActivityApplyComm> selectActivityApplyCommOrderList(ActivityApplyComm t);

	Integer selectActivityApplyCommOrderCount(ActivityApplyComm t);

	List<ActivityApplyComm> selectActivityApplyCommEntpList(ActivityApplyComm t);

	List<ActivityApplyComm> selectActivityApplyCommOrderEntpList(ActivityApplyComm t);

	List<ActivityApplyComm> selectActivityApplyCommTcList(ActivityApplyComm t);

	List<ActivityApplyComm> selectActivityApplyCommAuditList(ActivityApplyComm t);

	Integer selectActivityApplyCommTcCount(ActivityApplyComm t);

	Integer selectActivityApplyCommOrderEntpCount(ActivityApplyComm t);
}
