package com.ebiz.webapp.dao;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.Activity;

/**
 * @author Wu,Yang
 * @version 2019-04-04 11:00
 */
public interface ActivityDao extends EntityDao<Activity> {
	Activity selectActivityOrderSum(Activity t);

}
