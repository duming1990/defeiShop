package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.VillageInfo;

/**
 * @author Wu,Yang
 * @version 2018-01-15 16:11
 */
public interface VillageInfoService {

	Integer createVillageInfo(VillageInfo t);

	int modifyVillageInfo(VillageInfo t);

	int removeVillageInfo(VillageInfo t);

	VillageInfo getVillageInfo(VillageInfo t);

	List<VillageInfo> getVillageInfoList(VillageInfo t);

	Integer getVillageInfoCount(VillageInfo t);

	List<VillageInfo> getVillageInfoPaginatedList(VillageInfo t);

}