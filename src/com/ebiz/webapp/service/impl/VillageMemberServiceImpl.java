package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.VillageInfoDao;
import com.ebiz.webapp.dao.VillageMemberDao;
import com.ebiz.webapp.domain.VillageInfo;
import com.ebiz.webapp.domain.VillageMember;
import com.ebiz.webapp.service.VillageMemberService;

/**
 * @author Wu,Yang
 * @version 2018-01-26 15:45
 */
@Service
public class VillageMemberServiceImpl implements VillageMemberService {

	@Resource
	private VillageMemberDao villageMemberDao;

	@Resource
	private VillageInfoDao villageInfoDao;

	public Integer createVillageMember(VillageMember t) {
		return this.villageMemberDao.insertEntity(t);
	}

	public VillageMember getVillageMember(VillageMember t) {
		return this.villageMemberDao.selectEntity(t);
	}

	public Integer getVillageMemberCount(VillageMember t) {
		return this.villageMemberDao.selectEntityCount(t);
	}

	public List<VillageMember> getVillageMemberList(VillageMember t) {
		return this.villageMemberDao.selectEntityList(t);
	}

	public int modifyVillageMember(VillageMember t) {
		Integer flag = this.villageMemberDao.updateEntity(t);
		if (t.getMap().get("add_village_member_count") != null) {
			VillageInfo village = new VillageInfo();
			village.setId(t.getVillage_id());
			village.getMap().put("add_count", 1);
			this.villageInfoDao.updateEntity(village);
		}
		if (t.getMap().get("sub_village_member_count") != null) {
			VillageInfo village = new VillageInfo();
			village.setId(t.getVillage_id());
			village.getMap().put("sub_count", 1);
			this.villageInfoDao.updateEntity(village);
		}
		return flag;
	}

	public int removeVillageMember(VillageMember t) {
		return this.villageMemberDao.deleteEntity(t);
	}

	public List<VillageMember> getVillageMemberPaginatedList(VillageMember t) {
		return this.villageMemberDao.selectEntityPaginatedList(t);
	}

	public Integer getVillageMemberCountByPIndex(VillageMember t) {
		return this.villageMemberDao.selectVillageMemberCountByPIndex(t);
	}
}
