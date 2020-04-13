package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.BaseFilesDao;
import com.ebiz.webapp.domain.BaseFiles;

/**
 * @author Wu,Yang
 * @version 2013-07-02 11:42
 */
@Service
public class BaseFilesDaoSqlMapImpl extends EntityDaoSqlMapImpl<BaseFiles> implements BaseFilesDao {

}
