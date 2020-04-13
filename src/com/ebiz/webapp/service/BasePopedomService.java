package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.BasePopedom;

/**
 * @author Wu,Yang
 * @version 2012-02-14 10:56
 */
public interface BasePopedomService {

	Integer createBasePopedom(BasePopedom t);

	int modifyBasePopedom(BasePopedom t);

	int removeBasePopedom(BasePopedom t);

	BasePopedom getBasePopedom(BasePopedom t);

	List<BasePopedom> getBasePopedomList(BasePopedom t);

	Integer getBasePopedomCount(BasePopedom t);

	List<BasePopedom> getBasePopedomPaginatedList(BasePopedom t);

}