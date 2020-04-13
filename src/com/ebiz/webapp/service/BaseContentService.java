package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.BaseContent;

/**
 * @author Wu,Yang
 * @version 2012-05-30 08:59
 */
public interface BaseContentService {

	Integer createBaseContent(BaseContent t);

	int modifyBaseContent(BaseContent t);

	int removeBaseContent(BaseContent t);

	BaseContent getBaseContent(BaseContent t);

	List<BaseContent> getBaseContentList(BaseContent t);

	Integer getBaseContentCount(BaseContent t);

	List<BaseContent> getBaseContentPaginatedList(BaseContent t);

}