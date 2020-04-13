package com.ebiz.webapp.dao;

import java.util.List;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.ActivityApply;

/**
 * @author Wu,Yang
 * @version 2019-04-04 11:00
 */
public interface ActivityApplyDao extends EntityDao<ActivityApply> {

	List<ActivityApply> selectActivityApplyOrderSum(ActivityApply t);

	List<ActivityApply> selectActivityApplyOrderac(ActivityApply t);

	Integer selectActivityApplyacCount(ActivityApply t);

}
