package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.YhqLinkDao;
import com.ebiz.webapp.domain.YhqLink;
import com.ebiz.webapp.service.YhqLinkService;

/**
 * @author Wu,Yang
 * @version 2018-05-22 14:59
 */
@Service
public class YhqLinkServiceImpl implements YhqLinkService {

	@Resource
	private YhqLinkDao yhqLinkDao;

	public Integer createYhqLink(YhqLink t) {
		return this.yhqLinkDao.insertEntity(t);
	}

	public YhqLink getYhqLink(YhqLink t) {
		return this.yhqLinkDao.selectEntity(t);
	}

	public Integer getYhqLinkCount(YhqLink t) {
		return this.yhqLinkDao.selectEntityCount(t);
	}

	public List<YhqLink> getYhqLinkList(YhqLink t) {
		return this.yhqLinkDao.selectEntityList(t);
	}

	public int modifyYhqLink(YhqLink t) {
		return this.yhqLinkDao.updateEntity(t);
	}

	public int removeYhqLink(YhqLink t) {
		int count = this.yhqLinkDao.deleteEntity(t);
		String[] comm_ids = (String[]) t.getMap().get("comm_ids");
		String id = String.valueOf(t.getMap().get("id"));
		if (count > 0) {
			if (null != comm_ids) {
				for (String ids : comm_ids) {
					YhqLink coupons = new YhqLink();
					coupons.setComm_id(Integer.valueOf(ids));
					coupons.setYhq_id(Integer.valueOf(id));
					this.yhqLinkDao.insertEntity(coupons);
				}
			}
		}
		return count;
	}

	public List<YhqLink> getYhqLinkPaginatedList(YhqLink t) {
		return this.yhqLinkDao.selectEntityPaginatedList(t);
	}

}
