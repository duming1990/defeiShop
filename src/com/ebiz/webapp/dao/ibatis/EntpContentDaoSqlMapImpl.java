package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.EntpContentDao;
import com.ebiz.webapp.domain.EntpContent;
import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;

/**
 * @author Wu,Yang
 * @version 2016-03-28 15:00
 */
@Service
public class EntpContentDaoSqlMapImpl extends EntityDaoSqlMapImpl<EntpContent> implements EntpContentDao {

}
