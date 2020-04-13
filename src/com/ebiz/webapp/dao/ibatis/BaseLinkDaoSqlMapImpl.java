package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.BaseLinkDao;
import com.ebiz.webapp.domain.BaseLink;

/**
 * @author Wu,Yang
 * @version 2013-05-27 14:06
 */
@Service
public class BaseLinkDaoSqlMapImpl extends EntityDaoSqlMapImpl<BaseLink> implements BaseLinkDao {

}
