package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.MsgReceiverDao;
import com.ebiz.webapp.domain.MsgReceiver;
import com.ebiz.webapp.service.MsgReceiverService;

/**
 * @author Wu,Yang
 * @version 2015-03-13 12:59
 */
@Service
public class MsgReceiverServiceImpl implements MsgReceiverService {

	@Resource
	private MsgReceiverDao msgReceiverDao;

	@Override
	public Integer createMsgReceiver(MsgReceiver t) {
		return this.msgReceiverDao.insertEntity(t);
	}

	@Override
	public MsgReceiver getMsgReceiver(MsgReceiver t) {
		return this.msgReceiverDao.selectEntity(t);
	}

	@Override
	public Integer getMsgReceiverCount(MsgReceiver t) {
		return this.msgReceiverDao.selectEntityCount(t);
	}

	@Override
	public List<MsgReceiver> getMsgReceiverList(MsgReceiver t) {
		return this.msgReceiverDao.selectEntityList(t);
	}

	@Override
	public int modifyMsgReceiver(MsgReceiver t) {
		int row = this.msgReceiverDao.updateEntity(t);
		System.out.println("==row:" + row);
		return row;
	}

	@Override
	public int removeMsgReceiver(MsgReceiver t) {
		return this.msgReceiverDao.deleteEntity(t);
	}

	@Override
	public List<MsgReceiver> getMsgReceiverPaginatedList(MsgReceiver t) {
		return this.msgReceiverDao.selectEntityPaginatedList(t);
	}

}
