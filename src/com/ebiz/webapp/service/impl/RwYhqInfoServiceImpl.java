package com.ebiz.webapp.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.RwHbInfoDao;
import com.ebiz.webapp.dao.RwHbYhqDao;
import com.ebiz.webapp.dao.RwYhqInfoDao;
import com.ebiz.webapp.domain.RwHbInfo;
import com.ebiz.webapp.domain.RwHbYhq;
import com.ebiz.webapp.domain.RwYhqInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.service.RwYhqInfoService;

/**
 * @author Wu,Yang
 * @version 2016-06-16 18:18
 */
@Service
public class RwYhqInfoServiceImpl implements RwYhqInfoService {

	@Resource
	private RwYhqInfoDao rwYhqInfoDao;

	@Resource
	private RwHbInfoDao rwHbInfoDao;

	@Resource
	private RwHbYhqDao rwHbYhqDao;

	public Integer createRwYhqInfo(RwYhqInfo t) {
		Integer Yhq_id = this.rwYhqInfoDao.insertEntity(t);
		//
		if (null != t.getMap().get("insertYhqAndYhqHbByOrderId")) {
			UserInfo userInfo = (UserInfo) t.getMap().get("userInfo");
			RwHbInfo rwHbInfo = (RwHbInfo) t.getMap().get("rwHbInfo");

			RwHbYhq rwHbYhq = new RwHbYhq();
			rwHbYhq.setYhq_id(Yhq_id);
			rwHbYhq.setHb_id(rwHbInfo.getId());
			rwHbYhq.setAdd_uid(userInfo.getId());
			rwHbYhq.setAdd_date(new Date());
			rwHbYhqDao.insertEntity(rwHbYhq);

			RwHbInfo rwHbInfoForUpdate = new RwHbInfo();
			rwHbInfoForUpdate.setId(rwHbInfo.getId());
			rwHbInfoForUpdate.setHb_rec_count(rwHbInfo.getHb_rec_count() + 1);
			if (rwHbInfoForUpdate.getHb_rec_count() >= rwHbInfo.getHb_max_count()) {
				rwHbInfoForUpdate.setIs_end(1);
			}

			rwHbInfoDao.updateEntity(rwHbInfoForUpdate);
		}

		return Yhq_id;
	}

	public RwYhqInfo getRwYhqInfo(RwYhqInfo t) {
		return this.rwYhqInfoDao.selectEntity(t);
	}

	public Integer getRwYhqInfoCount(RwYhqInfo t) {
		return this.rwYhqInfoDao.selectEntityCount(t);
	}

	public List<RwYhqInfo> getRwYhqInfoList(RwYhqInfo t) {
		return this.rwYhqInfoDao.selectEntityList(t);
	}

	public int modifyRwYhqInfo(RwYhqInfo t) {
		return this.rwYhqInfoDao.updateEntity(t);
	}

	public int removeRwYhqInfo(RwYhqInfo t) {
		return this.rwYhqInfoDao.deleteEntity(t);
	}

	public List<RwYhqInfo> getRwYhqInfoPaginatedList(RwYhqInfo t) {
		return this.rwYhqInfoDao.selectEntityPaginatedList(t);
	}

	@Override
	public RwYhqInfo getRwYhqInfoStatisticaSum(RwYhqInfo t) {
		return this.rwYhqInfoDao.selectRwYhqInfoStatisticaSum(t);
	}
}
