package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.BaseClassLinkAttributeDao;
import com.ebiz.webapp.domain.BaseClassLinkAttribute;

/**
 * @author Wu,Yang
 * @version 2012-05-30 08:53
 */
@Service
public class BaseClassLinkAttributeDaoSqlMapImpl extends EntityDaoSqlMapImpl<BaseClassLinkAttribute> implements
		BaseClassLinkAttributeDao {

}
