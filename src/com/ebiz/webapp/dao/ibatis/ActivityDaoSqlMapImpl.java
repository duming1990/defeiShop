package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.ActivityDao;
import com.ebiz.webapp.domain.Activity;

/**
 * @author Wu,Yang
 * @version 2019-04-04 11:00
 */
@Service
public class ActivityDaoSqlMapImpl extends EntityDaoSqlMapImpl<Activity> implements ActivityDao {

	public Activity selectActivityOrderSum(Activity t) {
		return (Activity) super.getSqlMapClientTemplate().queryForObject("selectActivityOrderSum", t);
	}

}
