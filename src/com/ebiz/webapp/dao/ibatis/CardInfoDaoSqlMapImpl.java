package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.CardInfoDao;
import com.ebiz.webapp.domain.CardInfo;

/**
 * @author Wu,Yang
 * @version 2019-03-19 14:37
 */
@Service
public class CardInfoDaoSqlMapImpl extends EntityDaoSqlMapImpl<CardInfo> implements CardInfoDao {

	@Override
	public Integer insertCardInfoByList(CardInfo t) {
		return (Integer) super.getSqlMapClientTemplate().insert("insertCardInfoByList", t);
	}

}
