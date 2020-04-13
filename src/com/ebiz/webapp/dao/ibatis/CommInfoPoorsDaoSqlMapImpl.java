package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.CommInfoPoorsDao;
import com.ebiz.webapp.domain.CommInfoPoors;
import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;

/**
 * @author Wu,Yang
 * @version 2018-01-22 10:47
 */
@Service
public class CommInfoPoorsDaoSqlMapImpl extends EntityDaoSqlMapImpl<CommInfoPoors> implements CommInfoPoorsDao {

}
