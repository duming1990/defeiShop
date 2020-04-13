package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.MBaseLink;

/**
 * @version 2014-10-30 10:49
 */
public interface MBaseLinkService {

	Integer createMBaseLink(MBaseLink t);

	void createMBaseLinkByList(List<MBaseLink> baselinklist);

	int modifyMBaseLink(MBaseLink t);

	int removeMBaseLink(MBaseLink t);

	MBaseLink getMBaseLink(MBaseLink t);

	List<MBaseLink> getMBaseLinkList(MBaseLink t);

	Integer getMBaseLinkCount(MBaseLink t);

	List<MBaseLink> getMBaseLinkPaginatedList(MBaseLink t);

}