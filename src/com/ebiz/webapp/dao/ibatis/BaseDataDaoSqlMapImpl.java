package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.BaseDataDao;
import com.ebiz.webapp.domain.BaseData;

/**
 * @author Wu,Yang
 * @version 2011-04-20 15:36
 */
@Service
public class BaseDataDaoSqlMapImpl extends EntityDaoSqlMapImpl<BaseData> implements BaseDataDao {

}
