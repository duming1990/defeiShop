package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.CommInfoPromotionDao;
import com.ebiz.webapp.domain.CommInfoPromotion;
import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;

/**
 * @author Wu,Yang
 * @version 2017-04-21 17:10
 */
@Service
public class CommInfoPromotionDaoSqlMapImpl extends EntityDaoSqlMapImpl<CommInfoPromotion> implements CommInfoPromotionDao {

}
