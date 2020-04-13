package com.ebiz.webapp.dao.ibatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.ActivityApplyDao;
import com.ebiz.webapp.domain.ActivityApply;

/**
 * @author Wu,Yang
 * @version 2019-04-04 11:00
 */
@Service
public class ActivityApplyDaoSqlMapImpl extends EntityDaoSqlMapImpl<ActivityApply> implements ActivityApplyDao {

	public List<ActivityApply> selectActivityApplyOrderSum(ActivityApply t) {
		return super.getSqlMapClientTemplate().queryForList("selectActivityApplyOrderSum", t);
	}

	public List<ActivityApply> selectActivityApplyOrderac(ActivityApply t) {
		return super.getSqlMapClientTemplate().queryForList("selectActivityApplyOrderac", t);
	}

	@Override
	public Integer selectActivityApplyacCount(ActivityApply t) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectActivityApplyacCount", t);
	}

}
