package com.ebiz.webapp.dao.ibatis;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.ServiceCenterInfoDao;
import com.ebiz.webapp.domain.ServiceCenterInfo;

/**
 * @version 2015-12-06 14:47
 */
@Service
public class ServiceCenterInfoDaoSqlMapImpl extends EntityDaoSqlMapImpl<ServiceCenterInfo> implements
		ServiceCenterInfoDao {

	public List<HashMap> selectServiceCenterInfoCountByPIndex(ServiceCenterInfo t) {
		return super.getSqlMapClientTemplate().queryForList("selectServiceCenterInfoCountByPIndex", t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceCenterInfo> selectServiceCenterInfoPaiMingList(ServiceCenterInfo t) {
		return super.getSqlMapClientTemplate().queryForList("selectServiceCenterInfoPaiMingList", t);
	}

	public List<ServiceCenterInfo> selectMyServiceCenterInfoList(ServiceCenterInfo t) {
		return super.getSqlMapClientTemplate().queryForList("selectMyServiceCenterInfoList", t);
	}

}
