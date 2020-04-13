package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.BaseProvince;

/**
 * @author Wu,Yang
 * @version 2011-04-20 02:13:04
 */
public interface BaseProvinceService {

	Integer createBaseProvince(BaseProvince t);

	int modifyBaseProvince(BaseProvince t);

	int removeBaseProvince(BaseProvince t);

	BaseProvince getBaseProvince(BaseProvince t);

	List<BaseProvince> getBaseProvinceList(BaseProvince t);

	Integer getBaseProvinceCount(BaseProvince t);

	List<BaseProvince> getBaseProvincePaginatedList(BaseProvince t);

	List<BaseProvince> getBaseProvinceResultForGroupByPalpha(BaseProvince t);

	int modifyBaseProvinceForFuwuCount(BaseProvince t);

	List<BaseProvince> getBaseProvinceHasWeiHuList(BaseProvince t);
}
