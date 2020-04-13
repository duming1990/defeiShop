package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.MsgDao;
import com.ebiz.webapp.domain.Msg;

/**
 * @author Wu,Yang
 * @version 2015-03-13 12:59
 */
@Service
public class MsgDaoSqlMapImpl extends EntityDaoSqlMapImpl<Msg> implements MsgDao {

}
