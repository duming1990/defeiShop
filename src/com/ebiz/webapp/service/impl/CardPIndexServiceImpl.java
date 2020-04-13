package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.CardPIndexDao;
import com.ebiz.webapp.domain.CardPIndex;
import com.ebiz.webapp.service.CardPIndexService;

/**
 * @author Wu,Yang
 * @version 2019-03-19 14:37
 */
@Service
public class CardPIndexServiceImpl implements CardPIndexService {

	@Resource
	private CardPIndexDao cardPIndexDao;
	

	public Integer createCardPIndex(CardPIndex t) {
		return this.cardPIndexDao.insertEntity(t);
	}

	public CardPIndex getCardPIndex(CardPIndex t) {
		return this.cardPIndexDao.selectEntity(t);
	}

	public Integer getCardPIndexCount(CardPIndex t) {
		return this.cardPIndexDao.selectEntityCount(t);
	}

	public List<CardPIndex> getCardPIndexList(CardPIndex t) {
		return this.cardPIndexDao.selectEntityList(t);
	}

	public int modifyCardPIndex(CardPIndex t) {
		return this.cardPIndexDao.updateEntity(t);
	}

	public int removeCardPIndex(CardPIndex t) {
		return this.cardPIndexDao.deleteEntity(t);
	}

	public List<CardPIndex> getCardPIndexPaginatedList(CardPIndex t) {
		return this.cardPIndexDao.selectEntityPaginatedList(t);
	}

}
