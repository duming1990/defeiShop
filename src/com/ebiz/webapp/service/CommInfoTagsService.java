package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.CommInfoTags;

/**
 * @author Wu,Yang
 * @version 2017-04-21 14:39
 */
public interface CommInfoTagsService {

	Integer createCommInfoTags(CommInfoTags t);

	int modifyCommInfoTags(CommInfoTags t);

	int removeCommInfoTags(CommInfoTags t);

	CommInfoTags getCommInfoTags(CommInfoTags t);

	List<CommInfoTags> getCommInfoTagsList(CommInfoTags t);

	Integer getCommInfoTagsCount(CommInfoTags t);

	List<CommInfoTags> getCommInfoTagsPaginatedList(CommInfoTags t);

	Integer getCommInfoTagsJiaZaiCount(CommInfoTags t);

	List<CommInfoTags> getCommInfoTagsJiaZaiPaginatedList(CommInfoTags t);

}