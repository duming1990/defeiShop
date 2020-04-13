package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.ShortMessageReceiverDao;
import com.ebiz.webapp.domain.ShortMessageReceiver;
import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;

/**
 * @author Wu,Yang
 * @version 2018-07-02 10:38
 */
@Service
public class ShortMessageReceiverDaoSqlMapImpl extends EntityDaoSqlMapImpl<ShortMessageReceiver> implements ShortMessageReceiverDao {

}
