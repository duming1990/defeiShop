package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.ShortMessage;

/**
 * @author Wu,Yang
 * @version 2018-07-02 10:24
 */
public interface ShortMessageService {

	Integer createShortMessage(ShortMessage t);

	int modifyShortMessage(ShortMessage t);

	int removeShortMessage(ShortMessage t);

	ShortMessage getShortMessage(ShortMessage t);

	List<ShortMessage> getShortMessageList(ShortMessage t);

	Integer getShortMessageCount(ShortMessage t);

	List<ShortMessage> getShortMessagePaginatedList(ShortMessage t);

}