package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.MsgDao;
import com.ebiz.webapp.dao.MsgReceiverDao;
import com.ebiz.webapp.dao.UserInfoDao;
import com.ebiz.webapp.domain.Msg;
import com.ebiz.webapp.domain.MsgReceiver;
import com.ebiz.webapp.service.MsgService;

/**
 * @author Wu,Yang
 * @version 2015-03-13 12:59
 */
@Service
public class MsgServiceImpl extends BaseImpl implements MsgService {

	@Resource
	private MsgDao msgDao;

	@Resource
	private MsgReceiverDao msgReceiverDao;

	@Resource
	UserInfoDao userInfoDao;

	@Override
	public Integer createMsg(Msg t) {

		int id = msgDao.insertEntity(t);

		List<MsgReceiver> msgReceiverList = t.getMsgReceiverList();
		if ((null != msgReceiverList) && (msgReceiverList.size() > 0)) {
			for (MsgReceiver msgReceiver : msgReceiverList) {
				msgReceiver.setMsg_id(id);
				msgReceiverDao.insertEntity(msgReceiver);
			}
		}
		return id;

	}

	@Override
	public Msg getMsg(Msg t) {
		return this.msgDao.selectEntity(t);
	}

	@Override
	public Integer getMsgCount(Msg t) {
		return this.msgDao.selectEntityCount(t);
	}

	@Override
	public List<Msg> getMsgList(Msg t) {
		return this.msgDao.selectEntityList(t);
	}

	@Override
	public int modifyMsg(Msg t) {
		int rows = msgDao.updateEntity(t);

		int id = t.getId();
		List<MsgReceiver> msgReceiverList = t.getMsgReceiverList();
		if ((null != msgReceiverList) && (msgReceiverList.size() > 0)) {
			MsgReceiver mr = new MsgReceiver();
			mr.setMsg_id(t.getId());
			msgReceiverDao.deleteEntity(mr);
			for (MsgReceiver msgReceiver : msgReceiverList) {
				msgReceiver.setMsg_id(id);
				msgReceiverDao.insertEntity(msgReceiver);
			}
		}
		return rows;
	}

	@Override
	public int removeMsg(Msg t) {
		return this.msgDao.deleteEntity(t);
	}

	@Override
	public List<Msg> getMsgPaginatedList(Msg t) {
		return this.msgDao.selectEntityPaginatedList(t);
	}

}
