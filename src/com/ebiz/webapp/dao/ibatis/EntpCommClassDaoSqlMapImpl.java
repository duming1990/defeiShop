package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.EntpCommClassDao;
import com.ebiz.webapp.domain.EntpCommClass;
import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;

/**
 * @author Wu,Yang
 * @version 2018-07-10 15:03
 */
@Service
public class EntpCommClassDaoSqlMapImpl extends EntityDaoSqlMapImpl<EntpCommClass> implements EntpCommClassDao {

}
