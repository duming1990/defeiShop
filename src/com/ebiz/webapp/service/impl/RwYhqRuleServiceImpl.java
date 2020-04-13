package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.RwYhqRuleDao;
import com.ebiz.webapp.domain.RwYhqRule;
import com.ebiz.webapp.service.RwYhqRuleService;

/**
 * @author Wu,Yang
 * @version 2016-06-16 18:18
 */
@Service
public class RwYhqRuleServiceImpl implements RwYhqRuleService {

	@Resource
	private RwYhqRuleDao rwYhqRuleDao;
	

	public Integer createRwYhqRule(RwYhqRule t) {
		return this.rwYhqRuleDao.insertEntity(t);
	}

	public RwYhqRule getRwYhqRule(RwYhqRule t) {
		return this.rwYhqRuleDao.selectEntity(t);
	}

	public Integer getRwYhqRuleCount(RwYhqRule t) {
		return this.rwYhqRuleDao.selectEntityCount(t);
	}

	public List<RwYhqRule> getRwYhqRuleList(RwYhqRule t) {
		return this.rwYhqRuleDao.selectEntityList(t);
	}

	public int modifyRwYhqRule(RwYhqRule t) {
		return this.rwYhqRuleDao.updateEntity(t);
	}

	public int removeRwYhqRule(RwYhqRule t) {
		return this.rwYhqRuleDao.deleteEntity(t);
	}

	public List<RwYhqRule> getRwYhqRulePaginatedList(RwYhqRule t) {
		return this.rwYhqRuleDao.selectEntityPaginatedList(t);
	}

}
