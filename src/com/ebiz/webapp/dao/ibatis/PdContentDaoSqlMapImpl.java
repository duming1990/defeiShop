package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.PdContentDao;
import com.ebiz.webapp.domain.PdContent;

/**
 * @author Wu,Yang
 * @version 2012-03-21 17:16
 */
@Service
public class PdContentDaoSqlMapImpl extends EntityDaoSqlMapImpl<PdContent> implements PdContentDao {

}
