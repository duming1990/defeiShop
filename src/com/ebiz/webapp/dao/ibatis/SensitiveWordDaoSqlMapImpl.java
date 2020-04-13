package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.SensitiveWordDao;
import com.ebiz.webapp.domain.SensitiveWord;
import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;

/**
 * @author Wu,Yang
 * @version 2018-01-30 10:26
 */
@Service
public class SensitiveWordDaoSqlMapImpl extends EntityDaoSqlMapImpl<SensitiveWord> implements SensitiveWordDao {

}
