package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.InvoiceInfoDao;
import com.ebiz.webapp.domain.InvoiceInfo;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:54
 */
@Service
public class InvoiceInfoDaoSqlMapImpl extends EntityDaoSqlMapImpl<InvoiceInfo> implements InvoiceInfoDao {

}
