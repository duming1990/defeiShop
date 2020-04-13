package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.CardPIndex;

/**
 * @author Wu,Yang
 * @version 2019-03-19 14:37
 */
public interface CardPIndexService {

	Integer createCardPIndex(CardPIndex t);

	int modifyCardPIndex(CardPIndex t);

	int removeCardPIndex(CardPIndex t);

	CardPIndex getCardPIndex(CardPIndex t);

	List<CardPIndex> getCardPIndexList(CardPIndex t);

	Integer getCardPIndexCount(CardPIndex t);

	List<CardPIndex> getCardPIndexPaginatedList(CardPIndex t);

}