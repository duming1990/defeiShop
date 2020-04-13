package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.HelpModule;

/**
 * @author Wu,Yang
 */
public interface HelpModuleService {

	Integer createHelpModule(HelpModule t);

	int modifyHelpModule(HelpModule t);

	int removeHelpModule(HelpModule t);

	HelpModule getHelpModule(HelpModule t);

	List<HelpModule> getHelpModuleList(HelpModule t);

	Integer getHelpModuleCount(HelpModule t);

	List<HelpModule> getHelpModulePaginatedList(HelpModule t);

	/**
	 * @author Wu,Yang
	 */
	Integer getHelpModuleWithLevelNumber(HelpModule t);

	/**
	 * @author Wu,Yang
	 */
	List<HelpModule> getHelpModuleParentList(HelpModule t);

}
