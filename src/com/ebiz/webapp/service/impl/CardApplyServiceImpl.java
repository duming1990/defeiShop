package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.CardApplyDao;
import com.ebiz.webapp.dao.CardGenHisDao;
import com.ebiz.webapp.dao.CardInfoDao;
import com.ebiz.webapp.dao.CardPIndexDao;
import com.ebiz.webapp.domain.CardApply;
import com.ebiz.webapp.domain.CardGenHis;
import com.ebiz.webapp.domain.CardInfo;
import com.ebiz.webapp.domain.CardPIndex;
import com.ebiz.webapp.service.CardApplyService;

/**
 * @author Wu,Yang
 * @version 2019-03-19 14:37
 */
@Service
public class CardApplyServiceImpl implements CardApplyService {

	@Resource
	private CardApplyDao cardApplyDao;

	@Resource
	private CardPIndexDao cardPIndexDao;

	@Resource
	private CardGenHisDao cardGenHisDao;

	@Resource
	private CardInfoDao cardInfoDao;

	@SuppressWarnings("unchecked")
	public Integer createCardApply(CardApply t) {
		int id = this.cardApplyDao.insertEntity(t);
		List<CardPIndex> cPIndexList = (List<CardPIndex>) t.getMap().get("insert_CardPIndex_list");
		if (null != cPIndexList && cPIndexList.size() > 0) {
			for (CardPIndex temp : cPIndexList) {
				temp.setCard_apply_id(id);
				cardPIndexDao.insertEntity(temp);
			}
		}
		return id;
	}

	public CardApply getCardApply(CardApply t) {
		return this.cardApplyDao.selectEntity(t);
	}

	public Integer getCardApplyCount(CardApply t) {
		return this.cardApplyDao.selectEntityCount(t);
	}

	public List<CardApply> getCardApplyList(CardApply t) {
		return this.cardApplyDao.selectEntityList(t);
	}

	@SuppressWarnings("unchecked")
	public int modifyCardApply(CardApply t) {
		int flag = this.cardApplyDao.updateEntity(t);

		List<CardPIndex> cPIndexList = (List<CardPIndex>) t.getMap().get("update_CardPIndex_list");
		if (null != cPIndexList && cPIndexList.size() > 0) {
			// 先刪
			CardPIndex cPIndex = new CardPIndex();
			cPIndex.getMap().put("card_apply_id", t.getId());
			cardPIndexDao.deleteEntity(cPIndex);
			// 後增
			for (CardPIndex temp : cPIndexList) {
				cardPIndexDao.insertEntity(temp);
			}
		}

		CardGenHis cardGenHis = (CardGenHis) t.getMap().get("insert_card_gen_his");
		if (null != cardGenHis) {
			int gen_id = this.cardGenHisDao.insertEntity(cardGenHis);
			// 插入子表card_info
			List<CardInfo> cardList = (List<CardInfo>) cardGenHis.getMap().get("insert_card_info");
			if (null != cardList && cardList.size() > 0) {
				CardInfo card = new CardInfo();
				card.setGen_id(gen_id);
				card.getMap().put("cardInfoList", cardList);
				this.cardInfoDao.insertCardInfoByList(card);
			}

		}

		CardGenHis upGenHis = (CardGenHis) t.getMap().get("update_card_gen_his");
		if (null != upGenHis) {
			this.cardGenHisDao.updateEntity(upGenHis);
			// 插入子表card_info
			if (null != upGenHis.getMap().get("update_card_info")) {
				CardInfo card = new CardInfo();
				card.getMap().put("gen_id", upGenHis.getId());
				card.setCard_state(upGenHis.getInfo_state());
				this.cardInfoDao.updateEntity(card);
			}
		}
		return flag;
	}

	public int removeCardApply(CardApply t) {
		return this.cardApplyDao.deleteEntity(t);
	}

	public List<CardApply> getCardApplyPaginatedList(CardApply t) {
		return this.cardApplyDao.selectEntityPaginatedList(t);
	}

}
