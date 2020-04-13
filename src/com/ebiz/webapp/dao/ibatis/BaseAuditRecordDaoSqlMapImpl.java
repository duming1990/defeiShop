package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.BaseAuditRecordDao;
import com.ebiz.webapp.domain.BaseAuditRecord;
import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;

/**
 * @author Wu,Yang
 * @version 2016-01-05 16:48
 */
@Service
public class BaseAuditRecordDaoSqlMapImpl extends EntityDaoSqlMapImpl<BaseAuditRecord> implements BaseAuditRecordDao {

}
