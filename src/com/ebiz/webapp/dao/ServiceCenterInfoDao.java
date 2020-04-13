package com.ebiz.webapp.dao;

import java.util.HashMap;
import java.util.List;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.ServiceCenterInfo;

/**
 * @version 2015-12-06 14:47
 */
public interface ServiceCenterInfoDao extends EntityDao<ServiceCenterInfo> {

	List<HashMap> selectServiceCenterInfoCountByPIndex(ServiceCenterInfo t);

	List<ServiceCenterInfo> selectServiceCenterInfoPaiMingList(ServiceCenterInfo t);

	List<ServiceCenterInfo> selectMyServiceCenterInfoList(ServiceCenterInfo t);

}
