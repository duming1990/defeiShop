package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.PoorZeRenDao;
import com.ebiz.webapp.domain.PoorZeRen;
import com.ebiz.webapp.service.PoorZeRenService;

/**
 * @author Wu,Yang
 * @version 2018-03-01 15:13
 */
@Service
public class PoorZeRenServiceImpl implements PoorZeRenService {

	@Resource
	private PoorZeRenDao poorZeRenDao;

	public Integer createPoorZeRen(PoorZeRen t) {

		if (null != t.getMap().get("add_poor_ze_ren")) {// 村站添加多条责任人记录
			List<PoorZeRen> zerenList = t.getZeRenList();
			int insert_flag = 0;
			if (zerenList != null && zerenList.size() > 0) {
				// 先删
				List<PoorZeRen> zeRenOldList = this.poorZeRenDao.selectEntityList(t);
				if (zeRenOldList != null && zeRenOldList.size() > 0) {
					for (PoorZeRen temp : zeRenOldList) {
						this.poorZeRenDao.deleteEntity(temp);
					}
				}
				// 后增
				for (PoorZeRen temp : zerenList) {
					Integer poor_zeren_id = this.poorZeRenDao.insertEntity(temp);
					insert_flag = poor_zeren_id;
				}
			}
			return insert_flag;
		} else {// 正常新增
			return this.poorZeRenDao.insertEntity(t);
		}
	}

	public PoorZeRen getPoorZeRen(PoorZeRen t) {
		return this.poorZeRenDao.selectEntity(t);
	}

	public Integer getPoorZeRenCount(PoorZeRen t) {
		return this.poorZeRenDao.selectEntityCount(t);
	}

	public List<PoorZeRen> getPoorZeRenList(PoorZeRen t) {
		return this.poorZeRenDao.selectEntityList(t);
	}

	public int modifyPoorZeRen(PoorZeRen t) {
		return this.poorZeRenDao.updateEntity(t);
	}

	public int removePoorZeRen(PoorZeRen t) {
		return this.poorZeRenDao.deleteEntity(t);
	}

	public List<PoorZeRen> getPoorZeRenPaginatedList(PoorZeRen t) {
		return this.poorZeRenDao.selectEntityPaginatedList(t);
	}

}
