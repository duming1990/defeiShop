package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.EntpBaseLink;

/**
 * @author Wu,Yang
 * @version 2018-05-03 09:37
 */
public interface EntpBaseLinkService {

	Integer createEntpBaseLink(EntpBaseLink t);

	int modifyEntpBaseLink(EntpBaseLink t);

	int removeEntpBaseLink(EntpBaseLink t);

	EntpBaseLink getEntpBaseLink(EntpBaseLink t);

	List<EntpBaseLink> getEntpBaseLinkList(EntpBaseLink t);

	Integer getEntpBaseLinkCount(EntpBaseLink t);

	List<EntpBaseLink> getEntpBaseLinkPaginatedList(EntpBaseLink t);

}