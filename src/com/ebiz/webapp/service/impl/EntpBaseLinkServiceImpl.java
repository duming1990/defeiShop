package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.EntpBaseLinkDao;
import com.ebiz.webapp.domain.EntpBaseLink;
import com.ebiz.webapp.service.EntpBaseLinkService;

/**
 * @author Wu,Yang
 * @version 2018-05-03 09:37
 */
@Service
public class EntpBaseLinkServiceImpl implements EntpBaseLinkService {

	@Resource
	private EntpBaseLinkDao entpBaseLinkDao;
	

	public Integer createEntpBaseLink(EntpBaseLink t) {
		return this.entpBaseLinkDao.insertEntity(t);
	}

	public EntpBaseLink getEntpBaseLink(EntpBaseLink t) {
		return this.entpBaseLinkDao.selectEntity(t);
	}

	public Integer getEntpBaseLinkCount(EntpBaseLink t) {
		return this.entpBaseLinkDao.selectEntityCount(t);
	}

	public List<EntpBaseLink> getEntpBaseLinkList(EntpBaseLink t) {
		return this.entpBaseLinkDao.selectEntityList(t);
	}

	public int modifyEntpBaseLink(EntpBaseLink t) {
		return this.entpBaseLinkDao.updateEntity(t);
	}

	public int removeEntpBaseLink(EntpBaseLink t) {
		return this.entpBaseLinkDao.deleteEntity(t);
	}

	public List<EntpBaseLink> getEntpBaseLinkPaginatedList(EntpBaseLink t) {
		return this.entpBaseLinkDao.selectEntityPaginatedList(t);
	}

}
