package com.ebiz.webapp.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.CommInfoPoorsDao;
import com.ebiz.webapp.domain.CommInfoPoors;
import com.ebiz.webapp.service.CommInfoPoorsService;

/**
 * @author Wu,Yang
 * @version 2018-01-22 10:47
 */
@Service
public class CommInfoPoorsServiceImpl implements CommInfoPoorsService {

	@Resource
	private CommInfoPoorsDao commInfoPoorsDao;

	public Integer createCommInfoPoors(CommInfoPoors t) {
		return this.commInfoPoorsDao.insertEntity(t);
	}

	public CommInfoPoors getCommInfoPoors(CommInfoPoors t) {
		return this.commInfoPoorsDao.selectEntity(t);
	}

	public Integer getCommInfoPoorsCount(CommInfoPoors t) {
		return this.commInfoPoorsDao.selectEntityCount(t);
	}

	public List<CommInfoPoors> getCommInfoPoorsList(CommInfoPoors t) {
		return this.commInfoPoorsDao.selectEntityList(t);
	}

	public int modifyCommInfoPoors(CommInfoPoors t) {

		return this.commInfoPoorsDao.updateEntity(t);
	}

	public int removeAndInsertCommInfoPoors(CommInfoPoors t) {
		if (null != t.getComm_id()) {
			CommInfoPoors delCommInfoPoor = new CommInfoPoors();
			delCommInfoPoor.getMap().put("comm_id", t.getComm_id());
			int i = this.commInfoPoorsDao.deleteEntity(delCommInfoPoor);

			// if (i > 0) {
			String[] poor_ids = (String[]) t.getMap().get("poor_ids");// 扶贫对象数组
			for (int j = 0; j < poor_ids.length; j++) {
				CommInfoPoors insertCommInfoPoor = new CommInfoPoors();
				insertCommInfoPoor.setComm_id(t.getComm_id());
				insertCommInfoPoor.setPoor_id(Integer.valueOf(poor_ids[j]));
				insertCommInfoPoor.setAdd_date(new Date());
				insertCommInfoPoor.setAdd_user_id(t.getAdd_user_id());
				insertCommInfoPoor.setIs_temp(0);
				this.commInfoPoorsDao.insertEntity(insertCommInfoPoor);
			}
			// }
			return 1;
		}
		return 0;

	}

	public int removeCommInfoPoors(CommInfoPoors t) {
		return this.commInfoPoorsDao.deleteEntity(t);
	}

	public List<CommInfoPoors> getCommInfoPoorsPaginatedList(CommInfoPoors t) {
		return this.commInfoPoorsDao.selectEntityPaginatedList(t);
	}

}
