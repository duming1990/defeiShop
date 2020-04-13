package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.YhqInfoDao;
import com.ebiz.webapp.dao.YhqInfoSonDao;
import com.ebiz.webapp.dao.YhqLinkDao;
import com.ebiz.webapp.domain.YhqInfo;
import com.ebiz.webapp.domain.YhqInfoSon;
import com.ebiz.webapp.domain.YhqLink;
import com.ebiz.webapp.service.YhqInfoService;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 * @version 2017-04-19 17:50
 */
@Service
public class YhqInfoServiceImpl implements YhqInfoService {

	@Resource
	private YhqInfoDao yhqInfoDao;

	@Resource
	private YhqInfoSonDao yhqInfoSonDao;

	@Resource
	private YhqLinkDao yhqLinkDao;

	public Integer createYhqInfo(YhqInfo t) {
		int id = this.yhqInfoDao.insertEntity(t);
		String[] comm_ids = (String[]) t.getMap().get("comm_ids");
		if (id > 0) {
			for (String ids : comm_ids) {
				YhqLink coupons = new YhqLink();
				coupons.setComm_id(Integer.valueOf(ids));
				coupons.setYhq_id(id);
				this.yhqLinkDao.insertEntity(coupons);
			}
		}
		return id;
	}

	public YhqInfo getYhqInfo(YhqInfo t) {
		return this.yhqInfoDao.selectEntity(t);
	}

	public Integer getYhqInfoCount(YhqInfo t) {
		return this.yhqInfoDao.selectEntityCount(t);
	}

	public List<YhqInfo> getYhqInfoList(YhqInfo t) {
		return this.yhqInfoDao.selectEntityList(t);
	}

	public int modifyYhqInfo(YhqInfo t) {
		int id = this.yhqInfoDao.updateEntity(t);
		String link_id = (String) t.getMap().get("link_id");
		if (id > 0) {
			if (null != t.getMap().get("yhqInfoSon")) {
				YhqInfoSon infoSon = (YhqInfoSon) t.getMap().get("yhqInfoSon");
				this.yhqInfoSonDao.insertEntity((infoSon));
			}
			if (null != link_id) {
				YhqInfoSon yhqInfoSonUpdate = new YhqInfoSon();// 优惠券被删除。领取过得变为不可用
				yhqInfoSonUpdate.setYhq_state(Keys.YhqState.YHQ_STATE_10_N.getIndex());
				yhqInfoSonUpdate.getMap().put("yhq_state_opt", 10);
				yhqInfoSonUpdate.getMap().put("link_id", Integer.valueOf(link_id));
				this.yhqInfoSonDao.updateEntity(yhqInfoSonUpdate);
			}
		}
		return id;
	}

	public int removeYhqInfo(YhqInfo t) {
		return this.yhqInfoDao.deleteEntity(t);
	}

	public List<YhqInfo> getYhqInfoPaginatedList(YhqInfo t) {
		return this.yhqInfoDao.selectEntityPaginatedList(t);
	}

}
