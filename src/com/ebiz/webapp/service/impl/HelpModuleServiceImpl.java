package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.HelpInfoDao;
import com.ebiz.webapp.dao.HelpModuleDao;
import com.ebiz.webapp.domain.HelpInfo;
import com.ebiz.webapp.domain.HelpModule;
import com.ebiz.webapp.service.HelpModuleService;

/**
 * @author Wu,Yang
 */
@Service
public class HelpModuleServiceImpl implements HelpModuleService {

	@Resource
	private HelpModuleDao helpModuleDao;

	@Resource
	private HelpInfoDao helpInfoDao;

	public Integer createHelpModule(HelpModule t) {
		return this.helpModuleDao.insertEntity(t);
	}

	public HelpModule getHelpModule(HelpModule t) {
		return this.helpModuleDao.selectEntity(t);
	}

	public Integer getHelpModuleCount(HelpModule t) {
		return this.helpModuleDao.selectEntityCount(t);
	}

	public List<HelpModule> getHelpModuleList(HelpModule t) {
		return this.helpModuleDao.selectEntityList(t);
	}

	public int modifyHelpModule(HelpModule t) {
		return this.helpModuleDao.updateEntity(t);
	}

	public int removeHelpModule(HelpModule t) {
		int rows = this.helpModuleDao.deleteEntity(t);

		if (null != t.getH_mod_id()) {
			HelpModule helpModule = new HelpModule();
			helpModule.setPar_id(t.getH_mod_id());
			this.helpModuleDao.deleteEntity(helpModule);

			HelpInfo HelpInfo = new HelpInfo();
			HelpInfo.setIs_del(0);
			HelpInfo.setH_mod_id(t.getH_mod_id());
			helpInfoDao.deleteEntity(HelpInfo);
		}

		return rows;
	}

	public List<HelpModule> getHelpModulePaginatedList(HelpModule t) {
		return this.helpModuleDao.selectEntityPaginatedList(t);
	}

	/**
	 * @author Wu,Yang
	 */
	public Integer getHelpModuleWithLevelNumber(HelpModule t) {
		return this.helpModuleDao.selectHelpModuleWithLevelNumber(t);
	}

	/**
	 * @author Wu,Yang
	 */
	public List<HelpModule> getHelpModuleParentList(HelpModule t) {
		return this.helpModuleDao.selectHelpModuleParentList(t);
	}

}
