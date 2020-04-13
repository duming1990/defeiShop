package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.DiscountStoresDao;
import com.ebiz.webapp.domain.DiscountStores;

/**
 * @author Wu,Yang
 * @version 2014-08-08 09:55
 */
@Service
public class DiscountStoresDaoSqlMapImpl extends EntityDaoSqlMapImpl<DiscountStores> implements DiscountStoresDao {

}
