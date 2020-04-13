package com.ebiz.webapp.dao;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.CardInfo;

/**
 * @author Wu,Yang
 * @version 2019-03-19 14:37
 */
public interface CardInfoDao extends EntityDao<CardInfo> {

	Integer insertCardInfoByList(CardInfo t);

}
