package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.UserScoreRecordDao;
import com.ebiz.webapp.domain.UserScoreRecord;

/**
 * @author Wu,Yang
 * @version 2014-05-27 16:43
 */
@Service
public class UserScoreRecordDaoSqlMapImpl extends EntityDaoSqlMapImpl<UserScoreRecord> implements UserScoreRecordDao {

}
