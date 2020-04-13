package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.BaseComminfoTags;

/**
 * @author Wu,Yang
 * @version 2017-04-21 14:39
 */
public interface BaseComminfoTagsService {

	Integer createBaseComminfoTags(BaseComminfoTags t);

	int modifyBaseComminfoTags(BaseComminfoTags t);

	int removeBaseComminfoTags(BaseComminfoTags t);

	BaseComminfoTags getBaseComminfoTags(BaseComminfoTags t);

	List<BaseComminfoTags> getBaseComminfoTagsList(BaseComminfoTags t);

	Integer getBaseComminfoTagsCount(BaseComminfoTags t);

	List<BaseComminfoTags> getBaseComminfoTagsPaginatedList(BaseComminfoTags t);
	
	List<BaseComminfoTags> getBaseComminfoTagsPaginatedListByPIndex(BaseComminfoTags t);

}