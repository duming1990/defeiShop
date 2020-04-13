package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.BaseAttribute;

/**
 * @author Wu,Yang
 * @version 2012-05-30 08:53
 */
public interface BaseAttributeService {

	Integer createBaseAttribute(BaseAttribute t);

	int modifyBaseAttribute(BaseAttribute t);

	int removeBaseAttribute(BaseAttribute t);

	BaseAttribute getBaseAttribute(BaseAttribute t);

	List<BaseAttribute> getBaseAttributeList(BaseAttribute t);

	Integer getBaseAttributeCount(BaseAttribute t);

	List<BaseAttribute> getBaseAttributePaginatedList(BaseAttribute t);

}