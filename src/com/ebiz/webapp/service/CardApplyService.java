package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.CardApply;

/**
 * @author Wu,Yang
 * @version 2019-03-19 14:37
 */
public interface CardApplyService {

	Integer createCardApply(CardApply t);

	int modifyCardApply(CardApply t);

	int removeCardApply(CardApply t);

	CardApply getCardApply(CardApply t);

	List<CardApply> getCardApplyList(CardApply t);

	Integer getCardApplyCount(CardApply t);

	List<CardApply> getCardApplyPaginatedList(CardApply t);

}