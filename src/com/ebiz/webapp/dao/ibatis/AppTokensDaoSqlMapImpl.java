package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.AppTokensDao;
import com.ebiz.webapp.domain.AppTokens;
import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;

/**
 * @author Wu,Yang
 * @version 2018-03-26 10:36
 */
@Service
public class AppTokensDaoSqlMapImpl extends EntityDaoSqlMapImpl<AppTokens> implements AppTokensDao {

}
