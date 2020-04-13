package com.ebiz.webapp.service;

import java.util.HashMap;
import java.util.List;

import com.ebiz.webapp.domain.ServiceCenterInfo;

/**
 * @version 2015-12-06 14:47
 */
public interface ServiceCenterInfoService {

	Integer createServiceCenterInfo(ServiceCenterInfo t);

	Integer createServiceCenterInfo(ServiceCenterInfo t, String[] file);

	int modifyServiceCenterInfo(ServiceCenterInfo t);

	int removeServiceCenterInfo(ServiceCenterInfo t);

	ServiceCenterInfo getServiceCenterInfo(ServiceCenterInfo t);

	List<ServiceCenterInfo> getServiceCenterInfoList(ServiceCenterInfo t);

	Integer getServiceCenterInfoCount(ServiceCenterInfo t);

	List<ServiceCenterInfo> getServiceCenterInfoPaginatedList(ServiceCenterInfo t);

	List<ServiceCenterInfo> getServiceCenterInfoPaiMingList(ServiceCenterInfo t);

	List<ServiceCenterInfo> getMyServiceCenterInfoList(ServiceCenterInfo t);

	List<HashMap> getServiceCenterInfoCountByPIndex(ServiceCenterInfo t);

}