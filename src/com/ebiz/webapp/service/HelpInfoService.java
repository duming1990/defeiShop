package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.HelpInfo;

/**
 * @author Jin,QingHua
 */
public interface HelpInfoService {

	Integer createHelpInfo(HelpInfo t);

	int modifyHelpInfo(HelpInfo t);

	int removeHelpInfo(HelpInfo t);

	HelpInfo getHelpInfo(HelpInfo t);

	List<HelpInfo> getHelpInfoList(HelpInfo t);

	Integer getHelpInfoCount(HelpInfo t);

	List<HelpInfo> getHelpInfoPaginatedList(HelpInfo t);

}
