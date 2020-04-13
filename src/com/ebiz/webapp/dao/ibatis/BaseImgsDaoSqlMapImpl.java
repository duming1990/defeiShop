package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.BaseImgsDao;
import com.ebiz.webapp.domain.BaseImgs;

/**
 * @author Wu,Yang
 * @version 2012-05-30 08:59
 */
@Service
public class BaseImgsDaoSqlMapImpl extends EntityDaoSqlMapImpl<BaseImgs> implements BaseImgsDao {

}
