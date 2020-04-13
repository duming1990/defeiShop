package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.RwHbRule;

/**
 * @author Wu,Yang
 * @version 2016-06-16 18:18
 */
public interface RwHbRuleService {

	Integer createRwHbRule(RwHbRule t);

	int modifyRwHbRule(RwHbRule t);

	int removeRwHbRule(RwHbRule t);

	RwHbRule getRwHbRule(RwHbRule t);

	List<RwHbRule> getRwHbRuleList(RwHbRule t);

	Integer getRwHbRuleCount(RwHbRule t);

	List<RwHbRule> getRwHbRulePaginatedList(RwHbRule t);

}