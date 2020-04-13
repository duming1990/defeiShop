package com.ebiz.webapp.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.MServiceBaseLinkDao;
import com.ebiz.webapp.domain.MServiceBaseLink;
import com.ebiz.webapp.service.MServiceBaseLinkService;

/**
 * @author Wu,Yang
 * @version 2018-06-13 14:06
 */
@Service
public class MServiceBaseLinkServiceImpl implements MServiceBaseLinkService {

	@Resource
	private MServiceBaseLinkDao mServiceBaseLinkDao;

	public Integer createMServiceBaseLink(MServiceBaseLink t) {
		return this.mServiceBaseLinkDao.insertEntity(t);
	}

	public MServiceBaseLink getMServiceBaseLink(MServiceBaseLink t) {
		return this.mServiceBaseLinkDao.selectEntity(t);
	}

	public Integer getMServiceBaseLinkCount(MServiceBaseLink t) {
		return this.mServiceBaseLinkDao.selectEntityCount(t);
	}

	public List<MServiceBaseLink> getMServiceBaseLinkList(MServiceBaseLink t) {
		return this.mServiceBaseLinkDao.selectEntityList(t);
	}

	public int modifyMServiceBaseLink(MServiceBaseLink t) {
		int count = this.mServiceBaseLinkDao.updateEntity(t);
		if (null != t.getMap().get("del_id")) {
			int del_id = (Integer) t.getMap().get("del_id");
			if (count > 0) {
				MServiceBaseLink link = new MServiceBaseLink();
				link.setUpdate_time(new Date());
				link.setUpdate_uid(new Integer(del_id));
				link.setDel_time(new Date());
				link.setDel_uid(new Integer(del_id));
				link.setIs_del(1);
				if (null != t.getMap().get("pks")) {
					link.getMap().put("par_ids", t.getMap().get("pks"));
				} else {
					link.getMap().put("par_id", t.getId());
				}
				this.mServiceBaseLinkDao.updateEntity(link);
			}
		}
		return count;
	}

	public int removeMServiceBaseLink(MServiceBaseLink t) {
		return this.mServiceBaseLinkDao.deleteEntity(t);
	}

	public List<MServiceBaseLink> getMServiceBaseLinkPaginatedList(MServiceBaseLink t) {
		return this.mServiceBaseLinkDao.selectEntityPaginatedList(t);
	}

}
