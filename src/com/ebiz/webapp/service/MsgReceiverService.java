package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.MsgReceiver;

/**
 * @author Wu,Yang
 * @version 2015-03-13 12:59
 */
public interface MsgReceiverService {

	Integer createMsgReceiver(MsgReceiver t);

	int modifyMsgReceiver(MsgReceiver t);

	int removeMsgReceiver(MsgReceiver t);

	MsgReceiver getMsgReceiver(MsgReceiver t);

	List<MsgReceiver> getMsgReceiverList(MsgReceiver t);

	Integer getMsgReceiverCount(MsgReceiver t);

	List<MsgReceiver> getMsgReceiverPaginatedList(MsgReceiver t);

}