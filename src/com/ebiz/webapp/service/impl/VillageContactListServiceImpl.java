package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.UserInfoDao;
import com.ebiz.webapp.dao.VillageContactListDao;
import com.ebiz.webapp.dao.VillageDynamicRecordDao;
import com.ebiz.webapp.domain.VillageContactList;
import com.ebiz.webapp.service.VillageContactListService;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 * @version 2018-01-26 15:45
 */
@Service
public class VillageContactListServiceImpl extends BaseImpl implements VillageContactListService {

	@Resource
	private VillageContactListDao villageContactListDao;

	@Resource
	private VillageDynamicRecordDao villageDynamicRecordDao;

	@Resource
	private UserInfoDao userInfoDao;

	public Integer createVillageContactList(VillageContactList t) {
		int id = this.villageContactListDao.insertEntity(t);
		// 插入记录
		insertRecord(null, id, Keys.DynamicRecordType.DynamicRecordType_1.getIndex(), t.getContact_user_id(),
				t.getContact_user_name(), t.getAdd_user_id(), t.getAdd_user_name(), villageDynamicRecordDao);

		return id;
	}

	public VillageContactList getVillageContactList(VillageContactList t) {
		return this.villageContactListDao.selectEntity(t);
	}

	public Integer getVillageContactListCount(VillageContactList t) {
		return this.villageContactListDao.selectEntityCount(t);
	}

	public List<VillageContactList> getVillageContactListList(VillageContactList t) {
		return this.villageContactListDao.selectEntityList(t);
	}

	public int modifyVillageContactList(VillageContactList t) {
		return this.villageContactListDao.updateEntity(t);
	}

	public int removeVillageContactList(VillageContactList t) {
		return this.villageContactListDao.deleteEntity(t);
	}

	public List<VillageContactList> getVillageContactListPaginatedList(VillageContactList t) {
		return this.villageContactListDao.selectEntityPaginatedList(t);
	}

}
