package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.MServiceBaseLink;

/**
 * @author Wu,Yang
 * @version 2018-06-13 14:06
 */
public interface MServiceBaseLinkService {

	Integer createMServiceBaseLink(MServiceBaseLink t);

	int modifyMServiceBaseLink(MServiceBaseLink t);

	int removeMServiceBaseLink(MServiceBaseLink t);

	MServiceBaseLink getMServiceBaseLink(MServiceBaseLink t);

	List<MServiceBaseLink> getMServiceBaseLinkList(MServiceBaseLink t);

	Integer getMServiceBaseLinkCount(MServiceBaseLink t);

	List<MServiceBaseLink> getMServiceBaseLinkPaginatedList(MServiceBaseLink t);

}