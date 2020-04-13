package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.AppInfoDao;
import com.ebiz.webapp.domain.AppInfo;
import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;

/**
 * @author Wu,Yang
 * @version 2018-03-26 10:36
 */
@Service
public class AppInfoDaoSqlMapImpl extends EntityDaoSqlMapImpl<AppInfo> implements AppInfoDao {

}
