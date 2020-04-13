package com.ebiz.webapp.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.SysModuleDao;
import com.ebiz.webapp.domain.SysModule;

/**
 * @author Wu,Yang
 * @version 2011-04-20 15:36
 */
@Service
public class SysModuleDaoSqlMapImpl extends EntityDaoSqlMapImpl<SysModule> implements SysModuleDao {
	/**
	 * @author Wu,Yang
	 * @desc 取父节点list
	 */
	@SuppressWarnings("unchecked")
	public List<SysModule> procedureGetSysModelParentList(SysModule sysModule) throws DataAccessException {
		return super.getSqlMapClientTemplate().queryForList("procedureGetSysModelParentList", sysModule);
	}

	/**
	 * @author Wu,Yang
	 * @desc 取子节点list
	 */
	@SuppressWarnings("unchecked")
	public List<SysModule> procedureGetSysModelSonList(SysModule sysModule) throws DataAccessException {
		return super.getSqlMapClientTemplate().queryForList("procedureGetSysModelSonList", sysModule);
	}

}
