package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.BaseLinkDao;
import com.ebiz.webapp.domain.BaseLink;
import com.ebiz.webapp.service.BaseLinkService;

/**
 * @author Wu,Yang
 * @version 2013-05-27 14:06
 */
@Service
public class BaseLinkServiceImpl implements BaseLinkService {

	@Resource
	private BaseLinkDao baseLinkDao;

	public Integer createBaseLink(BaseLink t) {
		return this.baseLinkDao.insertEntity(t);
	}

	public void createBaseLinkByList(List<BaseLink> baselinklist) {
		for (BaseLink t : baselinklist) {
			createBaseLink(t);
		}
	}

	public BaseLink getBaseLink(BaseLink t) {
		return this.baseLinkDao.selectEntity(t);
	}

	public Integer getBaseLinkCount(BaseLink t) {
		return this.baseLinkDao.selectEntityCount(t);
	}

	public List<BaseLink> getBaseLinkList(BaseLink t) {
		return this.baseLinkDao.selectEntityList(t);
	}

	public int modifyBaseLink(BaseLink t) {
		return this.baseLinkDao.updateEntity(t);
	}

	public int removeBaseLink(BaseLink t) {
		return this.baseLinkDao.deleteEntity(t);
	}

	public List<BaseLink> getBaseLinkPaginatedList(BaseLink t) {
		return this.baseLinkDao.selectEntityPaginatedList(t);
	}

}
