package com.ebiz.webapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.SysModuleDao;
import com.ebiz.webapp.domain.SysModule;
import com.ebiz.webapp.service.SysModuleService;

/**
 * @author Wu,Yang
 * @version 2011-04-20 02:13:04
 */
@Service
public class SysModuleServiceImpl implements SysModuleService {

	@Resource
	private SysModuleDao sysModuleDao;

	public Integer createSysModule(SysModule t) {
		return this.sysModuleDao.insertEntity(t);
	}

	public int modifySysModule(SysModule t) {
		return this.sysModuleDao.updateEntity(t);
	}

	public int removeSysModule(SysModule t) {
		return this.sysModuleDao.deleteEntity(t);
	}

	public SysModule getSysModule(SysModule t) {
		return this.sysModuleDao.selectEntity(t);
	}

	public Integer getSysModuleCount(SysModule t) {
		return this.sysModuleDao.selectEntityCount(t);
	}

	public List<SysModule> getSysModuleList(SysModule t) {
		return this.sysModuleDao.selectEntityList(t);
	}

	public List<SysModule> getSysModulePaginatedList(SysModule t) {
		return this.sysModuleDao.selectEntityPaginatedList(t);
	}

	/**
	 * @author Wu,Yang
	 * @desc 取父节点list
	 */
	public List<SysModule> proGetSysModelParentList(SysModule t) {
		// 子查父
		List<SysModule> parentList = new ArrayList<SysModule>();
		List<SysModule> proGetSysModelParentList = this.getParentList(t.getMod_id(), parentList);
		return proGetSysModelParentList;
		// return this.sysModuleDao.procedureGetSysModelParentList(t);
	}

	/**
	 * @author Wu,Yang
	 * @desc 取子节点list
	 */
	public List<SysModule> proGetSysModuleSonList(SysModule t) {

		// 父查子
		List<SysModule> sonList = new ArrayList<SysModule>();
		List<SysModule> proGetSysModuleSonList = this.getSonList(true, t.getMod_id(), sonList, t.getMod_group());
		return proGetSysModuleSonList;

		// return this.sysModuleDao.procedureGetSysModelSonList(t);
	}

	public List<SysModule> getParentList(Long par_id, List<SysModule> parentList) {

		SysModule SysModule = new SysModule();
		SysModule.setMod_id(par_id);
		SysModule.setIs_del(0);
		SysModule = this.sysModuleDao.selectEntity(SysModule);
		if (null != SysModule) {
			parentList.add(0, SysModule);
			this.getParentList(SysModule.getPar_id(), parentList);
		}
		return parentList;
	}

	public List<SysModule> getSonList(Boolean is_first, Long par_id, List<SysModule> sonList, Integer mod_group) {

		if (is_first) {
			SysModule temp = new SysModule();
			temp.setMod_id(par_id);
			temp.setIs_del(0);
			temp = this.sysModuleDao.selectEntity(temp);
			if (null != temp) {
				sonList.add(temp);
			}
		}

		SysModule SysModule = new SysModule();
		SysModule.setPar_id(par_id);
		SysModule.setIs_del(0);
		SysModule.setMod_group(mod_group);
		List<SysModule> SysModuleList = this.sysModuleDao.selectEntityList(SysModule);
		if (null != SysModuleList && SysModuleList.size() > 0) {
			for (SysModule temp : SysModuleList) {
				sonList.add(temp);
				this.getSonList(false, temp.getMod_id(), sonList, temp.getMod_group());
			}
		}
		return sonList;
	}
}
