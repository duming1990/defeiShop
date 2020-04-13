package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.CardGenHis;

/**
 * @author Wu,Yang
 * @version 2019-03-19 14:37
 */
public interface CardGenHisService {

	Integer createCardGenHis(CardGenHis t);

	int modifyCardGenHis(CardGenHis t);

	int removeCardGenHis(CardGenHis t);

	CardGenHis getCardGenHis(CardGenHis t);

	List<CardGenHis> getCardGenHisList(CardGenHis t);

	Integer getCardGenHisCount(CardGenHis t);

	List<CardGenHis> getCardGenHisPaginatedList(CardGenHis t);

}