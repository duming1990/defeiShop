package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.PoorFamilyDao;
import com.ebiz.webapp.domain.PoorFamily;
import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;

/**
 * @author Wu,Yang
 * @version 2018-03-01 15:13
 */
@Service
public class PoorFamilyDaoSqlMapImpl extends EntityDaoSqlMapImpl<PoorFamily> implements PoorFamilyDao {

}
