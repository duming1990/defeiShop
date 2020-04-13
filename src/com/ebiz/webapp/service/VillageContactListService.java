package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.VillageContactList;

/**
 * @author Wu,Yang
 * @version 2018-01-26 15:45
 */
public interface VillageContactListService {

	Integer createVillageContactList(VillageContactList t);

	int modifyVillageContactList(VillageContactList t);

	int removeVillageContactList(VillageContactList t);

	VillageContactList getVillageContactList(VillageContactList t);

	List<VillageContactList> getVillageContactListList(VillageContactList t);

	Integer getVillageContactListCount(VillageContactList t);

	List<VillageContactList> getVillageContactListPaginatedList(VillageContactList t);
}