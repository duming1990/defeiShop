package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.ShortMessageReceiver;

/**
 * @author Wu,Yang
 * @version 2018-07-02 10:38
 */
public interface ShortMessageReceiverService {

	Integer createShortMessageReceiver(ShortMessageReceiver t);

	int modifyShortMessageReceiver(ShortMessageReceiver t);

	int removeShortMessageReceiver(ShortMessageReceiver t);

	ShortMessageReceiver getShortMessageReceiver(ShortMessageReceiver t);

	List<ShortMessageReceiver> getShortMessageReceiverList(ShortMessageReceiver t);

	Integer getShortMessageReceiverCount(ShortMessageReceiver t);

	List<ShortMessageReceiver> getShortMessageReceiverPaginatedList(ShortMessageReceiver t);

}