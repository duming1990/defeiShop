package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.BaseClass;

/**
 * @author Wu,Yang
 * @version 2012-05-30 08:53
 */
public interface BaseClassService {

	Integer createBaseClass(BaseClass t);

	int modifyBaseClass(BaseClass t);

	int removeBaseClass(BaseClass t);

	BaseClass getBaseClass(BaseClass t);

	List<BaseClass> getBaseClassList(BaseClass t);

	Integer getBaseClassCount(BaseClass t);

	List<BaseClass> getBaseClassPaginatedList(BaseClass t);

	BaseClass procedureUpdateClass(BaseClass t);

	List<BaseClass> proGetBaseClassParentList(BaseClass t);

	List<BaseClass> proGetBaseClassSonList(BaseClass t);

}