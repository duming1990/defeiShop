package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.YhqInfoDao;
import com.ebiz.webapp.domain.YhqInfo;
import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;

/**
 * @author Wu,Yang
 * @version 2017-04-19 17:50
 */
@Service
public class YhqInfoDaoSqlMapImpl extends EntityDaoSqlMapImpl<YhqInfo> implements YhqInfoDao {

}
