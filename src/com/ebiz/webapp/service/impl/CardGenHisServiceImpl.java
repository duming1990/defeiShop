package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.CardGenHisDao;
import com.ebiz.webapp.dao.CardInfoDao;
import com.ebiz.webapp.domain.CardGenHis;
import com.ebiz.webapp.domain.CardInfo;
import com.ebiz.webapp.service.CardGenHisService;

/**
 * @author Wu,Yang
 * @version 2019-03-19 14:37
 */
@Service
public class CardGenHisServiceImpl implements CardGenHisService {

	@Resource
	private CardGenHisDao cardGenHisDao;

	@Resource
	private CardInfoDao cardInfoDao;

	@SuppressWarnings("unchecked")
	public Integer createCardGenHis(CardGenHis t) {
		int id = this.cardGenHisDao.insertEntity(t);

		return id;
	}

	public CardGenHis getCardGenHis(CardGenHis t) {
		return this.cardGenHisDao.selectEntity(t);
	}

	public Integer getCardGenHisCount(CardGenHis t) {
		return this.cardGenHisDao.selectEntityCount(t);
	}

	public List<CardGenHis> getCardGenHisList(CardGenHis t) {
		return this.cardGenHisDao.selectEntityList(t);
	}

	public int modifyCardGenHis(CardGenHis t) {
		int flag = this.cardGenHisDao.updateEntity(t);
		if (null != t.getMap().get("update_cardInfo_is_del")) {
			CardInfo card = new CardInfo();
			card.getMap().put("gen_id", t.getId());
			card.setIs_del(t.getIs_del());
			cardInfoDao.updateEntity(card);
		}
		return flag;

	}

	public int removeCardGenHis(CardGenHis t) {
		int flag = this.cardGenHisDao.deleteEntity(t);
		if (t.getMap().get("remove_card_info") != null) {
			CardInfo card = new CardInfo();
			card.getMap().put("gen_id", t.getId());
			cardInfoDao.deleteEntity(card);
		}
		return flag;
	}

	public List<CardGenHis> getCardGenHisPaginatedList(CardGenHis t) {
		return this.cardGenHisDao.selectEntityPaginatedList(t);
	}

}
