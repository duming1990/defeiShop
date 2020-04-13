package com.ebiz.webapp.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.HelpModuleDao;
import com.ebiz.webapp.domain.HelpModule;

/**
 * @author Wu,Yang
 */
@Repository
public class HelpModuleDaoSqlMapImpl extends EntityDaoSqlMapImpl<HelpModule> implements HelpModuleDao {

	/**
	 * @author Wu,Yang
	 */
	public Integer selectHelpModuleWithLevelNumber(HelpModule t) throws DataAccessException {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectHelpModuleWithLevelNumber", t);
	}

	/**
	 * @author Wu,Yang
	 */
	@SuppressWarnings("unchecked")
	public List<HelpModule> selectHelpModuleParentList(HelpModule t) throws DataAccessException {
		return super.getSqlMapClientTemplate().queryForList("selectHelpModuleParentList", t);
	}

}
