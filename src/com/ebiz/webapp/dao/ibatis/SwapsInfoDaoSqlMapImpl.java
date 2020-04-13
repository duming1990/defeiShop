package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.SwapsInfoDao;
import com.ebiz.webapp.domain.SwapsInfo;

/**
 * @version 2014-06-18 16:13
 */
@Service
public class SwapsInfoDaoSqlMapImpl extends EntityDaoSqlMapImpl<SwapsInfo> implements SwapsInfoDao {

}
