package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.BaseBrandInfo;

/**
 * @author Wu,Yang
 * @version 2012-05-18 14:18
 */
public interface BaseBrandInfoService {

	Integer createBaseBrandInfo(BaseBrandInfo t);

	int modifyBaseBrandInfo(BaseBrandInfo t);

	int removeBaseBrandInfo(BaseBrandInfo t);

	BaseBrandInfo getBaseBrandInfo(BaseBrandInfo t);

	List<BaseBrandInfo> getBaseBrandInfoList(BaseBrandInfo t);

	Integer getBaseBrandInfoCount(BaseBrandInfo t);

	List<BaseBrandInfo> getBaseBrandInfoPaginatedList(BaseBrandInfo t);

}