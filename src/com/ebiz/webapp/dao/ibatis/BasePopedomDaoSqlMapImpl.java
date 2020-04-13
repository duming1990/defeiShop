package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.BasePopedomDao;
import com.ebiz.webapp.domain.BasePopedom;

/**
 * @author Wu,Yang
 * @version 2012-02-14 10:56
 */
@Service
public class BasePopedomDaoSqlMapImpl extends EntityDaoSqlMapImpl<BasePopedom> implements BasePopedomDao {

}
