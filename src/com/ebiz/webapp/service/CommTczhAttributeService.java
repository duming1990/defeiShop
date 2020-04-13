package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.CommTczhAttribute;

/**
 * @author Wu,Yang
 * @version 2014-05-21 10:41
 */
public interface CommTczhAttributeService {

	Integer createCommTczhAttribute(CommTczhAttribute t);

	int modifyCommTczhAttribute(CommTczhAttribute t);

	int removeCommTczhAttribute(CommTczhAttribute t);

	CommTczhAttribute getCommTczhAttribute(CommTczhAttribute t);

	List<CommTczhAttribute> getCommTczhAttributeList(CommTczhAttribute t);

	Integer getCommTczhAttributeCount(CommTczhAttribute t);

	List<CommTczhAttribute> getCommTczhAttributePaginatedList(CommTczhAttribute t);

	public CommTczhAttribute getCommTczhAttributeForGetCommTczhId(CommTczhAttribute t);

}