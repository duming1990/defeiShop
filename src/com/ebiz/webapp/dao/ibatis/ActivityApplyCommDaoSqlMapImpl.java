package com.ebiz.webapp.dao.ibatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.ActivityApplyCommDao;
import com.ebiz.webapp.domain.ActivityApplyComm;

/**
 * @author Wu,Yang
 * @version 2019-04-04 11:00
 */
@Service
public class ActivityApplyCommDaoSqlMapImpl extends EntityDaoSqlMapImpl<ActivityApplyComm> implements
		ActivityApplyCommDao {

	public List<ActivityApplyComm> selectActivityApplyCommOrderList(ActivityApplyComm t) {
		return super.getSqlMapClientTemplate().queryForList("selectActivityApplyCommOrderList", t);
	}

	@Override
	public Integer selectActivityApplyCommOrderCount(ActivityApplyComm t) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectActivityApplyCommOrderCount", t);
	}

	public List<ActivityApplyComm> selectActivityApplyCommEntpList(ActivityApplyComm t) {
		return super.getSqlMapClientTemplate().queryForList("selectActivityApplyCommEntpList", t);
	}

	public List<ActivityApplyComm> selectActivityApplyCommOrderEntpList(ActivityApplyComm t) {
		return super.getSqlMapClientTemplate().queryForList("selectActivityApplyCommOrderEntpList", t);
	}

	public List<ActivityApplyComm> selectActivityApplyCommTcList(ActivityApplyComm t) {
		return super.getSqlMapClientTemplate().queryForList("selectActivityApplyCommTcList", t);
	}

	@Override
	public Integer selectActivityApplyCommTcCount(ActivityApplyComm t) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectActivityApplyCommTcCount", t);
	}

	@Override
	public Integer selectActivityApplyCommOrderEntpCount(ActivityApplyComm t) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectActivityApplyCommOrderEntpCount", t);
	}

	@Override
	public List<ActivityApplyComm> selectActivityApplyCommAuditList(ActivityApplyComm t) {
		return super.getSqlMapClientTemplate().queryForList("selectActivityApplyCommAuditList", t);
	}
}
