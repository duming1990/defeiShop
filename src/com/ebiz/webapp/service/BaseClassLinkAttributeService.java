package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.BaseClassLinkAttribute;

/**
 * @author Wu,Yang
 * @version 2012-05-30 08:53
 */
public interface BaseClassLinkAttributeService {

	Integer createBaseClassLinkAttribute(BaseClassLinkAttribute t);

	int modifyBaseClassLinkAttribute(BaseClassLinkAttribute t);

	int removeBaseClassLinkAttribute(BaseClassLinkAttribute t);

	BaseClassLinkAttribute getBaseClassLinkAttribute(BaseClassLinkAttribute t);

	List<BaseClassLinkAttribute> getBaseClassLinkAttributeList(BaseClassLinkAttribute t);

	Integer getBaseClassLinkAttributeCount(BaseClassLinkAttribute t);

	List<BaseClassLinkAttribute> getBaseClassLinkAttributePaginatedList(BaseClassLinkAttribute t);

}