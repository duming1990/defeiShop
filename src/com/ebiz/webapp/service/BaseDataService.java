package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.BaseData;

/**
 * @author Wu,Yang
 * @version 2011-04-20 02:13:04
 */
public interface BaseDataService {

	Integer createBaseData(BaseData t);

	int modifyBaseData(BaseData t);

	int removeBaseData(BaseData t);

	BaseData getBaseData(BaseData t);

	List<BaseData> getBaseDataList(BaseData t);

	Integer getBaseDataCount(BaseData t);

	List<BaseData> getBaseDataPaginatedList(BaseData t);

}
