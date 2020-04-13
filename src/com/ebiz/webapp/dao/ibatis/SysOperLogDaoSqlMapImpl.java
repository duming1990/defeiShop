package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.SysOperLogDao;
import com.ebiz.webapp.domain.SysOperLog;

/**
 * @author Wu,Yang
 * @version 2013-12-05 07:20
 */
@Service
public class SysOperLogDaoSqlMapImpl extends EntityDaoSqlMapImpl<SysOperLog> implements SysOperLogDao {

}
