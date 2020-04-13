package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.RwHbRuleDao;
import com.ebiz.webapp.domain.RwHbRule;
import com.ebiz.webapp.service.RwHbRuleService;

/**
 * @author Wu,Yang
 * @version 2016-06-16 18:18
 */
@Service
public class RwHbRuleServiceImpl implements RwHbRuleService {

	@Resource
	private RwHbRuleDao rwHbRuleDao;
	

	public Integer createRwHbRule(RwHbRule t) {
		return this.rwHbRuleDao.insertEntity(t);
	}

	public RwHbRule getRwHbRule(RwHbRule t) {
		return this.rwHbRuleDao.selectEntity(t);
	}

	public Integer getRwHbRuleCount(RwHbRule t) {
		return this.rwHbRuleDao.selectEntityCount(t);
	}

	public List<RwHbRule> getRwHbRuleList(RwHbRule t) {
		return this.rwHbRuleDao.selectEntityList(t);
	}

	public int modifyRwHbRule(RwHbRule t) {
		return this.rwHbRuleDao.updateEntity(t);
	}

	public int removeRwHbRule(RwHbRule t) {
		return this.rwHbRuleDao.deleteEntity(t);
	}

	public List<RwHbRule> getRwHbRulePaginatedList(RwHbRule t) {
		return this.rwHbRuleDao.selectEntityPaginatedList(t);
	}

}
