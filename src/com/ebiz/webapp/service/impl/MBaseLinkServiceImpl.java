package com.ebiz.webapp.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.MBaseLinkDao;
import com.ebiz.webapp.domain.MBaseLink;
import com.ebiz.webapp.service.MBaseLinkService;

/**
 * @version 2014-10-30 10:49
 */
@Service
public class MBaseLinkServiceImpl implements MBaseLinkService {

	@Resource
	private MBaseLinkDao mBaseLinkDao;

	public Integer createMBaseLink(MBaseLink t) {
		return this.mBaseLinkDao.insertEntity(t);
	}

	public void createMBaseLinkByList(List<MBaseLink> baselinklist) {
		for (MBaseLink t : baselinklist) {
			createMBaseLink(t);
		}
	}

	public MBaseLink getMBaseLink(MBaseLink t) {
		return this.mBaseLinkDao.selectEntity(t);
	}

	public Integer getMBaseLinkCount(MBaseLink t) {
		return this.mBaseLinkDao.selectEntityCount(t);
	}

	public List<MBaseLink> getMBaseLinkList(MBaseLink t) {
		return this.mBaseLinkDao.selectEntityList(t);
	}

	public int modifyMBaseLink(MBaseLink t) {
		int count = this.mBaseLinkDao.updateEntity(t);
		if (null != t.getMap().get("del_id")) {
			int del_id = (Integer) t.getMap().get("del_id");
			if (count > 0) {
				MBaseLink link = new MBaseLink();
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
				this.mBaseLinkDao.updateEntity(link);
			}
		}
		return count;
	}

	public int removeMBaseLink(MBaseLink t) {
		return this.mBaseLinkDao.deleteEntity(t);
	}

	public List<MBaseLink> getMBaseLinkPaginatedList(MBaseLink t) {
		return this.mBaseLinkDao.selectEntityPaginatedList(t);
	}

}
