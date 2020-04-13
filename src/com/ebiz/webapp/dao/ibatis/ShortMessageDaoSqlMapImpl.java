package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.ShortMessageDao;
import com.ebiz.webapp.domain.ShortMessage;
import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;

/**
 * @author Wu,Yang
 * @version 2018-07-02 10:24
 */
@Service
public class ShortMessageDaoSqlMapImpl extends EntityDaoSqlMapImpl<ShortMessage> implements ShortMessageDao {

}
