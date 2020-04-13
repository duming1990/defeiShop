package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.TongjiDao;
import com.ebiz.webapp.domain.Tongji;
import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;

/**
 * @author Wu,Yang
 * @version 2015-12-23 17:15
 */
@Service
public class TongjiDaoSqlMapImpl extends EntityDaoSqlMapImpl<Tongji> implements TongjiDao {

}
