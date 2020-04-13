package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.PoorFamilyDao;
import com.ebiz.webapp.domain.PoorFamily;
import com.ebiz.webapp.service.PoorFamilyService;

/**
 * @author Wu,Yang
 * @version 2018-03-01 15:13
 */
@Service
public class PoorFamilyServiceImpl implements PoorFamilyService {

	@Resource
	private PoorFamilyDao poorFamilyDao;

	public Integer createPoorFamily(PoorFamily t) {
		if (null != t.getMap().get("add_poor_family")) {// 村站添加多条成员记录
			List<PoorFamily> familyList = t.getFamilyList();
			int insert_flag = 0;
			if (familyList != null && familyList.size() > 0) {
				// 先删
				List<PoorFamily> familyOldList = this.poorFamilyDao.selectEntityList(t);
				if (familyOldList != null && familyOldList.size() > 0) {
					for (PoorFamily temp : familyOldList) {
						this.poorFamilyDao.deleteEntity(temp);
					}
				}
				// 后增
				for (PoorFamily temp : familyList) {
					Integer poor_family_id = this.poorFamilyDao.insertEntity(temp);
					insert_flag = poor_family_id;
				}
			}
			return insert_flag;
		} else {// 正常新增
			return this.poorFamilyDao.insertEntity(t);
		}
	}

	public PoorFamily getPoorFamily(PoorFamily t) {
		return this.poorFamilyDao.selectEntity(t);
	}

	public Integer getPoorFamilyCount(PoorFamily t) {
		return this.poorFamilyDao.selectEntityCount(t);
	}

	public List<PoorFamily> getPoorFamilyList(PoorFamily t) {
		return this.poorFamilyDao.selectEntityList(t);
	}

	public int modifyPoorFamily(PoorFamily t) {
		return this.poorFamilyDao.updateEntity(t);
	}

	public int removePoorFamily(PoorFamily t) {
		return this.poorFamilyDao.deleteEntity(t);
	}

	public List<PoorFamily> getPoorFamilyPaginatedList(PoorFamily t) {
		return this.poorFamilyDao.selectEntityPaginatedList(t);
	}

}
