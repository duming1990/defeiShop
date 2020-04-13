package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.UserRelationDao;
import com.ebiz.webapp.domain.UserRelation;
import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;

/**
 * @author Wu,Yang
 * @version 2015-11-21 14:17
 */
@Service
public class UserRelationDaoSqlMapImpl extends EntityDaoSqlMapImpl<UserRelation> implements UserRelationDao {

}
