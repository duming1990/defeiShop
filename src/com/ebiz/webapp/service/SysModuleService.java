package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.SysModule;

/**
 * @author Wu,Yang
 * @version 2011-04-20 02:13:04
 */
public interface SysModuleService {

	Integer createSysModule(SysModule t);

	int modifySysModule(SysModule t);

	int removeSysModule(SysModule t);

	SysModule getSysModule(SysModule t);

	List<SysModule> getSysModuleList(SysModule t);

	Integer getSysModuleCount(SysModule t);

	List<SysModule> getSysModulePaginatedList(SysModule t);

	/**
	 * @author Wu,Yang
	 * @desc 取父节点list
	 */
	List<SysModule> proGetSysModelParentList(SysModule t);

	/**
	 * @author Wu,Yang
	 * @desc 取子节点list
	 */
	List<SysModule> proGetSysModuleSonList(SysModule t);

}
