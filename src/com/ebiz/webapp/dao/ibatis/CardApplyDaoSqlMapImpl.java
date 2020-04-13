package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.CardApplyDao;
import com.ebiz.webapp.domain.CardApply;
import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;

/**
 * @author Wu,Yang
 * @version 2019-03-19 14:37
 */
@Service
public class CardApplyDaoSqlMapImpl extends EntityDaoSqlMapImpl<CardApply> implements CardApplyDao {

}
