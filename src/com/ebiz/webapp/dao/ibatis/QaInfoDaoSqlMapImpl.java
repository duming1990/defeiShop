package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.QaInfoDao;
import com.ebiz.webapp.domain.QaInfo;

/**
 * @author Wu,Yang
 * @version 2012-03-21 17:16
 */
@Service
public class QaInfoDaoSqlMapImpl extends EntityDaoSqlMapImpl<QaInfo> implements QaInfoDao {

}
