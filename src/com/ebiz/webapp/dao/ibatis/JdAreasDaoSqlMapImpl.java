package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.JdAreasDao;
import com.ebiz.webapp.domain.JdAreas;
import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;

/**
 * @author Wu,Yang
 * @version 2018-03-04 16:08
 */
@Service
public class JdAreasDaoSqlMapImpl extends EntityDaoSqlMapImpl<JdAreas> implements JdAreasDao {

}
