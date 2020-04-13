package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.ShortMessageReceiverDao;
import com.ebiz.webapp.domain.ShortMessageReceiver;
import com.ebiz.webapp.service.ShortMessageReceiverService;

/**
 * @author Wu,Yang
 * @version 2018-07-02 10:38
 */
@Service
public class ShortMessageReceiverServiceImpl implements ShortMessageReceiverService {

	@Resource
	private ShortMessageReceiverDao shortMessageReceiverDao;
	

	public Integer createShortMessageReceiver(ShortMessageReceiver t) {
		return this.shortMessageReceiverDao.insertEntity(t);
	}

	public ShortMessageReceiver getShortMessageReceiver(ShortMessageReceiver t) {
		return this.shortMessageReceiverDao.selectEntity(t);
	}

	public Integer getShortMessageReceiverCount(ShortMessageReceiver t) {
		return this.shortMessageReceiverDao.selectEntityCount(t);
	}

	public List<ShortMessageReceiver> getShortMessageReceiverList(ShortMessageReceiver t) {
		return this.shortMessageReceiverDao.selectEntityList(t);
	}

	public int modifyShortMessageReceiver(ShortMessageReceiver t) {
		return this.shortMessageReceiverDao.updateEntity(t);
	}

	public int removeShortMessageReceiver(ShortMessageReceiver t) {
		return this.shortMessageReceiverDao.deleteEntity(t);
	}

	public List<ShortMessageReceiver> getShortMessageReceiverPaginatedList(ShortMessageReceiver t) {
		return this.shortMessageReceiverDao.selectEntityPaginatedList(t);
	}

}
