package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.FreightDao;
import com.ebiz.webapp.domain.Freight;

/**
 * @author Wu,Yang
 * @version 2014-05-22 19:36
 */
@Service
public class FreightDaoSqlMapImpl extends EntityDaoSqlMapImpl<Freight> implements FreightDao {

}
