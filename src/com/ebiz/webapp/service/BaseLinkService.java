package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.BaseLink;

/**
 * @author Wu,Yang
 * @version 2013-05-27 14:06
 */
public interface BaseLinkService {

	Integer createBaseLink(BaseLink t);

	void createBaseLinkByList(List<BaseLink> baselinklist);

	int modifyBaseLink(BaseLink t);

	int removeBaseLink(BaseLink t);

	BaseLink getBaseLink(BaseLink t);

	List<BaseLink> getBaseLinkList(BaseLink t);

	Integer getBaseLinkCount(BaseLink t);

	List<BaseLink> getBaseLinkPaginatedList(BaseLink t);

}