package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.CardInfo;

/**
 * @author Wu,Yang
 * @version 2019-03-19 14:37
 */
public interface CardInfoService {

	Integer createCardInfo(CardInfo t);

	Integer createCardInfoByList(CardInfo t);

	int modifyCardInfo(CardInfo t);

	int removeCardInfo(CardInfo t);

	CardInfo getCardInfo(CardInfo t);

	List<CardInfo> getCardInfoList(CardInfo t);

	Integer getCardInfoCount(CardInfo t);

	List<CardInfo> getCardInfoPaginatedList(CardInfo t);

}