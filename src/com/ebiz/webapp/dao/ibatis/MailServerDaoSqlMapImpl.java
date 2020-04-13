package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.MailServerDao;
import com.ebiz.webapp.domain.MailServer;

/**
 * @author Wu,Yang
 * @version 2012-03-30 15:43
 */
@Service
public class MailServerDaoSqlMapImpl extends EntityDaoSqlMapImpl<MailServer> implements MailServerDao {

}
