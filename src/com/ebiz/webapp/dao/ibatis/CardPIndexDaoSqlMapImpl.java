package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.CardPIndexDao;
import com.ebiz.webapp.domain.CardPIndex;
import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;

/**
 * @author Wu,Yang
 * @version 2019-03-19 14:37
 */
@Service
public class CardPIndexDaoSqlMapImpl extends EntityDaoSqlMapImpl<CardPIndex> implements CardPIndexDao {

}
