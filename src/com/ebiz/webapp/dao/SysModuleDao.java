package com.ebiz.webapp.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.SysModule;

/**
 * @author Wu,Yang
 * @version 2011-04-20 15:36
 */
public interface SysModuleDao extends EntityDao<SysModule> {
	/**
	 * @author Wu,Yang
	 * @desc 取父节点list
	 */
	List<SysModule> procedureGetSysModelParentList(SysModule sysModule) throws DataAccessException;

	/**
	 * @author Wu,Yang
	 * @desc 取子节点list
	 */
	List<SysModule> procedureGetSysModelSonList(SysModule sysModule) throws DataAccessException;

}
