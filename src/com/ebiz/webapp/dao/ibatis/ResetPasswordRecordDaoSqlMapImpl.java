package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.ResetPasswordRecordDao;
import com.ebiz.webapp.domain.ResetPasswordRecord;

/**
 * @author Li,Ka
 * @version 2013-07-23 10:05
 */
@Service
public class ResetPasswordRecordDaoSqlMapImpl extends EntityDaoSqlMapImpl<ResetPasswordRecord> implements
		ResetPasswordRecordDao {

}
