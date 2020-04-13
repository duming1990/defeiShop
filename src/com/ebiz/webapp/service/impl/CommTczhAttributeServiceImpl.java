package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.CommTczhAttributeDao;
import com.ebiz.webapp.domain.CommTczhAttribute;
import com.ebiz.webapp.service.CommTczhAttributeService;

/**
 * @author Wu,Yang
 * @version 2014-05-21 10:41
 */
@Service
public class CommTczhAttributeServiceImpl implements CommTczhAttributeService {

	@Resource
	private CommTczhAttributeDao commTczhAttributeDao;

	public Integer createCommTczhAttribute(CommTczhAttribute t) {
		return this.commTczhAttributeDao.insertEntity(t);
	}

	public CommTczhAttribute getCommTczhAttribute(CommTczhAttribute t) {
		return this.commTczhAttributeDao.selectEntity(t);
	}

	public Integer getCommTczhAttributeCount(CommTczhAttribute t) {
		return this.commTczhAttributeDao.selectEntityCount(t);
	}

	public List<CommTczhAttribute> getCommTczhAttributeList(CommTczhAttribute t) {
		return this.commTczhAttributeDao.selectEntityList(t);
	}

	public int modifyCommTczhAttribute(CommTczhAttribute t) {
		return this.commTczhAttributeDao.updateEntity(t);
	}

	public int removeCommTczhAttribute(CommTczhAttribute t) {
		return this.commTczhAttributeDao.deleteEntity(t);
	}

	public List<CommTczhAttribute> getCommTczhAttributePaginatedList(CommTczhAttribute t) {
		return this.commTczhAttributeDao.selectEntityPaginatedList(t);
	}

	public CommTczhAttribute getCommTczhAttributeForGetCommTczhId(CommTczhAttribute t) {
		return this.commTczhAttributeDao.selectCommTczhAttributeForGetCommTczhId(t);
	}
}
