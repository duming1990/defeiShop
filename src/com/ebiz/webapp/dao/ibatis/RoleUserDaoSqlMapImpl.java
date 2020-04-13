package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.RoleUserDao;
import com.ebiz.webapp.domain.RoleUser;

/**
 * @author Wu,Yang
 * @version 2011-04-20 15:38
 */
@Service
public class RoleUserDaoSqlMapImpl extends EntityDaoSqlMapImpl<RoleUser> implements RoleUserDao {

}
