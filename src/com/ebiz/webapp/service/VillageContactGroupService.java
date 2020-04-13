package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.VillageContactGroup;

/**
 * @author Wu,Yang
 * @version 2018-01-26 15:45
 */
public interface VillageContactGroupService {

	Integer createVillageContactGroup(VillageContactGroup t);

	int modifyVillageContactGroup(VillageContactGroup t);

	int removeVillageContactGroup(VillageContactGroup t);

	VillageContactGroup getVillageContactGroup(VillageContactGroup t);

	List<VillageContactGroup> getVillageContactGroupList(VillageContactGroup t);

	Integer getVillageContactGroupCount(VillageContactGroup t);

	List<VillageContactGroup> getVillageContactGroupPaginatedList(VillageContactGroup t);

	List<VillageContactGroup> getVillageContactGroupNameAndCount(VillageContactGroup t);

}