package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.RoleDao;
import com.ebiz.webapp.domain.Role;

/**
 * @author Wu,Yang
 * @version 2011-04-20 15:38
 */
@Service
public class RoleDaoSqlMapImpl extends EntityDaoSqlMapImpl<Role> implements RoleDao {

}
