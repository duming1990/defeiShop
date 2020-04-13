package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Repository;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.HelpContentDao;
import com.ebiz.webapp.domain.HelpContent;

/**
 * @author Jin,QingHua
 */
@Repository
public class HelpContentDaoSqlMapImpl extends EntityDaoSqlMapImpl<HelpContent> implements HelpContentDao {

}
