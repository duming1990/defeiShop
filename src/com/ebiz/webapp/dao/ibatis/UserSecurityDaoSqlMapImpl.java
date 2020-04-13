package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.UserSecurityDao;
import com.ebiz.webapp.domain.UserSecurity;
import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;

/**
 * @author Wu,Yang
 * @version 2015-12-06 15:07
 */
@Service
public class UserSecurityDaoSqlMapImpl extends EntityDaoSqlMapImpl<UserSecurity> implements UserSecurityDao {

}
