package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.SysSetting;

/**
 * @author Wu,Yang
 * @version 2011-04-20 02:13:04
 */
public interface SysSettingService {

	Integer createSysSetting(SysSetting t);

	int modifySysSetting(SysSetting t);

	int removeSysSetting(SysSetting t);

	SysSetting getSysSetting(SysSetting t);

	List<SysSetting> getSysSettingList(SysSetting t);

	Integer getSysSettingCount(SysSetting t);

	List<SysSetting> getSysSettingPaginatedList(SysSetting t);

}
