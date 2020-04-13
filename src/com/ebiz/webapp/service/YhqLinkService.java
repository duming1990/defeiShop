package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.YhqLink;

/**
 * @author Wu,Yang
 * @version 2018-05-22 14:59
 */
public interface YhqLinkService {

	Integer createYhqLink(YhqLink t);

	int modifyYhqLink(YhqLink t);

	int removeYhqLink(YhqLink t);

	YhqLink getYhqLink(YhqLink t);

	List<YhqLink> getYhqLinkList(YhqLink t);

	Integer getYhqLinkCount(YhqLink t);

	List<YhqLink> getYhqLinkPaginatedList(YhqLink t);

}