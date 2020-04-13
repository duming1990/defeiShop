package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.Msg;

/**
 * @author Wu,Yang
 * @version 2015-03-13 12:59
 */
public interface MsgService {

	Integer createMsg(Msg t);

	int modifyMsg(Msg t);

	int removeMsg(Msg t);

	Msg getMsg(Msg t);

	List<Msg> getMsgList(Msg t);

	Integer getMsgCount(Msg t);

	List<Msg> getMsgPaginatedList(Msg t);

}