package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.RwYhqRule;

/**
 * @author Wu,Yang
 * @version 2016-06-16 18:18
 */
public interface RwYhqRuleService {

	Integer createRwYhqRule(RwYhqRule t);

	int modifyRwYhqRule(RwYhqRule t);

	int removeRwYhqRule(RwYhqRule t);

	RwYhqRule getRwYhqRule(RwYhqRule t);

	List<RwYhqRule> getRwYhqRuleList(RwYhqRule t);

	Integer getRwYhqRuleCount(RwYhqRule t);

	List<RwYhqRule> getRwYhqRulePaginatedList(RwYhqRule t);

}