package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Repository;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.HelpInfoDao;
import com.ebiz.webapp.domain.HelpInfo;

/**
 * @author Jin,QingHua
 */
@Repository
public class HelpInfoDaoSqlMapImpl extends EntityDaoSqlMapImpl<HelpInfo> implements HelpInfoDao {

}
